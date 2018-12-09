$("#color").colorpicker()
.on('changeColor colorpickerChange colorpickerCreate', function (e) {
  $('#chosen')
  .css('background-color', e.value);
});

if (typeof existingCategory !== 'undefined' && existingCategory !== null) {
  $("#color").change();
}