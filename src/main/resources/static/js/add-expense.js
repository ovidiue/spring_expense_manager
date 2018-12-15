initializeSelect("selectTag", TAGS, true, formatEntryDisplay);
initializeSelect("selectCat", CATEGORIES, false, formatEntryDisplay);
initializeDatepicker("datepicker");
NOTIFY.display(notificationInfo);

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
      '<div class="ds-table full-width">' + entry.text
      + '<span class="select-color" style="background: ' + entry.color
      + ';"></span></div>'
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
  });
  let $ctrl = $("#" + id);
  $ctrl.select2({
    data: arrForSelect,
    multiple: multiple,
    templateResult: templateResult,
    placeholder: "Select",
    allowClear: true
  });
}

function initializeDatepicker(id) {
  let today = new Date();
  let $ctrl = $("#" + id);

  $ctrl.datepicker({
    minDate: today,
    format: "dd-mm-yyyy"
  });
}