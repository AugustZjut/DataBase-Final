<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>教师管理</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f4f4f4; margin: 0; }
        .container { max-width: 900px; margin: 2rem auto; background: #fff; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.08); padding: 2rem; }
        h2 { color: #dc3545; text-align: center; }
        table { width: 100%; border-collapse: collapse; margin-top: 1.5rem; }
        th, td { padding: 0.8rem; border: 1px solid #ddd; text-align: center; }
        th { background: #f8f9fa; color: #333; }
        tr:nth-child(even) { background: #f6f6f6; }
        a { color: #dc3545; text-decoration: none; margin: 0 0.5rem; }
        a:hover { text-decoration: underline; }
        .add-btn { display: inline-block; margin: 1.5rem 0 0 0; padding: 0.5rem 1.5rem; background: #dc3545; color: #fff; border-radius: 4px; text-decoration: none; font-weight: bold; }
        .add-btn:hover { background: #b52a37; }
    </style>
</head>
<body>
<div class="container">
<h2>教师信息管理</h2>
<form method="get" action="${pageContext.request.contextPath}/admin/teacher" style="margin-bottom: 1.5rem; display: flex; gap: 1rem; align-items: center;">
    <label>工号：<input type="text" name="jsbh" value="${param.jsbh}" style="padding: 0.3rem; border-radius: 4px; border: 1px solid #ccc;"></label>
    <label>姓名：<input type="text" name="jsxmc" value="${param.jsxmc}" style="padding: 0.3rem; border-radius: 4px; border: 1px solid #ccc;"></label>
    <label>职称：<input type="text" name="jszc" value="${param.jszc}" style="padding: 0.3rem; border-radius: 4px; border: 1px solid #ccc;"></label>
    <button type="submit" style="padding: 0.4rem 1.2rem; background: #dc3545; color: #fff; border: none; border-radius: 4px; font-weight: bold;">查询</button>
    <a href="${pageContext.request.contextPath}/admin/teacher" style="color: #666; text-decoration: underline; margin-left: 0.5rem;">重置</a>
</form>
<table>
    <tr>
        <th>工号</th>
        <th>姓名</th>
        <th>性别</th>
        <th>职称</th>
        <th>联系方式</th>
        <th>操作</th>
    </tr>
    <c:forEach var="teacher" items="${teacherList}">
        <tr>
            <td>${teacher.jsbh}</td>
            <td>${teacher.jsxmc}</td>
            <td>${teacher.jsxb}</td>
            <td>${teacher.jszc}</td>
            <td>${teacher.jslxdh}</td>
            <td>
                <a href="${pageContext.request.contextPath}/admin/teacher?action=edit&jsbh=${teacher.jsbh}">编辑</a>
                <a href="${pageContext.request.contextPath}/admin/teacher?action=delete&jsbh=${teacher.jsbh}">删除</a>
            </td>
        </tr>
    </c:forEach>
</table>
<a class="add-btn" href="${pageContext.request.contextPath}/admin/teacher/add.jsp">+ 新增教师</a>
<a class="add-btn" href="${pageContext.request.contextPath}/admin/dashboard" style="background:#6c757d; margin-left:1rem;">返回仪表盘</a>
</div>
</body>
</html>
