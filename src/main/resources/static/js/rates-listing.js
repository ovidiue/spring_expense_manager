let columns = [
    {
        title: "Observation",
        data: "observation"
    },
    {
        title: "Amount",
        data: "amount"
    },
    {
        title: "Created On",
        data: "creationDate"
    }
];

$('#ratesTable').DataTable({
    data: RATES,
    columns: columns,
    dom: '<"toolbar full-width"f>t<"custom-footer"ilpr>'
});

$("div.toolbar").append('<div class="add-btn float-right"><a class="btn btn-primary" href="/rates/add">Add rate</a></div>');