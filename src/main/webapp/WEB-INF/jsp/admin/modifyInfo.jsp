<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>修改个人信息页面</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/plugin/jeasyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/plugin/jeasyui/themes/icon.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/css/idea.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/plugin/mdeditor/css/editormd.min.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/css/customAdmin.css">

</head>
<body style="margin: 10px">
<div id="p" class="easyui-panel" title="修改个人信息" style="padding: 10px">
    <form id="form1" method="post"
          enctype="multipart/form-data">
        <table cellspacing="20px" width="100%">
            <tr>
                <td width="80px">用户名：</td>
                <td>
                    <input type="hidden" id="id" name="id" value="${user.id }"/>
                    <input type="text" id="username" name="username" style="width: 200px;"
                           value="${user.username}" readonly="readonly"/>
                </td>
            </tr>
            <tr>
                <td>个人头像：</td>
                <td><input type="file" id="avatarFile" name="avatarFile" style="width: 400px;"/></td>
            </tr>
            <tr>
                <td valign="top">个人简介：</td>
                <td>
                    <div id="content-editormd" class="form-group" style="z-index: 100">
                        <textarea id="content-editormd-markdown-doc" style="display: none">${user.profile}</textarea>
                    </div>
                    <input type="hidden" id="profile" name="profile"/>
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <a href="javascript:submitData()" class="easyui-linkbutton"
                       data-options="iconCls:'icon-submit'">提交 </a>
                </td>
            </tr>

        </table>
    </form>
</div>

<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/jquery.form.min.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/plugin/jeasyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/plugin/jeasyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/plugin/mdeditor/editormd.min.js"></script>
<script type="text/javascript">

    var editor;

    $(function () {
        editor = editormd("content-editormd", {
            width: "100%",
            height: 600,
            syncScrolling: "single",
            path: "${pageContext.request.contextPath}/static/plugin/mdeditor/lib/",
            htmlDecode      : "style,script,iframe",
            saveHTMLToTextarea: true,    // 保存 HTML 到 Textarea

            /**上传图片相关配置如下*/
            imageUpload: true,
            imageFormats: ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
            imageUploadURL: "/admin/uploadImg"

        });
    });


    function submitData() {
        var profile = editor.getMarkdown();

        $("#profile").val(profile);
        $('#form1').ajaxSubmit({
            type: "post",
            url: "${pageContext.request.contextPath}/admin/user/update",
            contentType: false,
            processData: false,
            success: function (result) {
                if (result == "success") {
                    alert("修改成功");

                } else {
                    alert("修改失败");
                }
            }
        });
    }

</script>
</body>
</html>