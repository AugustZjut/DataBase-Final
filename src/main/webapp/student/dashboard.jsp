<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>学生个人主页</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; }
        .header { background-color: #007bff; color: white; padding: 1rem; text-align: center; position: relative; }
        .header h1 { margin: 0; }
        .logout-link { position: absolute; top: 1.5rem; right: 1.5rem; color: white; text-decoration: none; }
        .container { max-width: 1000px; margin: 2rem auto; padding: 2rem; background-color: #fff; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        .info-table, .grade-table { width: 100%; border-collapse: collapse; margin-bottom: 2rem; }
        .info-table th, .info-table td, .grade-table th, .grade-table td { padding: 0.8rem; border: 1px solid #ddd; text-align: left; }
        .info-table th { background-color: #f2f2f2; width: 150px; }
        .grade-table th { background-color: #e9ecef; }
        .query-form { margin-bottom: 2rem; }
        .query-form input, .query-form button { padding: 0.5rem; border-radius: 4px; border: 1px solid #ddd; }
        .query-form button { background-color: #007bff; color: white; cursor: pointer; }
    </style>
</head>
<body>

    <div class="header">
        <h1>学生个人中心</h1>
        <a href="${pageContext.request.contextPath}/logout" class="logout-link">退出登录</a>
        <a href="${pageContext.request.contextPath}/user/changePassword" style="position:absolute; top:1.5rem; left:1.5rem; color:white; text-decoration:underline; font-size:1rem;">修改密码</a>
    </div>

    <div class="container">
        <c:if test="${not empty msg}">
            <div style="color: green; font-weight: bold; margin-bottom: 1rem;">${msg}</div>
        </c:if>

        <c:if test="${studentInfo != null}">
            <h2>个人信息</h2>
            <table class="info-table">
                <tr>
                    <th>学号</th><td>${studentInfo.zyc_xh}</td>
                    <th>姓名</th><td>${studentInfo.zyc_xsxm}</td>
                </tr>
                <tr>
                    <th>性别</th><td>${studentInfo.zyc_xsxb}</td>
                    <th>出生日期</th><td>${studentInfo.zyc_xscsrq}</td>
                </tr>
                <tr>
                    <th>生源地</th><td>${studentInfo.zyc_syd_name}</td>
                    <th>已修学分</th><td>${studentInfo.zyc_yxxf}</td>
                </tr>
                <tr>
                    <th>行政班</th><td>${studentInfo.zyc_bjmc}</td>
                </tr>
            </table>
        </c:if>

        <h2>成绩查询</h2>
        <div class="query-form">
            <form action="${pageContext.request.contextPath}/student/dashboard" method="get">
                <label for="semester">按学期查询:</label>
                <input type="text" id="semester" name="semester" value="${selectedSemester}" placeholder="例如: 2023-2024-1">
                <button type="submit">查询</button>
                <a href="${pageContext.request.contextPath}/student/dashboard">查询全部</a>
            </form>
        </div>

        <table class="grade-table">
            <thead>
                <tr>
                    <th>学期</th>
                    <th>课程名称</th>
                    <th>成绩</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="grade" items="${gradeList}">
                    <tr>
                        <td>${grade.zyc_xq}</td>
                        <td>${grade.zyc_kcmc}</td>
                        <td>
                            <c:choose>
                                <c:when test="${empty grade.zyc_cj}">未评分</c:when>
                                <c:otherwise>${grade.zyc_cj}</c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty gradeList}">
                    <tr>
                        <td colspan="3" style="text-align: center;">没有找到相关成绩记录。</td>
                    </tr>
                </c:if>
            </tbody>
        </table>

        <h2>选课中心</h2>
        <table class="grade-table">
            <thead>
                <tr>
                    <th>课程名称</th>
                    <th>教师姓名</th>
                    <th>教师职称</th>
                    <th>教学班名称</th>
                    <th>上课时间</th>
                    <th>上课地点</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="course" items="${availableCourseList}">
                    <tr>
                        <td>${course.zyc_kcmc}</td>
                        <td>${course.zyc_jsxm}</td>
                        <td>${course.zyc_jszc}</td>
                        <td>${course.zyc_jxbmc}</td>
                        <td>${course.zyc_sksj}</td>
                        <td>${course.zyc_skdd}</td>
                        <td>
                            <c:choose>
                                <c:when test="${course.selected eq true}">
                                    <form action="${pageContext.request.contextPath}/student/selectCourse" method="post" style="display:inline;">
                                        <input type="hidden" name="jxbbh" value="${course.zyc_jxbbh}" />
                                        <input type="hidden" name="xq" value="${course.zyc_kkxq}" />
                                        <input type="hidden" name="action" value="withdraw" />
                                        <button type="submit" style="color:red;">退选</button>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <form action="${pageContext.request.contextPath}/student/selectCourse" method="post" style="display:inline;">
                                        <input type="hidden" name="jxbbh" value="${course.zyc_jxbbh}" />
                                        <input type="hidden" name="xq" value="${course.zyc_kkxq}" />
                                        <button type="submit">选课</button>
                                    </form>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty availableCourseList}">
                    <tr><td colspan="7" style="text-align:center;">暂无可选课程</td></tr>
                </c:if>
            </tbody>
        </table>

    </div>

</body>
</html>
