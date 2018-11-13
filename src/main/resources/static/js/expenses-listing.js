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
            return extractArrAsSpan(data);
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
    },
    {
        targets: 1,
        render: function (data) {
            if (data && data.length > 60) {
                let shortData = data.substring(0, 60) + "...";
                return "<span>" +
                    "<a href='javascript:void(0)' class='normal-text' data-toggle='popover' data-content='" +
                    data +
                    "' data-trigger='focus'>" +
                    shortData +
                    "</a>" +
                    "<i class='fas fa-info float-right'></i>" +
                    "</span>";
            } else {
                return data;
            }

        }
    }
];

function extractArrAsSpan(arr) {
    if (arr) {
        return arr.map(el =>
        "<span class='tag-underline' style='border-bottom: 6px solid " + el.color + "'>" + el.name + "</span>")
            .join(",");
    }

    return "";
}

const table = $('#expensesTable').DataTable({
    data: EXPENSES,
    columns: columns,
    columnDefs: columnDefs,
    dom: '<"toolbar full-width"f>t<"custom-footer"ilpr>'
});

$("div.toolbar").append('<div class="add-btn float-right"><a class="btn btn-primary" href="/expenses/add">Add expense</a></div>');

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

$(function () {
    $('[data-toggle="popover"]').popover({container: 'body'})
});

