/**
 * Created by Ovidiu on 08-Nov-18.
 */
const DATE_FORMAT = "DD-MM-YYYY";
const DATE_FORMAT_HS = "DD-MM-YYYY, H:mm";

(function () {
  let currentLocation = window.location.pathname.replace("\/", "");
  $('nav li').removeClass('active');

  $('nav a').each(function () {
    let href = $(this).attr('href').replace("/", "");
    if (currentLocation.substring(0, href.length) === href) {
      $(this).closest('li').addClass('active');
    }
  })
})();
