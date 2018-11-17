let baseConfig = {
    theme: "metroui",
    timeout: 5000,
    progressBar: true
};
const NOTIFY = {
    success: function (text) {
        let config = Object.assign({
            text: text,
            type: "success",
        }, baseConfig);
        return new Noty(config).show();
    },
    error: function (text) {
        let config = Object.assign({
            text: text,
            type: "error",
        }, baseConfig);
        return new Noty(config).show();
    },
    info: function (text) {
        let config = Object.assign({
            text: text,
            type: "info",
        }, baseConfig);
        return new Noty(config).show();
    }
};