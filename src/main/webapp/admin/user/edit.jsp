<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head><title>编辑账号</title></head>
<body>
<h2>编辑账号</h2>
<form action="/admin/user" method="post">
    <input type="hidden" name="action" value="edit" />
    <input type="hidden" name="yhm" value="${user.zyc_yhm}" />
    角色：<input type="text" name="role" value="${user.zyc_role}" required /><br/>
    状态：<input type="text" name="status" value="${user.zyc_status}" /><br/>
    <button type="submit">保存</button>
</form>
<a href="list.jsp">返回列表</a>
</body>
</html>
