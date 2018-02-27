$(document).ready(function () {
    $("#saveUserInfo").click(function () {
        var $saveForm = $('#saveForm');
        $saveForm.validate({
            errorClass: "has-error",
            rules: {
                email: "required",
                phone: "required",
                nickname: "required"
            },
            onkeyup: false
        });
        if ($saveForm.valid()) {
            var data = Commons.formData("#saveForm");
            if (data.password && !data.affirmPassword) {
                Messager.warn("两次密码不一致，请重新输入");
                return;
            }
            if (data.password && data.affirmPassword && data.password !== data.affirmPassword) {
                Messager.warn("两次密码不一致，请重新输入");
                return;
            }
            if (data.password && !data.oldPassword) {
                Messager.warn("请输入原始密码");
                return;
            }
            Ajax.Post('/user/info/save', JSON.stringify(data), function (result) {
                if (result.code === 200) {
                    Messager.success('保存成功');
                    location.reload()
                } else {
                    Messager.warn(result.msg);
                }
            });
        }
    });
});
