console.log(TAGS);
console.log(CATEGORIES);

initializeSelect("selectTag", TAGS, true, formatEntryDisplay);
initializeSelect("selectCat", CATEGORIES, false, formatEntryDisplay);
initializeDatepicker("datepicker");

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

function initializeDatepicker(id) {
    let today = new Date();
    $("#" + id).datepicker({
        minDate: today
    });
}