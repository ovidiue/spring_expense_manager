let baseConfig = {
  theme: "metroui",
  timeout: 5000,
  progressBar: true
};

let textConfig = {
  theme: "metroui",
  layout: 'topCenter',
  killer: true,
  timeout: 10000,
  progressBar: true,
  container: '.title-container',
  animation: {
    open: 'zoomIn animated',
    close: 'zoomOut animated'
  },
  type: 'info'
};

const NOTIFY = {
  display: function (obj) {
    if (isSet(obj)) {
      let config = Object.assign({
        text: obj.text,
        type: obj.type,
      }, baseConfig);
      return new Noty(config).show();
    }
  },
  previewDescription: function (text) {
    if (isSet(text)) {
      let config = Object.assign({
        text: text
      }, textConfig);
      return new Noty(config).show();
    }
  }
};