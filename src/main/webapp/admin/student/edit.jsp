<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>编辑学生</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f4f4f4; margin: 0; }
        .container { max-width: 500px; margin: 2rem auto; background: #fff; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.08); padding: 2rem; }
        h2 { color: #dc3545; text-align: center; }
        form { display: flex; flex-direction: column; gap: 1rem; }
        label { font-weight: bold; }
        input[type="text"], input[type="password"], input[type="date"], select { padding: 0.5rem; border-radius: 4px; border: 1px solid #ccc; }
        button { padding: 0.6rem 1.5rem; background: #dc3545; color: #fff; border: none; border-radius: 4px; font-weight: bold; cursor: pointer; }
        button:hover { background: #b52a37; }
        .back-link { display: inline-block; margin-top: 1.5rem; color: #666; text-decoration: underline; }
    </style>
</head>
<body>
<div class="container">
<h2>编辑学生</h2>
<form action="${pageContext.request.contextPath}/admin/student" method="post">
    <input type="hidden" name="action" value="edit" />
    <input type="hidden" name="xh" value="${student.zyc_xh}" />
    <label>姓名：<input type="text" name="xsxm" value="${student.zyc_xsxm}" required /></label>
    <label>性别：<input type="text" name="xsxb" value="${student.zyc_xsxb}" /></label>
    <label>出生日期：<input type="text" name="xscsrq" value="${student.zyc_xscsrq}" /></label>
    <label>行政班：
        <select name="bjbh" required>
            <option value="">请选择</option>
            <c:forEach var="ban" items="${banList}">
                <option value="${ban.xzbbh}" ${student.zyc_bjbh == ban.xzbbh ? 'selected' : ''}>${ban.xzbmc}</option>
            </c:forEach>
        </select>
    </label>
    <label>生源地：
        <select name="syd" required>
            <option value="">请选择</option>
            <c:forEach var="dq" items="${diquList}">
                <option value="${dq.dqbh}" ${student.zyc_syd == dq.dqbh ? 'selected' : ''}>${dq.dqmc}</option>
            </c:forEach>
        </select>
    </label>
    <button type="submit">保存</button>
</form>
<a class="back-link" href="list.jsp">返回列表</a>
</div>
</body>
</html>
