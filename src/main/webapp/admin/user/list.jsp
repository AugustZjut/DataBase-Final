<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>账号管理</title>
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
<h2>用户账号管理</h2>
<c:if test="${not empty msg}">
        <div style="color: green; font-weight: bold; margin-bottom: 1rem;">${msg}</div>
    </c:if>
<form method="get" action="${pageContext.request.contextPath}/admin/user" style="margin-bottom: 1.5rem; display: flex; gap: 1rem; align-items: center;">
    <label>账号：<input type="text" name="zh" value="${zh}" style="padding: 0.3rem; border-radius: 4px; border: 1px solid #ccc;"></label>
    <label>状态：<input type="text" name="zt" value="${zt}" style="padding: 0.3rem; border-radius: 4px; border: 1px solid #ccc;"></label>
    <button type="submit" style="padding: 0.4rem 1.2rem; background: #dc3545; color: #fff; border: none; border-radius: 4px; font-weight: bold;">查询</button>
    <a href="${pageContext.request.contextPath}/admin/user" style="color: #666; text-decoration: underline; margin-left: 0.5rem;">重置</a>
</form>
<table>
    <tr>
        <th>用户名</th>
        <th>角色</th>
        <th>状态</th>
        <th>操作</th>
    </tr>
    <c:forEach var="user" items="${userList}">
        <tr>
            <td>${user.zyc_zh}</td>
            <td>${user.zyc_qx}</td>
            <td>${user.zyc_zt}</td>
            <td>
                <c:choose>
                    <c:when test="${user.zyc_zt eq '禁用'}">
                        <a href="${pageContext.request.contextPath}/admin/user?action=enable&zh=${user.zyc_zh}" onclick="return confirm('确定要启用该账号吗？');">启用</a>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/admin/user?action=disable&zh=${user.zyc_zh}" onclick="return confirm('确定要禁用该账号吗？');">禁用</a>
                    </c:otherwise>
                </c:choose>
                <a href="${pageContext.request.contextPath}/admin/user?action=delete&zh=${user.zyc_zh}" onclick="return confirm('确定要删除该账号吗？');">删除</a>
                <a href="${pageContext.request.contextPath}/admin/user?action=reset&zh=${user.zyc_zh}" onclick="return confirm('确定要重置该账号密码吗？');">重置密码</a>
            </td>
        </tr>
    </c:forEach>
</table>
<a class="add-btn" href="${pageContext.request.contextPath}/admin/user/add.jsp">+ 新增账号</a>
<a class="add-btn" href="${pageContext.request.contextPath}/admin/dashboard" style="background:#6c757d; margin-left:1rem;">返回仪表盘</a>
</div>
</body>
</html>
