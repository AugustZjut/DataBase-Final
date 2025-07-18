<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>编辑课程</title>
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
<h2>编辑课程</h2>
<form action="${pageContext.request.contextPath}/admin/course" method="post">
    <input type="hidden" name="action" value="edit" />
    <input type="hidden" name="kcbh" value="${course.zyc_kcbh}" />
    <label>课程名称：<input type="text" name="kcmc" value="${course.zyc_kcmc}" required /></label>
    <label>学分：<input type="text" name="xf" value="${course.zyc_xf}" required /></label>
    <label>开课学期：<input type="text" name="kkxq" value="${course.zyc_kkxq}" /></label>
    <label>学时：<input type="text" name="xs" value="${course.zyc_xs}" /></label>
    <label>课程类型：<input type="text" name="ksfs" value="${course.zyc_ksfs}" /></label>
    <button type="submit">保存</button>
</form>
<a class="back-link" href="${pageContext.request.contextPath}/admin/course">返回列表</a>
</div>
</body>
</html>
