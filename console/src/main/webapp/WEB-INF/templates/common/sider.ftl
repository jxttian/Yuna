<aside class="main-sidebar">
    <section class="sidebar">
        <div class="user-panel">
            <div class="pull-left image">
                <img src="/static/lib/admin/img/user4-128x128.jpg" class="img-circle" alt="User Image">
            </div>
            <div class="pull-left info">
                <p>${session_user.nickname!"Unknown User"}</p>
                <a href="#"><i class="fa fa-circle text-success"></i> 在线</a>
            </div>
        </div>

        <ul class="sidebar-menu">
            <li class="header">菜单</li>
        <#if current_permissions??>
            <#list current_permissions["0"] as root_permission>
                <#if current_permissions["${root_permission.id}"]??>
                    <li class="treeview">
                        <a href="#">
                            <i class="${root_permission.icon}"></i>
                            <span>${root_permission.name}</span>
                            <span class="pull-right-container">
                            <i class="fa fa-angle-left pull-right"></i>
                        </span>
                        </a>
                        <ul class="treeview-menu">
                            <#list current_permissions["${root_permission.id}"] as permission>
                                <li>
                                    <a data-addtab="${permission.code}" data-url="${permission.value}">
                                        <i class="${permission.icon}"></i>
                                        <span>${permission.name}&nbsp;&nbsp;</span>
                                    </a>
                                </li>
                            </#list>
                        </ul>
                    </li>
                <#else>
                    <li>
                        <a data-addtab="${root_permission.code}" data-url="${root_permission.value}">
                            <i class="${root_permission.icon}"></i>
                            <span>${root_permission.name}&nbsp;&nbsp;</span>
                        </a>
                    </li>
                </#if>
            </#list>
        </#if>
        </ul>
    </section>
</aside>
