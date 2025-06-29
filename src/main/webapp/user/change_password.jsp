<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>修改密码</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f4f4f4; margin: 0; }
        .container { max-width: 400px; margin: 2rem auto; background: #fff; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.08); padding: 2rem; }
        h2 { color: #dc3545; text-align: center; }
        form { display: flex; flex-direction: column; gap: 1rem; }
        label { font-weight: bold; }
        input[type="password"] { padding: 0.5rem; border-radius: 4px; border: 1px solid #ccc; }
        button { padding: 0.6rem 1.5rem; background: #dc3545; color: #fff; border: none; border-radius: 4px; font-weight: bold; cursor: pointer; }
        button:hover { background: #b52a37; }
        .msg { color: #dc3545; text-align: center; margin-bottom: 1rem; }
    </style>
</head>
<body>
<div class="container">
<h2>修改密码</h2>
<c:if test="${not empty msg}"><div class="msg">${msg}</div></c:if>
<form action="${pageContext.request.contextPath}/user/changePassword" method="post">
    <label>原密码：<input type="password" name="oldPwd" required /></label>
    <label>新密码：<input type="password" name="newPwd" required /></label>
    <label>确认新密码：<input type="password" name="confirmPwd" required /></label>
    <button type="submit">提交</button>
</form>
</div>
</body>
</html>
