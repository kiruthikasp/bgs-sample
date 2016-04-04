
var serviceName = 'com.red_folder.phonegap.plugin.backgroundservice.sample.MyService';


var factory = require('com.red_folder.phonegap.plugin.backgroundservice.BackgroundService');
module.exports = factory.create(serviceName);


var exec = cordova.require('cordova/exec');

/**
 * PushNotification constructor.
 *
 * @param {Object} options to initiate Push Notifications.
 * @return {PushNotification} instance that can be monitored and cancelled.
 */

var Service = function(options) {
    this._handlers = {
        'registration': [],
        'notification': [],
        'error': []
    };

    // require options parameter
    if (typeof options === 'undefined') {
        throw new Error('The options argument is required.');
    }

    // store the options to this object instance
    this.options = options;

    // triggered on registration and notification
    var that = this;
    var success = function(result) {
        if (result && typeof result.registrationId !== 'undefined') {
            that.emit('registration', result);
        } else if (result && result.additionalData && typeof result.additionalData.callback !== 'undefined') {
            var executeFunctionByName = function(functionName, context /*, args */) {
                var args = Array.prototype.slice.call(arguments, 2);
                var namespaces = functionName.split('.');
                var func = namespaces.pop();
                for (var i = 0; i < namespaces.length; i++) {
                    context = context[namespaces[i]];
                }
                return context[func].apply(context, args);
            };

            executeFunctionByName(result.additionalData.callback, window, result);
        } else if (result) {
            that.emit('notification', result);
        }
    };

    // triggered on error
    var fail = function(msg) {
        var e = (typeof msg === 'string') ? new Error(msg) : msg;
        that.emit('error', e);
    };

    // wait at least one process tick to allow event subscriptions
    setTimeout(function() {
        exec(success, fail, 'Service', 'init', []);
    }, 10);
};




module.exports = {


    init: function(options) {
        return new Service(options);
    },

    Service: Service
};
