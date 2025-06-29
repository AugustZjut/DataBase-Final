package com.example.dbdemo.bean;
import java.math.BigDecimal;
import java.sql.Date;

public class Xuesheng {
    private String zyc_xh;
    private String zyc_xsxm;
    private String zyc_xsxb;
    private Date zyc_xscsrq;
    private int zyc_syd;
    private String zyc_syd_name; // 生源地名称
    private BigDecimal zyc_yxxf;
    private int zyc_bjbh;
    private String zyc_bjmc; // 行政班名称

    public String getZyc_xh() { return zyc_xh; }
    public void setZyc_xh(String zyc_xh) { this.zyc_xh = zyc_xh; }
    public String getZyc_xsxm() { return zyc_xsxm; }
    public void setZyc_xsxm(String zyc_xsxm) { this.zyc_xsxm = zyc_xsxm; }
    public String getZyc_xsxb() { return zyc_xsxb; }
    public void setZyc_xsxb(String zyc_xsxb) { this.zyc_xsxb = zyc_xsxb; }
    public Date getZyc_xscsrq() { return zyc_xscsrq; }
    public void setZyc_xscsrq(Date zyc_xscsrq) { this.zyc_xscsrq = zyc_xscsrq; }
    public int getZyc_syd() { return zyc_syd; }
    public void setZyc_syd(int zyc_syd) { this.zyc_syd = zyc_syd; }
    public String getZyc_syd_name() { return zyc_syd_name; }
    public void setZyc_syd_name(String zyc_syd_name) { this.zyc_syd_name = zyc_syd_name; }
    public BigDecimal getZyc_yxxf() { return zyc_yxxf; }
    public void setZyc_yxxf(BigDecimal zyc_yxxf) { this.zyc_yxxf = zyc_yxxf; }
    public int getZyc_bjbh() { return zyc_bjbh; }
    public void setZyc_bjbh(int zyc_bjbh) { this.zyc_bjbh = zyc_bjbh; }
    public String getZyc_bjmc() { return zyc_bjmc; }
    public void setZyc_bjmc(String zyc_bjmc) { this.zyc_bjmc = zyc_bjmc; }
}
