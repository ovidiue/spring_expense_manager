let columns = [
    {
        title: "Name",
        data: 'name'
    },
    {
        title: "Description",
        data: 'description'
    },
    {
        title: "Color",
        data: 'color'
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
            return `
            <div>
                <span><i class="far fa-edit edit-tag"></i></span>                
                <span><i class="far fa-trash-alt delete-tag"></i></span>
            </div>
            `;
        },
        width: "10%"
    }];

const table = $('#tagsTable').DataTable({
    data: TAGS,
    columns: columns,
    columnDefs: columnDefs
});

$('#tagsTable tbody').on('click', '.delete-tag', function () {
    const data = table.row($(this).parents('tr')).data();
    console.log("DATA: ", data);
    swal({
        title: 'Delete tag: ' + data.name,
        text: "You won't be able to revert this!",
        type: 'warning',
        showCancelButton: true,
        cancelButtonColor: '#3085d6',
        confirmButtonColor: '#d33',
        confirmButtonText: 'DELETE!'
    }).then((result) => {
        console.log(result);
        if (result.value) {
            window.location.pathname = "/tags/delete/" + data.id
        }
    })

});

$('#tagsTable tbody').on('click', '.edit-tag', function () {
    const data = table.row($(this).parents('tr')).data();
    console.log("DATA: ", data);
    window.location.assign("/tags/edit/" + data.id);
});