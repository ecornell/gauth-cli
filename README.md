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

## License

The MIT License (MIT)

Copyright (c) 2013 Eljah Cornell

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.


## Credits 
 * James Cuff and Michele Clamp 2011/2012
 * https://github.com/mclamp/JAuth
 * http://blog.jcuff.net/2011/02/cli-java-based-google-authenticator.html
 * http://blog.jcuff.net/2011/09/beautiful-two-factor-desktop-client.html
 * JCommander is released under the Apache 2.0 license. 