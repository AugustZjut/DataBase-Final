-- =====================================================
-- 高校学生成绩管理系统数据库初始化脚本
-- 数据库平台：OpenGauss/PostgreSQL
-- 命名规范：zhouyc_表名（以zhouyc为示例）
-- =====================================================

-- 设置搜索路径
SET search_path TO public;

-- =====================================================
-- 2. 创建基本表
-- =====================================================

-- 2.1 创建专业表
CREATE TABLE IF NOT EXISTS zhouyc_zhuanye (
    zyc_zybh SERIAL PRIMARY KEY,
    zyc_zymc VARCHAR(50) NOT NULL UNIQUE
);

COMMENT ON TABLE zhouyc_zhuanye IS '专业信息表';
COMMENT ON COLUMN zhouyc_zhuanye.zyc_zybh IS '专业编号（自增）';
COMMENT ON COLUMN zhouyc_zhuanye.zyc_zymc IS '专业名称';

-- 2.2 创建地区表
CREATE TABLE IF NOT EXISTS zhouyc_diqu (
    zyc_dqbh SERIAL PRIMARY KEY,
    zyc_dqmc VARCHAR(50) NOT NULL
);

COMMENT ON TABLE zhouyc_diqu IS '地区信息表';
COMMENT ON COLUMN zhouyc_diqu.zyc_dqbh IS '地区编号（自增）';
COMMENT ON COLUMN zhouyc_diqu.zyc_dqmc IS '地区名称';

-- 2.3 创建行政班表
CREATE TABLE IF NOT EXISTS zhouyc_xingzhengban (
    zyc_xzbbh SERIAL PRIMARY KEY,
    zyc_xzbmc VARCHAR(50) NOT NULL,
    zyc_zybh INTEGER NOT NULL,
    CONSTRAINT fk_xzb_zy FOREIGN KEY (zyc_zybh) 
        REFERENCES zhouyc_zhuanye(zyc_zybh) 
        ON DELETE RESTRICT ON UPDATE CASCADE
);

COMMENT ON TABLE zhouyc_xingzhengban IS '行政班信息表';
COMMENT ON COLUMN zhouyc_xingzhengban.zyc_xzbbh IS '行政班编号（自增）';
COMMENT ON COLUMN zhouyc_xingzhengban.zyc_xzbmc IS '行政班名称';
COMMENT ON COLUMN zhouyc_xingzhengban.zyc_zybh IS '专业编号';

-- 2.4 创建教师表
CREATE TABLE IF NOT EXISTS zhouyc_jiaoshi (
    zyc_jsbh VARCHAR(10) PRIMARY KEY,
    zyc_jsxm VARCHAR(20) NOT NULL,
    zyc_jsxb VARCHAR(2) NOT NULL CHECK (zyc_jsxb IN ('男', '女')),
    zyc_jscsrq DATE NOT NULL,
    zyc_jszc VARCHAR(20),
    zyc_jslxdh VARCHAR(15),
    CONSTRAINT chk_js_csrq CHECK (zyc_jscsrq <= CURRENT_DATE)
);

COMMENT ON TABLE zhouyc_jiaoshi IS '教师信息表';
COMMENT ON COLUMN zhouyc_jiaoshi.zyc_jsbh IS '教师编号';
COMMENT ON COLUMN zhouyc_jiaoshi.zyc_jsxm IS '教师姓名';
COMMENT ON COLUMN zhouyc_jiaoshi.zyc_jsxb IS '教师性别';
COMMENT ON COLUMN zhouyc_jiaoshi.zyc_jscsrq IS '教师出生日期';
COMMENT ON COLUMN zhouyc_jiaoshi.zyc_jszc IS '教师职称';
COMMENT ON COLUMN zhouyc_jiaoshi.zyc_jslxdh IS '教师联系电话';

-- 2.5 创建学生表
CREATE TABLE IF NOT EXISTS zhouyc_xuesheng (
    zyc_xh VARCHAR(12) PRIMARY KEY,
    zyc_xsxm VARCHAR(20) NOT NULL,
    zyc_xsxb VARCHAR(2) NOT NULL CHECK (zyc_xsxb IN ('男', '女')),
    zyc_xscsrq DATE NOT NULL,
    zyc_syd INTEGER NOT NULL,
    zyc_yxxf DECIMAL(5,2) NOT NULL DEFAULT 0 CHECK (zyc_yxxf >= 0),
    zyc_bjbh INTEGER NOT NULL,
    CONSTRAINT fk_xs_dq FOREIGN KEY (zyc_syd) 
        REFERENCES zhouyc_diqu(zyc_dqbh) 
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_xs_xzb FOREIGN KEY (zyc_bjbh) 
        REFERENCES zhouyc_xingzhengban(zyc_xzbbh) 
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT chk_xs_csrq CHECK (zyc_xscsrq <= CURRENT_DATE)
);

COMMENT ON TABLE zhouyc_xuesheng IS '学生信息表';
COMMENT ON COLUMN zhouyc_xuesheng.zyc_xh IS '学号';
COMMENT ON COLUMN zhouyc_xuesheng.zyc_xsxm IS '学生姓名';
COMMENT ON COLUMN zhouyc_xuesheng.zyc_xsxb IS '学生性别';
COMMENT ON COLUMN zhouyc_xuesheng.zyc_xscsrq IS '学生出生日期';
COMMENT ON COLUMN zhouyc_xuesheng.zyc_syd IS '生源地';
COMMENT ON COLUMN zhouyc_xuesheng.zyc_yxxf IS '已修学分';
COMMENT ON COLUMN zhouyc_xuesheng.zyc_bjbh IS '班级编号';

