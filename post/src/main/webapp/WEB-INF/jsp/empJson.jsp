<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="title"/></title>
    <style type="text/css">
        .tab {
            text-align: center;
            height: 40px;
            line-height: 40px;
        }

        .tab {
            background-color: #F8F8F8;
        }

        section {
            height: 300px;
            width: 800px;
            border: 1px solid #930;
        }

        .div0 {
            width: 800px;
            position: relative;
            border: 1px solid #C39
        }

        .div1 {
            width: 800px;
        }

        .div2 {
            width: 800px;
            position: absolute;
            left: 0;
            top: 20px;
            z-index: 5
        }

        marquee {
            width: 800px;
        }
    </style>
</head>
<body>
<h3>
    <marquee behavior="scroll" direction="up">
        <spring:message code="global.greeting" arguments="DZ"/>
    </marquee>
</h3>
<div>
    <spring:message code="page.cl"/>
    <br>
    <a href="/u/zh">中文</a>
    <a href="/u/en">English.</a>
    <a href="/u/jp">小日本</a>
    <br><br>
</div>
<table>
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
        <td><input type="button" value="+" class="empAdd"/></td>
        <td><input type="button" value="-" class="empRem"/></td>
    </tr>
    </tbody>
</table>
<input type="button" id="addAll" value="批量添加" style="display: block;width: 100px;height: 40px; margin: 0 auto"/>


<form method="post" enctype="multipart/form-data">
    选择Excel：<br/>
    <input type="file" name="file"/><span></span><br/>
    <!--<input type="file" name="file"/><span></span><br/>-->
</form>
<br/>
<input type="button" value="导入" onclick="upload()" id="sub"/>
<br/>
<div align="center"><a href="/excel">Excel下载</a></div>

<table class="tab" border="1" align="center" width="60%" bordercolor="#E2E2E2">
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
            <td><a href="/del/${emp.number}">删除</a></td>
        </tr>
    </c:forEach>
</table>


<input type="file" id="fff"/>
<input type="button" id="gg" value="提交测试"/>
</body>
<script src="../../js/jquery.js"></script>
<script type="text/javascript">

    function upload() {
        var formData = new FormData($('form')[0]);
        console.log(formData);
        $.ajax({
            url:"/excelImport",
            method:"post",
            contentType:false,
            data:formData,
            processData:false
        }).done(function (data) {
            if(data.gg === "succeed"){
                window.location.href = "http://localhost:8080/ee";
            }
        });
    }
    //轻量级ajax



    //es6
    function formTest() {
        var data = new FormData();
        data.append("aaa", "第一个数据");
        data.append("file", document.querySelector("#fff").files[0]);
        fetch("/getFile", {
            method: "post",
            body: data
        }).then(resp => reso.body)
    }
    //ajax上传文件
    $("#gg").on("click", function () {
        var data = new FormData();
        data.append("aaa", "第一个数据");
        data.append("file", document.querySelector("#fff").files[0]);
        $.ajax({
            url:"/getFile",
            method:"post",
            contentType:false,
            data:data,
            processData:false
        }).done(function (data) {
            console.log(data.gg);
        });
    });


    $(function () {
        var tbody = $("#tbody01");
        trNode = tbody.clone();
        tbody.on("click", " .empAdd", function () {
            $("#tbody01").append(trNode.clone());
        });

        tbody.on("click", ".empRem", function () {
            var num = $("tr", tbody).length;
            if (num === 1) {
                alert("最后一行不能删除");
                return false;
            }
            $(this).parent().parent().remove();
        });
    });
    /*数组*/
    $("#addAll").click(function () {

        var list = [];
        $("#tbody01 tr").each(function (i, obj) {
            list.push(
                {
                    number: $("input[name=number]", obj).val(),
                    empName: $("input[name=empName]", obj).val(),
                    empSex: $("select[name=empSex]", obj).val(),
                    education: $("select[name=education]", obj).val(),
                    monthly: $("input[name=monthly]", obj).val()
                }
            );
        });
        console.log(list);
        $.post({
            url: "/emp01",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(list) /*JSON.stringify({
                number: $("input[name=number]", $("#tbody01 tr")).val(),
                empName: $("input[name=empName]", $("#tbody01 tr")).val(),
                empSex: $("select[name=empSex]", $("#tbody01 tr")).val(),
                education: $("select[name=education]", $("#tbody01 tr")).val(),
                monthly: $("input[name=monthly]", $("#tbody01 tr")).val()
            }*/
        }).done(function (data) {
            if (data.msg === "succeed") {
                window.location.href = "/ee";
            }
        });
    });


</script>

</html>
