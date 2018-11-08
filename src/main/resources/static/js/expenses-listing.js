let columns = [
    {
        title: "Title",
        data: "title"
    },
    {
        title: "Description",
        data: 'description'
    },
    {
        title: "Amount",
        data: 'amount'
    },
    {
        title: "Recurrent",
        data: 'recurrent'
    },
    {
        title: "Created On",
        data: 'createdOn'
    },
    {
        title: "Due Date",
        data: 'dueDate'
    },
    {
        title: "Category",
        data: 'category'
    },
    {
        title: "Tags",
        data: 'tags'
    },
    {title: "Delete"}
];

let columnDefs = [
    {
        targets: 7,
        render: function (data, type, row, meta) {
            return extractArrAsString(data);
        }
    },
    {
        targets: 6,
        render: function (data, type, row, meta) {
            return data ? data.name : ""
        }
    }, {
        targets: -1,
        render: function (data, type, row, meta) {
            return "<button class='delete-expense btn btn-danger'>Delete</button>";
        },
        width: "10%"
    }, {
        targets: 4,
        render: function (data) {
            return moment(data).format('MM-DD-YYYY, H:mm');
        }
    }, {
        targets: 5,
        render: function (data) {
            return data ? moment(data).format('MM-DD-YYYY, H:mm') : '';
        }
    }];

function extractArrAsString(arr) {
    if (arr) {
        return arr.map(el => el.name)
            .join(",");
    }

    return "";
}

const table = $('#expensesTable').DataTable({
    data: EXPENSES,
    columns: columns,
    columnDefs: columnDefs
});

$('#expensesTable tbody').on('click', '.delete-expense', function () {
    const data = table.row($(this).parents('tr')).data();
    console.log("DATA: ", data);
    swal({
        title: 'Delete expense: ' + data.title,
        text: "You won't be able to revert this!",
        type: 'warning',
        showCancelButton: true,
        cancelButtonColor: '#3085d6',
        confirmButtonColor: '#d33',
        confirmButtonText: 'DELETE!'
    }).then((result) => {
        console.log(result);
        if (result.value) {
            window.location.pathname = "/expenses/delete/" + data.id
            /*swal(
             'Deleted!',
             'Your file has been deleted.',
             'success'
             )*/
        }
    })

});

console.log("expense objects: ", EXPENSES);