-- 2.6 创建课程表
CREATE TABLE IF NOT EXISTS zhouyc_kecheng (
    zyc_kcbh SERIAL PRIMARY KEY,
    zyc_kcmc VARCHAR(50) NOT NULL,
    zyc_kkxq VARCHAR(15) NOT NULL,
    zyc_xs INTEGER NOT NULL CHECK (zyc_xs > 0),
    zyc_ksfs VARCHAR(10) NOT NULL CHECK (zyc_ksfs IN ('考试', '考查')),
    zyc_xf DECIMAL(3,1) NOT NULL CHECK (zyc_xf >= 1 AND zyc_xf <= 4),
);

COMMENT ON TABLE zhouyc_kecheng IS '课程信息表';
COMMENT ON COLUMN zhouyc_kecheng.zyc_kcbh IS '课程编号（自增）';
COMMENT ON COLUMN zhouyc_kecheng.zyc_kcmc IS '课程名称';

COMMENT ON COLUMN zhouyc_kecheng.zyc_kkxq IS '开课学期';
COMMENT ON COLUMN zhouyc_kecheng.zyc_xs IS '学时';
COMMENT ON COLUMN zhouyc_kecheng.zyc_ksfs IS '考试方式';
COMMENT ON COLUMN zhouyc_kecheng.zyc_xf IS '学分';

-- 2.7 创建教学班表 - 使用哈希分区设计
CREATE TABLE IF NOT EXISTS zhouyc_jiaoxueban (
    zyc_jxbbh SERIAL PRIMARY KEY,
    zyc_jxbmc VARCHAR(50) NOT NULL,
    zyc_sksj VARCHAR(100) NOT NULL,
    zyc_skdd VARCHAR(50) NOT NULL,
    zyc_kcbh INTEGER NOT NULL,
    zyc_jsbh VARCHAR(10) NOT NULL,
    zyc_xdrs INTEGER CHECK (zyc_xdrs > 0),
    CONSTRAINT fk_jxb_kc FOREIGN KEY (zyc_kcbh)
        REFERENCES zhouyc_kecheng(zyc_kcbh)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_jxb_js FOREIGN KEY (zyc_jsbh)
        REFERENCES zhouyc_jiaoshi(zyc_jsbh)
        ON DELETE RESTRICT ON UPDATE CASCADE
) PARTITION BY HASH(zyc_jxbbh) (
    PARTITION p1,
    PARTITION p2,
    PARTITION p3,
    PARTITION p4
);

COMMENT ON TABLE zhouyc_jiaoxueban IS '教学班信息表（哈希分区）';
COMMENT ON COLUMN zhouyc_jiaoxueban.zyc_jxbbh IS '教学班编号（自增）';
COMMENT ON COLUMN zhouyc_jiaoxueban.zyc_jxbmc IS '教学班名称';
COMMENT ON COLUMN zhouyc_jiaoxueban.zyc_sksj IS '上课时间（格式：星期:节次-节次;星期:节次-节次，如1:1-2;3:3-4表示周一1-2节，周三3-4节）';
COMMENT ON COLUMN zhouyc_jiaoxueban.zyc_skdd IS '上课地点';
COMMENT ON COLUMN zhouyc_jiaoxueban.zyc_kcbh IS '课程编号';
COMMENT ON COLUMN zhouyc_jiaoxueban.zyc_jsbh IS '教师编号';
COMMENT ON COLUMN zhouyc_jiaoxueban.zyc_xdrs IS '限定人数（最大容纳人数）';

-- 2.8 创建SC表（成绩表）- 使用分区设计
CREATE TABLE IF NOT EXISTS zhouyc_sc (
    zyc_xh VARCHAR(12) NOT NULL,
    zyc_xq VARCHAR(15) NOT NULL,
    zyc_jxbbh INTEGER NOT NULL,
    zyc_cj DECIMAL(5,2) CHECK (zyc_cj >= 0 AND zyc_cj <= 100),
    PRIMARY KEY (zyc_xh, zyc_xq, zyc_jxbbh),
    CONSTRAINT fk_sc_xs FOREIGN KEY (zyc_xh)
        REFERENCES zhouyc_xuesheng(zyc_xh)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_sc_jxb FOREIGN KEY (zyc_jxbbh)
        REFERENCES zhouyc_jiaoxueban(zyc_jxbbh)
        ON DELETE CASCADE ON UPDATE CASCADE
) PARTITION BY RANGE (zyc_xq) (
    PARTITION p_2022_1 VALUES LESS THAN ('2022-2023-2'),
    PARTITION p_2022_2 VALUES LESS THAN ('2023-2024-1'),
    PARTITION p_2023_1 VALUES LESS THAN ('2023-2024-2'),
    PARTITION p_2023_2 VALUES LESS THAN ('2024-2025-1'),
    PARTITION p_2024_1 VALUES LESS THAN ('2024-2025-2'),
    PARTITION p_2024_2 VALUES LESS THAN ('2025-2026-1'),
    PARTITION p_future VALUES LESS THAN (MAXVALUE)
);

COMMENT ON TABLE zhouyc_sc IS '学生成绩表（按学期分区）';
COMMENT ON COLUMN zhouyc_sc.zyc_xh IS '学号';
COMMENT ON COLUMN zhouyc_sc.zyc_xq IS '学期';
COMMENT ON COLUMN zhouyc_sc.zyc_jxbbh IS '教学班编号';
COMMENT ON COLUMN zhouyc_sc.zyc_cj IS '成绩';

