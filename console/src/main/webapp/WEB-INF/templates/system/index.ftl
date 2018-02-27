<#assign title = "业务系统管理-Yuna">
<#import "/layout.ftl" as l>
<#macro link>
</#macro>
<#macro script>
<script src="${request.contextPath}/static/app/js/system/index.js"></script>
</#macro>
<@detail/>
<#macro detail>
    <@l.layout title="${title}" enabled=true script=script link=link iframe=true>
    <section class="content-header">
        <h1>
            业务系统管理
            <small>Business System Manage</small>
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
                                    <input type="text" name="code" class="form-control" placeholder="编码">
                                </div>
                                <div class="col-md-2">
                                    <input type="text" name="name" class="form-control" placeholder="名称">
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
                               data-url="${request.contextPath}/system/list.json"
                               data-side-pagination="server"
                               data-pagination="true"
                               data-click-to-select="true"
                               data-query-params="SystemConst.Query"
                               data-id-field="id"
                               data-unique-id="id"
                               data-sort-name="rank"
                               data-sort-order="desc"
                               data-page-list="[10, 20, 50, 100]">
                            <thead>
                            <tr>
                                <th data-field="" data-checkbox="true"></th>
                                <th data-field="" data-formatter="Formatter.Index">#</th>
                                <th data-field="id">ID</th>
                                <th data-field="code">编码</th>
                                <th data-field="name">名称</th>
                                <th data-field="domain">域名</th>
                                <th data-field="rank" data-sortable="true">等级分值</th>
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
                    <div class="col-md-5"><label>编码</label>
                        <input type="hidden" name="id" value="{{d.id}}">
                        <input type="text" class="form-control" name="code"
                               placeholder="编码" value="{{= d.code}}" {{# if(d.id) {}} disabled {{#}}}>
                    </div>
                    <div class="col-md-7"><label>名称</label>
                        <input type="text" class="form-control" name="name"
                               placeholder="名称" value="{{= d.name}}">
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-md-8"><label>域名</label>
                        <input type="text" class="form-control" name="domain"
                               placeholder="域名" value="{{= d.domain}}">
                    </div>
                    <div class="col-md-4"><label>等级分值</label>
                        <input type="number" class="form-control" name="rank"
                               placeholder="等级分值" value="{{= d.rank}}">
                    </div>
                </div>
                <br/>
            </div>
        </form>
    </script>
    </@l.layout>
</#macro>