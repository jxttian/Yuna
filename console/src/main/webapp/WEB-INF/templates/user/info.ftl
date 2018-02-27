<#assign title = "用户管理-Yuna">
<#import "/layout.ftl" as l>
<#macro link>
</#macro>
<#macro script>
<script src="${request.contextPath}/static/app/js/user/info.js"></script>
</#macro>
<@detail/>
<#macro detail>
    <@l.layout title="${title}" enabled=true script=script link=link iframe=true>
    <section class="content-header">
        <h1>
            用户信息
            <small>User Info</small>
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
                        <div class="container-fluid">
                            <form id="saveForm">
                                <div class="row">
                                    <div class="col-md-6"><label>邮箱</label>
                                        <input type="hidden" name="id" value="${user.id}">
                                        <input type="email" class="form-control" name="email"
                                               placeholder="邮箱" value="${user.email}" disabled>
                                    </div>
                                    <div class="col-md-6"><label>手机号码</label>
                                        <input type="text" class="form-control" name="phone"
                                               placeholder="手机号码" value="${user.phone}">
                                    </div>
                                </div>
                                <br/>
                                <div class="row">
                                    <div class="col-md-6"><label>昵称</label>
                                        <input type="text" class="form-control" name="nickname"
                                               placeholder="昵称" value="${user.nickname}">
                                    </div>
                                </div>
                                <br/>
                                <div class="row">
                                    <div class="col-md-6"><label>原始密码</label>
                                        <input type="password" class="form-control" name="oldPassword"
                                               placeholder="原始密码">
                                    </div>
                                </div>
                                <br/>
                                <div class="row">
                                    <div class="col-md-6"><label>密码</label>
                                        <input type="password" class="form-control" name="password"
                                               placeholder="密码">
                                    </div>
                                    <div class="col-md-6"><label>确认密码</label>
                                        <input type="password" class="form-control" name="affirmPassword"
                                               placeholder="确认密码">
                                    </div>
                                </div>
                                <br/>
                            </form>
                        </div>
                    </div>
                    <div class="box-footer">
                        <button id="saveUserInfo" type="button" class="btn btn-primary">
                            <i class="glyphicon glyphicon-search"></i> 保存
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </section>
    </@l.layout>
</#macro>