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

public class Main {
	Timer timer;

	public static void main(String[] args) {

		try {

			List<String[]> auths = new ArrayList<String[]>();
			Scanner scanner = new Scanner(new FileInputStream("./gauth.cfg"),
					"UTF-8");
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (!line.startsWith("#") || !line.startsWith(" ")) {
					String[] v = line.split(":");
					auths.add(v);
				}
			}

			System.out.println("\nAuthenticator Started\n");

			int selection = 0;

			if (auths.size() > 1) {
				for (int i = 0; i < auths.size(); i++) {
					System.out.println(i + " : " + auths.get(i)[0]);
				}

				System.out.print("\nSelect account: -> ");

				InputStreamReader sysIn = new InputStreamReader(System.in);
				BufferedReader input = new BufferedReader(sysIn);
				String userInput = input.readLine();

				selection = Integer.parseInt(userInput);
			}

			System.out.println("\nGenerating codes for: "
					+ auths.get(selection)[0]);

			System.out.println(":----------------------------:--------:");
			System.out.println(":       Code Wait Time       :  Code  :");
			System.out.println(":----------------------------:--------:");
			Main main = new Main();

			main.reminder(auths.get(selection)[1]);

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
