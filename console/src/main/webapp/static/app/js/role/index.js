var RoleConst = {
    SidPermissionMap: {},
    Query: function (params) {
        return Commons.formData("#searchForm", params);
    },
    /**
     * @return {string}
     */
    OperatesFormatter: function (value) {
        return '<div class="btn-group" role="group">' +
            '<a class="btn btn-default btn-xs bind-permission-modal" title="绑定权限"><li class="fa fa-user-secret"></li></a>' +
            '<a class="btn btn-default btn-xs edit-modal" title="编辑"><li class="fa fa-edit"></li></a>' +
            '<a class="btn btn-warning btn-xs remove-modal" title="删除"><li class="fa fa-remove"></li></a>' +
            '</div>';
    },
    PermissionSelectedFormatter: function (value, row) {
        if (row.selected) {
            return {
                checked: true
            }
        }
        return value;
    },
    Operates: {
        'click .bind-permission-modal': function (e, value, role, index) {
            Ajax.Get('/role/' + role.id + '/permissions', null, function (result) {
                RoleConst.SidPermissionMap = {};
                _.forEach(result.data, function (item) {
                    if (!RoleConst.SidPermissionMap[item.sid]) {
                        RoleConst.SidPermissionMap[item.sid] = [];
                    }
                    RoleConst.SidPermissionMap[item.sid].push(item.id);
                });
                Ajax.Get('/system/list.json', null, function (result) {
                    var businessSystemTable = $("#businessSystemTable");
                    var permissionTable = $("#permissionTable");
                    var saveBindPermissionBtn = $("#saveBindPermissionBtn");
                    businessSystemTable.bootstrapTable('load', result.rows);
                    businessSystemTable.on('load-error.bs.table', function (status) {
                        Logger.warn(status);
                        Messager.warn("加载数据失败");
                    });
                    permissionTable.on('load-error.bs.table', function (status) {
                        Logger.warn(status);
                        Messager.warn("加载数据失败");
                    });
                    permissionTable.bootstrapTable('load', []);
                    businessSystemTable.unbind('check.bs.table');
                    businessSystemTable.on('check.bs.table', function (e, system) {
                        Ajax.Get('/permission/list.json?sid=' + system.id, null, function (result) {
                            var permissions = permissionTable.bootstrapTable('getSelections');
                            if (permissions && permissions.length > 0) {
                                RoleConst.SidPermissionMap[permissions[0].sid] = [];
                                _.forEach(permissions, function (permission) {
                                    RoleConst.SidPermissionMap[permission.sid].push(permission.id);
                                });
                            }

                            _.forEach(result.rows, function (permission) {
                                _.forEach(RoleConst.SidPermissionMap[system.id], function (permissionId) {
                                    if (permission.id === permissionId) {
                                        permission.selected = true;
                                    }
                                });
                            });
                            permissionTable.bootstrapTable('load', result.rows);
                        });
                    });
                    $("#bindPermissionModel").modal('show');
                    saveBindPermissionBtn.unbind('click');
                    saveBindPermissionBtn.click(function () {
                        var permissions = permissionTable.bootstrapTable('getSelections');
                        if (permissions && permissions.length > 0) {
                            RoleConst.SidPermissionMap[permissions[0].sid] = [];
                            _.forEach(permissions, function (permission) {
                                RoleConst.SidPermissionMap[permission.sid].push(permission.id);
                            });
                        }
                        var pids = _.flatten(_.values(RoleConst.SidPermissionMap));
                        Ajax.Post('/role/' + role.id + '/save_permissions', JSON.stringify(pids), function (result) {
                            if (result.code === 200) {
                                Messager.success('权限保存成功');
                                $("#bindPermissionModel").modal('hide');
                            } else {
                                Messager.warn(result.msg);
                            }
                        });
                    });
                });
            });
        },
        'click .edit-modal': function (e, value, row, index) {
            RoleConst.Save(row, '编辑');
        },
        'click .remove-modal': function (e, value, row, index) {
            RoleConst.Delete([row.id]);
        }
    },
    Save: function (data, title) {
        laytpl($('#saveModelTpl').html()).render(data, function (render) {
            var index = layer.open({
                type: 1,
                title: title,
                area: ['400px', '400px'],
                shadeClose: false,
                content: render,
                btn: ['保存', '取消'],
                yes: function () {
                    var $saveForm = $('#saveForm');
                    $saveForm.validate({
                        errorClass: "has-error",
                        rules: {
                            code: "required",
                            name: "required"
                        },
                        onkeyup: false
                    });
                    if ($saveForm.valid()) {
                        Ajax.Post('save', JSON.stringify(Commons.formData("#saveForm")), function (result) {
                            if (result.code === 200) {
                                Messager.success('保存成功');
                                $('#search').click();
                                $("#saveModal").modal("hide");
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
    }
};

$(document).ready(function () {
    var $table = $('#table'),
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
    $table.on('check.bs.table uncheck.bs.table ' +
        'check-all.bs.table uncheck-all.bs.table', function () {
        initButtonStatus();
    });

    $table.on('load-error.bs.table', function (status) {
        Logger.warn(status);
        Messager.warn("加载数据失败");
    });

    $create.click(function () {
        RoleConst.Save({}, '新增');
    });

    $disable.click(function () {
        if (!$disable.attr('disabled')) {
            layer.confirm('是否确认禁用所选角色?', {
                icon: 3,
                title: '提示'
            }, function (index) {
                layer.close(index);
            });
        }
    });
});
