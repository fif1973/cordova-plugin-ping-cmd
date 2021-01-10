var exec = require('cordova/exec');

var Ping = {
    ipReachable:  function (ipAddress, success, error) {
        exec(success, error, 'Ping', 'ipReachable', [ipAddress]);
    },
    ping: function (ipAddress, success, error) {
        exec(success, error, 'Ping', 'ping', [ipAddress]);
    }
}

module.exports = Ping;
