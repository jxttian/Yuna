var UserOnlineConst = {
    /**
     * @return {string}
     */
    OperateFormatter: function () {
        return '<div class="btn-group" role="group">' +
            '<a class="btn btn-default btn-xs force-quit" title="强制剔除该用户"><li class="fa fa-sign-out" style="color: #ff0000"></li></a>' +
            '</div>';
    },
    Operates: {
        'click .force-quit': function (e, value, row, index) {
            layer.confirm('是否确认剔除当前在线用户?', {
                icon: 3,
                title: '提示'
            }, function (index) {
                Ajax.Delete('/user/online/' + row.sessionId, null, function (result) {
                    if (result.code === 200) {
                        Messager.success('操作成功');
                        $('#table').bootstrapTable('removeByUniqueId', row.sessionId);
                    } else {
                        Messager.error(result.msg);
                    }
                });
                layer.close(index);
            });
        }
    }
};

$(document).ready(function () {
    var $table = $('#table');
    Ajax.Get('/user/online.json', null, function (result) {
        if (result.code === 200) {
            $table.bootstrapTable('load', result.data);
        } else {
            Messager.error(result.msg);
        }
    });

    setInterval(function () {
        console.log('定时刷新在线用户列表');
        Ajax.Get('/user/online.json', null, function (result) {
            if (result.code === 200) {
                $table.bootstrapTable('load', result.data);
            } else {
                Messager.error(result.msg);
            }
        });
    }, 10000);
});
