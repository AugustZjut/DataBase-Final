<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>登录 - 高校学生成绩管理系统</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }
        .login-container { background-color: #fff; padding: 2rem; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); width: 300px; }
        h2 { text-align: center; color: #333; }
        .form-group { margin-bottom: 1rem; }
        label { display: block; margin-bottom: 0.5rem; color: #555; }
        input[type="text"], input[type="password"] { width: 100%; padding: 0.5rem; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; }
        button { width: 100%; padding: 0.7rem; background-color: #007bff; color: white; border: none; border-radius: 4px; cursor: pointer; font-size: 1rem; }
        button:hover { background-color: #0056b3; }
        .error-message { color: red; text-align: center; margin-top: 1rem; }
    </style>
</head>
<body>

<div class="login-container">
    <h2>系统登录</h2>
    <form action="login" method="post">
        <div class="form-group">
            <label for="username">账号:</label>
            <input type="text" id="username" name="username" required>
        </div>
        <div class="form-group">
            <label for="password">密码:</label>
            <input type="password" id="password" name="password" required>
        </div>
        <button type="submit">登 录</button>
    </form>
    <%-- Display error message if login fails --%>
    <%
        String error = (String) request.getAttribute("error");
        if (error != null) {
    %>
    <p class="error-message"><%= error %></p>
    <%
        }
    %>
    <c:if test="${not empty param.msg}">
        <div class="error-message">${param.msg}</div>
    </c:if>
</div>

</body>
</html>