-- 2.9 创建用户表（登录系统用）
CREATE TABLE IF NOT EXISTS zhouyc_users (
    zyc_yhbh SERIAL PRIMARY KEY,
    zyc_zh VARCHAR(20) NOT NULL UNIQUE,
    zyc_mm VARCHAR(50) NOT NULL,
    zyc_qx VARCHAR(10) NOT NULL CHECK (zyc_qx IN ('学生', '教师', '管理员')),
    zyc_cjsj TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    zyc_zt VARCHAR(10) DEFAULT '正常' CHECK (zyc_zt IN ('正常', '禁用'))
);

COMMENT ON TABLE zhouyc_users IS '系统用户表';
COMMENT ON COLUMN zhouyc_users.zyc_yhbh IS '用户编号（自增）';
COMMENT ON COLUMN zhouyc_users.zyc_zh IS '账号（学号/教师编号/admin）';
COMMENT ON COLUMN zhouyc_users.zyc_mm IS '密码';
COMMENT ON COLUMN zhouyc_users.zyc_qx IS '权限（学生/教师/管理员）';
COMMENT ON COLUMN zhouyc_users.zyc_cjsj IS '创建时间';
COMMENT ON COLUMN zhouyc_users.zyc_zt IS '状态（正常/禁用）';

-- =====================================================
-- 3. 创建索引
-- =====================================================

-- 3.1 外键索引
CREATE INDEX idx_zhouyc_xzb_zybh ON zhouyc_xingzhengban(zyc_zybh);
CREATE INDEX idx_zhouyc_xs_syd ON zhouyc_xuesheng(zyc_syd);
CREATE INDEX idx_zhouyc_xs_bjbh ON zhouyc_xuesheng(zyc_bjbh);

CREATE INDEX idx_zhouyc_jxb_kcbh ON zhouyc_jiaoxueban(zyc_kcbh);
CREATE INDEX idx_zhouyc_jxb_jsbh ON zhouyc_jiaoxueban(zyc_jsbh);
CREATE INDEX idx_zhouyc_sc_xh ON zhouyc_sc(zyc_xh);
CREATE INDEX idx_zhouyc_sc_jxbbh ON zhouyc_sc(zyc_jxbbh);

-- 3.2 查询优化索引
CREATE INDEX idx_zhouyc_xs_xm ON zhouyc_xuesheng(zyc_xsxm);
CREATE INDEX idx_zhouyc_js_xm ON zhouyc_jiaoshi(zyc_jsxm);
CREATE INDEX idx_zhouyc_sc_xq ON zhouyc_sc(zyc_xq);
CREATE INDEX idx_zhouyc_kc_kcmc ON zhouyc_kecheng(zyc_kcmc);
CREATE INDEX idx_zhouyc_sc_xh_xq ON zhouyc_sc(zyc_xh, zyc_xq);
CREATE INDEX idx_zhouyc_xs_bjbh_xm ON zhouyc_xuesheng(zyc_bjbh, zyc_xsxm);

-- 3.3 用户表索引
CREATE INDEX idx_zhouyc_users_zh ON zhouyc_users(zyc_zh);
CREATE INDEX idx_zhouyc_users_qx ON zhouyc_users(zyc_qx);

-- =====================================================
-- 3.4 聚簇索引设计
-- =====================================================

-- 对于频繁按学号查询的SC表，按学号聚簇
CLUSTER zhouyc_sc USING zhouyc_sc_pkey;

-- 对于学生表，按班级聚簇（便于按班级查询）
CREATE INDEX idx_zhouyc_xs_bjbh_cluster ON zhouyc_xuesheng(zyc_bjbh);
CLUSTER zhouyc_xuesheng USING idx_zhouyc_xs_bjbh_cluster;

-- 对于教学班表，按课程聚簇（便于按课程查询）
CREATE INDEX idx_zhouyc_jxb_kcbh_cluster ON zhouyc_jiaoxueban(zyc_kcbh);
CLUSTER zhouyc_jiaoxueban USING idx_zhouyc_jxb_kcbh_cluster;

-- 对于课程表，按课程名称聚簇（便于按名称查询课程）
CREATE INDEX idx_zhouyc_kc_kcmc_cluster ON zhouyc_kecheng(zyc_kcmc);
CLUSTER zhouyc_kecheng USING idx_zhouyc_kc_kcmc_cluster;

-- =====================================================
-- 3.5 分区表索引优化
-- =====================================================

-- 为SC分区表创建额外的查询优化索引
CREATE INDEX idx_zhouyc_sc_cj ON zhouyc_sc(zyc_cj);

-- 为教学班分区表创建额外的查询优化索引
CREATE INDEX idx_zhouyc_jxb_xdrs ON zhouyc_jiaoxueban(zyc_xdrs);

-- =====================================================
-- 4. 创建触发器（在插入数据之前创建）
-- =====================================================

