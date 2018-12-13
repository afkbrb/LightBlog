//获取类别列表
function getTypeList() {
    $.post("/articleType/list", {}, function (data) {
        var length = data.length;
        var result = '';
        for (var i = 0; i < length; i++) {
            var type = data[i];
            result += '<a href="javascript:void(0)" onclick="getArticlesByType(' + type.id + ')">' + type.typeName + ' (' + type.blogCount + ')' + '</a>';
        }

        $(".ul-type").html(result);

    }, "json");
}

//按分类得到文章列表
function getArticlesByType(blogType) {
    window.location.href = "/index.html?blogType=" + blogType;
}

//搜索文章
function getArticleByTitle(title) {
    window.location.href = "/index.html?title='" + title + "'";
}

//获取友链列表
function getLinkList() {
    $.post("/link/list", {}, function (data) {
        var length = data.length;
        var result = '';
        for (var i = 0; i < length; i++) {
            var link = data[i];
            result += ' <li>\n' +
                '                        <a target="_blank" href="' + link.linkUrl + '" title="' + link.linkName + '">' + link.linkName + '</a>\n' +
                '                    </li>';
        }

        $(".ul-link").html(result);

    }, "json");
}

//获取按点击排行的文章列表
function getArticleListOrderByClickCount() {
    $.post("/article/order/clickCount", {}, function (data) {
        var length = data.length;
        var result = '';
        for (var i = 0; i < length && i < 9; i++) {
            var article = data[i];
            if (i == 0) {
                result += ' <li>\n' +
                    '                        <span><i class="badge badge-danger" style="color: #ffffff;">' + (i + 1) + '</i></span> &nbsp;&nbsp;\n' +
                    '                        <a target="_blank" href="/article/' + article.id + '.html">' + article.title + '</a>\n' +
                    '                    </li>';
            } else if (i == 1) {
                result += ' <li>\n' +
                    '                        <span><i class="badge badge-warning" style="color: #ffffff;">' + (i + 1) + '</i></span> &nbsp;&nbsp;\n' +
                    '                        <a target="_blank" href="/article/' + article.id + '.html">' + article.title + '</a>\n' +
                    '                    </li>';
            } else if (i == 2) {
                result += ' <li>\n' +
                    '                        <span><i class="badge badge-success" style="color: #ffffff;">' + (i + 1) + '</i></span> &nbsp;&nbsp;\n' +
                    '                        <a target="_blank" href="/article/' + article.id + '.html">' + article.title + '</a>\n' +
                    '                    </li>';
            } else {
                result += ' <li>\n' +
                    '                        <span><i class="badge badge-light" style="color: #676767;">' + (i + 1) + '</i></span> &nbsp;&nbsp;\n' +
                    '                        <a target="_blank" href="/article/' + article.id + '.html">' + article.title + '</a>\n' +
                    '                    </li>';
            }
        }
        $(".ul-sort").html(result);
    }, "json");
}

//加载更多文章
function addMore(page, rows, blogType, title) {

    var mobileMatcher = window.matchMedia('(max-width:768px)');
    var isEnd = false;

    //设为同步请求(不同步的话isEnd会一直为false)
    $.ajaxSettings.async = false;
    //获取文章列表
    $.post("/article/list", {
            page: page,
            rows: rows,
            blogType: blogType,
            title: title
        },
        function (data) {
            var articles = data.rows;
            var length = articles.length;
            var result = '';
            var total = data.total;

            if (page * rows >= total) {
                isEnd = true;
            }

            if (mobileMatcher.matches) {
                for (var i = 0; i < length; i++) {
                    var article = articles[i];
                    result += '<li>\n' +
                        '                    <div class="content">\n' +
                        '                        <a class="title" target="_blank"\n' +
                        '                           href="' + '/article/' + article.id + '.html">' + article.title + '</a>\n' +
                        '                        <p class="abstract">\n' + getSummary(article.summary, 48) +
                        '                        </p>\n' +
                        '                        <div class="meta">\n' +
                        '                            <span><i class="fa fa-comment-o"></i> ' + article.replyCount + '</span>\n' +
                        '                            <span><i class="fa fa-heart-o"></i> ' + article.likeCount + '</span>\n' +
                        '                            <span><i class="fa fa-eye"></i> ' + article.clickCount + '</span>\n' +
                        '                        </div>\n' +
                        '                    </div>\n' +
                        '                </li>';
                }
            } else {
                for (var i = 0; i < length; i++) {
                    var article = articles[i];
                    if (article.blogImage == null || article.blogImage == "") {
                        result += '<li>\n' +
                            '                    <div class="content">\n' +
                            '                        <a class="title" target="_blank"\n' +
                            '                           href="' + '/article/' + article.id + '.html">' + article.title + '</a>\n' +
                            '                        <p class="abstract">\n' + article.summary +
                            '                        </p>\n' +
                            '                        <div class="meta">\n' +
                            '                            <span><i class="fa fa-comment-o"></i> ' + article.replyCount + '</span>\n' +
                            '                            <span><i class="fa fa-heart-o"></i> ' + article.likeCount + '</span>\n' +
                            '                            <span><i class="fa fa-eye"></i> ' + article.clickCount + '</span>\n' +
                            '                        </div>\n' +
                            '                    </div>\n' +
                            '                </li>';
                    } else {
                        result += ' <li class="have-img">\n' +
                            '                    <a class="wrap-img" href="' + '/article/' + article.id + '.html" target="_blank">\n' +
                            '                        <img src="' + article.blogImage + '">\n' +
                            '                    </a>\n' +
                            '                    <div class="content">\n' +
                            '                        <a class="title" target="_blank" href="' + '/article/' + article.id + '.html">' + article.title + '</a>\n' +
                            '                        <p class="abstract">\n' +
                            '                            ' + article.summary + '\n' +
                            '                        </p>\n' +
                            '                        <div class="meta">\n' +
                            '                            <span><i class="fa fa-comment-o"></i> ' + article.replyCount + '</span>\n' +
                            '                            <span><i class="fa fa-heart-o"></i> ' + article.likeCount + '</span>\n' +
                            '                            <span><i class="fa fa-eye"></i> ' + article.clickCount + ' </span>\n' +
                            '                        </div>\n' +
                            '                    </div>\n' +
                            '                </li>';
                    }

                }
            }

            $('.blog-list').append(result);

        }
        , "json");

    $.ajaxSettings.async = true;

    return isEnd;
}

function getSummary(str, length){
    return str.substring(0, length) + "...";
}