initializeDatepicker("datepicker");
initializeSelect('selectExp', expenses);
setOnSubmitBehaviour();

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

function setOnSubmitBehaviour() {
  let $form = $("#formRate");

  $form.submit(function (event) {
    if (getSelectedExpense()) {
      let expense = getSelectedExpense(),
          rateAmount = getAmountValue(),
          projectedValue = Number(rateAmount) + expense.payed,
          exceeds = projectedValue > expense.amount;

      if (exceeds === true) {
        NOTIFY.display({
          type: 'warning',
          text: `Seems like this rate value will exceed total amount needed for this expense
          <br>Expense amount: ${expense.amount}
          <br>Expense payed: ${expense.payed}
          <br>Total value will be: <b>${projectedValue}</b>`
        });
        event.preventDefault();
      }
    }
  });
}

function getSelectedExpense() {
  let $selectExp = $('#selectExp'),
      selected = $selectExp.select2('data')[0];
  if (selected.id.length && selected.text.length) {
    return {
      id: selected.id,
      amount: selected.amount,
      payed: selected.payed
    };
  }
  return null;
}

function getAmountValue() {
  return $('#amountInput').val().trim();
}

function initializeSelect(id, arr, multiple = false) {
  if (arr && arr.length) {
    let arrForSelect = arr.map(e => {
          return {
            id: e.id,
            text: e.title,
            amount: e.amount,
            payed: e.payed
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