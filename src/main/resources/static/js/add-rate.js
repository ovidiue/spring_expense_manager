initializeDatepicker("datepicker");

function initializeDatepicker(id) {
    let today = new Date();

    $("#" + id).datepicker({
        minDate: today,
        value: typeof  existingExpense !== 'undefined' && existingExpense.dueDate ? moment(existingExpense.dueDate).format(DATE_FORMAT) : ""
    })
}