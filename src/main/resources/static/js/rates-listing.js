let columns = [
    {
        title: "Amount",
        data: "amount"
    },
    {
        title: "Observation",
        data: "observation"
    },
    {
        title: "Created On",
        data: "creationDate"
    },
    {
        title: "Payed On",
        data: "payedOn"
    },
    {title: "Actions"}
];

let columnDefs = [
    {
        targets: -1,
        render: function () {
            return `
            <div>
                <span><i class="far fa-edit edit-rate"></i></span>                
                <span><i class="far fa-trash-alt delete-rate"></i></span>
            </div>
            `;
        },
        width: "10%"
    },
    {
        targets: 2,
        render: function (data) {
            let value = data ? moment(data).format(DATE_FORMAT) : "";
            return value;
        }
    },
    {
        targets: 3,
        render: function (data) {
            let value = data ? moment(data).format(DATE_FORMAT) : "";
            return value;
        }
    }

];

const table = $('#ratesTable').DataTable({
    data: RATES,
    columns: columns,
    columnDefs: columnDefs,
    dom: '<"toolbar full-width"f>t<"custom-footer"ilpr>'
});

$("div.toolbar").append('<div class="add-btn float-right"><a class="btn btn-primary" href="/rates/add">Add rate</a></div>');

$('#ratesTable tbody').on('click', '.delete-rate', function () {
    const data = table.row($(this).parents('tr')).data();
    console.log("DATA: ", data);
    SWAL.delete('Delete rate: ' + data.amount)
        .then((result) => {
                console.log(result);
                if (result) {
                    window.location.assign("/rates/delete/" + data.id);
                }
            }
        );
});

$('#ratesTable tbody').on('click', '.edit-rate', function () {
    const data = table.row($(this).parents('tr')).data();
    console.log("DATA: ", data);
    window.location.assign("/rates/edit/" + data.id);
});

NOTIFY.display(notificationInfo);