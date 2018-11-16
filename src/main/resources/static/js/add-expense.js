initializeSelect("selectTag", TAGS, true, formatEntryDisplay);
initializeSelect("selectCat", CATEGORIES, false, formatEntryDisplay);
initializeDatepicker("datepicker");

if (typeof existingExpense !== 'undefined') {
    if (existingExpense.category && existingExpense.category.id) {
        $("#selectCat").val(existingExpense.category.id).change();
    }
    if (existingExpense.tags && existingExpense.tags) {
        let values = getKeysAsArrFromArr(existingExpense.tags, 'id');
        $("#selectTag").val(values).change();
    }
}

function getKeysAsArrFromArr(arr, key) {
    if (arr) {
        return arr.map(e => e[key]);
    }
}

function formatEntryDisplay(entry) {
    if (!entry.id) {
        return entry.text;
    }

    let $entry = $(
        '<div class="ds-table full-width">' + entry.text + '<span class="select-color" style="background: ' + entry.color + ';"></span></div>'
    );
    return $entry;
}

function initializeSelect(id, arr, multiple = false, templateResult) {
    let arrForSelect = arr.map(e => {
            return {
                id: e.id,
                text: e.name,
                color: e.color
            };
        }
    );
    id = "#" + id;
    $(id).select2({
        data: arrForSelect,
        multiple: multiple,
        templateResult: templateResult,
        placeholder: "Select",
        allowClear: true
    });
}

function initializeDatepicker(id) {
    let today = new Date();
    /* let config = {
     minDate: today
     };

     if (typeof existingExpense !== 'undefined')
     config.value = moment(existingExpense.dueDate).format(DATE_FORMAT);

     $("#" + id).datepicker(config);*/

    $("#" + id).datepicker({
        minDate: today,
        value: typeof  existingExpense !== 'undefined' && existingExpense.dueDate ? moment(existingExpense.dueDate).format(DATE_FORMAT) : ""
    })
}