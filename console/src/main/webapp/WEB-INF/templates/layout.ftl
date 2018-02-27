<#import "common/head.ftl" as head>
<#macro layout title enabled script link iframe>
<!DOCTYPE html>
<html>
<head>
    <@head.head title="${title}"/>
    <@link/>
</head>
<body class="hold-transition fixed skin-blue-light sidebar-mini">
<div class="wrapper">
    <#if iframe?? && !iframe>
        <#include "common/header.ftl">
        <#include "common/sider.ftl">
    </#if>

    <#if enabled?? && enabled>
        <#nested>
    </#if>


    <#if iframe?? && !iframe>
        <#include "common/setting.ftl">
    </#if>
    <#include "common/scripts.ftl">
    <@script/>
</div>
</body>
</html>
</#macro>