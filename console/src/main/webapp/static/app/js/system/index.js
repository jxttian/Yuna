var SystemConst = {
    Query: function (params) {
        return Commons.formData("#searchForm", params);
    },
    Operates: {}
};

$(document).ready(function () {
    var $table = $('#table'),
        $create = $('#create'),
        $edit = $('#edit'),
        $disable = $('#disable'),
        $search = $('#search');

    $search.click(function () {
        $table.bootstrapTable('refresh');
    });

    document.onkeydown = function (event) {
        var e = event || window.event || arguments.callee.caller.arguments[0];
        if (e && e.keyCode === 13) {
            $search.click();
        }
    };

    /**
     * 初始化按钮状态
     */
    var initButtonStatus = function () {
        $disable.attr('disabled', !$table.bootstrapTable('getSelections').length);
        $edit.attr('disabled', !($table.bootstrapTable('getSelections').length === 1));
    };

    /**
     * Check Box事件
     */
    $table.on('check.bs.table uncheck.bs.table ' +
        'check-all.bs.table uncheck-all.bs.table', function () {
        initButtonStatus();
    });

    $table.on('load-error.bs.table', function (status) {
        Logger.warn(status);
        Messager.warn("加载数据失败");
    });

    $create.click(function () {
        save({}, '新增');
    });

    $edit.click(function () {
        if (!$edit.attr('disabled')) {
            save($table.bootstrapTable('getSelections')[0], '编辑');
        }
    });

    /**
     * 保存
     * @param data
     * @param index
     * @param title
     */
    var save = function (data, title) {
        laytpl($('#saveModelTpl').html()).render(data, function (render) {
            var index = layer.open({
                type: 1,
                title: title,
                area: ['500px', '400px'],
                shadeClose: false,
                content: render,
                btn: ['保存', '取消'],
                yes: function () {
                    var $saveForm = $('#saveForm');
                    $saveForm.validate({
                        errorClass: "has-error",
                        rules: {
                            name: "required",
                            code: "required",
                            domain: "required",
                            rank: "required"
                        },
                        onkeyup: false
                    });
                    if ($saveForm.valid()) {
                        Ajax.Post('save', JSON.stringify(Commons.formData("#saveForm")), function (result) {
                            if (result.code === 200) {
                                Messager.success('保存成功');
                                $search.click();
                                layer.close(index);
                                $("#saveModal").modal("hide");
                            } else {
                                Messager.warn(result.msg);
                            }
                        });
                    }
                },
                close: function () {
                    layer.close(index);
                }
            });
        });


    };

    $disable.click(function () {
        if (!$disable.attr('disabled')) {
            layer.confirm('是否确认禁用所选系统?', {
                icon: 3,
                title: '提示'
            }, function (index) {
                layer.close(index);
            });
        }
    });
});
