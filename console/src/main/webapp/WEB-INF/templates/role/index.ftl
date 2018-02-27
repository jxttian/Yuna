<#assign title = "角色管理-Yuna">
<#import "/layout.ftl" as l>
<#macro link>
<link rel="stylesheet"
      href="${request.contextPath}/static/lib/bootstrap-table/extensions/tree-column/bootstrap-table-tree-column.css">
</#macro>
<#macro script>
<script src="${request.contextPath}/static/lib/bootstrap-table/extensions/tree-column/bootstrap-table-tree-column.js"></script>
<script src="${request.contextPath}/static/app/js/role/index.js"></script>
</#macro>
<@detail/>
<#macro detail>
    <@l.layout title="${title}" enabled=true script=script link=link iframe=true>
    <section class="content-header">
        <h1>
            角色管理
            <small>Role Manage</small>
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
                               data-url="${request.contextPath}/role/list.json"
                               data-side-pagination="server"
                               data-pagination="true"
                               data-click-to-select="false"
                               data-query-params="RoleConst.Query"
                               data-id-field="id"
                               data-unique-id="id"
                               data-sort-name="id"
                               data-sort-order="desc"
                               data-page-list="[10, 20, 50, 100]">
                            <thead>
                            <tr>
                                <th data-field="" data-checkbox="true"></th>
                                <th data-field="operate" data-formatter="RoleConst.OperatesFormatter"
                                    data-events="RoleConst.Operates">操作
                                </th>
                                <th data-field="code">编码</th>
                                <th data-field="name">名称</th>
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
                    <div class="col-md-12"><label>编码</label>
                        <input type="hidden" name="id" value="{{d.id}}">
                        <input type="text" class="form-control" name="code"
                               placeholder="编码" value="{{= d.code}}" {{# if(d.id) {}} readonly {{#}}}>
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-md-12"><label>名称</label>
                        <input type="text" class="form-control" name="name"
                               placeholder="名称" value="{{= d.name}}">
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-md-12"><label>是否可用</label>
                        <select class="form-control" name="enable">
                            <option value="0" {{# if(d.enable==0){ }} selected {{# } }}>否</option>
                            <option value="1" {{# if(d.enable==1){ }} selected {{# } }}>是</option>
                        </select>
                    </div>
                </div>
                <br/>
            </div>
        </form>
    </script>

    <script id="bindPermissionModelTpl" type="text/html">
        <form id="saveForm">
            <div class="container-fluid">
                <br/>
                <div class="row">
                    <div class="col-md-12"><label>编码</label>
                        <input type="hidden" name="id" value="{{d.id}}">
                        <input type="text" class="form-control" name="code"
                               placeholder="编码" value="{{= d.code}}" {{# if(d.id) {}} readonly {{#}}}>
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-md-12"><label>名称</label>
                        <input type="text" class="form-control" name="name"
                               placeholder="名称" value="{{= d.name}}">
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-md-12"><label>是否可用</label>
                        <select class="form-control" name="enable">
                            <option value="0" {{# if(d.enable==0){ }} selected {{# } }}>否</option>
                            <option value="1" {{# if(d.enable==1){ }} selected {{# } }}>是</option>
                        </select>
                    </div>
                </div>
                <br/>
            </div>
        </form>
    </script>

    <div class="modal fade reset-modal" id="bindPermissionModel" role="dialog">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="gridSystemModalLabel">选择权限</h4>
                </div>
                <div class="modal-body" style="overflow-y: scroll; max-height:600px;">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="row">
                                    <div class="col-md-4">
                                        <label>
                                            业务系统
                                        </label>
                                        <table id="businessSystemTable"
                                               data-classes="table table-no-bordered table-hover"
                                               data-toggle="table"
                                               data-pagination="false"
                                               data-click-to-select="true"
                                               data-id-field="id"
                                               data-unique-id="id"
                                               data-sort-order="desc">
                                            <thead>
                                            <tr>
                                                <th data-field="" data-radio="true"></th>
                                                <th data-field="code">系统编码</th>
                                                <th data-field="name">系统名称</th>
                                            </tr>
                                            </thead>
                                        </table>
                                    </div>
                                    <div class="col-md-8">
                                        <table id="permissionTable"
                                               data-classes="table table-no-bordered table-hover"
                                               data-toggle="table"
                                               data-tree-show-field="code"
                                               data-pagination="false"
                                               data-click-to-select="true"
                                               data-id-field="id"
                                               data-unique-id="id"
                                               data-sort-order="desc">
                                            <thead>
                                            <tr>
                                                <th data-field="" data-checkbox="true" data-formatter="RoleConst.PermissionSelectedFormatter"></th>
                                                <th data-field="code">编码</th>
                                                <th data-field="icon" data-formatter="Formatter.Icon">图标</th>
                                                <th data-field="name">名称</th>
                                            </tr>
                                            </thead>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button id="saveBindPermissionBtn" type="button" class="btn btn-primary">保存</button>
                </div>
            </div>
        </div>
    </div>
    </@l.layout>
</#macro>