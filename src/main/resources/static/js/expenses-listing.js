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
    {title: "Actions"}
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
            return `
            <div>
                <span><i class="far fa-edit ed-exp"></i></span>                
                <span><i class="far fa-trash-alt del-exp"></i></span>
            </div>
            `;
        },
        width: "10%"
    }, {
        targets: 4,
        render: function (data) {
            return moment(data).format(DATE_FORMAT);
        }
    }, {
        targets: 5,
        render: function (data) {
            return data ? moment(data).format(DATE_FORMAT) : '';
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

$('#expensesTable tbody').on('click', '.del-exp', function () {
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
        }
    })
});

$('#expensesTable tbody').on('click', '.ed-exp', function () {
    const data = table.row($(this).parents('tr')).data();
    console.log("DATA: ", data);
    window.location.assign("/expenses/edit/" + data.id);
});

