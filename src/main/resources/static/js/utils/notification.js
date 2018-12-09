let baseConfig = {
  theme: "metroui",
  timeout: 5000,
  progressBar: true
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
  }
};