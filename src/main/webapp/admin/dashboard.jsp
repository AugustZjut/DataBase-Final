<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>管理员主页</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; }
        .header { background-color: #dc3545; color: white; padding: 1rem; text-align: center; position: relative; }
        .header h1 { margin: 0; }
        .logout-link { position: absolute; top: 1.5rem; right: 1.5rem; color: white; text-decoration: none; }
        .container { max-width: 1200px; margin: 2rem auto; padding: 2rem; background-color: #fff; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        .stats-container { display: flex; justify-content: space-around; text-align: center; margin-bottom: 2rem; }
        .stat-card { background-color: #f8f9fa; border: 1px solid #dee2e6; border-radius: 8px; padding: 1.5rem; min-width: 150px; }
        .stat-card h3 { margin-top: 0; color: #007bff; }
        .stat-card p { font-size: 2rem; font-weight: bold; margin-bottom: 0; }
        .nav-modules { display: flex; justify-content: space-between; margin: 2rem 0 1rem 0; }
        .module-card { flex: 1; margin: 0 1rem; background: #f8f9fa; border: 1px solid #dee2e6; border-radius: 8px; text-align: center; padding: 2rem 1rem; transition: box-shadow 0.2s; }
        .module-card:hover { box-shadow: 0 4px 16px rgba(220,53,69,0.15); }
        .module-card a { display: block; color: #dc3545; font-size: 1.3rem; font-weight: bold; text-decoration: none; margin-bottom: 0.5rem; }
        .module-card p { color: #555; font-size: 1rem; }
        @media (max-width: 900px) {
            .nav-modules { flex-direction: column; }
            .module-card { margin: 1rem 0; }
        }
    </style>
</head>
<body>

    <div class="header">
        <h1>系统管理仪表盘</h1>
        <a href="${pageContext.request.contextPath}/logout" class="logout-link">退出登录</a>
    </div>

    <div class="container">
        <h2>系统数据总览</h2>
        <c:if test="${stats != null}">
            <div class="stats-container">
                <div class="stat-card">
                    <h3>学生总数</h3>
                    <p>${stats.studentCount}</p>
                </div>
                <div class="stat-card">
                    <h3>教师总数</h3>
                    <p>${stats.teacherCount}</p>
                </div>
                <div class="stat-card">
                    <h3>课程总数</h3>
                    <p>${stats.courseCount}</p>
                </div>
            </div>
        </c:if>

        <h2>基础管理模块</h2>
        <div class="nav-modules">
            <div class="module-card">
                <a href="${pageContext.request.contextPath}/admin/student?action=list">学生管理</a>
                <p>管理学生信息，包括添加、编辑、删除和查询。</p>
            </div>
            <div class="module-card">
                <a href="${pageContext.request.contextPath}/admin/teacher?action=list">教师管理</a>
                <p>管理教师信息，包括添加、编辑、删除和查询。</p>
            </div>
            <div class="module-card">
                <a href="${pageContext.request.contextPath}/admin/course?action=list">课程管理</a>
                <p>管理课程信息，包括添加、编辑、删除和查询。</p>
            </div>
            <div class="module-card">
                <a href="${pageContext.request.contextPath}/admin/class?action=list">教学班管理</a>
                <p>管理教学班信息，包括添加、编辑、删除和查询。</p>
            </div>
            <div class="module-card">
                <a href="${pageContext.request.contextPath}/admin/user?action=list">账号管理</a>
                <p>管理系统账号，包括添加、编辑、删除和查询。</p>
            </div>
        </div>
    </div>

</body>
</html>
