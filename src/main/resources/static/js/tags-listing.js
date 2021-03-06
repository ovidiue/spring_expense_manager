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
  columnDefs: columnDefs,
  dom: '<"toolbar full-width"f>t<"custom-footer"ilpr>'
});

$("div.toolbar").append(
    '<div class="add-btn float-right"><a class="btn btn-primary" href="/tags/add">Add tag</a></div>');

$('#tagsTable tbody').on('click', '.delete-tag', function () {
  const data = table.row($(this).parents('tr')).data();
  console.log("DATA: ", data);
  SWAL.delete('Delete tag: ' + data.name)
  .then((result) => {
        console.log(result);
        if (result) {
          window.location.assign("/tags/delete/" + data.id);
        }
      }
  );
});

$('#tagsTable tbody').on('click', '.edit-tag', function () {
  const data = table.row($(this).parents('tr')).data();
  console.log("DATA: ", data);
  window.location.assign("/tags/edit/" + data.id);
});

NOTIFY.display(notificationInfo);