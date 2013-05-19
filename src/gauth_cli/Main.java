/**
 * Google Two-Factor Authenticator CLI
 * 
 * The MIT License (MIT)
 * Copyright (c) 2013 Elijah Cornell
 * 
 */

package gauth_cli;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public class Main {

	Timer timer;

	@Parameter(names = { "-c", "--config" }, description = "Config file location")
	public String configFile = "./gauth.cfg";

	@Parameter(names = { "-s", "--secret" }, description = "Secret")
	public String cSecret;

	@Parameter(names = { "-h", "--help" }, description = "Show usage")
	public boolean cUsage = false;

	@Parameter(names = { "-l", "--list" }, description = "List accounts")
	public boolean cList = false;

	@Parameter(names = { "-a", "--account" }, description = "Use account #")
	public Integer cAccount = -1;

	@Parameter(names = { "-i", "--interactive" }, description = "Display timer and codes")
	public boolean cInteractive = false;

	//

	public static void main(String[] args) {

		Main main = new Main();
		main.run(args);

	}

	public void run(String[] args) {

		try {

			JCommander jcommander = new JCommander(this, args);

			if (cUsage) {
				jcommander.usage();
				return;
			}

			List<String[]> auths = new ArrayList<String[]>();
			Scanner scanner = new Scanner(new FileInputStream(configFile),
					"UTF-8");
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (!line.startsWith("#") || !line.startsWith(" ")) {
					String[] v = line.split(":");
					auths.add(v);
				}
			}

			// List Accounts

			if (cList) {
				for (int i = 0; i < auths.size(); i++) {
					System.out.println(i + " : " + auths.get(i)[0]);
				}
				return;
			}

			//

			int selection = 0;

			if (cAccount != -1) {
				selection = cAccount;
			} else {

				if (auths.size() > 1) {
					for (int i = 0; i < auths.size(); i++) {
						System.out.println(i + " : " + auths.get(i)[0]);
					}

					System.out.print("\nSelect account: -> ");

					InputStreamReader sysIn = new InputStreamReader(System.in);
					BufferedReader input = new BufferedReader(sysIn);
					String userInput = input.readLine();

					selection = Integer.parseInt(userInput.trim());
					
					System.out.println("");
				}
			}
			
			String secret = cSecret != null ? cSecret : auths.get(selection)[1];
			
			if (cInteractive) {

				System.out.println("Generating codes for: "
						+ auths.get(selection)[0]);

				System.out.println(":----------------------------:--------:");
				System.out.println(":       Code Wait Time       :  Code  :");
				System.out.println(":----------------------------:--------:");
				Main main = new Main();

				main.reminder(auths.get(selection)[1]);

			} else {

				String newout = Main.computePin(secret, null);
				
				System.out.println(newout);
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void reminder(String secret) {
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimedPin(secret), 0, 1 * 1000);
	}

	int count = 1;

	class TimedPin extends TimerTask {
		private String secret;

		public TimedPin(String secret) {
			this.secret = secret;
		}

		String previouscode = "";

		public void run() {
			String newout = Main.computePin(secret, null);
			if (previouscode.equals(newout)) {
				System.out.print(".");
			} else {
				if (count <= 30) {
					for (int i = count + 1; i <= 30; i++) {
						System.out.print("+");
					}
				}
				System.out.println(": " + newout + " :");
				count = 0;
			}
			previouscode = newout;
			count++;
		}
	}

	public static String computePin(String secret, Long counter) {
		if (secret == null || secret.length() == 0) {
			return "Null or empty secret";
		}
		try {
			final byte[] keyBytes = Base32String.decode(secret);
			Mac mac = Mac.getInstance("HMACSHA1");
			mac.init(new SecretKeySpec(keyBytes, ""));
			PasscodeGenerator pcg = new PasscodeGenerator(mac);
			return pcg.generateTimeoutCode();
		} catch (GeneralSecurityException e) {
			return "General security exception";
		} catch (Base32String.DecodingException e) {
			return "Decoding exception";
		}
	}
}
