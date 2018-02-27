<header class="main-header">
    <div class="logo">
        <span class="logo-mini"><b>Y</b>C</span>
        <span class="logo-lg"><b>Yuna</b> Console</span>
    </div>

    <nav class="navbar navbar-static-top" role="navigation">
        <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
            <span class="sr-only">Toggle navigation</span>
        </a>
        <div class="collapse navbar-collapse pull-left" id="navbar-collapse">
            <ul class="nav navbar-nav">
            <#if session_business_systems??><#list session_business_systems as businessSystem>
                <li class="<#if currentBusinessSystem?? && businessSystem.code == session_current_business_system>active</#if>">
                    <a
                            href="${request.contextPath}/location/${businessSystem.code}">${businessSystem.name} <span
                            class="sr-only">(current)</span></a></li>

            </#list></#if>
            </ul>
        </div>
        <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
                <li class="dropdown messages-menu">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <i class="fa fa-envelope-o"></i>
                        <span class="label label-success">0</span>
                    </a>
                    <ul class="dropdown-menu">
                        <li class="header">0条消息</li>
                        <li>
                            <ul class="menu">
                                <li>

                                </li>
                            </ul>
                        </li>
                        <li class="footer"><a href="#">更多</a></li>
                    </ul>
                </li>

                <li class="dropdown notifications-menu">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <i class="fa fa-bell-o"></i>
                        <span class="label label-warning">0</span>
                    </a>
                    <ul class="dropdown-menu">
                        <li class="header">0个通知</li>
                        <li>
                            <ul class="menu">
                                <li>

                                </li>
                            </ul>
                        </li>
                        <li class="footer"><a href="#">更多</a></li>
                    </ul>
                </li>

                <li class="dropdown tasks-menu">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <i class="fa fa-flag-o"></i>
                        <span class="label label-danger">0</span>
                    </a>
                    <ul class="dropdown-menu">
                        <li class="header">0个任务</li>
                        <li>
                            <ul class="menu">
                                <li>

                                </li>
                            </ul>
                        </li>
                        <li class="footer">
                            <a href="#">更多</a>
                        </li>
                    </ul>
                </li>
                <li class="dropdown user user-menu">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <img src="/static/lib/admin/img/user4-128x128.jpg" class="user-image" alt="User Image">
                        <span class="hidden-xs">${session_user.nickname!"Unknown User"}</span>
                    </a>
                    <ul class="dropdown-menu">
                        <li class="user-header">
                            <img src="/static/lib/admin/img/user4-128x128.jpg" class="img-circle" alt="User Image">
                            <p>
                            ${session_user.nickname!"Unknown User"}<br/>
                            ${session_user.email!""}
                            </p>
                        </li>
                        <li class="user-footer">
                            <div class="pull-left">
                                <a data-addtab="current_user_info" data-url="/user/info.html" href="#"
                                   class="btn btn-default btn-flat">
                                    <i class="fa fa-user"></i>
                                    <span>信息&nbsp;&nbsp;</span>
                                </a>
                            </div>
                            <div class="pull-right">
                                <a href="/logout" class="btn btn-default btn-flat">
                                    <i class="fa fa-power-off"></i>
                                    <span>退出&nbsp;&nbsp;</span>
                                </a>
                            </div>
                        </li>
                    </ul>
                </li>
                <li>
                    <a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a>
                </li>
            </ul>
        </div>
    </nav>
</header>

