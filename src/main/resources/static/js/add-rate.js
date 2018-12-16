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

function getDefinedElements(arr) {
  let result = [];
  arr.forEach(el => {
    if (el) {
      result.push(el)
    }
  });
  return result;
}

/** checks that expense id contained in the window location path is
 the same as the one user selected */
function isSelectedExpenseSameAsOrigin() {
  let arrPathIds = window.location.pathname.replace(/[a-z]/g, "").split(
      '/').filter(el => el),
      currentSelectedExpId = getSelectedExpense(),
      pathExpenseId = null;

  if (arrPathIds.length) {

    if (arrPathIds.length === 2) {
      pathExpenseId = arrPathIds[1];
    } else {
      pathExpenseId = arrPathIds[0];
    }

    return (pathExpenseId !== null && currentSelectedExpId !== null
    && pathExpenseId === currentSelectedExpId.id);
  }

  return false;
}

function setFlagForRedirect() {
  let comesFromExpenses = isSelectedExpenseSameAsOrigin();
  $('#redirectInput').val(comesFromExpenses);
}

function isSameExpense() {
  if (typeof previousExpense !== 'undefined' && previousExpense !== null
      && previousExpense.id) {
    return previousExpense.id.toString() === getSelectedExpense().id.toString();
  }

  return false;
}

function isExceeding() {
  let rateAmount = getAmountValue(),
      expense = getSelectedExpense(),
      projected = isSameExpense() ? (Number(expense.payed) - Number(rateValue))
          + Number(rateAmount)
          : expense.payed + Number(rateAmount);

  return Number(projected) > expense.amount;
}

function setOnSubmitBehaviour() {
  let $form = $("#formRate");

  $form.submit(function (event) {
    if (getSelectedExpense()) {
      let expense = getSelectedExpense(),
          exceeds = isExceeding();

      if (exceeds === true) {
        NOTIFY.display({
          type: 'warning',
          text: `Seems like this rate value will exceed total amount needed for this expense
          <br>Expense amount: ${expense.amount}
          <br>Expense payed: ${expense.payed}`
        });
        event.preventDefault();
      }
    }
    setFlagForRedirect();
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