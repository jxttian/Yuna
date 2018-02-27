<#macro head title>
<meta charset="utf-8">
<meta http-equiv="x-ua-compatible" content="ie=edge">
    <#if !title??||title?length==0>
    <title>Yuna</title>
    <#else>
    <title>${title}</title>
    </#if>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<meta name="description" content="">
<meta name="Keywords" content="">
    <#include "links.ftl"/>
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
<script src="/static/lib/other/ie8/html5shiv.min.js"></script>
<script src="/static/lib/other/ie8/respond.min.js"></script>
<![endif]-->
</#macro>