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
      return data ? "<span class='category-mark' style='border: 3px double "
          + data.color
          + "'>" + data.name + "</span>" : ""
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
            "<a href='javascript:void(0)' class='normal-text' data-toggle='popover' data-content='"
            +
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
    "<span class='tag-underline' style='border-bottom: 6px solid " + el.color
    + "'>" + el.name + "</span>")
    .join(",");
  }

  return "";
}

function createTableElementFromArr(arr, expId) {
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
    tr.setAttribute('data-id', el.id);
    tr.setAttribute('title', 'Click to edit');
    tr.style.cursor = 'pointer';
    for (let key in el) {
      if (key === 'amount' || key === 'payedOn') {
        let td = tr.appendChild(getDomEl("td"));
        td.textContent = el[key];
      }
    }
    tr.onclick = function (ev) {
      return (function () {
        let id = ev.target.parentElement.getAttribute('data-id');
        window.location.assign('rates/edit/' + id + "/" + expId);
      })()
    };
  });

  return table;
}

function initializeTable() {
  const table = $('#expensesTable').DataTable({
    data: EXPENSES,
    columns: columns,
    columnDefs: columnDefs,
    dom: '<"toolbar full-width"f>t<"custom-footer"ilpr>'
  });
  return table;
}

function setTableAddBtn() {
  $("div.toolbar").append(
      [
        '<div class="add-btn float-right">',
        '<a class="btn btn-primary" href="/expenses/add">',
        'Add expense',
        '</a>',
        '</div>'].join("")
  );
}

function setTableDeleteAction(table) {
  $('#expensesTable tbody').on('click', '.del-exp', function () {
    const data = table.row($(this).parents('tr')).data();
    swal({
      text: 'Delete expense: ' + data.title,
      icon: "warning",
      dangerMode: true,
      title: "Delete",
      buttons: {
        cancel: 'Cancel',
        delete: {
          value: 'delete',
          text: "Delete expense"
        },
        deleteRates: {
          value: 'andRates',
          text: "Delete also rates"
        }
      }
    }).then(value => {
      if (value === 'delete') {
        window.location.assign("/expenses/delete/" + data.id);
      }
      if (value === "andRates") {
        swal({
          title: 'Delete',
          text: 'Are you sure you want to delete also the rates ?',
          dangerMode: true,
          buttons: {
            cancel: 'Cancel',
            delete: {
              value: 'delete',
              text: "Yes Delete ALL"
            }
          }
        }).then(value => {
          if (value === 'delete') {
            window.location.assign("/expenses/delete-rates/" + data.id);
          }
        });
      }
    })
  });
}

function setTableEdit(table) {
  $('#expensesTable tbody').on('click', '.ed-exp', function () {
    const data = table.row($(this).parents('tr')).data();
    console.log("DATA: ", data);
    window.location.assign("/expenses/edit/" + data.id);
  });
}

function setTableViewRatesAction(table) {
  $('#expensesTable tbody').on('click', '.vw-r', function () {
    const data = table.row($(this).parents('tr')).data();
    axios.get("http://localhost:8080/api/rates/get-all/" + data.id)
    .then(resp => {
      console.table(resp);
      if (resp.data && resp.data.length) {
        let domTable = createTableElementFromArr(resp.data, data.id);
        swal({
          buttons: {
            close: "Close",
            details: {
              value: 'details',
              text: "See more details"
            },
            add: {
              value: 'add',
              text: "Add new"
            }
          },
          content: domTable
        }).then(value => {
          if (value === 'details') {
            window.location.assign("rates/" + data.id);
          }
          if (value === "add") {
            window.location.assign("rates/add/" + data.id);
          }
        })
      } else {
        swal({
          title: "Rates",
          text: data.title + " doesn't have any rates attached",
          buttons: {
            close: 'Close',
            add: {
              text: 'Add new',
              value: 'add'
            }
          }
        }).then(value => {
          if (value === 'add') {
            window.location.assign("rates/add/" + data.id);
          }
        });
      }
    })
  });
}

$("#catId").select2({
  placeholder: "Select category",
  allowClear: true,
  width: "100%"
});

$("#tagId").select2({
  placeholder: "Select tags",
  allowClear: true,
  multiple: true,
  width: "100%"
});

function setTableDescriptionPopover() {
  $('[data-toggle="popover"]').popover({container: 'body'})
}

function openFiltersIfValuesExist() {
  if (isFilterEmpty == false) {
    $(".row.filter").removeClass('not-shown');
  }
}

function setBehaviourForFiltersDisplay() {
  let toggle = $('.filter-toggle'),
      filterHolder = $('.filter');

  toggle.off('click').on('click', function () {
    filterHolder.toggleClass("not-shown");
    let hasClass = filterHolder.hasClass("not-shown");
    if (hasClass) {
      $(this).removeClass("fa-minus").addClass("fa-plus");
    } else {
      $(this).removeClass("fa-plus").addClass("fa-minus");
    }
  });
}

function initializeDatepickers() {
  let $ids = [
    $("#datepickerFrom"),
    $("#datepickerTo"),
    $("#datepickerFromCr"),
    $("#datepickerToCr")];
  $ids.forEach(el => {
    el.datepicker({
      format: "dd-mm-yyyy",
      uiLibrary: "bootstrap4"
    });
  })
}

NOTIFY.display(notificationInfo);

const table = initializeTable();
setTableAddBtn();
setTableDescriptionPopover();
setTableEdit(table);
setTableViewRatesAction(table);
setTableDeleteAction(table);

setBehaviourForFiltersDisplay();
openFiltersIfValuesExist();
initializeDatepickers();

