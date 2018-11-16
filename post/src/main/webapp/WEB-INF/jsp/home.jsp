<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>...</title>
</head>
<body>

<form action="/" method="post">
    <input type="text" name="pname" placeholder="标题" style="width: 100px;height: 20px"/><br/>
    <input type="text" name="content" placeholder="内容" style="width: 100px;height: 80px"/><br/>
    <select name="aid">
        <c:forEach items="${authors}" var="author">
            <option value="${author.id}">${author.name}</option>
        </c:forEach>
    </select>
    <input type="submit"/>
</form>
<a href="/cc">添加作者</a>
<div style="margin:0 auto;width:500px;height: 300px;">
    <c:forEach items="${post}" var="post">
        <h2>${post.pname}</h2>
        <h5>${post.author.name}&nbsp;&nbsp;&nbsp;&nbsp;${post.writedate.toString()}</h5>
        <h4>${post.content}</h4>
    </c:forEach>
    <br/>
    <div>
        <p>总共有 ${pageInfo.pages} 页，总共有 ${pageInfo.total}条</p>
        <p>当前是第 ${pageInfo.pageNum} 页</p>
        <c:forEach begin="1" end="${pageInfo.pages}" var="p">
            <a href="/?page=${p}">第 ${p} 页 </a>
        </c:forEach>
    </div>
</div>


</body>
</html>
