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
    }
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
    }];

function extractArrAsString(arr) {
    if (arr) {
        return arr.map(el => el.name)
            .join(",");
    }

    return "";
}

$('#expensesTable').DataTable({
    data: EXPENSES,
    columns: columns,
    columnDefs: columnDefs
});

console.log("expense objects: ", EXPENSES);