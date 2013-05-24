define(function (require) {
    var shortcut = require("sugar-html-activity/shortcut");

    var activity = {};

    var bus = require("sugar-html-bus/bus");

    activity.setup = function () {
        shortcut.add("Ctrl", "Q", this.close);
    };

    activity.getXOColor = function(callback) {
        if (AndroidActivity) {
            var colorString = AndroidActivity.getXOColor();
            callback(colorString.split(','));
            return;
        }
        try {
            bus.sendMessage("activity.get_xo_color", [], callback);
        }
        // Fallback: use sample colors
        catch (error) {
            var sampleColors = ["#00A0FF", "#8BFF7A"];
            callback(sampleColors);
        }
    };

    activity.setXOColor = function(colors) {
        if (AndroidActivity) {
            var colorString = colors.toString();
            AndroidActivity.setXOColor(colorString);
        }
    }

    activity.close = function(callback) {
        if (AndroidActivity) {
            AndroidActivity.stop();
            return;
        }
        try {
            bus.sendMessage("activity.close", [], callback);
        }
        // Fallback: display a message in the console
        catch (error) {
            console.log("activity.close called");
        }
    };

    return activity;
});
