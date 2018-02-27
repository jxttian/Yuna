<#assign title = "首页-Yuna">
<#import "/layout.ftl" as l>
<#macro link>
<style>
    .tab-pane {
        height: 100%;
    }
</style>

</#macro>
<#macro script>
<script src="${request.contextPath}/static/lib/bootstrap-addtab/bootstrap.addtabs.js"></script>
<script src="${request.contextPath}/static/app/js/yuna.js"></script>
</#macro>
<@detail/>
<#macro detail>
    <@l.layout title="${session_current_business_system!'Yuna'}" enabled=true script=script link=link iframe=false>
    <div class="content-wrapper" style="height: 100%">
        <div class="nav-tabs-custom" style="cursor: move; height: 100%">
            <ul class="nav nav-tabs" style="height: 5%">
            </ul>
            <div class="tab-content no-padding" style="width: 100%; height: 96%">
                <!-- Morris chart - Sales -->
            </div>
        </div>
    </div>
    </@l.layout>
</#macro>