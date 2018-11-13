initializeDatepicker("datepicker");
initializeSelect('selectExp', expenses);

if (typeof previousExpense !== 'undefined' && previousExpense !== null && previousExpense.id) {
    $("#selectExp").val(previousExpense.id).change();
}

function initializeDatepicker(id) {
    $("#" + id).datepicker({
        value: typeof  existingExpense !== 'undefined' && existingExpense.dueDate ? moment(existingExpense.dueDate).format(DATE_FORMAT) : ""
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