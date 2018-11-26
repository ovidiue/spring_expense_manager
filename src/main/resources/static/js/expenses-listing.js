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
	{
		title: "Total Payed",
		data: 'payed'
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
			return $("<div><span><i class='far fa-edit ed-exp'></i></span>" +
				"<span><i class='far fa-trash-alt del-exp'></i></span>" +
				"<span><i class='fas fa-list-ul vw-r'></i></span>" +
				"</div>").html();
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

function createTableElementFromArr(arr) {
	let getDomEl = function (el) {
		return document.createElement(el);
	};

	let titles = ["Value", "Payed On"];

	let table = getDomEl("table");
	table.className = "table table-striped table-dark";
	let tHead = table.appendChild(getDomEl("thead"));
	let tHeadRow = table.tHead.appendChild(getDomEl("tr"));
	titles.forEach(el => {
		let th = tHeadRow.appendChild(getDomEl("th"));
		th.textContent = el;
	});

	let tBody = table.appendChild(getDomEl("tBody"));
	arr.forEach(el => {
		let tr = tBody.appendChild(getDomEl("tr"));
		for (let key in el) {
			if (key === 'amount' || key === 'payedOn') {
				let td = tr.appendChild(getDomEl("td"));
				td.textContent = el[key];
			}
		}
	});

	return table;
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
	/*SWAL.delete('Delete expense: ' + data.title)
	 .then((result) => {
	 console.log(result);
	 if (result) {
	 window.location.assign("/expenses/delete/" + data.id);
	 }
	 }
	 );*/
	swal({
		text: 'Delete expense: ' + data.title,
		icon: "warning",
		dangerMode: true,
		title: "Delete",
		buttons: {
			delete: {
				value: 'delete',
				text: "Delete expense"
			},
			deleteRates: {
				value: 'andRates',
				text: "Delete also rates"
			},
			cancel: 'Cancel'
		}
	}).then(value => {
		if (value === 'delete') {
			window.location.assign("/expenses/delete/" + data.id);
		}
		if (value === "andRates") {
			window.location.assign("/expenses/delete-rates/" + data.id);
		}
	})
})
;

$('#expensesTable tbody').on('click', '.ed-exp', function () {
	const data = table.row($(this).parents('tr')).data();
	console.log("DATA: ", data);
	window.location.assign("/expenses/edit/" + data.id);
});

$('#expensesTable tbody').on('click', '.vw-r', function () {
	const data = table.row($(this).parents('tr')).data();
	axios.get("http://localhost:8080/api/rates/get-all/" + data.id)
	.then(resp => {
		console.table(resp);
		if (resp.data) {
			let domTable = createTableElementFromArr(resp.data);
			swal({
				buttons: {
					details: {
						value: 'details',
						text: "See more details"
					},
					add: {
						value: 'add',
						text: "Add new"
					},
					ok: true
				},
				content: domTable
			}).then(value => {
				if (value === 'details') {
					window.location.assign("rates/" + data.id);
				}
				if (value === "add") {
					window.location.assign("rates/add");
				}
			})
		}
	})
});

$(function () {
	$('[data-toggle="popover"]').popover({container: 'body'})
});

NOTIFY.display(notificationInfo);

