package com.example.dbdemo.bean;
import java.sql.Date;

public class Jiaoshi {
    private String jsbh; // 教师编号
    private String jsxmc; // 教师姓名
    private String jsxb; // 教师性别
    private Date jscsrq; // 教师出生日期
    private String jszc; // 教师职称
    private String jslxdh; // 教师联系电话

    // 业务统计字段
    private int courseCount;
    private int classCount;
    private int totalHours;
    private int studentCount;

    public String getJsbh() { return jsbh; }
    public void setJsbh(String jsbh) { this.jsbh = jsbh; }
    public String getJsxmc() { return jsxmc; }
    public void setJsxmc(String jsxmc) { this.jsxmc = jsxmc; }
    public String getJsxb() { return jsxb; }
    public void setJsxb(String jsxb) { this.jsxb = jsxb; }
    public Date getJscsrq() { return jscsrq; }
    public void setJscsrq(Date jscsrq) { this.jscsrq = jscsrq; }
    public String getJszc() { return jszc; }
    public void setJszc(String jszc) { this.jszc = jszc; }
    public String getJslxdh() { return jslxdh; }
    public void setJslxdh(String jslxdh) { this.jslxdh = jslxdh; }

    // 业务统计字段getter/setter
    public int getCourseCount() { return courseCount; }
    public void setCourseCount(int courseCount) { this.courseCount = courseCount; }
    public int getClassCount() { return classCount; }
    public void setClassCount(int classCount) { this.classCount = classCount; }
    public int getTotalHours() { return totalHours; }
    public void setTotalHours(int totalHours) { this.totalHours = totalHours; }
    public int getStudentCount() { return studentCount; }
    public void setStudentCount(int studentCount) { this.studentCount = studentCount; }

    // 兼容JSP的name、teacherId、title属性
    public String getName() { return jsxmc; }
    public String getTeacherId() { return jsbh; }
    public String getTitle() { return jszc; }
}
