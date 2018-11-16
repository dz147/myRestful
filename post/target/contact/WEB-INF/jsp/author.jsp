<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>...</title>
</head>
<body>
<h1>空：${cc}</h1>
    <marquee behavior="" direction="">啊哈哈</marquee>
    <form action="/add" method="post">
        <input type="text" name="name" placeholder="姓名" style="width: 100px;height: 20px"/><br/>
        <input type="text" name="tel" placeholder="电话" style="width: 100px;height: 20px"/><br/>
        <input type="text" name="address" placeholder="地址" style="width: 100px;height: 30px"/><br/>
        <input type="submit"/>
    </form>



</body>
</html>
