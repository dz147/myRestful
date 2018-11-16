<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="title"/></title>
    <style type="text/css">
        table tr {
            text-align: center;
            height: 40px;
            line-height: 40px;
        }

        table tr:hover {
            background-color: #F8F8F8;
        }
    </style>
</head>
<body>

<marquee behavior="scroll" direction="up">
    <spring:message code="global.greeting" arguments="DZ"/>
</marquee>

<div style="margin:auto">
    <form action="/addPicture" method="post" enctype="multipart/form-data">
        <input type="file" name="img"/>
        <input type="submit"/>
    </form>
</div>

<div>
    <spring:message code="page.cl"/>
    <br>
    <a href="/u/zh">中文</a>
    <a href="/u/en">English.</a>
    <a href="/u/jp">小日本</a>
    <br><br>
</div>
<%--文件上传 提交方法 enctype="multipart/form-data"--%>
<form:form action="/empAdd" method="post" modelAttribute="employee">
    <div style="color: red">
        error:<form:errors path="*"/>
        <br/>
        <c:forEach items="${errs}" var="e">
            <div>${e}</div>
        </c:forEach>
    </div>
    <table border="0px" align="center" width="60%" bordercolor="#E2E2E2" id="emps_table">
        <thead>
        <tr>
            <th>编号</th>
            <th>姓名</th>
            <th>性别</th>
            <th>学历</th>
            <th>月薪</th>
            <th>添加</th>
            <th>移除</th>
        </tr>
        </thead>
        <tbody id="tbody01">
        <tr>
            <td><input type="text" name="addEmp[0].number"/></td>
            <td><input type="text" name="addEmp[0].empName"/></td>
            <td>
                <select name="addEmp[0].empSex">
                    <option>请选择</option>
                    <option value="男">男</option>
                    <option value="女">女</option>
                </select>
            </td>
            <td>
                <select name="addEmp[0].education">
                    <option>请选择</option>
                    <option value="本科">本科</option>
                    <option value="专科">专科</option>
                    <option value="硕士">硕士</option>
                    <option value="博士">博士</option>
                </select>
            </td>
            <td><input type="text" name="addEmp[0].monthly"/></td>
            <td><input type="button" value="+" onclick="add()"/></td>
            <td><input type="button" value="-"/></td>
        </tr>
        </tbody>
    </table>
    <input type="submit" value="批量添加" style="display: block;width: 100px;height: 40px; margin: 0 auto"/>
</form:form>


<form action="/empAdd2" method="post">
    <table border="0px" align="center" width="60%" bordercolor="#E2E2E2" id="emps_table">
        <thead>
        <tr>
            <th>编号</th>
            <th>姓名</th>
            <th>性别</th>
            <th>学历</th>
            <th>月薪</th>
            <th>添加</th>
            <th>移除</th>
        </tr>
        </thead>
        <tbody id="gg">
        <tr>
            <td><input type="text" name="number"/></td>
            <td><input type="text" name="empName"/></td>
            <td>
                <select name="empSex">
                    <option>请选择</option>
                    <option value="男">男</option>
                    <option value="女">女</option>
                </select>
            </td>
            <td>
                <select name="education">
                    <option>请选择</option>
                    <option value="本科">本科</option>
                    <option value="专科">专科</option>
                    <option value="硕士">硕士</option>
                    <option value="博士">博士</option>
                </select>
            </td>
            <td><input type="text" name="monthly"/></td>
            <td><input type="button" value="+" onclick="add()"/></td>
            <td><input type="button" value="-"/></td>
        </tr>
        </tbody>
    </table>
    <input type="submit" value="添加" style="display: block;width: 100px;height: 40px; margin: 0 auto"/>
</form>

<table border="1" align="center" width="60%" bordercolor="#E2E2E2">
    <tr class="th">
        <th><spring:message code="user.id"/></th>
        <th><spring:message code="user.name"/></th>
        <th>性别</th>
        <th>学历</th>
        <th><spring:message code="user.sal"/></th>
    </tr>
    <c:forEach var="emp" items="${emps}">
        <tr>
            <td>${emp.number}</td>
            <td>${emp.empName}</td>
            <td>${emp.empSex}</td>
            <td>${emp.education}</td>
            <td>${emp.monthly}</td>
        </tr>
    </c:forEach>
</table>

</body>
<script src="../../js/jquery.js"></script>
<script type="text/javascript">


    function text() {
        var str = new URLSearchParams();
            str.append("key","value");
            str.append("符号","可自动转换");
        fetch("/url", {
            method: "post",
            headers: {
                "context-type":"multipart/form-data"
            },
            body: JSON.stringify(str)
        }).then(resp => reso.body)
    };
    





    /*数组*/

    var i = 0;

    function add() {
        i++;
        var t = "<tr><td><input type='text' name='addEmp[" + i + "].number'/>" +
            "</td><td><input type='text' name='addEmp[" + i + "].empName'/>" +
            "</td><td><select name='addEmp[" + i + "].empSex'><option value='本科'>请选择</option>" +
            "<option value='男'>男</option>" +
            "<option value='女'>女</option></select></td>" +
            "<td><select name='addEmp[" + i + "].education'> <option value='本科'>请选择</option>" +
            "<option value='专科'>专科</option>" +
            "<option value='硕士'>硕士</option>" +
            "<option value='博士'>博士</option></select></td>" +
            "<td><input type='number' name='addEmp[" + i + "].monthly'/></td>" +
            "<td><input type='button' value='+' onclick='add()'/></td>" +
            "<td><input type='button' value='-' onclick='rem(this)'/></td></tr>";
        var table = document.getElementById("emps_table").innerHTML;
        table += t;
        document.getElementById("emps_table").innerHTML = table;
    }

    function rem(obj) {
        if (i > 0) {
            i--;
            obj.parentElement.parentElement.remove();
        } else {
            alert("不能再移除了！")
        }
    }
</script>
</html>
