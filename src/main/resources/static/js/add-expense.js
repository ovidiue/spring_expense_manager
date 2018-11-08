console.log(TAGS);
console.log(CATEGORIES);
console.log(existingExpense);

initializeSelect("selectTag", TAGS, true, formatEntryDisplay);
initializeSelect("selectCat", CATEGORIES, false, formatEntryDisplay);
initializeDatepicker("datepicker", existingExpense);

if (existingExpense) {
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
        allowClear: multiple,
        templateResult: templateResult
    });
}

function initializeDatepicker(id, expense) {
    let today = new Date();
    $("#" + id).datepicker({
        minDate: today,
        value: expense && expense.dueDate ? moment(expense.dueDate).format(DATE_FORMAT) : ""
    })
}