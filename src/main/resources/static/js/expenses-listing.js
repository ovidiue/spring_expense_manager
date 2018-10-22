let columns = [
    {title: "Title"},
    {title: "Description"},
    {title: "Amount"},
    {title: "Recurrent"},
    {title: "Created On"},
    {title: "Due Date"},
    {title: "Category"},
    {title: "Tags"}
];

$('#expensesTable').DataTable({
    data: EXPENSES,
    columns: columns
});