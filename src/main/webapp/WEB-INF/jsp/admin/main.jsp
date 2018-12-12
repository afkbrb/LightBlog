<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Java个人博客系统后台管理页面</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/plugin/jeasyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/plugin/jeasyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/customAdmin.css">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/plugin/jeasyui/jquery.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/plugin/jeasyui/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/plugin/jeasyui/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript">

        var url;

        function openTab(text, url, iconCls) {
            if ($("#tabs").tabs("exists", text)) {
                $("#tabs").tabs("select", text);
            } else {
                var content = "<iframe frameborder=0 scrolling='auto' style='width:100%;height:100%' src='${pageContext.request.contextPath}/admin/" + url + "'></iframe>";
                $("#tabs").tabs("add", {
                    title: text,
                    iconCls: iconCls,
                    closable: true,
                    content: content
                });
            }
        }


        function openPasswordModifyDialog() {
            $("#dlg").dialog("open").dialog("setTitle", "修改密码");
            url = "${pageContext.request.contextPath}/admin/user/updatePassword/${user.id}";
        }

        function modifyPassword() {
            $("#fm").form("submit", {
                url: url,
                onSubmit: function () {
                    var newPassword = $("#newPassword").val();
                    var newPassword2 = $("#newPassword2").val();
                    if (!$(this).form("validate")) {
                        return false;
                    }
                    if (newPassword != newPassword2) {
                        $.messager.alert("系统提示", "确认密码输入错误！");
                        return false;
                    }
                    return true;
                },
                success: function (result) {
                    // var result = eval('(' + result + ')');
                    if (result == "success") {
                        $.messager.alert("系统提示", "密码修改成功，下一次登录生效！");
                        resetValue();
                        $("#dlg").dialog("close");
                    } else {
                        $.messager.alert("系统提示", "密码修改失败！");
                        return;
                    }
                }
            });
        }

        function closePasswordModifyDialog() {
            resetValue();
            $("#dlg").dialog("close");
        }

        function resetValue() {
            $("#oldPassword").val("");
            $("#newPassword").val("");
            $("#newPassword2").val("");
        }

        function logout() {
            $.messager.confirm("系统提示", "您确定要退出系统吗？", function (r) {
                if (r) {
                    window.location.href = '${pageContext.request.contextPath}/admin/user/logout';
                }
            });
        }

    </script>
</head>
<body class="easyui-layout">
<div region="north" style="position: relative; height: 40px; background-color: #E0ECFF; overflow:hidden">
    <div style="position: absolute; left: 24px; top: 10px; font-size: 14px; font-family: initial; color: #1a3868; font-weight: 600">Light Blog Admin</div>
    <img style="width: 30px; height: 25px; position: absolute; right: 68px; top: 8px" alt="头像"
         src="${user.avatar}"/>
    <div style="position: absolute; right: 17px; top: 11px; font-size: 14px"> ${user.username}</div>
</div>
<div region="center">
    <div class="easyui-tabs" fit="true" border="false" id="tabs">
        <div title="首页" data-options="iconCls:'icon-home'">
            <div align="center" style="padding-top: 100px"><font color="red" size="10">欢迎使用</font></div>
        </div>
    </div>
</div>
<div region="west" style="width: 200px" title="导航菜单" split="true">
    <div class="easyui-accordion" data-options="fit:true,border:false">
        <div title="常用操作" data-options="selected:true,iconCls:'icon-item'" style="padding: 10px">
            <a href="javascript:openTab('写博客','writeBlog','icon-writeblog')" class="easyui-linkbutton"
               data-options="plain:true,iconCls:'icon-writeblog'" style="width: 150px">写博客</a>
            <a href="javascript:openTab('评论审核','commentReview','icon-review')" class="easyui-linkbutton"
               data-options="plain:true,iconCls:'icon-review'" style="width: 150px">评论审核</a>
        </div>
        <div title="博客管理" data-options="iconCls:'icon-bkgl'" style="padding:10px;">
            <a href="javascript:openTab('写博客','writeBlog','icon-writeblog')" class="easyui-linkbutton"
               data-options="plain:true,iconCls:'icon-writeblog'">写博客</a>
            <a href="javascript:openTab('博客信息管理','blogManage','icon-bkgl')" class="easyui-linkbutton"
               data-options="plain:true,iconCls:'icon-bkgl'">博客信息管理</a>
        </div>
        <div title="博客类别管理" data-options="iconCls:'icon-bklb'" style="padding:10px">
            <a href="javascript:openTab('博客类别信息管理','blogTypeManage','icon-bklb')" class="easyui-linkbutton"
               data-options="plain:true,iconCls:'icon-bklb'">博客类别信息管理</a>
        </div>
        <div title="评论管理" data-options="iconCls:'icon-plgl'" style="padding:10px">
            <a href="javascript:openTab('评论审核','commentReview','icon-review')" class="easyui-linkbutton"
               data-options="plain:true,iconCls:'icon-review'" style="width: 150px">评论审核</a>
            <a href="javascript:openTab('评论信息管理','commentManage','icon-plgl')" class="easyui-linkbutton"
               data-options="plain:true,iconCls:'icon-plgl'">评论信息管理</a>
        </div>
        <div title="个人信息管理" data-options="iconCls:'icon-grxx'" style="padding:10px">
            <a href="javascript:openTab('修改个人信息','modifyInfo','icon-grxxxg')" class="easyui-linkbutton"
               data-options="plain:true,iconCls:'icon-grxxxg'">修改个人信息</a>
        </div>
        <div title="系统管理" data-options="iconCls:'icon-system'" style="padding:10px">
            <a href="javascript:openTab('友情链接管理','linkManage','icon-link')" class="easyui-linkbutton"
               data-options="plain:true,iconCls:'icon-link'" style="width: 150px">友情链接管理</a>
            <a href="javascript:openPasswordModifyDialog()" class="easyui-linkbutton"
               data-options="plain:true,iconCls:'icon-modifyPassword'">修改密码</a>
            <%--<a href="javascript:refreshSystem()" class="easyui-linkbutton"--%>
               <%--data-options="plain:true,iconCls:'icon-refresh'">刷新系统缓存</a>--%>
            <a href="javascript:logout()" class="easyui-linkbutton"
               data-options="plain:true,iconCls:'icon-exit'">安全退出</a>
        </div>
    </div>
</div>
<div region="south" style="height: 25px;padding: 5px" align="center">

</div>

<div id="dlg" class="easyui-dialog" style="width:400px;height:200px;padding: 10px 20px"
     closed="true" buttons="#dlg-buttons">

    <form id="fm" method="post">
        <table cellspacing="8px">
            <tr>
                <td>用户名：</td>
                <td><input type="text" id="username" name="username" readonly="readonly"
                           value="${user.username }" style="width: 200px"/></td>
            </tr>
            <tr>
                <td>新密码：</td>
                <td><input type="password" id="newPassword" name="newPassword" class="easyui-validatebox"
                           required="true" style="width: 200px"/></td>
            </tr>
            <tr>
                <td>确认新密码：</td>
                <td><input type="password" id="newPassword2" name="newPassword2" class="easyui-validatebox"
                           required="true" style="width: 200px"/></td>
            </tr>
        </table>
    </form>
</div>

<div id="dlg-buttons">
    <a href="javascript:modifyPassword()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
    <a href="javascript:closePasswordModifyDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>

</body>
</html>