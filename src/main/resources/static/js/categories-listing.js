let columns = [
    {
        title: "Name",
        data: "name"
    },
    {
        title: "Description",
        data: "description"
    },
    {
        title: "Color",
        data: "color"
    },
    {title: "Delete"}
];

let columnDefs = [
    {
        targets: 2,
        render: function (data, type, row, meta) {
            return "<div class='table-color' style='background:" + data + "'></div>"
        }
    }, {
        targets: -1,
        render: function (data, type, row, meta) {
            return "<button class='delete-cat btn btn-danger'>Delete</button>";
        },
        width: "10%"
    }
];

const table = $('#catTable').DataTable({
    data: CATEGORIES,
    columns: columns,
    columnDefs: columnDefs
});

$('#catTable tbody').on('click', '.delete-cat', function () {
    const data = table.row($(this).parents('tr')).data();
    console.log("DATA: ", data);
    swal({
        title: 'Delete category: ' + data.name,
        text: "You won't be able to revert this!",
        type: 'warning',
        showCancelButton: true,
        cancelButtonColor: '#3085d6',
        confirmButtonColor: '#d33',
        confirmButtonText: 'DELETE!'
    }).then((result) => {
        console.log(result);
        if (result.value) {
            window.location.pathname = "/categories/delete/" + data.id
            /*swal(
             'Deleted!',
             'Your file has been deleted.',
             'success'
             )*/
        }
    })

});