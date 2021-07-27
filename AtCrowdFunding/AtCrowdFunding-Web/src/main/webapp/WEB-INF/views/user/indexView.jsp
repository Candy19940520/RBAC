<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CH">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="stylesheet" href="${APP_PATH}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/font-awesome.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/main.css">
    <style>
        .tree li {
            list-style-type: none;
            cursor:pointer;
        }
        table tbody tr:nth-child(odd){background:#F4F4F4;}
        table tbody td:nth-child(even){color:#C00;}
    </style>
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 用户维护</a></div>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li style="padding-top:8px;">
                    <div class="btn-group">
                        <button type="button" class="btn btn-default btn-success dropdown-toggle" data-toggle="dropdown">
                            <i class="glyphicon glyphicon-user"></i> 张三 <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu" role="menu">
                            <li><a href="#"><i class="glyphicon glyphicon-cog"></i> 个人设置</a></li>
                            <li><a href="#"><i class="glyphicon glyphicon-comment"></i> 消息</a></li>
                            <li class="divider"></li>
                            <li><a href="${APP_PATH}/logout"><i class="glyphicon glyphicon-off"></i> 退出系统</a></li>
                        </ul>
                    </div>
                </li>
                <li style="margin-left:10px;padding-top:8px;">
                    <button type="button" class="btn btn-default btn-danger">
                        <span class="glyphicon glyphicon-question-sign"></span> 帮助
                    </button>
                </li>
            </ul>
            <form class="navbar-form navbar-right">
                <input type="text" class="form-control" placeholder="Search...">
            </form>
        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <div class="tree">
                <%@ include file="/WEB-INF/views/common/menu.jsp"%>
            </div>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="queryTest" class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="queryButton" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button type="button" onclick="deleteAll()" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <button type="button" class="btn btn-primary" style="float:right;" onclick="window.location.href='${APP_PATH}/user/add'"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <form id="checkboxForm">
                            <table class="table  table-bordered">
                                <thead>
                                <tr >
                                    <th width="30">#</th>
                                    <th width="30"><input type="checkbox" id="allCheckbox"></th>
                                    <th>账号</th>
                                    <th>名称</th>
                                    <th>邮箱地址</th>
                                    <th width="100">操作</th>
                                </tr>
                                </thead>
                                <tbody id="userData">

                                </tbody>
                                <tfoot>
                                <tr >
                                    <td colspan="6" align="center">
                                        <ul class="pagination">

                                        </ul>
                                    </td>
                                </tr>

                                </tfoot>
                            </table>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH}/script/docs.min.js"></script>
<script src="${APP_PATH}/layer/layer.js"></script>
<script type="text/javascript">
    $(function () {
        $(".list-group-item").click(function(){
            if ( $(this).find("ul") ) {
                $(this).toggleClass("tree-closed");
                if ( $(this).hasClass("tree-closed") ) {
                    $("ul", this).hide("fast");
                } else {
                    $("ul", this).show("fast");
                }
            }
        });
        pageQuery(1,5,"");
        $("#queryButton").click(function(){
            var queryTest = $("#queryTest").val();
            pageQuery(1,5,queryTest);
        });

        $("#allCheckbox").click(function(){
            var flag = this.checked;
            //把所有复选框选中
            $("#userData :checkbox").each(function(){
                this.checked = flag;
            });
        });

    });
    $("tbody .btn-success").click(function(){
        window.location.href = "assignRole.html";
    });
    $("tbody .btn-primary").click(function(){
        window.location.href = "edit.html";
    });


    //分页查询
    function pageQuery(pageNo,pageSize,lastName){
        var loadingIndex = null;
        $.ajax({
            type : "POST",
            url : "${APP_PATH}/user/pageQuery",
            data : {
                "pageNo": pageNo,
                "pageSize": pageSize,
                "lastName": lastName
            },
            beforeSend:function(){//加载动画
                loadingIndex = layer.msg('处理中', {icon: 16});
            },
            success:function(result){//如果返回数据后，则取消加载动画
                layer.close(loadingIndex);
                if(result.success){
                    var tableHtml = "";
                    var pageHtml = "";

                    var page = result.data;//分页对象
                    var totalPage = result.totalPage;//总页数
                    var records = page.records;//数据列表

                    $.each(records,function(index,obj){
                        tableHtml += '<tr>';
                        tableHtml += '  <td>'+(index+1)+'</td>';
                        tableHtml += '  <td><input type="checkbox" name="employeeIds" value="'+obj.employeeId+'"></td>';
                        tableHtml += '  <td>'+obj.email+'</td>';
                        tableHtml += '  <td>'+obj.lastName+'</td>';
                        tableHtml += '  <td>'+obj.email+'</td>';
                        tableHtml += '  <td>';
                        tableHtml += '  <button type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>';
                        tableHtml += '  <button type="button" onclick="updateButton('+obj.employeeId+')" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>';
                        tableHtml += '  <button type="button" onclick="deleteButton('+obj.employeeId+')" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>';
                        tableHtml += '  </td>';
                        tableHtml += '</tr>';
                    });
                    $("#userData").html(tableHtml);
                    if(page.current > 1){
                        pageHtml += '<li><a href="javascript:;" onclick="pageQuery('+(page.current-1)+',5)">上一页</a></li>';
                    }

                    for(var i=1;i<=totalPage;i++){
                        pageHtml += '<li ';
                        if(pageNo == i){
                            pageHtml += 'class="active"';
                        }
                        pageHtml += '> <a href="javascript:;"';
                        if(pageNo != i){
                            pageHtml += 'onclick="pageQuery('+i+',5)"';
                        }
                        pageHtml += '>'+i;
                        if(pageNo == i){
                            pageHtml += '<span class="sr-only">(current)</span>';
                        }
                        pageHtml += '</a></li>';
                    }
                    if(page.current < totalPage){
                        pageHtml += '<li><a href="javascript:;" onclick="pageQuery('+(page.current+1)+',5)">下一页</a></li>';
                    }
                    $(".pagination").html(pageHtml);
                }else{
                    layer.msg("数据加载失败", {time:3000, icon:5, shift:6});
                }
            }

        })
    }

    function updateButton(employeeId){
        window.location.href = "${APP_PATH}/user/update?employeeId="+employeeId;
    }
    function deleteButton(employeeId){
        layer.confirm("确定删除用户？",  {icon: 3, title:'提示'}, function(cindex){
            var loadingIndex = null;
            $.ajax({
                type:"POST",
                url:"${APP_PATH}/user/deleteAJAX",
                data:{"employeeId":employeeId},
                beforeSend:function(){//加载动画
                    loadingIndex = layer.msg('处理中', {icon: 16});
                },
                success:function(result){
                    layer.close(loadingIndex);
                    if(result.success){
                        pageQuery(1,5,"");
                    }else{
                        layer.msg("删除失败", {time:3000, icon:5, shift:6});
                    }

                }
            });
            layer.close(cindex);
        }, function(cindex){
            layer.close(cindex);
        });
    }

    //通过checkBox复选框批量删除
    function deleteAll(){
        //因为前台往后台传数据是通过name属性，所以说如果checkbox复选框的name值一样的话，
        // 后台会接收到一个数组数据(后台只能拿数据接收，不能是集合List)，但是需要在form里面，通过序列化传递数据
        var boxs = $("#userData :checkbox");
        if(boxs.length == 0){
            layer.msg("请选择删除的信息", {time:3000, icon:5, shift:6});
        }else{
            layer.confirm("确定批量删除所选的用户？",  {icon: 3, title:'提示'}, function(cindex){
                var loadingIndex = null;
                $.ajax({
                    type:"POST",
                    url:"${APP_PATH}/user/deleteAllAJAX",
                    data:$("#checkboxForm").serialize(),
                    beforeSend:function(){//加载动画
                        loadingIndex = layer.msg('处理中', {icon: 16});
                    },
                    success:function(result){
                        layer.close(loadingIndex);
                        if(result.success){
                            pageQuery(1,5,"");
                        }else{
                            layer.msg("删除失败", {time:3000, icon:5, shift:6});
                        }

                    }
                });
                layer.close(cindex);
            }, function(cindex){
                layer.close(cindex);
            });
        }

    }

</script>
</body>
</html>