-- 4.1 学分自动更新触发器函数
CREATE OR REPLACE FUNCTION zhouyc_update_credits()
RETURNS TRIGGER AS $$
BEGIN
    -- 当插入或更新成绩时，如果成绩>=60，更新学生已修学分
    IF TG_OP = 'INSERT' OR TG_OP = 'UPDATE' THEN
        IF NEW.zyc_cj >= 60 THEN
            UPDATE zhouyc_xuesheng
            SET zyc_yxxf = (
                SELECT COALESCE(SUM(k.zyc_xf), 0)
                FROM zhouyc_sc s
                JOIN zhouyc_jiaoxueban j ON s.zyc_jxbbh = j.zyc_jxbbh
                JOIN zhouyc_kecheng k ON j.zyc_kcbh = k.zyc_kcbh
                WHERE s.zyc_xh = NEW.zyc_xh AND s.zyc_cj >= 60
            )
            WHERE zyc_xh = NEW.zyc_xh;
        END IF;
    END IF;

    -- 当删除成绩时，重新计算学分
    IF TG_OP = 'DELETE' THEN
        UPDATE zhouyc_xuesheng
        SET zyc_yxxf = (
            SELECT COALESCE(SUM(k.zyc_xf), 0)
            FROM zhouyc_sc s
            JOIN zhouyc_jiaoxueban j ON s.zyc_jxbbh = j.zyc_jxbbh
            JOIN zhouyc_kecheng k ON j.zyc_kcbh = k.zyc_kcbh
            WHERE s.zyc_xh = OLD.zyc_xh AND s.zyc_cj >= 60
        )
        WHERE zyc_xh = OLD.zyc_xh;
    END IF;

    IF TG_OP = 'DELETE' THEN
        RETURN OLD;
    ELSE
        RETURN NEW;
    END IF;
END;
$$ LANGUAGE plpgsql;

-- 创建学分更新触发器
CREATE TRIGGER zhouyc_tr_update_credits
    AFTER INSERT OR UPDATE OR DELETE ON zhouyc_sc
    FOR EACH ROW
    EXECUTE PROCEDURE zhouyc_update_credits();

-- 4.2 成绩录入验证触发器函数
CREATE OR REPLACE FUNCTION zhouyc_validate_score()
RETURNS TRIGGER AS $$
BEGIN
    -- 验证学生是否存在
    IF NOT EXISTS (SELECT 1 FROM zhouyc_xuesheng WHERE zyc_xh = NEW.zyc_xh) THEN
        RAISE EXCEPTION '学号 % 不存在', NEW.zyc_xh;
    END IF;

    -- 验证教学班是否存在
    IF NOT EXISTS (SELECT 1 FROM zhouyc_jiaoxueban WHERE zyc_jxbbh = NEW.zyc_jxbbh) THEN
        RAISE EXCEPTION '教学班编号 % 不存在', NEW.zyc_jxbbh;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- 创建成绩验证触发器
CREATE TRIGGER zhouyc_tr_validate_score
    BEFORE INSERT OR UPDATE ON zhouyc_sc
    FOR EACH ROW
    EXECUTE PROCEDURE zhouyc_validate_score();

-- 4.3 学生用户自动创建触发器函数
CREATE OR REPLACE FUNCTION zhouyc_create_student_user()
RETURNS TRIGGER AS $$
BEGIN
    -- 当插入学生时，自动创建用户账号
    IF TG_OP = 'INSERT' THEN
        -- 检查账号是否已存在
        IF NOT EXISTS (SELECT 1 FROM zhouyc_users WHERE zyc_zh = NEW.zyc_xh) THEN
            INSERT INTO zhouyc_users (zyc_zh, zyc_mm, zyc_qx)
            VALUES (NEW.zyc_xh, SUBSTR(NEW.zyc_xh, -6), '学生');
        END IF;
    END IF;

    -- 当更新学号时，更新用户账号
    IF TG_OP = 'UPDATE' AND OLD.zyc_xh != NEW.zyc_xh THEN
        UPDATE zhouyc_users
        SET zyc_zh = NEW.zyc_xh, zyc_mm = SUBSTR(NEW.zyc_xh, -6)
        WHERE zyc_zh = OLD.zyc_xh AND zyc_qx = '学生';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- 创建学生用户自动创建触发器
CREATE TRIGGER zhouyc_tr_create_student_user
    AFTER INSERT OR UPDATE ON zhouyc_xuesheng
    FOR EACH ROW
    EXECUTE PROCEDURE zhouyc_create_student_user();

-- 4.4 教师用户自动创建触发器函数
CREATE OR REPLACE FUNCTION zhouyc_create_teacher_user()
RETURNS TRIGGER AS $$
BEGIN
    -- 当插入教师时，自动创建用户账号
    IF TG_OP = 'INSERT' THEN
        -- 检查账号是否已存在
        IF NOT EXISTS (SELECT 1 FROM zhouyc_users WHERE zyc_zh = NEW.zyc_jsbh) THEN
            INSERT INTO zhouyc_users (zyc_zh, zyc_mm, zyc_qx)
            VALUES (NEW.zyc_jsbh, SUBSTR(NEW.zyc_jsbh, -6), '教师');
        END IF;
    END IF;

    -- 当更新教师编号时，更新用户账号
    IF TG_OP = 'UPDATE' AND OLD.zyc_jsbh != NEW.zyc_jsbh THEN
        UPDATE zhouyc_users
        SET zyc_zh = NEW.zyc_jsbh, zyc_mm = SUBSTR(NEW.zyc_jsbh, -6)
        WHERE zyc_zh = OLD.zyc_jsbh AND zyc_qx = '教师';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- 创建教师用户自动创建触发器
CREATE TRIGGER zhouyc_tr_create_teacher_user
    AFTER INSERT OR UPDATE ON zhouyc_jiaoshi
    FOR EACH ROW
    EXECUTE PROCEDURE zhouyc_create_teacher_user();

