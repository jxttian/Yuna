/**
 * 根据formId将from里的所有元素转成对象
 * @param form
 */
const Commons = {
    formData: function (selector, params, withoutTemp) {
        var formValues = $(selector).serializeArray();
        var paramObject = {};
        if (params) {
            paramObject = params;
        }
        for (var i = 0, len = formValues.length; i < len; i++) {
            if (!withoutTemp || !formValues[i].name.startsWith("temp_"))
                if (formValues[i].value === '' || formValues[i].value === null) {
                    paramObject[formValues[i].name] = undefined;
                } else {
                    paramObject[formValues[i].name] = Commons.xssFilter(formValues[i].value);
                }
        }
        return paramObject;
    },
    /**
     * 获取选中的id
     * @param table
     * @returns {*}
     */
    getIdSelections: function (table, row) {
        return $.map(table.bootstrapTable('getSelections'), row);
    },

    /**
     * Js端简单Xss过滤
     * @param s
     * @returns {string}
     */
    xssFilter: function (s) {
        return s.replace(/</g, '&lt;')
            .replace(/>/g, '&gt;')
            //.replace(/\//g, '&#x2f;')
            .replace(/"\s*[a-zA-Z0-9]*\s*javascript:/g, '&quot;javascript:')
            .replace(/'\s*[a-zA-Z0-9]*\s*javascript:/g, '&#x27;javascript:');
    },
    onLoadError: function (status) {
        Messager.warn("加载数据失败 Status:" + status);
    },
    append0Left: function (str, length) {
        if (str.length >= length)
            return str;
        else
            return Commons.append0Left("0" + str, length);
    }
};

const Logger = {
    level: 'log',
    log: function (message) {
        if (Logger.level === 'log') {
            console.log(location.href);
            console.log(message);
        }
    },
    warn: function (message) {
        if (Logger.level === 'log' || Logger.level === 'warn') {
            console.warn(location.href);
            console.warn(message);
        }
    },
    error: function (message) {
        console.error(location.href);
        console.error(message);
    }
};

//Ajax工具类
const Ajax = {
    RequestMethod: {GET: "GET", POST: "POST", PUT: "PUT", DELETE: "DELETE"},
    DataType: {JSON: "json", XML: "xml", JSONP: "jsonp", TEXT: "text", SCRIPT: "script", HTML: "html"},
    ContentType: {
        JSON: "application/json;charset=UTF-8",
        URL: "application/x-www-form-urlencoded;charset=UTF-8"
    },
    Get: function (url, data, success, type) {
        return this.Request(url, this.RequestMethod.GET, data, success, type);
    },
    Put: function (url, data, success, type) {
        return this.Request(url, this.RequestMethod.PUT, data, success, type);
    },
    Post: function (url, data, success, type) {
        return this.Request(url, this.RequestMethod.POST, data, success, type);
    },
    Delete: function (url, data, success, type) {
        return this.Request(url, this.RequestMethod.DELETE, data, success, type);
    },
    Request: function (url, method, data, success, type) {
        var ajax = {
            type: method,
            url: url,
            data: data,
            success: function (data, textStatus) {
                success(data, textStatus);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.error(XMLHttpRequest);
                success({code: -1, msg: textStatus}, textStatus)
            }
        };
        if (type) {
            ajax.dataType = !type.dataType ? this.DataType.JSON : type.dataType;
            ajax.contentType = !type.contentType ? this.ContentType.JSON : type.contentType;
        } else {
            ajax.dataType = this.DataType.JSON;
            ajax.contentType = this.ContentType.JSON;
        }
        if (method === this.RequestMethod.POST) {
            ajax.headers = {};
            // ajax.headers['_RequestToken'] = $("#merchantCsrfToken").val();
        }
        return $.ajax(ajax);
    }
};

/**
 * validator
 */
$.extend(jQuery.validator.messages, {
    required: "必选字段",
    remote: "请修正该字段",
    email: "请输入正确格式的电子邮件",
    url: "请输入合法的网址",
    date: "请输入合法的日期",
    dateISO: "请输入合法的日期 (ISO).",
    number: "请输入合法的数字",
    digits: "只能输入整数",
    creditcard: "请输入合法的信用卡号",
    equalTo: "请再次输入相同的值",
    accept: "请输入拥有合法后缀名的字符串",
    maxlength: jQuery.validator.format("请输入一个长度最多是 {0} 的字符串"),
    minlength: jQuery.validator.format("请输入一个长度最少是 {0} 的字符串"),
    rangelength: jQuery.validator.format("请输入一个长度介于 {0} 和 {1} 之间的字符串"),
    range: jQuery.validator.format("请输入一个介于 {0} 和 {1} 之间的值"),
    max: jQuery.validator.format("请输入一个最大为 {0} 的值"),
    min: jQuery.validator.format("请输入一个最小为 {0} 的值")
});

$.validator.addMethod("zipCode", function (value, element) {
    var zipCode = /^[0-9]{6}$/;
    return this.optional(element) || (zipCode.test(value));
}, "请正确填写您的邮政编码");

/**
 * 消息
 * @type {{error: Function, success: Function, warn: Function, message: Function}}
 */
const Messager = {
    error: function (e) {
        layer.msg(e, {
            icon: 2, offset: 0,
            shift: 6
        });
    },
    success: function (e) {
        layer.msg(e, {icon: 1});
    },
    warn: function (e) {
        layer.msg(e, {icon: 7});
    }
};
