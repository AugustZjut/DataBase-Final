package com.example.dbdemo.bean;
import java.sql.Date;

public class Teacher {
    private String id; // 教师编号
    private String name; // 教师姓名
    private String gender; // 教师性别
    private Date birthday; // 教师出生日期
    private String title; // 教师职称
    private String phone; // 教师联系电话

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public Date getBirthday() { return birthday; }
    public void setBirthday(Date birthday) { this.birthday = birthday; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
