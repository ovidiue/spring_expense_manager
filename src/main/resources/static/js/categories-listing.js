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
    {title: "Actions"}
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
            //return "<button class='delete-cat btn btn-danger'>Delete</button>";
            //<span><i class="fas fa-info view-cat"></i></span>
            return `
            <div>
                <span><i class="far fa-edit edit-cat"></i></span>                
                <span><i class="far fa-trash-alt delete-cat"></i></span>
            </div>
            `;
        },
        width: "10%"
    }
];

const table = $('#catTable').DataTable({
    data: CATEGORIES,
    columns: columns,
    columnDefs: columnDefs,
    dom: '<"toolbar full-width"f>t<"custom-footer"ilpr>'
});

$("div.toolbar").append('<div class="add-btn float-right"><a class="btn btn-primary" href="/categories/add">Add category</a></div>');

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
        }
    })

});

$('#catTable tbody').on('click', '.edit-cat', function () {
    const data = table.row($(this).parents('tr')).data();
    console.log("DATA: ", data);
    window.location.assign("/categories/edit/" + data.id);
});

NOTIFY.display(notificationInfo);