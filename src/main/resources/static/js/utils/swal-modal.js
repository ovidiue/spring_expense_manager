const SWAL = {
    delete: function (text) {
        return swal({
            text: text,
            icon: "warning",
            dangerMode: true,
            title: "Delete",
            buttons: true
        });
    }
};