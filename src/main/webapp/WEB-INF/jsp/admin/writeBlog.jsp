<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>写博客页面</title>
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
<div id="p" class="easyui-panel" title="编写博客" style="padding: 10px">
    <form id="blogForm" enctype="multipart/form-data">
        <table cellspacing="20px" width="100%">
            <tr>
                <td width="80px">博客标题：</td>
                <td><input type="text" id="title" name="title" style="width: 400px;"/></td>
            </tr>
            <tr>
                <td>文章图片</td>
                <td><input type="file" id="imageFile" name="imageFile" style="width: 400px;"></td>
            </tr>
            <tr>
                <td>转载地址</td>
                <td><input type="text" id="reprint" name="reprint" style="width: 400px;"></td>
            </tr>
            <tr>
                <td>所属类别：</td>
                <td>
                    <select class="easyui-combobox" style="width: 154px" id="blogTypeId" name="blogTypeId"
                            editable="false"
                            panelHeight="auto">
                        <option value="">请选择博客类别...</option>
                        <c:forEach var="blogType" items="${blogTypeList }">
                            <option value="${blogType.id }">${blogType.typeName}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td valign="top">博客内容：</td>
                <td>
                    <div id="content-editormd" class="form-group" style="z-index: 100">
                        <textarea id="content-editormd-markdown-doc" style="display: none"></textarea>
                    </div>
                </td>
            </tr>
            <tr>
                <%--对齐--%>
                <td></td>
                <td>
                    <a href="javascript:submitData()" class="easyui-linkbutton"
                       data-options="iconCls:'icon-submit'">发布博客</a>
                </td>
            </tr>
        </table>
        <input type="hidden" id="content" name="content"/>
        <input type="hidden" id="summary" name="summary"/>
        <input type="hidden" id="typeId" name="typeId"/>
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
            saveHTMLToTextarea : true,    // 保存 HTML 到 Textarea

            /**上传图片相关配置如下*/
            imageUpload: true,
            imageFormats: ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
            imageUploadURL: "/admin/uploadImg"

        });
    })

    function submitData() {
        var title = $("#title").val();
        var typeId = $("#blogTypeId").combobox("getValue");
        var content = editor.getMarkdown();
        console.log(editor.getHTML());
        var summary = getSummary(editor.getHTML()).substring(0, 90) + "...";

        if (title == null || title == '') {
            alert("请输入标题！");
        } else if (typeId == null || typeId == '') {
            alert("请选择博客类别！");
        } else if (content == null || content == '') {
            alert("请输入内容！");
        } else {
            $("#content").val(content);
            $("#summary").val(summary);
            $("#typeId").val(typeId);
            $("#blogForm").ajaxSubmit({
                type: "post",
                url: "${pageContext.request.contextPath}/admin/blog/insert",
                contentType: false,
                processData: false,
                success: function (result) {
                    if (result == "success") {
                        alert("博客发布成功!");
                        resetValue();
                    } else {
                        alert("博客发布失败!");
                    }
                }
            });
        }
    }

    // 重置数据
    function resetValue() {
        $("#title").val("");
        $("#blogTypeId").combobox("setValue", "");
        // $("#content-editormd-markdown-doc").html("lallalala");
        // console.log("content: " + $("#content-editormd-markdown-doc").html());
        console.log(editor.getHTML());
        editor.clear();
    }

    function getSummary(html){
        var str1 = html.replace(/<\/?.+?>/g,"");
        var str2 = str1.replace(/\s/g,"");
        return str2;
    }

</script>
</body>
</html>