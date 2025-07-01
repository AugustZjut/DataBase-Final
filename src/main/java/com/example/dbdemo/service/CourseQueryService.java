package com.example.dbdemo.service;

import com.example.dbdemo.bean.Jiaoxueban;
import com.example.dbdemo.dao.JiaoxuebanDAO;
import com.example.dbdemo.dao.ChengjiDAO;
import com.example.dbdemo.bean.Chengji;
import com.example.dbdemo.dao.KechengDAO;
import com.example.dbdemo.dao.JiaoshiDAO;
import com.example.dbdemo.bean.Kecheng;
import com.example.dbdemo.bean.Jiaoshi;
import com.example.dbdemo.util.DBUtil;
import com.example.dbdemo.util.ConfigUtil;
import java.sql.Connection;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class CourseQueryService {
    private JiaoxuebanDAO jiaoxuebanDAO = new JiaoxuebanDAO();
    private ChengjiDAO chengjiDAO = new ChengjiDAO();

    // 查询未被该学生选过的教学班，返回包含课程名、教师名、职称等信息的Map列表
    public List<Map<String, Object>> getAvailableCourses(String xh, String semester) {
        List<Jiaoxueban> all = jiaoxuebanDAO.findAll();
        List<Chengji> selected = chengjiDAO.findAll();
        HashSet<Integer> selectedJxbbh = new HashSet<>();
        for (Chengji cj : selected) {
            if (cj.getZyc_xh().equals(xh) && (semester == null || semester.equals(cj.getZyc_xq()))) {
                selectedJxbbh.add(cj.getZyc_jxbbh());
            }
        }
        List<Jiaoxueban> available = all.stream()
            .filter(jxb -> !selectedJxbbh.contains(jxb.getZyc_jxbbh()))
            .collect(Collectors.toList());
        // 查询课程、教师信息
        List<Kecheng> kcList = new KechengDAO().findAll();
        Map<Integer, Kecheng> kcMap = new HashMap<>();
        for (Kecheng kc : kcList) kcMap.put(kc.getZyc_kcbh(), kc);
        List<Map<String, Object>> result = new java.util.ArrayList<>();
        try (Connection conn = DBUtil.getConnection()) {
            List<Jiaoshi> jsList = new JiaoshiDAO().findAll(conn);
            Map<String, Jiaoshi> jsMap = new HashMap<>();
            for (Jiaoshi js : jsList) jsMap.put(js.getJsbh(), js);
            for (Jiaoxueban jxb : available) {
                Map<String, Object> map = new HashMap<>();
                // map.put("zyc_jxbbh", jxb.getZyc_jxbbh()); // 不传编号到前端
                map.put("zyc_jxbmc", jxb.getZyc_jxbmc());
                map.put("zyc_sksj", jxb.getZyc_sksj());
                map.put("zyc_skdd", jxb.getZyc_skdd());
                Kecheng kc = kcMap.get(jxb.getZyc_kcbh());
                // map.put("zyc_kcbh", jxb.getZyc_kcbh()); // 不传编号到前端
                map.put("zyc_kcmc", kc != null ? kc.getZyc_kcmc() : "");
                Jiaoshi js = jsMap.get(jxb.getZyc_jsbh());
                // map.put("zyc_jsbh", jxb.getZyc_jsbh()); // 不传编号到前端
                map.put("zyc_jsxm", js != null ? js.getJsxmc() : "");
                map.put("zyc_jszc", js != null ? js.getJszc() : "");
                map.put("zyc_kkxq", kc != null ? kc.getZyc_kkxq() : "");
                map.put("zyc_xf", kc != null ? kc.getZyc_xf() : null);
                result.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 获取下学期字符串
    private String getNextSemester() {
        String list = ConfigUtil.get("semester.list");
        String current = ConfigUtil.getCurrentSemester();
        if (list != null && current != null) {
            String[] arr = list.split(",");
            for (int i = 0; i < arr.length - 1; i++) {
                if (arr[i].trim().equals(current.trim())) {
                    return arr[i + 1].trim();
                }
            }
        }
        return null;
    }

    // 查询下学期未被该学生选过的教学班
    public List<Map<String, Object>> getNextSemesterAvailableCourses(String xh) {
        String nextSemester = getNextSemester();
        if (nextSemester == null) return java.util.Collections.emptyList();
        List<Jiaoxueban> all = jiaoxuebanDAO.findAll();
        List<Chengji> selected = chengjiDAO.findAll();
        HashSet<Integer> selectedJxbbh = new HashSet<>();
        for (Chengji cj : selected) {
            if (cj.getZyc_xh().equals(xh) && nextSemester.equals(cj.getZyc_xq())) {
                selectedJxbbh.add(cj.getZyc_jxbbh());
            }
        }
        // 只保留下学期的教学班
        List<Kecheng> kcList = new KechengDAO().findAll();
        Map<Integer, Kecheng> kcMap = new HashMap<>();
        for (Kecheng kc : kcList) kcMap.put(kc.getZyc_kcbh(), kc);
        List<Jiaoxueban> available = all.stream()
            .filter(jxb -> {
                Kecheng kc = kcMap.get(jxb.getZyc_kcbh());
                return kc != null && nextSemester.equals(kc.getZyc_kkxq()) && !selectedJxbbh.contains(jxb.getZyc_jxbbh());
            })
            .collect(Collectors.toList());
        List<Map<String, Object>> result = new java.util.ArrayList<>();
        try (Connection conn = DBUtil.getConnection()) {
            List<Jiaoshi> jsList = new JiaoshiDAO().findAll(conn);
            Map<String, Jiaoshi> jsMap = new HashMap<>();
            for (Jiaoshi js : jsList) jsMap.put(js.getJsbh(), js);
            for (Jiaoxueban jxb : available) {
                Map<String, Object> map = new HashMap<>();
                // map.put("zyc_jxbbh", jxb.getZyc_jxbbh()); // 不传编号到前端
                map.put("zyc_jxbmc", jxb.getZyc_jxbmc());
                map.put("zyc_sksj", jxb.getZyc_sksj());
                map.put("zyc_skdd", jxb.getZyc_skdd());
                Kecheng kc = kcMap.get(jxb.getZyc_kcbh());
                // map.put("zyc_kcbh", jxb.getZyc_kcbh()); // 不传编号到前端
                map.put("zyc_kcmc", kc != null ? kc.getZyc_kcmc() : "");
                Jiaoshi js = jsMap.get(jxb.getZyc_jsbh());
                // map.put("zyc_jsbh", jxb.getZyc_jsbh()); // 不传编号到前端
                map.put("zyc_jsxm", js != null ? js.getJsxmc() : "");
                map.put("zyc_jszc", js != null ? js.getJszc() : "");
                map.put("zyc_kkxq", kc != null ? kc.getZyc_kkxq() : "");
                map.put("zyc_xf", kc != null ? kc.getZyc_xf() : "");
                result.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 查询下学期所有教学班及选课状态
    public List<Map<String, Object>> getNextSemesterAllCoursesWithSelectStatus(String xh) {
        String nextSemester = getNextSemester();
        if (nextSemester == null) return java.util.Collections.emptyList();
        List<Jiaoxueban> all = jiaoxuebanDAO.findAll();
        List<Chengji> selected = chengjiDAO.findAll();
        HashSet<Integer> selectedJxbbh = new HashSet<>();
        for (Chengji cj : selected) {
            if (cj.getZyc_xh().equals(xh) && nextSemester.equals(cj.getZyc_xq())) {
                selectedJxbbh.add(cj.getZyc_jxbbh());
            }
        }
        // 只保留下学期的教学班
        List<Kecheng> kcList = new KechengDAO().findAll();
        Map<Integer, Kecheng> kcMap = new HashMap<>();
        for (Kecheng kc : kcList) kcMap.put(kc.getZyc_kcbh(), kc);
        List<Map<String, Object>> result = new java.util.ArrayList<>();
        try (Connection conn = DBUtil.getConnection()) {
            List<Jiaoshi> jsList = new JiaoshiDAO().findAll(conn);
            Map<String, Jiaoshi> jsMap = new HashMap<>();
            for (Jiaoshi js : jsList) jsMap.put(js.getJsbh(), js);
            for (Jiaoxueban jxb : all) {
                Kecheng kc = kcMap.get(jxb.getZyc_kcbh());
                if (kc != null && nextSemester.equals(kc.getZyc_kkxq())) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("zyc_jxbbh", jxb.getZyc_jxbbh()); // 传递教学班编号到前端
                    map.put("zyc_jxbmc", jxb.getZyc_jxbmc());
                    map.put("zyc_sksj", jxb.getZyc_sksj());
                    map.put("zyc_skdd", jxb.getZyc_skdd());
                    map.put("zyc_kcmc", kc.getZyc_kcmc());
                    Jiaoshi js = jsMap.get(jxb.getZyc_jsbh());
                    // map.put("zyc_jsbh", jxb.getZyc_jsbh()); // 不传编号到前端
                    map.put("zyc_jsxm", js != null ? js.getJsxmc() : "");
                    map.put("zyc_jszc", js != null ? js.getJszc() : "");
                    map.put("selected", selectedJxbbh.contains(jxb.getZyc_jxbbh()));
                    map.put("zyc_kkxq", kc.getZyc_kkxq());
                    result.add(map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
