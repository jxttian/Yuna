<script src="${request.contextPath}/static/lib/jQuery/jquery-2.2.3.min.js"></script>
<script src="${request.contextPath}/static/lib/admin/plugins/pace/pace.min.js"></script>
<script src="${request.contextPath}/static/lib/bootstrap/js/bootstrap.min.js"></script>
<script src="${request.contextPath}/static/lib/admin/js/app.min.js"></script>
<script src="${request.contextPath}/static/lib/admin/plugins/iCheck/icheck.min.js"></script>
<script src="${request.contextPath}/static/lib/admin/plugins/slimScroll/jquery.slimscroll.min.js"></script>
<script src="${request.contextPath}/static/lib/admin/plugins/select2/select2.full.js"></script>
<script src="${request.contextPath}/static/lib/admin/plugins/select2/i18n/zh-CN.js"></script>

<script src="${request.contextPath}/static/lib/bootstrap-table/bootstrap-table.min.js"></script>
<script src="${request.contextPath}/static/lib/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="${request.contextPath}/static/lib/bootstrap-table/extensions/editable/bootstrap-table-editable.js"></script>
<script src="${request.contextPath}/static/lib/bootstrap-table/extensions/editable/bootstrap-editable.js"></script>

<script src="${request.contextPath}/static/lib/other/layer/layer.js"></script>
<script src="${request.contextPath}/static/lib/other/layer/laytpl.js"></script>
<script src="${request.contextPath}/static/lib/other/laydate/laydate.js"></script>

<script src="${request.contextPath}/static/lib/jQuery/jquery.validate.min.js"></script>

<script src="${request.contextPath}/static/lib/upload/js/fileinput.min.js"></script>
<script src="${request.contextPath}/static/lib/upload/js/locales/zh.js"></script>

<script src="${request.contextPath}/static/lib/other/underscore-min.js"></script>
<script src="${request.contextPath}/static/app/js/common.js"></script>
<script src="${request.contextPath}/static/app/js/formatter.js"></script>

<script type="text/javascript">
    $(document).ajaxStart(function () {
        Pace.restart();
    });
    $(document).ready(function () {
        $('.reset-modal').on('hide.bs.modal', function () {
            var forms = $(this).find('form');
            if (forms.length > 0) {
                _.each(forms, function (form) {
                    form.reset();
                });
            }
        });
        $('input[type="checkbox"].minimal-green, input[type="radio"].minimal-green').iCheck({
            checkboxClass: 'icheckbox_minimal-green',
            radioClass: 'iradio_minimal-green'
        });
        $(".select2").select2({
            language: "zh-CN"
        });
    });
</script>