-- =====================================================
-- 5. 插入基础数据
-- =====================================================

-- 4.1 插入专业数据
INSERT INTO zhouyc_zhuanye (zyc_zymc) VALUES
('计算机科学与技术'),
('软件工程'),
('信息系统'),
('人工智能'),
('数据科学与大数据技术');

-- 4.2 插入地区数据
INSERT INTO zhouyc_diqu (zyc_dqmc) VALUES
('北京市'),
('天津市'),
('河北省'),
('山西省'),
('内蒙古自治区'),
('辽宁省'),
('吉林省'),
('黑龙江省'),
('上海市'),
('江苏省');

-- 4.3 插入行政班数据
INSERT INTO zhouyc_xingzhengban (zyc_xzbmc, zyc_zybh) VALUES
('计算机2021级1班', 1),
('计算机2021级2班', 1),
('软件工程2021级1班', 2),
('信息系统2021级1班', 3),
('人工智能2021级1班', 4);

-- 4.4 插入教师数据
INSERT INTO zhouyc_jiaoshi (zyc_jsbh, zyc_jsxm, zyc_jsxb, zyc_jscsrq, zyc_jszc, zyc_jslxdh) VALUES
('T202401001', '张教授', '男', '1975-03-15', '教授', '13800138001'),
('T202401002', '李副教授', '女', '1980-07-22', '副教授', '13800138002'),
('T202401003', '王讲师', '男', '1985-11-08', '讲师', '13800138003'),
('T202401004', '刘教授', '女', '1970-05-30', '教授', '13800138004'),
('T202401005', '陈副教授', '男', '1978-09-12', '副教授', '13800138005');

-- 4.5 插入课程数据
INSERT INTO zhouyc_kecheng (zyc_kcmc, zyc_kkxq, zyc_xs, zyc_ksfs, zyc_xf) VALUES
('程序设计基础', '2023-2024-1', 64, '考试', 4.0),
('数据结构', '2023-2024-1', 48, '考试', 3.0),
('数据库原理', '2023-2024-2', 48, '考试', 3.0),
('操作系统', '2023-2024-2', 48, '考试', 3.0),
('计算机网络', '2023-2024-1', 32, '考查', 2.0);

-- 4.6 插入学生数据
INSERT INTO zhouyc_xuesheng (zyc_xh, zyc_xsxm, zyc_xsxb, zyc_xscsrq, zyc_syd, zyc_yxxf, zyc_bjbh) VALUES
('202101001', '张三', '男', '2003-05-15', 1, 0, 1),
('202101002', '李四', '女', '2003-08-22', 2, 0, 1),
('202101003', '王五', '男', '2003-03-10', 3, 0, 1),
('202101004', '赵六', '女', '2003-11-08', 4, 0, 2),
('202101005', '钱七', '男', '2003-07-25', 5, 0, 2),
('202101006', '孙八', '女', '2003-09-12', 6, 0, 3),
('202101007', '周九', '男', '2003-04-18', 7, 0, 3),
('202101008', '吴十', '女', '2003-12-03', 8, 0, 4),
('202101009', '郑一', '男', '2003-06-28', 9, 0, 5),
('202101010', '王二', '女', '2003-10-14', 10, 0, 5);

-- 4.7 插入教学班数据
-- 上课时间格式说明：星期:节次-节次;星期:节次-节次
-- 星期编号：1=周一, 2=周二, 3=周三, 4=周四, 5=周五, 6=周六, 7=周日
-- 示例：1:1-2;3:3-4 表示周一1-2节，周三3-4节
INSERT INTO zhouyc_jiaoxueban (zyc_jxbmc, zyc_sksj, zyc_skdd, zyc_kcbh, zyc_jsbh, zyc_xdrs) VALUES
('程序设计基础1班', '1:1-2;3:3-4', 'A101', 1, 'T202401001', 50),
('程序设计基础2班', '2:1-2;4:3-4', 'A102', 1, 'T202401001', 50),
('数据结构1班', '1:3-4;3:1-2', 'B201', 2, 'T202401002', 45),
('数据库原理1班', '2:3-4;4:1-2', 'B202', 3, 'T202401003', 40),
('操作系统1班', '1:5-6;3:5-6', 'C301', 4, 'T202401004', 35),
('计算机网络1班', '5:1-2', 'C302', 5, 'T202401005', 30);

-- 4.8 插入成绩数据
INSERT INTO zhouyc_sc (zyc_xh, zyc_xq, zyc_jxbbh, zyc_cj) VALUES
('202101001', '2023-2024-1', 1, 85.5),
('202101001', '2023-2024-1', 3, 78.0),
('202101001', '2023-2024-1', 6, 92.0),
('202101002', '2023-2024-1', 1, 90.5),
('202101002', '2023-2024-1', 3, 88.0),
('202101002', '2023-2024-1', 6, 85.0),
('202101003', '2023-2024-1', 2, 76.5),
('202101003', '2023-2024-1', 3, 82.0),
('202101004', '2023-2024-1', 2, 88.5),
('202101004', '2023-2024-1', 3, 85.0),
('202101005', '2023-2024-1', 1, 79.0),
('202101006', '2023-2024-1', 1, 91.5),
('202101007', '2023-2024-1', 2, 87.0),
('202101008', '2023-2024-1', 1, 83.5),
('202101009', '2023-2024-1', 2, 89.0),
('202101010', '2023-2024-1', 1, 86.5);

-- 5.9 插入管理员用户数据
INSERT INTO zhouyc_users (zyc_zh, zyc_mm, zyc_qx) VALUES
('admin', 'admin123', '管理员');

