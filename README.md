# Google Two-Factor Authenticator CLI

## Install / Configuration

Rename the gauth.cfg-sample to gauth.cfg

Added your account and secret information 

```
# GAuth_CLI Configuration File
#
# Format: account_name:secret
#
john.doe@gmail.com:1234567890123456
some.other.site:0987654321123456
```


## Running

% java -jar gauth_cli.jar


### Usage
```
Usage: <main class> [options]
  Options:
    -a, --account
       Use account #
       Default: -1
    -c, --config
       Config file location
       Default: ./gauth.cfg
    -h, --help
       Show usage
       Default: false
    -i, --interactive
       Display timer and codes
       Default: false
    -l, --list
       List accounts
       Default: false
    -s, --secret
       Secret
```

## Notes

**How to get the Google Account Auth secret**

The totp secret embedded in the QR code. Use a separate QR reader to scan the QR code to view the link. It will look like "otpauth://totp/mail@gmail.com?secret=1234567890123456"

## Credits / Licenses
 * James Cuff and Michele Clamp 2011/2012
 * https://github.com/mclamp/JAuth
 * http://blog.jcuff.net/2011/02/cli-java-based-google-authenticator.html
 * http://blog.jcuff.net/2011/09/beautiful-two-factor-desktop-client.html
 * JCommander is released under the Apache 2.0 license. 