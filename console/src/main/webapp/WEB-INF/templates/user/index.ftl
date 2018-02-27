<#assign title = "用户管理-Yuna">
<#import "/layout.ftl" as l>
<#macro link>
</#macro>
<#macro script>
<script src="${request.contextPath}/static/app/js/user/index.js"></script>
</#macro>
<@detail/>
<#macro detail>
    <@l.layout title="${title}" enabled=true script=script link=link iframe=true>
    <section class="content-header">
        <h1>
            用户管理
            <small>User Manage</small>
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
                    <div class="box-header">
                        <h3 class="box-title">筛选</h3>
                    </div>
                    <div class="box-body">
                        <form id="searchForm" onkeydown="if(event.keyCode===13){return false;}">
                            <div class="row">
                                <div class="col-md-2">
                                    <input type="text" name="id" class="form-control" placeholder="用户ID">
                                </div>
                                <div class="col-md-2">
                                    <input type="email" name="email" class="form-control" placeholder="邮箱">
                                </div>
                                <div class="col-md-2">
                                    <input type="text" name="phone" class="form-control" placeholder="手机号码">
                                </div>
                                <div class="col-md-6">
                                    <button id="reset" type="reset" class="btn btn-default">
                                        <i class="glyphicon glyphicon-refresh"></i> 重置
                                    </button>
                                    <span class="pull-right">&nbsp;</span>
                                    <button id="search" type="button" class="btn btn-primary">
                                        <i class="glyphicon glyphicon-search"></i> 搜索
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

                <div class="box box-primary">
                    <div class="box-body">
                        <div id="toolbar">
                            <div class="btn-group" role="group">
                                <a id="create" class="btn btn-default">
                                    <i class="glyphicon glyphicon-plus"></i> 添加
                                </a>
                                <a id="edit" class="btn btn-default" disabled>
                                    <i class="glyphicon glyphicon-edit"></i> 编辑
                                </a>
                            </div>

                            <a id="clearCache" class="btn btn-default">
                                <i class="glyphicon glyphicon-trash"></i> 清除缓存
                            </a>
                        </div>
                        <table id="table"
                               data-toolbar="#toolbar"
                               data-classes="table table-no-bordered"
                               data-toggle="table"
                               data-show-toggle="true"
                               data-show-columns="true"
                               data-height="600"
                               data-url="${request.contextPath}/user/list.json"
                               data-side-pagination="server"
                               data-pagination="true"
                               data-click-to-select="true"
                               data-query-params="UserConst.Query"
                               data-id-field="id"
                               data-unique-id="id"
                               data-sort-name="id"
                               data-sort-order="desc"
                               data-page-list="[10, 20, 50, 100]">
                            <thead>
                            <tr>
                                <th data-field="" data-checkbox="true"></th>
                                <th data-field="" data-formatter="Formatter.Index">#</th>
                                <th data-field="email">邮箱</th>
                                <th data-field="phone">手机号码</th>
                                <th data-field="nickname">昵称</th>
                                <th data-field="enable" data-formatter="Formatter.Boolean">可用</th>
                                <th data-field="creationTime" data-formatter="Formatter.DateTime">创建时间</th>
                            </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <script id="saveModelTpl" type="text/html">
        <form id="saveForm">
            <div class="container-fluid">
                <br/>
                <div class="row">
                    <div class="col-md-6"><label>邮箱</label>
                        <input type="hidden" name="id" value="{{d.id}}">
                        <input type="email" class="form-control" name="email"
                               placeholder="邮箱" value="{{= d.email}}">
                    </div>
                    <div class="col-md-6"><label>手机号码</label>
                        <input type="text" class="form-control" name="phone"
                               placeholder="手机号码" value="{{= d.phone}}">
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-md-6"><label>昵称</label>
                        <input type="text" class="form-control" name="nickname"
                               placeholder="昵称" value="{{= d.nickname}}">
                    </div>
                    <div class="col-md-6">
                        <label>是否可用</label>
                        <select class="form-control" name="enable">
                            <option value="0" {{#if(d.enable==0){ }} selected {{# } }}>否</option>
                            <option value="1" {{#if(d.enable==1){ }} selected {{# } }}>是</option>
                        </select>
                    </div>
                </div>
                <br/>
                {{# if(d.id) {}}
                <div class="row">
                    <div class="col-md-6"><label>原始密码</label>
                        <input type="password" class="form-control" name="oldPassword"
                               placeholder="原始密码">
                    </div>
                </div>
                <br/>
                {{#}}}
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
                <div class="row">
                    <div class="col-md-12"><label>选择角色</label>
                        <select class="form-control select2" id="selectRole" name="roles" style="width: 100%" multiple>
                            <#if roles??><#list roles as role>
                                <option value="${role.id}">${role.code}-${role.name}</option>
                            </#list></#if>
                        </select>
                    </div>
                </div>
                <br/>
            </div>
        </form>
    </script>
    </@l.layout>
</#macro>