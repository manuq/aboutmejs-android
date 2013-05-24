define(function (require) {
    var activity = require("sugar-html-activity/activity");
    var icon = require("sugar-html-graphics/icon");
    var xocolor = require("activity/xocolor");

    // Manipulate the DOM only when it is ready.
    require(['domReady!'], function (doc) {

        // Initialize the activity.
        activity.setup();

        var currentColors;

        // Colorize the activity icon.
        var activityButton = document.getElementById("activity-button");
        var iconCurrent = document.getElementById("icon-current");
        activity.getXOColor(function (colors) {
            icon.colorize(activityButton, colors);
            icon.colorize(iconCurrent, colors);
            currentColors = colors;
        });

        // Make the activity stop with the stop button.
        var stopButton = document.getElementById("stop-button");
        stopButton.onclick = function () {
            activity.close();
        };

        // Store the color of each button.
        var colors = {};

        var colorButtons = document.querySelectorAll("#color-icons button");
        for (i = 0; i < colorButtons.length; i++) {
            var button = colorButtons[i];
            button.addEventListener("click", clickHandler);
            var randomColors = xocolor.colors[Math.floor(Math.random() *
                xocolor.colors.length)];
            icon.colorize(button, randomColors);
            colors[button.id] = randomColors;
        }

        function clickHandler(evt) {
            var button = evt.target;

            // colorize iconCurrent with colors of the clicked button
            icon.colorize(iconCurrent, colors[button.id]);
            currentColors = colors[button.id];

            // colorize button with new random colors
            var randomColors = xocolor.colors[Math.floor(Math.random() *
                xocolor.colors.length)];
            icon.colorize(button, randomColors);
            colors[button.id] = randomColors;
        }

    });

});
