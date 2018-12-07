initializeDatepicker("datepicker");
initializeSelect('selectExp', expenses);

if (typeof previousExpense !== 'undefined' && previousExpense !== null
    && previousExpense.id) {
  $("#selectExp").val(previousExpense.id).change();
}

if (typeof preselectedExpenseId !== 'undefined' &&
    preselectedExpenseId !== null) {
  $("#selectExp").val(preselectedExpenseId).change();
}

function initializeDatepicker(id) {
  let today = new Date();

  $("#" + id).datepicker({
    maxDate: today,
    format: "dd-mm-yyyy"
  })
}

function initializeSelect(id, arr, multiple = false) {
  if (arr && arr.length) {
    let arrForSelect = arr.map(e => {
          return {
            id: e.id,
            text: e.title
          };
        }
    );
    id = "#" + id;
    $(id).select2({
      data: arrForSelect,
      multiple: false,
      allowClear: true,
      placeholder: 'Select'
    });
  }
}