const SWAL = {
  delete: function (text) {
    return swal({
      text: text,
      icon: "warning",
      dangerMode: true,
      title: "Delete",
      buttons: {
        cancel: "Cancel",
        delete: {
          text: 'Delete',
          value: true
        }
      }
    });
  }
};