-- 5.10 为已存在的学生和教师创建用户账号（手动触发）
-- 为学生创建用户账号
INSERT INTO zhouyc_users (zyc_zh, zyc_mm, zyc_qx)
SELECT
    zyc_xh,
    SUBSTR(zyc_xh, -6),
    '学生'
FROM zhouyc_xuesheng
WHERE zyc_xh NOT IN (SELECT zyc_zh FROM zhouyc_users WHERE zyc_qx = '学生');

-- 为教师创建用户账号
INSERT INTO zhouyc_users (zyc_zh, zyc_mm, zyc_qx)
SELECT
    zyc_jsbh,
    SUBSTR(zyc_jsbh, -6),
    '教师'
FROM zhouyc_jiaoshi
WHERE zyc_jsbh NOT IN (SELECT zyc_zh FROM zhouyc_users WHERE zyc_qx = '教师');

-- =====================================================
-- 6. 创建存储过程
-- =====================================================

-- 6.1 学生成绩查询存储过程
-- 6.1 学生成绩查询存储过程
CREATE OR REPLACE FUNCTION zhouyc_sp_student_scores(
    p_xh VARCHAR(12),
    p_xq VARCHAR(15) DEFAULT NULL
)
RETURNS TABLE (
    学号 VARCHAR(12),
    姓名 VARCHAR(20),
    学期 VARCHAR(15),
    课程名称 VARCHAR(50),
    教学班名称 VARCHAR(50),
    成绩 DECIMAL(5,2),
    学分 DECIMAL(3,1)
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        s.zyc_xh,
        xs.zyc_xsxm,
        s.zyc_xq,
        k.zyc_kcmc,
        j.zyc_jxbmc,
        s.zyc_cj,
        k.zyc_xf
    FROM zhouyc_sc s
    JOIN zhouyc_xuesheng xs ON s.zyc_xh = xs.zyc_xh
    JOIN zhouyc_jiaoxueban j ON s.zyc_jxbbh = j.zyc_jxbbh
    JOIN zhouyc_kecheng k ON j.zyc_kcbh = k.zyc_kcbh
    WHERE s.zyc_xh = p_xh
    AND (p_xq IS NULL OR s.zyc_xq = p_xq)
    ORDER BY s.zyc_xq, k.zyc_kcmc;
END;
$$ LANGUAGE plpgsql;

-- 6.2 用户登录验证存储过程
CREATE OR REPLACE FUNCTION zhouyc_sp_user_login(
    p_zh VARCHAR(20),
    p_mm VARCHAR(50)
)
RETURNS TABLE (
    用户编号 INTEGER,
    账号 VARCHAR(20),
    权限 VARCHAR(10),
    状态 VARCHAR(10),
    登录结果 VARCHAR(20)
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        u.zyc_yhbh,
        u.zyc_zh,
        u.zyc_qx,
        u.zyc_zt,
        CASE
            WHEN u.zyc_zh IS NULL THEN '账号不存在'
            WHEN u.zyc_mm != p_mm THEN '密码错误'
            WHEN u.zyc_zt = '禁用' THEN '账号已禁用'
            ELSE '登录成功'
        END
    FROM zhouyc_users u
    WHERE u.zyc_zh = p_zh;

    -- 如果没有找到用户，返回失败信息
    IF NOT FOUND THEN
        RETURN QUERY
        SELECT
            NULL::INTEGER,
            p_zh,
            ''::VARCHAR(10),
            ''::VARCHAR(10),
            '账号不存在'::VARCHAR(20);
    END IF;
END;
$$ LANGUAGE plpgsql;

-- 6.3 课程成绩统计存储过程
CREATE OR REPLACE FUNCTION zhouyc_sp_course_statistics(
    p_jxbbh INTEGER,
    p_xq VARCHAR(15)
)
RETURNS TABLE (
    教学班编号 INTEGER,
    教学班名称 VARCHAR(50),
    课程名称 VARCHAR(50),
    学期 VARCHAR(15),
    选课人数 BIGINT,
    平均分 DECIMAL(5,2),
    最高分 DECIMAL(5,2),
    最低分 DECIMAL(5,2),
    及格人数 BIGINT,
    及格率 DECIMAL(5,2)
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        j.zyc_jxbbh,
        j.zyc_jxbmc,
        k.zyc_kcmc,
        p_xq,
        COUNT(s.zyc_xh),
        ROUND(AVG(s.zyc_cj), 2),
        MAX(s.zyc_cj),
        MIN(s.zyc_cj),
        COUNT(CASE WHEN s.zyc_cj >= 60 THEN 1 END),
        ROUND(
            COUNT(CASE WHEN s.zyc_cj >= 60 THEN 1 END) * 100.0 / COUNT(s.zyc_xh),
            2
        )
    FROM zhouyc_jiaoxueban j
    JOIN zhouyc_kecheng k ON j.zyc_kcbh = k.zyc_kcbh
    LEFT JOIN zhouyc_sc s ON j.zyc_jxbbh = s.zyc_jxbbh AND s.zyc_xq = p_xq
    WHERE j.zyc_jxbbh = p_jxbbh
    GROUP BY j.zyc_jxbbh, j.zyc_jxbmc, k.zyc_kcmc;
END;
$$ LANGUAGE plpgsql;

-- 6.4 用户密码重置存储过程
CREATE OR REPLACE FUNCTION zhouyc_sp_reset_password(
    p_zh VARCHAR(20),
    p_qx VARCHAR(10)
)
RETURNS TABLE (
    操作结果 VARCHAR(20),
    新密码 VARCHAR(50)
) AS $$
DECLARE
    v_new_password VARCHAR(50);
BEGIN
    -- 根据权限生成新密码
    IF p_qx = '学生' THEN
        v_new_password := SUBSTR(p_zh, -6);  -- 学号后6位
    ELSIF p_qx = '教师' THEN
        v_new_password := SUBSTR(p_zh, -6);  -- 教师编号后6位
    ELSIF p_qx = '管理员' THEN
        v_new_password := 'admin123';      -- 管理员默认密码
    ELSE
        RETURN QUERY SELECT '权限类型错误'::VARCHAR(20), ''::VARCHAR(50);
        RETURN;
    END IF;

    -- 更新密码
    UPDATE zhouyc_users
    SET zyc_mm = v_new_password
    WHERE zyc_zh = p_zh AND zyc_qx = p_qx;

    IF FOUND THEN
        RETURN QUERY SELECT '密码重置成功'::VARCHAR(20), v_new_password;
    ELSE
        RETURN QUERY SELECT '用户不存在'::VARCHAR(20), ''::VARCHAR(50);
    END IF;
END;
$$ LANGUAGE plpgsql;

-- =====================================================
-- 7. 创建视图
-- =====================================================

-- 7.1 学生信息综合视图
CREATE VIEW zhouyc_v_student_info AS
SELECT
    xs.zyc_xh AS 学号,
    xs.zyc_xsxm AS 姓名,
    xs.zyc_xsxb AS 性别,
    xs.zyc_xscsrq AS 出生日期,
    dq.zyc_dqmc AS 生源地,
    xs.zyc_yxxf AS 已修学分,
    xzb.zyc_xzbmc AS 行政班名称,
    zy.zyc_zymc AS 专业名称,
    EXTRACT(YEAR FROM AGE(CURRENT_DATE, xs.zyc_xscsrq)) AS 年龄
FROM zhouyc_xuesheng xs
JOIN zhouyc_diqu dq ON xs.zyc_syd = dq.zyc_dqbh
JOIN zhouyc_xingzhengban xzb ON xs.zyc_bjbh = xzb.zyc_xzbbh
JOIN zhouyc_zhuanye zy ON xzb.zyc_zybh = zy.zyc_zybh;

COMMENT ON VIEW zhouyc_v_student_info IS '学生信息综合视图';

-- 7.2 课程教学安排视图
CREATE VIEW zhouyc_v_course_schedule AS
SELECT
    k.zyc_kcbh AS 课程编号,
    k.zyc_kcmc AS 课程名称,
    k.zyc_kkxq AS 开课学期,
    k.zyc_xs AS 学时,
    k.zyc_xf AS 学分,
    k.zyc_ksfs AS 考试方式,
    j.zyc_jxbbh AS 教学班编号,
    j.zyc_jxbmc AS 教学班名称,
    j.zyc_sksj AS 上课时间,
    j.zyc_skdd AS 上课地点,
    js.zyc_jsxm AS 授课教师,
    js.zyc_jszc AS 教师职称
FROM zhouyc_kecheng k
JOIN zhouyc_jiaoxueban j ON k.zyc_kcbh = j.zyc_kcbh
JOIN zhouyc_jiaoshi js ON j.zyc_jsbh = js.zyc_jsbh;

COMMENT ON VIEW zhouyc_v_course_schedule IS '课程教学安排视图';

-- 7.3 成绩统计视图
CREATE VIEW zhouyc_v_score_statistics AS
SELECT
    s.zyc_xq AS 学期,
    j.zyc_jxbbh AS 教学班编号,
    j.zyc_jxbmc AS 教学班名称,
    k.zyc_kcmc AS 课程名称,
    COUNT(s.zyc_xh) AS 选课人数,
    ROUND(AVG(s.zyc_cj), 2) AS 平均分,
    MAX(s.zyc_cj) AS 最高分,
    MIN(s.zyc_cj) AS 最低分,
    COUNT(CASE WHEN s.zyc_cj >= 60 THEN 1 END) AS 及格人数,
    ROUND(
        COUNT(CASE WHEN s.zyc_cj >= 60 THEN 1 END) * 100.0 / COUNT(s.zyc_xh),
        2
    ) AS 及格率
FROM zhouyc_sc s
JOIN zhouyc_jiaoxueban j ON s.zyc_jxbbh = j.zyc_jxbbh
JOIN zhouyc_kecheng k ON j.zyc_kcbh = k.zyc_kcbh
WHERE s.zyc_cj IS NOT NULL
GROUP BY s.zyc_xq, j.zyc_jxbbh, j.zyc_jxbmc, k.zyc_kcmc;

COMMENT ON VIEW zhouyc_v_score_statistics IS '成绩统计视图';

-- 7.4 教师工作量统计视图
CREATE VIEW zhouyc_v_teacher_workload AS
SELECT
    js.zyc_jsbh AS 教师编号,
    js.zyc_jsxm AS 教师姓名,
    js.zyc_jszc AS 职称,
    COUNT(DISTINCT k.zyc_kcbh) AS 授课课程数,
    COUNT(DISTINCT j.zyc_jxbbh) AS 授课教学班数,
    SUM(k.zyc_xs) AS 总学时,
    COUNT(DISTINCT s.zyc_xh) AS 授课学生数
FROM zhouyc_jiaoshi js
LEFT JOIN zhouyc_jiaoxueban j ON js.zyc_jsbh = j.zyc_jsbh
LEFT JOIN zhouyc_kecheng k ON j.zyc_kcbh = k.zyc_kcbh
LEFT JOIN zhouyc_sc s ON j.zyc_jxbbh = s.zyc_jxbbh
GROUP BY js.zyc_jsbh, js.zyc_jsxm, js.zyc_jszc;

COMMENT ON VIEW zhouyc_v_teacher_workload IS '教师工作量统计视图';

-- 7.5 用户管理视图
CREATE VIEW zhouyc_v_user_management AS
SELECT
    u.zyc_yhbh AS 用户编号,
    u.zyc_zh AS 账号,
    u.zyc_qx AS 权限,
    u.zyc_zt AS 状态,
    u.zyc_cjsj AS 创建时间,
    CASE
        WHEN u.zyc_qx = '学生' THEN xs.zyc_xsxm
        WHEN u.zyc_qx = '教师' THEN js.zyc_jsxm
        WHEN u.zyc_qx = '管理员' THEN '系统管理员'
        ELSE '未知'
    END AS 真实姓名,
    CASE
        WHEN u.zyc_qx = '学生' THEN xzb.zyc_xzbmc
        WHEN u.zyc_qx = '教师' THEN js.zyc_jszc
        ELSE '系统管理'
    END AS 所属单位
FROM zhouyc_users u
LEFT JOIN zhouyc_xuesheng xs ON u.zyc_zh = xs.zyc_xh AND u.zyc_qx = '学生'
LEFT JOIN zhouyc_xingzhengban xzb ON xs.zyc_bjbh = xzb.zyc_xzbbh
LEFT JOIN zhouyc_jiaoshi js ON u.zyc_zh = js.zyc_jsbh AND u.zyc_qx = '教师';

COMMENT ON VIEW zhouyc_v_user_management IS '用户管理视图';

-- =====================================================
-- 8. 数据验证
-- =====================================================

-- 验证表创建
SELECT tablename FROM pg_tables WHERE schemaname = 'public' AND tablename LIKE 'zhouyc_%';

-- 验证数据插入
SELECT
    '专业表' as 表名, COUNT(*) as 记录数 FROM zhouyc_zhuanye
UNION ALL
SELECT
    '地区表' as 表名, COUNT(*) as 记录数 FROM zhouyc_diqu
UNION ALL
SELECT
    '行政班表' as 表名, COUNT(*) as 记录数 FROM zhouyc_xingzhengban
UNION ALL
SELECT
    '教师表' as 表名, COUNT(*) as 记录数 FROM zhouyc_jiaoshi
UNION ALL
SELECT
    '学生表' as 表名, COUNT(*) as 记录数 FROM zhouyc_xuesheng
UNION ALL
SELECT
    '课程表' as 表名, COUNT(*) as 记录数 FROM zhouyc_kecheng
UNION ALL
SELECT
    '教学班表' as 表名, COUNT(*) as 记录数 FROM zhouyc_jiaoxueban
UNION ALL
SELECT
    'SC表' as 表名, COUNT(*) as 记录数 FROM zhouyc_sc
UNION ALL
SELECT
    '用户表' as 表名, COUNT(*) as 记录数 FROM zhouyc_users;

-- 验证用户创建情况
SELECT
    zyc_qx AS 权限类型,
    COUNT(*) AS 用户数量
FROM zhouyc_users
GROUP BY zyc_qx
ORDER BY zyc_qx;

-- =====================================================
-- 9. 测试查询示例
-- =====================================================

-- 9.1 基本查询
-- 查询所有学生信息
-- SELECT * FROM zhouyc_v_student_info ORDER BY 学号;

-- 查询指定专业的学生
-- SELECT 学号, 姓名, 性别, 行政班名称
-- FROM zhouyc_v_student_info
-- WHERE 专业名称 = '计算机科学与技术';

-- 9.2 统计查询
-- 各专业学生人数统计
-- SELECT
--     专业名称,
--     COUNT(*) AS 学生人数,
--     COUNT(CASE WHEN 性别 = '男' THEN 1 END) AS 男生人数,
--     COUNT(CASE WHEN 性别 = '女' THEN 1 END) AS 女生人数
-- FROM zhouyc_v_student_info
-- GROUP BY 专业名称
-- ORDER BY 学生人数 DESC;

-- 课程成绩统计
-- SELECT * FROM zhouyc_v_score_statistics
-- WHERE 学期 = '2023-2024-1';

-- 教师工作量统计
-- SELECT * FROM zhouyc_v_teacher_workload
-- ORDER BY 总学时 DESC;

-- 9.3 存储过程调用示例
-- 查询指定学生成绩
-- SELECT * FROM zhouyc_sp_student_scores('202101001', '2023-2024-1');

-- 查询课程统计
-- SELECT * FROM zhouyc_sp_course_statistics(1, '2023-2024-1');

-- 用户登录验证
-- SELECT * FROM zhouyc_sp_user_login('202101001', '101001');
-- SELECT * FROM zhouyc_sp_user_login('T202401001', '401001');
-- SELECT * FROM zhouyc_sp_user_login('admin', 'admin123');

-- 密码重置
-- SELECT * FROM zhouyc_sp_reset_password('202101001', '学生');

-- 查询用户管理信息
-- SELECT * FROM zhouyc_v_user_management ORDER BY 权限, 账号;

-- =====================================================
-- 初始化完成
-- =====================================================
