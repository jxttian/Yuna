/**
 * 公共Formatter
 * @type {{}}
 */
var Formatter = {
    /**
     * 链接类型格式化
     * @param value
     * @returns {string}
     * @constructor
     */
    Url: function (value) {
        return (value) ? Commons.sprintf('<a target="_blank" href="%s">详情</a>', value) : '';
    },

    ImgUrl: function (value) {
        if (value)
            return '<a target="_blank" href="' + value + '"><img height="20px" src="' + value + '"/></a>';
    },

    Index: function (value, row, index) {
        return index + 1;
    },

    /**
     * @return {string}
     */
    Text: function (value, row, index) {
        if (value)
            return '<a title="' + value + '">' + (value.length > 32 ? value.substr(0, 31) + '...' : value) + '</a>';
    },
    /**
     * @return {string}
     */
    Icon: function (value) {
        if (value)
            return '<li class="' + value + '"></li>'
    },
    /**
     * @return {string}
     */
    Currency: function (value, row, index) {
        value = value.toString().replace(/\$|\,/g, '');
        if (isNaN(value))
            value = "0";
        sign = (value === (value = Math.abs(value)));
        value = Math.floor(value * 100 + 0.50000000001);
        cents = value % 100;
        value = Math.floor(value / 100).toString();
        if (cents < 10)
            cents = "0" + cents;
        for (var i = 0; i < Math.floor((value.length - (1 + i)) / 3); i++)
            value = value.substring(0, value.length - (4 * i + 3)) + ',' +
                value.substring(value.length - (4 * i + 3));
        return (((sign) ? '' : '-') + value + '.' + cents);
    },

    /**
     * 布尔类型格式化
     * @param value
     * @returns {string}
     * @constructor
     */
    Boolean: function (value) {
        return value === 1 ? '<li class="fa fa-check-square"></li>' : '<li class="fa fa-square-o"></li>'
    },

    /**
     * 日期格式化
     * @param value
     * @returns {string}
     * @constructor
     */
    Date: function (value) {
        if (!value) {
            return "-";
        }
        var date = new Date(value);
        var year = date.getFullYear();
        var month = date.getMonth() + 1;//js从0开始取
        var day = date.getDate();

        if (month < 10) {
            month = "0" + month;
        }
        if (day < 10) {
            day = "0" + day;
        }
        return year + "-" + month + "-" + day;
    },

    /**
     * 日期时间格式化
     * @param value
     * @returns {string}
     * @constructor
     */
    DateTime: function (value) {
        if (!value) {
            return "-";
        }
        var datetime = new Date(value);
        var year = datetime.getFullYear();
        var month = datetime.getMonth() + 1;//js从0开始取
        var date = datetime.getDate();
        var hour = datetime.getHours();
        var minutes = datetime.getMinutes();
        var second = datetime.getSeconds();
        if (month < 10) {
            month = "0" + month;
        }
        if (date < 10) {
            date = "0" + date;
        }
        if (hour < 10) {
            hour = "0" + hour;
        }
        if (minutes < 10) {
            minutes = "0" + minutes;
        }
        if (second < 10) {
            second = "0" + second;
        }
        return year + "-" + month + "-" + date + " " + hour + ":" + minutes + ":" + second;
    },

    /**
     * 时间格式化
     * @param value
     * @returns {string}
     * @constructor
     */
    Time: function (value) {
        if (!value) {
            return "-";
        }
        var time = new Date(value);
        var hour = time.getHours();
        var minutes = time.getMinutes();
        var second = time.getSeconds();
        if (hour < 10) {
            hour = "0" + hour;
        }
        if (minutes < 10) {
            minutes = "0" + minutes;
        }
        if (second < 10) {
            second = "0" + second;
        }
        return hour + ":" + minutes + ":" + second;
    }
};

