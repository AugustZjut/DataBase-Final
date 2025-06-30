<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>教师个人主页</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; }
        .header { background-color: #28a745; color: white; padding: 1rem; text-align: center; position: relative; }
        .header h1 { margin: 0; }
        .logout-link { position: absolute; top: 1.5rem; right: 1.5rem; color: white; text-decoration: none; }
        .container { max-width: 1000px; margin: 2rem auto; padding: 2rem; background-color: #fff; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        .info-table, .course-table { width: 100%; border-collapse: collapse; margin-bottom: 2rem; }
        .info-table th, .info-table td, .course-table th, .course-table td { padding: 0.8rem; border: 1px solid #ddd; text-align: left; }
        .info-table th { background-color: #f2f2f2; width: 150px; }
        .course-table th { background-color: #e9ecef; }
        .query-form { margin-bottom: 2rem; }
        .query-form input, .query-form button { padding: 0.5rem; border-radius: 4px; border: 1px solid #ddd; }
        .query-form button { background-color: #28a745; color: white; cursor: pointer; }
    </style>
</head>
<body>

    <div class="header">
        <h1>教师工作台</h1>
        <a href="${pageContext.request.contextPath}/logout" class="logout-link">退出登录</a>
        <a href="${pageContext.request.contextPath}/user/changePassword" style="position:absolute; top:1.5rem; left:1.5rem; color:white; text-decoration:underline; font-size:1rem;">修改密码</a>
    </div>

    <div class="container">
        <c:if test="${teacherInfo != null}">
            <h2>欢迎您, ${teacherInfo.jsxmc} 老师!</h2>
            <p>以下是您的本学期工作量总览：</p>
            <table class="info-table">
                <tr>
                    <th>教师姓名</th><td>${teacherInfo.jsxmc}</td>
                    <th>职称</th><td>${teacherInfo.jszc}</td>
                </tr>
                <tr>
                    <th>授课课程数</th><td>${teacherInfo.courseCount}</td>
                    <th>授课教学班数</th><td>${teacherInfo.classCount}</td>
                </tr>
                <tr>
                    <th>总学时</th><td>${teacherInfo.totalHours}</td>
                    <th>授课学生数</th><td>${teacherInfo.studentCount}</td>
                </tr>
            </table>
        </c:if>

        <h2>我的授课</h2>
        <div class="query-form">
            <form action="${pageContext.request.contextPath}/teacher/dashboard" method="get">
                <label for="semester">按学期查询:</label>
                <input type="text" id="semester" name="semester" value="${selectedSemester}" placeholder="例如: 2023-2024-1">
                <label for="courseName" style="margin-left:1rem;">课程名称:</label>
                <input type="text" id="courseName" name="courseName" value="${selectedCourseName}" placeholder="支持模糊查询">
                <button type="submit">查询</button>
                <a href="${pageContext.request.contextPath}/teacher/dashboard">查询全部</a>
            </form>
        </div>

        <table class="course-table">
            <thead>
                <tr>
                    <th>教学班名称</th>
                    <th>课程名称</th>
                    <th>学期</th>
                    <th>上课时间</th>
                    <th>上课地点</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="course" items="${courseList}">
                    <tr>
                        <td>${course.zyc_jxbmc}</td>
                        <td>${course.zyc_kcmc}</td>
                        <td>${course.zyc_kkxq}</td>
                        <td>${course.zyc_sksj}</td>
                        <td>${course.zyc_skdd}</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/teacher/scoreEntryPage" method="get" style="margin:0;">
                                <input type="hidden" name="jxbbh" value="${course.zyc_jxbbh}" />
                                <input type="hidden" name="xq" value="${course.zyc_kkxq}" />
                                <input type="submit" value="成绩录入" />
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty courseList}">
                    <tr>
                        <td colspan="6" style="text-align: center;">没有找到相关授课记录。</td>
                    </tr>
                </c:if>
            </tbody>
        </table>

        <a href="${pageContext.request.contextPath}/user/changePassword" style="margin-left:1rem; color:#007bff;">修改密码</a>
    </div>

</body>
</html>
