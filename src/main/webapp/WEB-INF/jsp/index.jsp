<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Home</title>
    <link rel="icon" href="/static/image/logo.ico" type="img/x-ico"/>
    <!-- Bootstrap core CSS -->
    <link href="/static/plugin/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet"
          href="/static/plugin/font-awesome/css/font-awesome.min.css">

    <!-- Custom styles for this template -->
    <link href="/static/css/global.css" rel="stylesheet">
    <link href="/static/css/home.css" rel="stylesheet">
</head>

<style>

</style>

<body>
<!-- Navigation -->
<nav class="navbar navbar-expand-lg navbar-light fixed-top"
     style="background-color: rgb(255,255,255); border-bottom: 1px solid #e9e9e9">
    <div class="container">
        <a class="navbar-brand" href="#" style="color: #ea6f5a; font-weight: bold">Light Blog</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
            <ul class="navbar-nav ml-auto text-center">
                <li class="nav-item active">
                    <a class="nav-link" href="javascript:void(0)">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/about.html">About</a>
                </li>
            </ul>
            <div id="search" class="input-group col-lg-3">
                <input id="search-title" type="text" class="form-control" placeholder="Search for..."
                       style="border-radius: 12px">
                <span class="input-group-btn">
                    <a class="btn btn-secondary" onclick="getArticleByTitle($('#search-title').val())"
                       style="z-index: 100;position: absolute; top: 0; right: 15px; background-color: #ea6f5a; color: #ffffff; border-color: #ea6f5a; border-top-right-radius: 12px; border-bottom-right-radius: 12px">
                        <i class="fa fa-search"></i>
                    </a>
                </span>
            </div>
        </div>
    </div>
</nav>

<!-- Page Content -->
<div class="container">
    <div class="row">
        <div class="col-md-8 px-md-3">
            <div id="carouselExampleIndicators" class="carousel slide my-4" data-ride="carousel">
                <ol class="carousel-indicators">
                    <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
                    <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
                    <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
                </ol>
                <div class="carousel-inner">
                    <div class="carousel-item active">
                        <img class="d-block w-100"
                             src="/static/userImage/2018/12/09/465d1b8d-e53b-40f2-b5e0-ce3abd8d8622.jpg"
                             alt="First slide">
                    </div>
                    <div class="carousel-item">
                        <img class="d-block w-100"
                             src="/static/userImage/2018/12/09/703f3df0-0530-4712-86b3-d1889f8bdb6b.jpg"
                             alt="Second slide">
                    </div>
                    <div class="carousel-item">
                        <img class="d-block w-100"
                             src="/static/userImage/2018/12/09/30b26520-ce15-4f57-b797-2c3eba8ac65b.jpg"
                             alt="Third slide">
                    </div>
                </div>
                <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
            </div>

            <div class="split-line"></div>

            <!-- 文章列表模块 -->
            <ul class="blog-list">
            </ul>

            <!-- 不分页了-->
            <!--  动态加载更多 -->

        </div>

        <!-- Sidebar -->
        <div class="col-md-4">

            <div class="blogerinfo my-4">
                <div class="blogerinfo-figure">
                    <img src="/static/userImage/2018/12/09/a77f6117-625b-4bfe-ab4b-681e1d785e77.jpg"
                         alt="DrownBugs">
                </div>
                <p class="blogerinfo-nickname">DrownBugs</p>
                <p class="blogerinfo-introduce">Fake IT, till you make IT</p>
                <!--<hr>-->
                <div class="blogerinfo-contact">
                    <a target="_blank" id="QQjl" title="QQ交流"
                       href="http://wpa.qq.com/msgrd?v=3&uin=2428391347&site=qq&menu=yes"><i
                            class="fa fa-qq fa-2x"></i></a>
                    <a target="_blank" id="gwxx" title="给我写信" href="mailto:2428391347@qq.com"><i
                            class="fa fa-envelope fa-2x"></i></a>
                    <a target="_blank" id="htgl" title="后台管理" href="/admin/main"><i
                            class="fa fa-database fa-2x"></i></a>
                </div>
            </div>

            <div class="sidebar">
                <div class="sidebar-title"><i class="fa fa-file-text-o"></i>&nbsp;博主介绍</div>
                <p>DrownBugs，一名普通大学生，热爱编程，主要写java。-- DrownBugs</p>
            </div>

            <div class="sidebar tag">
                <div class="sidebar-title"><i class="fa fa-tags"></i>&nbsp;分类</div>
                <ul class="ul-type"></ul>
            </div>

            <div class="sidebar">
                <div class="sidebar-title"><i class="fa fa-sort"></i>&nbsp;点击排行</div>
                <ul class="ul-sort"></ul>
            </div>

            <div class="sidebar">
                <div class="sidebar-title"><i class="fa fa-link"></i>&nbsp;友情链接</div>
                <ul class="ul-link"></ul>
            </div>
        </div>
    </div>
</div>

<!-- Footer -->
<footer class="py-3" style="background-color: white; border-top: 1px solid #f0f0f0">
    <div class="container">
        <p class="m-0 text-center" style="color: #c8c8c8;">Copyright &copy; Light Blog 2018</p>
    </div>
</footer>

<!--back to top-->
<a href="#" class="cd-top"><img style="width: 48px; height: 48px;"
                                src="/static/image/top.png"></a>

<!-- Bootstrap core JavaScript -->
<script src="/static/js/jquery-3.3.1.min.js"></script>
<script src="/static/plugin/bootstrap/js/bootstrap.min.js"></script>
<!-- Custom JavaScript -->
<script src="/static/js/global.js"></script>
<script src="/static/js/home.js"></script>

<script>
    <%--init--%>
    $(function () {

        /*初始化*/
        var page = 1;
        var rows = 10;
        var isEnd = false;
        /*结束标志*/

        /*首次加载*/
        isEnd = addMore(page, rows, ${blogType}, ${title});

        /*监听加载更多*/
        $(window).scroll(function () {
            if (isEnd == true) {
                return;
            }

            // 当滚动到最底部以上100像素时， 加载新内容
            // 核心代码
            if ($(document).height() - $(this).scrollTop() - $(this).height() < 100) {
                page++;
                isEnd = addMore(page, rows, ${blogType}, ${title});
            }
        });

        //获取类别列表
        getTypeList();

        //获取友链列表
        getLinkList();

        //获取按点击排行的文章列表
        getArticleListOrderByClickCount();

    });

</script>
</body>
</html>
