# cordova plugin ping

# Contents
* [General](#general)
* [Install](#install)
* [Functions](#functions)
* [Usage](#usage)

# General
If you want to perform ICMP operation such as echo-request/reply using cordova, you will find yourself easily stuck.
If you get your index.html url you will find that cordova does not use ```http (http://)``` protocol but ```file:/```
This little plugin provides two functions to perform simple ICMP operations.

# Install
```
cordova plugin add https://github.com/Spartan0nix/cordova-plugin-ping.git
```

# Functions

## Function 'ipReachable'
``` cordova.plugins.Ping.ipReachable(ipAddress, onsuccess, onerror);  ```
#### Check if host is reachable

   * **ipAddress** => Destination ip address 
      * The ip address need to be a public ip or an ip address on the same private network as yours.
   * **onsuccess**
      * Return a string 
        * ``` Host is reachable ```
        * ``` Host is not reachable ```
   * **onerror**
   
   
## Function 'ping'
``` cordova.plugins.Ping.ping(ipAddress, onsuccess, onerror);  ```
#### Perform a complete ``` ping <ipAddress> ```

   * **ipAddress** => Destination ip address 
      * The ip address need to be a public ip or an ip address on the same private network as yours.
   * **onsuccess**
      * Return the entire ping result in the same string.
      * Each line return is on a different ```<p></p>```.
   * **onerror**
      * Host not reachable, ping with 100% Packet Loss, etc... are not considered as an error *
       
# Usage
