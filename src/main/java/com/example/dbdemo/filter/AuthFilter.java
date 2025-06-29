package com.example.dbdemo.filter;

import com.example.dbdemo.bean.Yonghu;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {"/admin/*", "/teacher/*", "/student/*"})
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();

        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);

        if (isLoggedIn) {
            Yonghu yonghu = (Yonghu) session.getAttribute("user");
            String qx = yonghu.getZyc_qx();
            if (requestURI.startsWith(contextPath + "/admin") && "管理员".equals(qx)) {
                chain.doFilter(request, response);
            } else if (requestURI.startsWith(contextPath + "/teacher") && "教师".equals(qx)) {
                chain.doFilter(request, response);
            } else if (requestURI.startsWith(contextPath + "/student") && "学生".equals(qx)) {
                chain.doFilter(request, response);
            } else {
                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        } else {
            httpResponse.sendRedirect(contextPath + "/login.jsp");
        }
    }
}
