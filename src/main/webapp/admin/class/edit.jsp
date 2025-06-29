<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>编辑教学班</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f4f4f4; margin: 0; }
        .container { max-width: 500px; margin: 2rem auto; background: #fff; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.08); padding: 2rem; }
        h2 { color: #dc3545; text-align: center; }
        form { display: flex; flex-direction: column; gap: 1rem; }
        label { font-weight: bold; }
        input[type="text"], input[type="password"], input[type="date"] { padding: 0.5rem; border-radius: 4px; border: 1px solid #ccc; }
        button { padding: 0.6rem 1.5rem; background: #dc3545; color: #fff; border: none; border-radius: 4px; font-weight: bold; cursor: pointer; }
        button:hover { background: #b52a37; }
        .back-link { display: inline-block; margin-top: 1.5rem; color: #666; text-decoration: underline; }
    </style>
</head>
<body>
<div class="container">
<h2>编辑教学班</h2>
<form action="${pageContext.request.contextPath}/admin/class" method="post">
    <input type="hidden" name="action" value="edit" />
    <input type="hidden" name="jxbbh" value="${clazz.zyc_jxbbh}" />
    <label>班名：<input type="text" name="jxbmc" value="${clazz.zyc_jxbmc}" required /></label>
    <label>课程编号：<input type="text" name="kcbh" value="${clazz.zyc_kcbh}" required /></label>
    <label>授课教师：
        <select name="jsbh" required>
            <option value="">请选择教师</option>
            <c:forEach var="t" items="${teacherList}">
                <option value="${t.jsbh}" ${t.jsbh == clazz.zyc_jsbh ? 'selected' : ''}>${t.jsbh} - ${t.jsxmc}</option>
            </c:forEach>
        </select>
    </label>
    <label>上课时间：<input type="text" name="sksj" value="${clazz.zyc_sksj}" /></label>
    <label>上课地点：<input type="text" name="skdd" value="${clazz.zyc_skdd}" /></label>
    <label>选定人数：<input type="text" name="xdrs" value="${clazz.zyc_xdrs}" /></label>
    <button type="submit">保存</button>
</form>
<a class="back-link" href="${pageContext.request.contextPath}/admin/class">返回列表</a>
</div>
</body>
</html>
