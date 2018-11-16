$("#color").colorpicker()
    .on('changeColor colorpickerChange colorpickerCreate', function (e) {
        $('#chosen')
            .css('background-color', e.value);
    });

if (typeof existingTag !== 'undefined' && existingTag !== null) {
    $("#color").change();
}