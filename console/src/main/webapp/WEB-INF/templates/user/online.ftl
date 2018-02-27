<#assign title = "在线用户管理-Yuna">
<#import "/layout.ftl" as l>
<#macro link>
</#macro>
<#macro script>
<script src="${request.contextPath}/static/app/js/user/online.js"></script>
</#macro>
<@detail/>
<#macro detail>
    <@l.layout title="${title}" enabled=true script=script link=link iframe=true>
    <section class="content-header">
        <h1>
            在线用户管理
            <small>Online User Manage</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="${request.contextPath}/index.html"><i class="fa fa-dashboard"></i> 首页</a></li>
            <li class="active">Here</li>
        </ol>
    </section>
    <section class="content">
        <div class="row">
            <div class="col-xs-12">
                <div class="box box-primary">
                    <div class="box-body">
                        <table id="table"
                               data-classes="table table-no-bordered"
                               data-toggle="table"
                               data-show-toggle="true"
                               data-show-columns="true"
                               data-click-to-select="false"
                               data-unique-id="sessionId"
                               data-id-field="sessionId">
                            <thead>
                            <tr>
                                <th data-field="" data-checkbox="true"></th>
                                <th data-field="operate" data-formatter="UserOnlineConst.OperateFormatter"
                                    data-events="UserOnlineConst.Operates">操作
                                </th>
                                <th data-field="sessionId">会话</th>
                                <th data-field="email">邮箱</th>
                                <th data-field="phone" data-visible="false">手机号码</th>
                                <th data-field="nickname" data-visible="false">昵称</th>
                                <th data-field="exts.referer" data-formatter="Formatter.Text">来源</th>
                                <th data-field="loginTime" data-formatter="Formatter.DateTime">登录时间</th>
                                <th data-field="loginHost">登录IP</th>
                                <th data-field="exts.userAgent">登录信息</th>
                                <th data-field="lastOperate" data-formatter="Formatter.Text">最后操作</th>
                                <th data-field="lastOperateTime" data-formatter="Formatter.DateTime">最后操作时间</th>
                                <th data-field="lastOperateHost">最后操作IP</th>
                            </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </section>
    </@l.layout>
</#macro>