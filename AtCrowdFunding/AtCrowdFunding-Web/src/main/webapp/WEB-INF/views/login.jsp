<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keys" content="">
    <meta name="author" content="">
    <link rel="stylesheet" href="${APP_PATH}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/font-awesome.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/login.css">
    <style>

    </style>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <div><a class="navbar-brand" href="index.html" style="font-size:32px;">尚筹网-创意产品众筹平台</a></div>
        </div>
    </div>
</nav>

<div class="container">
    <h1 style="color: red">${param.errorMsg}</h1>
    <form id="loginForm" action="doLogin" method="post" class="form-signin" role="form">
        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-user"></i> 用户登录</h2>
        <div class="form-group has-success has-feedback">
            <input type="text" class="form-control" id="email" name="email" placeholder="请输入email" autofocus>
            <span class="glyphicon glyphicon-user form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <input type="text" class="form-control" id="lastName" name="lastName" placeholder="请输入登录密码" style="margin-top:10px;">
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <select class="form-control" >
                <option value="member">会员</option>
                <option value="user">管理</option>
            </select>
        </div>
        <div class="checkbox">
            <label>
                <input type="checkbox" value="remember-me"> 记住我
            </label>
            <br>
            <label>
                忘记密码
            </label>
            <label style="float:right">
                <a href="reg.html">我要注册</a>
            </label>
        </div>
        <a class="btn btn-lg btn-success btn-block" onclick="dologin()" > 登录</a>
    </form>
</div>
<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH}/layer/layer.js"></script>
<script>
    function dologin() {
        var email = $("#email").val();
        var lastName = $("#lastName").val();
        if(email == null || email == ""){
            // alert("账号不能为空");
            // layer.msg("账号不能为空", {time:1000, icon:5, shift:6}, function(){
            //     alert("回调方法");
            // });
            layer.msg("账号不能为空", {time:3000, icon:5, shift:6});
            return;
        }
        if(lastName == null || lastName == ""){
            // alert("密码不能为空");
            layer.msg("密码不能为空", {time:3000, icon:5, shift:6});
            return;
        }
        // $("#loginForm").submit();
        // alert("提交成功");
        var loadingIndex = null;
            $.ajax({
            type:"POST",
            url:"doAJAXLogin",
            data:{
                "email":email,
                "lastName":lastName
            },
            beforeSend:function(){//加载动画
                loadingIndex = layer.msg('登陆中', {icon: 16});
            },
            success:function(result){//如果返回数据后，则取消加载动画
                layer.close(loadingIndex);
                if(result.success){
                    window.location.href="main";
                }else{
                    layer.msg("账号或密码错误，请重新输入", {time:3000, icon:5, shift:6});
                }
            }
        });
    }
</script>
</body>
</html>
