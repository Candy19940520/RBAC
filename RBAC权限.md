

# RBAC权限

## 一、关于WEB请求乱码解决方案

### **原因**

1. 传递数据的时候，会采用当前浏览器的编码方式传递数据(当前页面编码方式是UTF-8)

   **看当前页面是否配置：**

   ```
   <%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
   ```

2. 服务器默认使用IOS8859-1编码方式解析数据，所以我们接收到的数据是乱码

### 解决方案一(POST请求)：

1. 首先使用服务器默认的编码方式将数据还原

   ```
   byte[] bytes = employee.getEmail().getBytes("ISO8859-1");
   ```

2. 然后重新使用和浏览器编码方式一致的编码

   ```
   employee.setEmail(new String(bytes,"UTF-8"));
   ```

### 解决方案二(POST请求)：

**使用过滤器Filter,基于零XML配置**

```
//支持REST请求方式和解决乱码
@Override
protected Filter[] getServletFilters() {
    return new Filter[] {new CharacterEncodingFilter("UTF-8"),new HiddenHttpMethodFilter()};
}
```



### 解决GET请求乱码：

**修改Tomcat的server.xml配置文件**

```
<Connector port="8080" protocol="HTTP/1.1"
               connectionTimeout="20000"
               redirectPort="8443" URIEncoding="UTF-8"/>
```



## 二、相对路径&绝对路径

### 1.相对路径(可改变的路径,需要以基准路径参照)

默认情况：相对路径的基准路径是当前资源的访问路径

路径以"/"开头，标识特殊的相对路径，在不同的场景中，相对路径会发生变化

例如(带项目名)URL：http://localhost:8080/abc-web/test/test.html

①前台路径：<a hrft="/user"><img src="">

​	相对服务器的根：http://localhost:8080/user

**但是web应用的名称可以改变，所以使用EL表达式动态获取项目名称**

```
${pageContext.request.contextPath}
```

②后台路径：forward("user.jsp")、xml等

​	相对web应用的根：http://localhost:8080/abc-web/user.jsp

### 2.绝对路径(不可改变的路径)

本地绝对路径：增加盘符的路径(D:\BaiduNetdiskDownload\a.html)

网络绝对路径：增加协议、IP地址、端口号的路径(http://localhost:8080/test/test.html)



## 三、RBAC权限控制

### 1.概念

权限管理，这是每个软件系统都会涉及到的，而且权限管理的需求本质往往都是一样，不同的角色拥有不同的权限，只要你充当了某个角色，你就拥有了相对应的功能。

**较简单的授权模型**

![RBAC-Demo](C:\Users\61788\Desktop\学习\RBAC\RBAC-Demo.jpg)



**较为复杂的授权模型**

![RBAC](C:\Users\61788\Desktop\学习\RBAC\RBAC.jpg)

### 2.RBAC级别

1. RBAC0

   `RBAC`核心模型, 其他的级别都是建立在该级别的基础上。

   ![RBAC0-1](C:\Users\61788\Desktop\学习\RBAC\RBAC0-1.jpg)

   ![RBAC0-2](C:\Users\61788\Desktop\学习\RBAC\RBAC0-2.jpg)

2. RBAC1

   基于`RBAC0`模型，进行了角色的分层，也就是说角色上有了上下级的区别。

   ![RBAC1](C:\Users\61788\Desktop\学习\RBAC\RBAC1.jpg)

3. RBAC2

   `RBAC2`，也是基于`RBAC0`模型的基础上，进行了角色的访问控制。

   ![RBAC2](C:\Users\61788\Desktop\学习\RBAC\RBAC2.jpg)

4. RBAC3

   `RBAC3`，是最全面的权限管理，它是在`RBAC0`的基础上，将`RBAC1`和`RBAC2`进行整合了，最全面，也最复杂的。

   ![RBAC3](C:\Users\61788\Desktop\学习\RBAC\RBAC3.jpg)

   



## 四、使用jQuery插件zTree来做菜单树形结构



### 1.再使用jQuery的前提下引入组件zTree

```
<link rel="stylesheet" href="${APP_PATH}/ztree/zTreeStyle.css">
<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/ztree/jquery.ztree.all-3.5.min.js"></script>
```

### 2.初始化最简单的树形结构代码

需要在页面声明一个ul

```
<ul id="permissionTree" class="ztree" style="width:260px; overflow:auto;"></ul>
```

在JS中声明

```
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
    var setting = {};
    var zNodes = [
        { name:"父节点1 - 展开", open:true,
            children: [
                { name:"父节点11 - 折叠",
                    children: [
                        { name:"叶子节点111"},
                        { name:"叶子节点112"},
                        { name:"叶子节点113"},
                        { name:"叶子节点114"}
                    ]},
                { name:"父节点12 - 折叠",
                    children: [
                        { name:"叶子节点121"},
                        { name:"叶子节点122"},
                        { name:"叶子节点123"},
                        { name:"叶子节点124"}
                    ]},
                { name:"父节点13 - 没有子节点", isParent:true}
            ]},
        { name:"父节点2 - 折叠",
            children: [
                { name:"父节点21 - 展开", open:true,
                    children: [
                        { name:"叶子节点211"},
                        { name:"叶子节点212"},
                        { name:"叶子节点213"},
                        { name:"叶子节点214"}
                    ]},
                { name:"父节点22 - 折叠",
                    children: [
                        { name:"叶子节点221"},
                        { name:"叶子节点222"},
                        { name:"叶子节点223"},
                        { name:"叶子节点224"}
                    ]},
                { name:"父节点23 - 折叠",
                    children: [
                        { name:"叶子节点231"},
                        { name:"叶子节点232"},
                        { name:"叶子节点233"},
                        { name:"叶子节点234"}
                    ]}
            ]},
        { name:"父节点3 - 没有子节点", isParent:true}
    ];
    $.fn.zTree.init($("#permissionTree"), setting, zNodes);
});
```















