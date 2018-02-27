var PermissionConst = {
    /**
     * @return {boolean}
     */
    Query: function (params) {
        params = Commons.formData("#searchForm", params);
        if (params.sid) {
            return params;
        } else {
            return false;
        }
    },
    /**
     * @return {string}
     */
    TypeFormatter: function (value) {
        switch (value) {
            case 1:
                return '<span class="label label-success">页面</span>';
            case 2:
                return '<span class="label label-primary">链接</span>';
            case 3:
                return '<span class="label label-default">按钮</span>';
            case 4:
                return '<span class="label label-warning">标签</span>';
        }
    },
    /**
     * @return {string}
     */
    OperatesFormatter: function (value) {
        return '<div class="btn-group" role="group">' +
            '<a class="btn btn-default btn-xs create-sub-modal" title="添加子权限"><li class="fa fa-plus"></li></a>' +
            '<a class="btn btn-default btn-xs edit-modal" title="编辑"><li class="fa fa-edit"></li></a>' +
            '<a class="btn btn-warning btn-xs remove-modal" title="删除"><li class="fa fa-remove"></li></a>' +
            '</div>';
    },
    Save: function (data, title) {
        data.sid = $("#searchSid").val();
        laytpl($('#saveModelTpl').html()).render(data, function (render) {
            var index = layer.open({
                type: 1,
                title: title,
                area: ['50%', '60%'],
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
                            rank: "required"
                        },
                        onkeyup: false
                    });
                    if ($saveForm.valid()) {
                        Ajax.Post('save', JSON.stringify(Commons.formData("#saveForm")), function (result) {
                            if (result.code === 200) {
                                Messager.success('保存成功');
                                $('#search').click();
                                layer.close(index);
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
    },
    Delete: function (ids) {
        layer.confirm("确认删除？", {
            icon: 3,
            title: '提示'
        }, function (confirmIndex) {
            Ajax.Delete('delete', JSON.stringify(ids), function (result) {
                if (result.code === 200) {
                    Messager.success('删除成功');
                    $('#search').click();
                } else {
                    Messager.warn(result.msg);
                }
            });
            layer.close(confirmIndex);
        });
    },
    Operates: {
        'click .create-sub-modal': function (e, value, row, index) {
            PermissionConst.Save({pid: row.id}, row.code + ':' + row.name + ' 添加子权限');
        },
        'click .edit-modal': function (e, value, row, index) {
            PermissionConst.Save(row, '编辑');
        },
        'click .remove-modal': function (e, value, row, index) {
            PermissionConst.Delete([row.id]);
        }
    }
};

$(document).ready(function () {
    var $table = $('#table'),
        $businessSystemTable = $('#businessSystemTable'),
        $create = $('#create'),
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
    };

    /**
     * Check Box事件
     */
    $businessSystemTable.on('check.bs.table uncheck.bs.table ' +
        'check-all.bs.table uncheck-all.bs.table', function (e, row) {
        $("#searchSid").val(row.id);
        $search.click();
    });

    $table.bootstrapTable({treeShowField: 'name'});

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
        if ($("#searchSid").val()) {
            PermissionConst.Save({pid: 0}, '新增');
        } else {
            Messager.warn("请选择业务系统");
        }
    });

    $disable.click(function () {
        if (!$disable.attr('disabled')) {
            layer.confirm('是否确认禁用所选权限?', {
                icon: 3,
                title: '提示'
            }, function (index) {
                layer.close(index);
            });
        }
    });
});
