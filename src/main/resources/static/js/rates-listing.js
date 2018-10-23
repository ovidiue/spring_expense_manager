let columns = [
    {title: "Observation"},
    {title: "Amount"},
    {title: "Created On"}
];

$('#ratesTable').DataTable({
    data: RATES,
    columns: columns
});