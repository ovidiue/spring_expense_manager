console.log('dashboard loaded');
initializePieCategory();
initializeBarchartCategory();

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