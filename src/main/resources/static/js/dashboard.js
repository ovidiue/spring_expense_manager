console.log('dashboard loaded');
initializePieCategory();
initializeBarchartCategory();
initializePieTags();
initializeBarchartTags();
initializeClusteredBarChartExpenses();
initializeClusteredBarChartExpensesByDate();

// *********************************************************
// *********************************************************
// *********************************************************

function initializePieCategory() {
  am4core.useTheme(am4themes_animated);
  var chart = am4core.create("pieHolder", am4charts.PieChart);

// Add data
  chart.data = categoryStats;
  chart.legend = new am4charts.Legend();

// Add and configure Series
  var pieSeries = chart.series.push(new am4charts.PieSeries());
  pieSeries.dataFields.value = "total";
  pieSeries.dataFields.category = "name";
  pieSeries.slices.template.stroke = am4core.color("#fff");
  pieSeries.slices.template.strokeWidth = 2;
  pieSeries.slices.template.strokeOpacity = 1;

// This creates initial animation
  pieSeries.hiddenState.properties.opacity = 1;
  pieSeries.hiddenState.properties.endAngle = -90;
  pieSeries.hiddenState.properties.startAngle = -90;
}

function initializePieTags() {
  am4core.useTheme(am4themes_animated);
  var chart = am4core.create("pieTagHolder", am4charts.PieChart);

// Add data
  chart.data = tagStats;
  chart.legend = new am4charts.Legend();

// Add and configure Series
  var pieSeries = chart.series.push(new am4charts.PieSeries());
  pieSeries.dataFields.value = "total";
  pieSeries.dataFields.category = "name";
  pieSeries.slices.template.stroke = am4core.color("#fff");
  pieSeries.slices.template.strokeWidth = 2;
  pieSeries.slices.template.strokeOpacity = 1;

// This creates initial animation
  pieSeries.hiddenState.properties.opacity = 1;
  pieSeries.hiddenState.properties.endAngle = -90;
  pieSeries.hiddenState.properties.startAngle = -90;
}

function initializeClusteredBarChartExpenses() {
  am4core.useTheme(am4themes_animated);
// Themes end

  // Create chart instance
  var chart = am4core.create("clusteredExp", am4charts.XYChart);

// Add data
  chart.data = expenses;
  chart.legend = new am4charts.Legend();

// Create axes
  var categoryAxis = chart.yAxes.push(new am4charts.CategoryAxis());
  categoryAxis.dataFields.category = "title";
  categoryAxis.numberFormatter.numberFormat = "#";
  categoryAxis.renderer.inversed = true;
  categoryAxis.renderer.grid.template.location = 0;
  categoryAxis.renderer.cellStartLocation = 0.1;
  categoryAxis.renderer.cellEndLocation = 0.9;

  var valueAxis = chart.xAxes.push(new am4charts.ValueAxis());
  valueAxis.renderer.opposite = true;

// Create series
  function createSeries(field, name) {
    var series = chart.series.push(new am4charts.ColumnSeries());
    series.dataFields.valueX = field;
    series.dataFields.categoryY = "title";
    series.name = name;
    series.columns.template.tooltipText = "{title}: [bold]{valueX}[/]";
    series.columns.template.height = am4core.percent(100);
    series.sequencedInterpolation = true;

    var valueLabel = series.bullets.push(new am4charts.LabelBullet());
    valueLabel.label.text = "{valueX}";
    valueLabel.label.horizontalCenter = "left";
    valueLabel.label.dx = 10;
    valueLabel.label.hideOversized = false;
    valueLabel.label.truncate = false;

    var categoryLabel = series.bullets.push(new am4charts.LabelBullet());
    categoryLabel.label.text = "{name}";
    categoryLabel.label.horizontalCenter = "right";
    categoryLabel.label.dx = -10;
    categoryLabel.label.fill = am4core.color("#fff");
    categoryLabel.label.hideOversized = false;
    categoryLabel.label.truncate = false;
  }

  createSeries("amount", "Amount");
  createSeries("payed", "Payed");
  chart.scrollbarX = new am4core.Scrollbar();
  chart.scrollbarY = new am4core.Scrollbar();
}

function initializeClusteredBarChartExpensesByDate() {
  am4core.useTheme(am4themes_animated);
// Themes end

  // Create chart instance
  var chart = am4core.create("clusteredExpDate", am4charts.XYChart);

// Add data
  chart.data = getArrFromObj(groupBy(expenses, 'month'));
  console.log('chart.data', chart.data);

  chart.legend = new am4charts.Legend();

// Create axes
  var categoryAxis = chart.yAxes.push(new am4charts.CategoryAxis());
  categoryAxis.dataFields.category = "month";
  categoryAxis.numberFormatter.numberFormat = "#";
  categoryAxis.renderer.inversed = true;
  categoryAxis.renderer.grid.template.location = 0;
  categoryAxis.renderer.cellStartLocation = 0.1;
  categoryAxis.renderer.cellEndLocation = 0.9;

  var valueAxis = chart.xAxes.push(new am4charts.ValueAxis());
  valueAxis.renderer.opposite = true;

// Create series
  function createSeries(field, name) {
    var series = chart.series.push(new am4charts.ColumnSeries());
    series.dataFields.valueX = field;
    series.dataFields.categoryY = "month";
    series.name = name;
    series.columns.template.tooltipText = "{month}: [bold]{valueX}[/]";
    series.columns.template.height = am4core.percent(100);
    series.sequencedInterpolation = true;

    var valueLabel = series.bullets.push(new am4charts.LabelBullet());
    valueLabel.label.text = "{valueX}";
    valueLabel.label.horizontalCenter = "left";
    valueLabel.label.dx = 10;
    valueLabel.label.hideOversized = false;
    valueLabel.label.truncate = false;

    var categoryLabel = series.bullets.push(new am4charts.LabelBullet());
    categoryLabel.label.text = "{name}";
    categoryLabel.label.horizontalCenter = "right";
    categoryLabel.label.dx = -10;
    categoryLabel.label.fill = am4core.color("#fff");
    categoryLabel.label.hideOversized = false;
    categoryLabel.label.truncate = false;
  }

  createSeries("total", "Total Amount");
  createSeries("totalPayed", "Total Amount Payed");
  chart.scrollbarX = new am4core.Scrollbar();
  chart.scrollbarY = new am4core.Scrollbar();
}

