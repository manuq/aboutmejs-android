define(function () {
    var util = require("sugar-html-graphics/util");

    var radioButtonsGroup = {};

    // ## RadioButtonsGroup
    //
    // A group of elements where only one can be active at the same
    // time.
    //
    // When an element is clicked, it becomes the active one.  The
    // active element gains the 'active' CSS class.
    //
    // Parameters:
    //
    // * **elems** Array of elements of the group.
    radioButtonsGroup.RadioButtonsGroup = function (elems) {
        this.elems = elems;
        var active;

        for (i = 0; i < elems.length; i++) {
            var elem = elems[i];
            elem.addEventListener("click", clickHandler);

            // The first element that has 'active' CSS class is made
            // the active of the group on startup.
            if (active === undefined && util.hasClass(elem, 'active')) {
                active = elem;
            }
        }

        // If no element has 'active' CSS class, the first element of
        // the array is made the active.
        if (active === undefined) {
            active = elems[0];
            updateClasses();
        }

        function clickHandler(evt) {
            active = evt.target;
            updateClasses();
        }

        function updateClasses() {
            for (i = 0; i < elems.length; i++) {
                var elem = elems[i];
                util.removeClass(elem, 'active');
            }
            util.addClass(active, 'active');
        }

        // Get the active element.
        this.getActive = function () {
            return active;
        };

    };

    return radioButtonsGroup;

});
