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
    }
];

let columnDefs = [{
    targets: 2,
    render: function (data, type, row, meta) {
        return "<div class='table-color' style='background:" + data + "'></div>"
    }
}];

$('#tagsTable').DataTable({
    data: TAGS,
    columns: columns,
    columnDefs: columnDefs
});