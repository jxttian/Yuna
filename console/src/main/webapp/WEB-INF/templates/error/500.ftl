<#assign title = "500 Oops">
<#import "/layout.ftl" as l>
<#macro link>
</#macro>
<#macro script>
</#macro>
<@detail/>
<#macro detail>
    <@l.layout title="${title}" enabled=true script=script link=link iframe=true>
    <section class="content-header">
        <h1>
            500 Error Page
        </h1>
        <ol class="breadcrumb">
            <li><a href="${request.contextPath}/index.html"><i class="fa fa-dashboard"></i> Home</a></li>
            <li class="active">500 error</li>
        </ol>
    </section>

    <section class="content">
        <div class="error-page">
            <h2 class="headline text-red">500</h2>
            <div class="error-content">
                <h3><i class="fa fa-warning text-red"></i> Oops! ${msg!"系统内部错误"}</h3>
                <p>
                    We will work on fixing that right away.
                    Meanwhile, you may <a href="${request.contextPath}/index.html">return to dashboard</a> or try using
                    the search form.
                </p>
                <form class="search-form">
                    <div class="input-group">
                        <input type="text" name="search" class="form-control" placeholder="Search">
                        <div class="input-group-btn">
                            <button type="submit" name="submit" class="btn btn-danger btn-flat"><i
                                    class="fa fa-search"></i>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </section>
    </@l.layout>
</#macro>