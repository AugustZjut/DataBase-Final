package com.example.dbdemo.service;

import com.example.dbdemo.bean.Jiaoshi;
import com.example.dbdemo.bean.Jiaoxueban;
import com.example.dbdemo.bean.Kecheng;
import com.example.dbdemo.dao.JiaoshiDAO;
import com.example.dbdemo.dao.JiaoxuebanDAO;
import com.example.dbdemo.dao.KechengDAO;
import com.example.dbdemo.util.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TeacherService {
    private JiaoshiDAO jiaoshiDAO = new JiaoshiDAO();
    private JiaoxuebanDAO jiaoxuebanDAO = new JiaoxuebanDAO();
    private KechengDAO kechengDAO = new KechengDAO();

    public Jiaoshi getTeacherInfo(String zyc_jsbh) {
        try (Connection conn = DBUtil.getConnection()) {
            List<Jiaoshi> list = jiaoshiDAO.findAll(conn);
            List<Jiaoxueban> allJxb = jiaoxuebanDAO.findAll();
            List<Kecheng> kechengList = kechengDAO.findAll();
            for (Jiaoshi j : list) {
                if (j.getJsbh().equals(zyc_jsbh)) {
                    // 统计授课教学班
                    List<Jiaoxueban> myJxb = allJxb.stream()
                        .filter(x -> x.getZyc_jsbh().equals(zyc_jsbh))
                        .collect(Collectors.toList());
                    j.setClassCount(myJxb.size());
                    // 授课课程数
                    long courseCount = myJxb.stream().map(Jiaoxueban::getZyc_kcbh).distinct().count();
                    j.setCourseCount((int) courseCount);
                    // 总学时（假设每个教学班的课程学时相加）
                    int totalHours = myJxb.stream()
                        .mapToInt(x -> kechengList.stream()
                            .filter(k -> k.getZyc_kcbh() == x.getZyc_kcbh())
                            .mapToInt(Kecheng::getZyc_xs).findFirst().orElse(0))
                        .sum();
                    j.setTotalHours(totalHours);
                    // 授课学生数（所有教学班选课人数之和）
                    int studentCount = myJxb.stream().mapToInt(Jiaoxueban::getZyc_xdrs).sum();
                    j.setStudentCount(studentCount);
                    return j;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Map<String, Object>> getTaughtCourses(String zyc_jsbh, String semester) {
        // 只返回该教师的教学班，若有学期参数可加过滤
        try {
            List<Jiaoxueban> all = jiaoxuebanDAO.findAll();
            List<Kecheng> kechengList = kechengDAO.findAll();
            return all.stream()
                .filter(jxb -> jxb.getZyc_jsbh().equals(zyc_jsbh))
                .map(jxb -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("zyc_jxbbh", jxb.getZyc_jxbbh());
                    map.put("zyc_jxbmc", jxb.getZyc_jxbmc());
                    // 课程信息
                    Kecheng kc = kechengList.stream()
                        .filter(k -> k.getZyc_kcbh() == jxb.getZyc_kcbh())
                        .findFirst().orElse(null);
                    map.put("zyc_kcmc", kc != null ? kc.getZyc_kcmc() : "");
                    map.put("zyc_kkxq", kc != null ? kc.getZyc_kkxq() : ""); // 学期
                    map.put("zyc_sksj", jxb.getZyc_sksj()); // 上课时间
                    map.put("zyc_skdd", jxb.getZyc_skdd()); // 上课地点
                    return map;
                })
                .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    /**
     * 教师端：按教师ID、学期、课程名称模糊查询授课
     */
    public List<Map<String, Object>> getMyTaughtCourses(String jsbh, String xq, String kcmc) {
        try {
            List<Jiaoxueban> list = jiaoxuebanDAO.findByTeacherAndCondition(jsbh, xq, kcmc);
            List<Kecheng> kechengList = kechengDAO.findAll();
            return list.stream().map(jxb -> {
                Map<String, Object> map = new HashMap<>();
                map.put("zyc_jxbbh", jxb.getZyc_jxbbh());
                map.put("zyc_jxbmc", jxb.getZyc_jxbmc());
                // 课程信息
                Kecheng kc = kechengList.stream().filter(k -> k.getZyc_kcbh() == jxb.getZyc_kcbh()).findFirst().orElse(null);
                map.put("zyc_kcmc", kc != null ? kc.getZyc_kcmc() : "");
                map.put("zyc_kkxq", kc != null ? kc.getZyc_kkxq() : ""); // 学期
                map.put("zyc_sksj", jxb.getZyc_sksj());
                map.put("zyc_skdd", jxb.getZyc_skdd());
                return map;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
