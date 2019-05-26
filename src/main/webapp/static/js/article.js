$(function () {
    //qqFace相关
    //防止高版本jquery包Cannot read property ‘msie’ of undefined错误
    jQuery.browser = {};
    (function () {
        jQuery.browser.msie = false;
        jQuery.browser.version = 0;
        if (navigator.userAgent.match(/MSIE ([0-9]+)./)) {
            jQuery.browser.msie = true;
            jQuery.browser.version = RegExp.$1;
        }
    })();
});


//关闭读者信息模拟框
function closeModal() {
    $('#visitorProfile').modal('hide');
}

//点击确定
function addProfile() {
    // 如果添加了头像
    if ($("#imageFile").prop('files')[0]) {
        var objUrl = getUrl($("#imageFile").prop('files')[0]);
        if (objUrl) {
            $("#myAvatar").attr("src", objUrl);
        }
    }
    if($("#username").val().length >= 2){
        $.cookie("ck_visitorName", $("#username").val(), {path: '/' });
        closeModal();
    }else{
        alert("名字至少2位");
    }
}

//打开模拟框
function openModal() {
    $('#visitorProfile').modal('show');
}

//评论表单校验
function commentValidation() {
    if ($("#username").val() == null || $("#username").val() == '' ||  $("#username").val().length < 2 || $("#myAvatar").attr("src") == null || $("#myAvatar").attr("src") == '') {
        openModal();
        return false;
    } else if (!$("#comment-text").val()) {
        alert("评论内容不能为空！");
        return false;
    } else if ($("#comment-text").val().length > 100) {
        alert("评论不能超过一百字！");
        return false;
    }
    return true;
}


//用于显示本地上传的图片
function getUrl(file) {
    var url = null;
    if (window.createObjectURL != undefined) {
        url = window.createObjectURL(file);
    } else if (window.URL != undefined) {
        url = window.URL.createObjectURL(file);
    } else if (window.webkitURL != undefined) {
        url = window.webkitURL.createObjectURL(file);
    }
    return url;
}

//日期显示设置
Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1, // month
        "d+": this.getDate(), // day
        "h+": this.getHours(), // hour
        "m+": this.getMinutes(), // minute
        "s+": this.getSeconds(), // second
        "q+": Math.floor((this.getMonth() + 3) / 3), // quarter
        "S": this.getMilliseconds()
        // millisecond
    }
    if (/(y+)/.test(format))
        format = format.replace(RegExp.$1, (this.getFullYear() + "")
            .substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(format))
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
    return format;
}

//日期显示相关
function formatDatebox(value) {
    if (value == null || value == '') {
        return '';
    }
    var dt;
    if (value instanceof Date) {
        dt = value;
    } else {

        dt = new Date(value);

    }

    return dt.format("yyyy-MM-dd hh:mm"); //扩展的Date的format方法(上述插件实现)
}

function html2Escape(sHtml) {
    return sHtml.replace(/[<>&"]/g,function(c){return {'<':'&lt;','>':'&gt;','&':'&amp;','"':'&quot;'}[c];});
}

//重置下次评论剩余时间
function resetCommentTime(commentSendInterval, articleId){
    $("#submitComment").removeAttr("onclick");
    var timeLeft = commentSendInterval;
    $("#submitComment").html(timeLeft);
    // 设置计时器
    var judge = setInterval(function() {
        timeLeft--;
        if (timeLeft <= 0) {
            // 清除计时器
            $("#submitComment").html("发送");
            $("#submitComment").attr("onclick", "submitComment(" + commentSendInterval +"," +  articleId + ")");
            clearInterval(judge);

        }else{
            // 标签当中显示时间
            $("#submitComment").html(timeLeft);
        }
    }, 1000);
}

//增加喜欢数
function addLike(articleId) {
    if ($.cookie("ck_like_" + articleId) != 1) {
        $.post("/article/like/add", {blogId: articleId}, function (data) {
            $("#like-btn").html("喜欢 " + data + "");
            $.cookie("ck_like_" + articleId, 1, {path: '/' });
        });
    }
}

//增加点击数(阅读数)
function addClick(articleId) {
    if ($.cookie("ck_click_" + articleId) != 1) {
        $.post("/article/click/add", {blogId: articleId}, function (data) {
            $.cookie("ck_click_" + articleId, 1, {path: '/' });
        });
    }
}

//提交评论表单
function submitComment(commentSendInterval, articleId) {
    if (commentValidation()) {
        //防止js注入
        $("#content").val(html2Escape($("#comment-text").val()));
        $("#blogId").val(articleId);
        $("#visitorAvatar").val($.cookie("ck_avatar"));
        $("#username").val(html2Escape($("#username").val()));
        //提交数据
        $("#comment-form").ajaxSubmit({
            type: "post",
            url: "/comment/insert",
            contentType: false,
            processData: false,
            success: function (result) {
                //先清空
                $("#comment-text").val("");

                if (result == "error") {
                    alert("提交失败");
                } else {
                    $.cookie("ck_avatar", result, {path: '/' });
                    alert("提交成功，等待审核");
                }
            }
        });
        //重置下次评论剩余时间
        resetCommentTime(commentSendInterval, articleId);
    }
}

//加载更多评论
function addMore(page, rows, articleId) {
    var isEnd = false;
    //设为同步请求(否则isEnd一直为false)
    $.ajaxSettings.async = false;
    $.post("/comment/get", {page: page, rows: rows, blogId: articleId},
        function (data) {
            var comments = data.rows;
            var length = comments.length;
            var result = '';
            var total = data.total;

            if (page * rows >= total) {
                isEnd = true;
            }

            for (var i = 0; i < length; i++) {
                var comment = comments[i];
                result += '<div class="comment">\n' +
                    '                        <div>\n' +
                    '                            <div class="author">\n' +
                    '                                <div class="avatar-container">\n' +
                    '                                    <a class="avatar">\n' +
                    '                                        <img src="' + comment.visitorAvatar + '">\n' +
                    '                                    </a>\n' +
                    '                                </div>\n' +
                    '                                <div class="info">\n' +
                    '                                    <a\n' +
                    '                                       class="name">' + comment.visitorName + '</a>\n' +
                    '                                    <div class="meta"><span>' + formatDatebox(comment.commentDate) + '</span></div>\n' +
                    '                                </div>\n' +
                    '                            </div>\n' +
                    '                            <div class="comment-wrap">\n' +
                    '                                <p>' + replace_em(comment.content) + '</p>\n' +
                    '                            </div>\n' +
                    '                        </div>\n' +
                    '                    </div>';
            }

            $('#featured-comment-list').append(result);

        }
        , "json");
    $.ajaxSettings.async = true;

    return isEnd;
}

//搜索文章
function getArticleByTitle(title) {
    window.location.href = "/index.html?title='" + title + "'";
}

//将评论中的qqFace图片显示出来
function replace_em(str) {
    str = str.replace(/\</g, '&lt;');
    str = str.replace(/\>/g, '&gt;');
    str = str.replace(/\n/g, '<br/>');
    str = str.replace(/\[em_([0-9]*)\]/g, '<img src="/static/plugin/qqFace/arclist/$1.gif" border="0" />');
    return str;
}


