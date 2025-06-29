<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>教学班管理</title>
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
<h2>教学班信息管理</h2>
<form method="get" action="${pageContext.request.contextPath}/admin/class" style="margin-bottom: 1.5rem; display: flex; gap: 1rem; align-items: center;">
    <label>教学班编号：<input type="text" name="jxbbh" value="${jxbbh}" style="padding: 0.3rem; border-radius: 4px; border: 1px solid #ccc;"></label>
    <label>课程名称：<input type="text" name="kcmc" value="${kcmc}" style="padding: 0.3rem; border-radius: 4px; border: 1px solid #ccc;"></label>
    <label>学期：<input type="text" name="xq" value="${xq}" style="padding: 0.3rem; border-radius: 4px; border: 1px solid #ccc;"></label>
    <button type="submit" style="padding: 0.4rem 1.2rem; background: #dc3545; color: #fff; border: none; border-radius: 4px; font-weight: bold;">查询</button>
    <a href="${pageContext.request.contextPath}/admin/class" style="color: #666; text-decoration: underline; margin-left: 0.5rem;">重置</a>
</form>
<table>
    <tr>
        <th>教学班编号</th>
        <th>教学班名称</th>
        <th>课程名称</th>
        <th>教师编号</th>
        <th>学期</th>
        <th>上课时间</th>
        <th>上课地点</th>
        <th>操作</th>
    </tr>
    <c:forEach var="clazzMap" items="${classList}">
        <tr>
            <td>${clazzMap.jxb.zyc_jxbbh}</td>
            <td>${clazzMap.jxb.zyc_jxbmc}</td>
            <td>${clazzMap.kcmc}</td>
            <td>${clazzMap.jxb.zyc_jsbh}</td>
            <td>${clazzMap.xueqi}</td>
            <td>${clazzMap.jxb.zyc_sksj}</td>
            <td>${clazzMap.jxb.zyc_skdd}</td>
            <td>
                <a href="${pageContext.request.contextPath}/admin/class?action=edit&jxbbh=${clazzMap.jxb.zyc_jxbbh}">编辑</a>
                <a href="${pageContext.request.contextPath}/admin/class?action=delete&jxbbh=${clazzMap.jxb.zyc_jxbbh}" onclick="return confirm('确定要删除该教学班吗？');">删除</a>
            </td>
        </tr>
    </c:forEach>
</table>
<a class="add-btn" href="${pageContext.request.contextPath}/admin/class?action=add">+ 新增教学班</a>
<a class="add-btn" href="${pageContext.request.contextPath}/admin/dashboard" style="background:#6c757d; margin-left:1rem;">返回仪表盘</a>
</div>
</body>
</html>
