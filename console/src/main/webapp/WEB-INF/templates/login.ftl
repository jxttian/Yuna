<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>登录-Yuna</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">

    <link rel="stylesheet" href="${request.contextPath}/static/lib/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${request.contextPath}/static/lib/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="${request.contextPath}/static/lib/ionicons/css/ionicons.min.css">
    <link rel="stylesheet" href="${request.contextPath}/static/lib/admin/plugins/pace/pace.min.css">
    <link rel="stylesheet" href="${request.contextPath}/static/lib/admin/plugins/iCheck/all.css">
    <link rel="stylesheet" href="${request.contextPath}/static/lib/admin/plugins/select2/select2.min.css">
    <link rel="stylesheet" href="${request.contextPath}/static/lib/admin/css/AdminLTE.min.css">
    <link rel="stylesheet" href="${request.contextPath}/static/lib/admin/css/skins/_all-skins.min.css">
    <link rel="stylesheet" href="${request.contextPath}/static/lib/other/layer/skin/layer.css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="/static/lib/other/ie8/html5shiv.min.js"></script>
    <script src="/static/lib/other/ie8/respond.min.js"></script>
    <![endif]-->
</head>
<body class="hold-transition login-page">
<div class="login-box">
    <div class="login-logo">
        <b>Yuna</b>Console
    </div>
    <div class="login-box-body">
        <form action="${request.contextPath}/login" method="post">
            <div class="form-group has-feedback">
                <input type="email" name="identification" class="form-control" placeholder="邮箱" required>
                <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input type="password" name="password" class="form-control" placeholder="密码" required>
                <span class="glyphicon glyphicon-lock form-control-feedback"></span>
            </div>
            <div class="row">
                <div class="col-xs-5">
                    <img class="form-control login-font" title="点击更换验证码" style="padding: 0px 0px" src="kaptcha.jpg"
                         id="kaptchaImage"/>
                </div>
                <div class="col-xs-7">
                    <input type="text" class="form-control" name="verification" placeholder="验证码" required/>
                </div>
            </div>
            <br/>
            <div class="row">
                <div class="col-xs-8">
                    <div class="checkbox icheck">
                        <label>
                            <input type="checkbox"> 记住我
                        </label>
                    </div>
                </div>
                <div class="col-xs-4">
                    <button type="submit" class="btn btn-primary btn-block btn-flat">登 录</button>
                </div>
            </div>
        </form>
        <div class="social-auth-links text-center">
            <p></p>
            <p></p>
        </div>
        <a href="#">忘记密码？</a><br>
    </div>
    <div class="login-box-msg">
        <strong>Copyright &copy; 2016 <a href="#">Pandora</a>.</strong> All rights reserved.
    </div>
</div>
<script src="${request.contextPath}/static/lib/jQuery/jquery-2.2.3.min.js"></script>
<script src="${request.contextPath}/static/lib/admin/plugins/pace/pace.min.js"></script>
<script src="${request.contextPath}/static/lib/bootstrap/js/bootstrap.min.js"></script>
<script src="${request.contextPath}/static/lib/admin/plugins/iCheck/icheck.min.js"></script>
<script src="${request.contextPath}/static/lib/other/layer/layer.js"></script>
<script src="${request.contextPath}/static/lib/other/underscore-min.js"></script>
<script src="${request.contextPath}/static/lib/jQuery/jquery.validate.min.js"></script>
<script src="${request.contextPath}/static/app/js/common.js"></script>
<script type="text/javascript">
    $(document).ajaxStart(function () {
        Pace.restart();
    });
</script>
<script>
    <#if errorMsg??>
    Messager.error("${errorMsg}");
    </#if>
    $(function () {
        $('input').iCheck({
            checkboxClass: 'icheckbox_square-blue',
            radioClass: 'iradio_square-blue',
            increaseArea: '20%' // optional
        });
    });
    $('#kaptchaImage').click(function () {
        $(this).attr('src', '${request.contextPath}/kaptcha.jpg?' + Math.floor(Math.random() * 100));
    });
    if (self !== top) {
        parent.window.location.replace(window.location.href);
    }
</script>
</body>
</html>
