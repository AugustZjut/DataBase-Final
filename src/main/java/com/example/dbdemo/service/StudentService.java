package com.example.dbdemo.service;

import com.example.dbdemo.bean.Xuesheng;
import com.example.dbdemo.bean.Chengji;
import com.example.dbdemo.bean.Diqu;
import com.example.dbdemo.bean.Xingzhengban;
import com.example.dbdemo.dao.XueshengDAO;
import com.example.dbdemo.dao.ChengjiDAO;
import com.example.dbdemo.dao.DiquDAO;
import com.example.dbdemo.dao.XingzhengbanDAO;
import com.example.dbdemo.util.DBUtil;
import java.sql.Connection;
import java.util.List;
import java.util.Collections;
import com.example.dbdemo.dao.JiaoxuebanDAO;
import com.example.dbdemo.dao.KechengDAO;
import com.example.dbdemo.bean.Jiaoxueban;
import com.example.dbdemo.bean.Kecheng;
import java.util.Map;
import java.util.HashMap;

public class StudentService {
    private XueshengDAO xueshengDAO = new XueshengDAO();
    private ChengjiDAO chengjiDAO = new ChengjiDAO();
    private DiquDAO diquDAO = new DiquDAO();
    private XingzhengbanDAO xingzhengbanDAO = new XingzhengbanDAO();
    private JiaoxuebanDAO jiaoxuebanDAO = new JiaoxuebanDAO();
    private KechengDAO kechengDAO = new KechengDAO();

    public Xuesheng getStudentInfo(String zyc_xh) {
        List<Xuesheng> list = xueshengDAO.findAll();
        for (Xuesheng x : list) {
            if (x.getZyc_xh().equals(zyc_xh)) {
                // 设置生源地名称
                try (Connection conn = DBUtil.getConnection()) {
                    List<Diqu> diquList = diquDAO.findAll(conn);
                    for (Diqu d : diquList) {
                        if (d.getDqbh() == x.getZyc_syd()) {
                            x.setZyc_syd_name(d.getDqmc());
                            break;
                        }
                    }
                    List<Xingzhengban> xzbList = xingzhengbanDAO.findAll(conn);
                    for (Xingzhengban b : xzbList) {
                        if (b.getXzbbh() == x.getZyc_bjbh()) {
                            x.setZyc_bjmc(b.getXzbmc());
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return x;
            }
        }
        return null;
    }

    public List<Chengji> getGrades(String zyc_xh, String semester) {
        // 只返回该学生的成绩，若有学期参数可加过滤
        try {
            List<Chengji> all = chengjiDAO.findAll();
            return all.stream()
                .filter(cj -> cj.getZyc_xh().equals(zyc_xh))
                .toList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    // 返回带课程名称的成绩列表
    public List<Map<String, Object>> getGradeWithCourseName(String zyc_xh, String semester) {
        List<Chengji> all = chengjiDAO.findAll();
        List<Jiaoxueban> jxbList = jiaoxuebanDAO.findAll();
        List<Kecheng> kcList = kechengDAO.findAll();
        List<Map<String, Object>> result = new java.util.ArrayList<>();
        for (Chengji cj : all) {
            if (cj.getZyc_xh().equals(zyc_xh) && (semester == null || semester.isEmpty() || semester.equals(cj.getZyc_xq()))) {
                Map<String, Object> map = new HashMap<>();
                map.put("zyc_xq", cj.getZyc_xq());
                map.put("zyc_cj", cj.getZyc_cj());
                // 通过教学班编号查课程编号，再查课程名
                int jxbbh = cj.getZyc_jxbbh();
                map.put("zyc_jxbbh", jxbbh);
                String kcmc = "";
                for (Jiaoxueban jxb : jxbList) {
                    if (jxb.getZyc_jxbbh() == jxbbh) {
                        int kcbh = jxb.getZyc_kcbh();
                        for (Kecheng kc : kcList) {
                            if (kc.getZyc_kcbh() == kcbh) {
                                kcmc = kc.getZyc_kcmc();
                                break;
                            }
                        }
                        break;
                    }
                }
                map.put("zyc_kcmc", kcmc);
                result.add(map);
            }
        }
        return result;
    }

    /**
     * 获取学生平均绩点
     */
    public Double getStudentAvgGPA(String xh) {
        try {
            return xueshengDAO.getAvgGPA(xh);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
