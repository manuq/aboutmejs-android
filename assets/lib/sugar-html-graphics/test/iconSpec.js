define(function (require) {
    var icon = require("sugar-html-graphics/icon");

    describe("icon", function () {
        var wasColorized;

        it("should change background more than once", function () {
            var elem = document.createElement('div');
            var iconUrl = "url(/base/test/sample-icon.svg)";
            var iconUrl2;

            function callback() {
                wasColorized = true;
            }

            runs(function () {
                wasColorized = false;
                elem.style.backgroundImage = iconUrl;
                icon.colorize(elem, ['#B20008', '#FF2B34'], callback);
            });

            waitsFor(function () {
                return wasColorized;
            }, "the element got colors", 200);

            runs(function () {
                expect(elem.style.backgroundImage).not.toBe(iconUrl);
            });

            // Colorize once again.

            runs(function () {
                iconUrl2 = elem.style.backgroundImage;
                wasColorized = false;
                icon.colorize(elem, ['#FF2B34', '#B20008'], callback);
            });

            waitsFor(function () {
                return wasColorized;
            }, "the element got colors", 200);

            runs(function () {
                expect(elem.style.backgroundImage).not.toBe(iconUrl2);
            });

        });
    });

});