function initializeBarchartTags() {
  am4core.useTheme(am4themes_animated);

  var chart = am4core.create("barTagHolder", am4charts.XYChart);
  chart.data = tagStats;

  chart.legend = new am4charts.Legend();

  chart.padding(40, 40, 40, 40);

  var categoryAxis = chart.xAxes.push(new am4charts.CategoryAxis());
  categoryAxis.renderer.grid.template.location = 0;
  categoryAxis.dataFields.category = "name";
  categoryAxis.renderer.minGridDistance = 60;

  var valueAxis = chart.yAxes.push(new am4charts.ValueAxis());
  valueAxis.min = 0;
  valueAxis.max = 24000;
  valueAxis.strictMinMax = true;
  valueAxis.renderer.minGridDistance = 30;

  valueAxis.renderer.labels.template.hiddenState.transitionDuration = 2000;
  valueAxis.renderer.labels.template.defaultState.transitionDuration = 2000;

// axis break
  var axisBreak = valueAxis.axisBreaks.create();
  axisBreak.startValue = 2100;
  axisBreak.endValue = 22900;
  axisBreak.breakSize = 0.01;

// make break expand on hover
  var hoverState = axisBreak.states.create("hover");
  hoverState.properties.breakSize = 1;
  hoverState.properties.opacity = 0.1;
  hoverState.transitionDuration = 1500;

  axisBreak.defaultState.transitionDuration = 1000;

  var series = chart.series.push(new am4charts.ColumnSeries());
  series.dataFields.categoryX = "name";
  series.dataFields.valueY = "total";
  series.columns.template.tooltipText = "{valueY.payed}";
  series.columns.template.tooltipY = 0;
  series.columns.template.strokeOpacity = 0;

}

function initializeBarchartCategory() {
  am4core.useTheme(am4themes_animated);

  var chart = am4core.create("barHolder", am4charts.XYChart);
  chart.data = categoryStats;

  chart.legend = new am4charts.Legend();

  chart.padding(40, 40, 40, 40);

  var categoryAxis = chart.xAxes.push(new am4charts.CategoryAxis());
  categoryAxis.renderer.grid.template.location = 0;
  categoryAxis.dataFields.category = "name";
  categoryAxis.renderer.minGridDistance = 60;

  var valueAxis = chart.yAxes.push(new am4charts.ValueAxis());
  valueAxis.min = 0;
  valueAxis.max = 24000;
  valueAxis.strictMinMax = true;
  valueAxis.renderer.minGridDistance = 30;

  valueAxis.renderer.labels.template.hiddenState.transitionDuration = 2000;
  valueAxis.renderer.labels.template.defaultState.transitionDuration = 2000;

// axis break
  var axisBreak = valueAxis.axisBreaks.create();
  axisBreak.startValue = 2100;
  axisBreak.endValue = 22900;
  axisBreak.breakSize = 0.01;

// make break expand on hover
  var hoverState = axisBreak.states.create("hover");
  hoverState.properties.breakSize = 1;
  hoverState.properties.opacity = 0.1;
  hoverState.transitionDuration = 1500;

  axisBreak.defaultState.transitionDuration = 1000;

  var series = chart.series.push(new am4charts.ColumnSeries());
  series.dataFields.categoryX = "name";
  series.dataFields.valueY = "total";
  series.columns.template.tooltipText = "{valueY.payed}";
  series.columns.template.tooltipY = 0;
  series.columns.template.strokeOpacity = 0;

}

function groupBy(objectArray, property) {
  return objectArray.reduce(function (acc, obj) {
    var key = obj[property];
    if (!acc[key]) {
      acc[key] = [];
    }
    acc[key].push(obj);
    return acc;
  }, {});
}

function getArrFromObj(obj) {
  let result = [];
  let sum = 0;
  for (let key in obj) {
    let total = obj[key].map(e => e.amount).reduce((sum, e) => sum += e, sum);
    let totalPayed = obj[key].map(e => e.payed).reduce((sum, e) => sum += e,
        sum);
    let newObj = {
      total: total,
      totalPayed: totalPayed,
      month: key
    };

    result.push(newObj);

  }

  result.forEach(el => el.month = moment(el.month).format('MMMM'));

  return result;
}