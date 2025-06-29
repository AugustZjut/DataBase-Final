<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>成绩录入</title>
    <style>
        table { border-collapse: collapse; width: 80%; margin: 20px auto; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: center; }
        th { background: #f5f5f5; }
        .msg { color: green; text-align: center; }
    </style>
</head>
<body>
<h2 style="text-align:center;">成绩录入</h2>
<c:if test="${not empty message}">
    <div class="msg">${message}</div>
</c:if>
<table>
    <tr>
        <th>学号</th>
        <th>姓名</th>
        <th>课程</th>
        <th>教学班</th>
        <th>成绩</th>
        <th>操作</th>
    </tr>
    <c:forEach var="stu" items="${studentList}">
        <tr>
            <form action="scoreEntry" method="post">
                <td><input type="hidden" name="xh" value="${stu.xh}"/>${stu.xh}</td>
                <td>${stu.xm}</td>
                <td>${stu.kcm}</td>
                <td><input type="hidden" name="jxbbh" value="${stu.jxbbh}"/>${stu.jxbbh}</td>
                <td><input type="text" name="cj" value="${stu.cj}" size="5"/></td>
                <td>
                    <input type="hidden" name="xq" value="${stu.xq}"/>
                    <input type="submit" value="保存"/>
                </td>
            </form>
        </tr>
    </c:forEach>
    <c:if test="${empty studentList}">
        <tr><td colspan="6">暂无学生可录入成绩，请确认该班有学生选课。</td></tr>
    </c:if>
</table>
</body>
</html>
