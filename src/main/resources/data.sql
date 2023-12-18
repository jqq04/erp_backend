use `seec_erp`;
-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'seecoder', '123456', 'INVENTORY_MANAGER');
INSERT INTO `user` VALUES (2, 'uncln', '123456', 'INVENTORY_MANAGER');
INSERT INTO `user` VALUES (3, 'kucun', '123456', 'INVENTORY_MANAGER');
INSERT INTO `user` VALUES (4, 'sky', '123456', 'ADMIN');
INSERT INTO `user` VALUES (5, 'zxr', '123456', 'SALE_MANAGER');
INSERT INTO `user` VALUES (6, '67', '123456', 'GM');
INSERT INTO `user` VALUES (7, 'xiaoshou', '123456', 'SALE_STAFF');
INSERT INTO `user` VALUES (8, 'Leone', '123456', 'GM');
INSERT INTO `user` VALUES (9, 'xiaoshoujingli', '123456', 'SALE_MANAGER');
INSERT INTO `user` VALUES (10, 'caiwu', '123456', 'FINANCIAL_STAFF');
INSERT INTO `user` VALUES (11, 'HR', '123456', 'HR');
INSERT INTO `user` VALUES (12, 'Jinqq', '123456', 'HR');
-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, '商品', 0, 0, 0, 0);
INSERT INTO `category` VALUES (2, '电子产品', 1, 0, 0, 0);
INSERT INTO `category` VALUES (3, '生活用品', 1, 1, 0, 0);
INSERT INTO `category` VALUES (4, '电脑', 2, 1, 2, 2);
INSERT INTO `category` VALUES (5, '手机', 2, 1, 3, 3);

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES (1, '供应商', 1, 'yzh', '12306', '南京大学', '123456', '654321@abc.com', 0.00, 0.00, 6500000.00, 'uncln');
INSERT INTO `customer` VALUES (2, '销售商', 1, 'lxs', '12580', '南哪儿大学', '123457', '12121@cba.com', 20000000.00, 4431400.00, 0.00, 'uncln');
INSERT INTO `customer` VALUES (3, '销售商', 1, 'test', '10086', '测试中的销售商', '123458', '10086@hq.com', 1000000.00, 50000.00, 0.00, 'xiaoshou');
-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('0000000000400000', '戴尔电脑', 4, '戴尔(DELL)Vostro笔记本电脑5410 123色 戴尔成就3500Vostro1625D', 500, 3000.00, 4056.00, 1900.00, 3000.00);
INSERT INTO `product` VALUES ('0000000000400001', '小米手机', 4, 'lalalalala', 1000, 2000.00, 3500.00, 2700.00, 4200.00);
INSERT INTO `product` VALUES ('0000000000500000', 'intel电脑', 5, 'iphone14maxpro', 0, 6000.00, 10000.00, NULL, NULL);
INSERT INTO `product` VALUES ('0000000000500001', 'iphone', 5, 'iphone14普通版', 0, 4000.00, 8000.00, NULL, NULL);
INSERT INTO `product` VALUES ('0000000000500002', '坚果', 5, 'pro3', 0, 2499.00, 3199.00, NULL, NULL);

-- ----------------------------
-- Records of purchase_returns_sheet
-- ----------------------------
INSERT INTO `purchase_returns_sheet` VALUES ('JHTHD-20220523-00000', 'JHD-20220523-00001', 'xiaoshoujingli', '审批完成', '2022-05-23 23:22:41', 800000.00, '退钱！');
INSERT INTO `purchase_returns_sheet` VALUES ('JHTHD-20220523-00001', 'JHD-20220523-00000', 'xiaoshoujingli', '审批完成', '2022-05-23 23:22:54', 500000.00, '退钱！！！');
INSERT INTO `purchase_returns_sheet` VALUES ('JHTHD-20220523-00002', 'JHD-20220523-00000', 'xiaoshoujingli', '审批完成', '2022-05-23 23:34:34', 100000.00, '退钱++++');
INSERT INTO `purchase_returns_sheet` VALUES ('JHTHD-20220523-00003', 'JHD-20220523-00000', 'xiaoshoujingli', '审批完成', '2022-05-23 23:39:30', 200000.00, 'mmmmm');
INSERT INTO `purchase_returns_sheet` VALUES ('JHTHD-20220523-00004', 'JHD-20220523-00000', '67', '审批完成', '2022-05-23 23:42:32', 200000.00, 'mmmmk');
INSERT INTO `purchase_returns_sheet` VALUES ('JHTHD-20220524-00000', 'JHD-20220523-00001', 'xiaoshoujingli', '待二级审批', '2022-05-24 01:00:18', 160000.00, NULL);
INSERT INTO `purchase_returns_sheet` VALUES ('JHTHD-20220524-00001', 'JHD-20220523-00002', 'xiaoshoujingli', '待一级审批', '2022-05-24 01:00:34', 140000.00, NULL);

-- ----------------------------
-- Records of purchase_returns_sheet_content
-- ----------------------------
INSERT INTO `purchase_returns_sheet_content` VALUES (23, 'JHTHD-20220523-00000', '0000000000400000', 500, 600000.00, 1200.00, 'b');
INSERT INTO `purchase_returns_sheet_content` VALUES (24, 'JHTHD-20220523-00000', '0000000000400001', 100, 200000.00, 2000.00, 'b');
INSERT INTO `purchase_returns_sheet_content` VALUES (25, 'JHTHD-20220523-00001', '0000000000400000', 500, 500000.00, 1000.00, 'a');
INSERT INTO `purchase_returns_sheet_content` VALUES (26, 'JHTHD-20220523-00002', '0000000000400000', 100, 100000.00, 1000.00, 'a');
INSERT INTO `purchase_returns_sheet_content` VALUES (27, 'JHTHD-20220523-00003', '0000000000400000', 200, 200000.00, 1000.00, 'a');
INSERT INTO `purchase_returns_sheet_content` VALUES (28, 'JHTHD-20220523-00004', '0000000000400000', 200, 200000.00, 1000.00, 'a');
INSERT INTO `purchase_returns_sheet_content` VALUES (29, 'JHTHD-20220524-00000', '0000000000400000', 50, 60000.00, 1200.00, 'b');
INSERT INTO `purchase_returns_sheet_content` VALUES (30, 'JHTHD-20220524-00000', '0000000000400001', 50, 100000.00, 2000.00, 'b');
INSERT INTO `purchase_returns_sheet_content` VALUES (31, 'JHTHD-20220524-00001', '0000000000400000', 0, 0.00, 1300.00, 'c');
INSERT INTO `purchase_returns_sheet_content` VALUES (32, 'JHTHD-20220524-00001', '0000000000400001', 50, 140000.00, 2800.00, 'c');

-- ----------------------------
-- Records of purchase_sheet
-- ----------------------------
INSERT INTO `purchase_sheet` VALUES ('JHD-20220523-00000', 1, 'xiaoshoujingli', 'a', 1000000.00, '审批完成', '2022-05-23 23:13:59');
INSERT INTO `purchase_sheet` VALUES ('JHD-20220523-00001', 1, 'xiaoshoujingli', 'b', 2200000.00, '审批完成', '2022-05-23 23:14:34');
INSERT INTO `purchase_sheet` VALUES ('JHD-20220523-00002', 1, 'xiaoshoujingli', 'c', 3450000.00, '审批完成', '2022-05-23 23:15:57');
INSERT INTO `purchase_sheet` VALUES ('JHD-20220524-00000', 1, 'xiaoshoujingli', NULL, 2200000.00, '待二级审批', '2022-05-24 00:56:54');
INSERT INTO `purchase_sheet` VALUES ('JHD-20220524-00001', 1, 'xiaoshoujingli', NULL, 3240000.00, '待一级审批', '2022-05-24 00:57:29');
INSERT INTO `purchase_sheet` VALUES ('JHD-20220524-00002', 1, 'xiaoshoujingli', NULL, 1650000.00, '审批完成', '2022-05-24 01:02:04');

-- ----------------------------
-- Records of purchase_sheet_content
-- ----------------------------
INSERT INTO `purchase_sheet_content` VALUES (51, 'JHD-20220523-00000', '0000000000400000', 1000, 1000.00, 1000000.00, 'a');
INSERT INTO `purchase_sheet_content` VALUES (52, 'JHD-20220523-00001', '0000000000400000', 1000, 1200.00, 1200000.00, 'b');
INSERT INTO `purchase_sheet_content` VALUES (53, 'JHD-20220523-00001', '0000000000400001', 500, 2000.00, 1000000.00, 'b');
INSERT INTO `purchase_sheet_content` VALUES (54, 'JHD-20220523-00002', '0000000000400000', 500, 1300.00, 650000.00, 'c');
INSERT INTO `purchase_sheet_content` VALUES (55, 'JHD-20220523-00002', '0000000000400001', 1000, 2800.00, 2800000.00, 'c');
INSERT INTO `purchase_sheet_content` VALUES (56, 'JHD-20220524-00000', '0000000000400000', 500, 1500.00, 750000.00, '');
INSERT INTO `purchase_sheet_content` VALUES (57, 'JHD-20220524-00000', '0000000000400001', 500, 2900.00, 1450000.00, NULL);
INSERT INTO `purchase_sheet_content` VALUES (58, 'JHD-20220524-00001', '0000000000400000', 600, 1900.00, 1140000.00, '');
INSERT INTO `purchase_sheet_content` VALUES (59, 'JHD-20220524-00001', '0000000000400001', 700, 3000.00, 2100000.00, NULL);
INSERT INTO `purchase_sheet_content` VALUES (60, 'JHD-20220524-00002', '0000000000400000', 300, 1900.00, 570000.00, '');
INSERT INTO `purchase_sheet_content` VALUES (61, 'JHD-20220524-00002', '0000000000400001', 400, 2700.00, 1080000.00, NULL);

-- ----------------------------
-- Records of sale_sheet
-- ----------------------------
INSERT INTO `sale_sheet` VALUES ('XSD-20220523-00000', 2, 'xiaoshoujingli', '卖卖卖', '审批失败', '2022-05-23 23:46:12', 'xiaoshoujingli', 1300000.00, 0.80, 1039800.00, 200.00);
INSERT INTO `sale_sheet` VALUES ('XSD-20220524-00000', 2, 'xiaoshoujingli', NULL, '审批完成', '2022-05-24 00:04:37', 'xiaoshoujingli', 4200000.00, 0.80, 3359800.00, 200.00);
INSERT INTO `sale_sheet` VALUES ('XSD-20220524-00001', 2, 'xiaoshoujingli', NULL, '审批完成', '2022-05-24 00:32:41', 'xiaoshoujingli', 620000.00, 0.80, 495800.00, 200.00);
INSERT INTO `sale_sheet` VALUES ('XSD-20220524-00002', 2, 'xiaoshoujingli', NULL, '审批完成', '2022-05-24 00:45:25', 'xiaoshoujingli', 720000.00, 0.80, 575800.00, 200.00);
INSERT INTO `sale_sheet` VALUES ('XSD-20220524-00003', 2, 'xiaoshoujingli', NULL, '待二级审批', '2022-05-24 01:05:15', 'xiaoshoujingli', 660000.00, 0.80, 527700.00, 300.00);
INSERT INTO `sale_sheet` VALUES ('XSD-20220524-00004', 2, 'xiaoshoujingli', NULL, '待一级审批', '2022-05-24 01:07:23', 'xiaoshoujingli', 2900000.00, 0.90, 2609800.00, 200.00);

-- ----------------------------
-- Records of sale_sheet_content
-- ----------------------------
INSERT INTO `sale_sheet_content` VALUES (26, 'XSD-20220523-00000', '0000000000400000', 100, 5000.00, 500000.00, '卖卖卖1');
INSERT INTO `sale_sheet_content` VALUES (27, 'XSD-20220523-00000', '0000000000400001', 400, 2000.00, 800000.00, '卖卖卖2');
INSERT INTO `sale_sheet_content` VALUES (28, 'XSD-20220524-00000', '0000000000400000', 600, 3500.00, 2100000.00, '');
INSERT INTO `sale_sheet_content` VALUES (29, 'XSD-20220524-00000', '0000000000400001', 600, 3500.00, 2100000.00, NULL);
INSERT INTO `sale_sheet_content` VALUES (30, 'XSD-20220524-00001', '0000000000400000', 100, 2200.00, 220000.00, '');
INSERT INTO `sale_sheet_content` VALUES (31, 'XSD-20220524-00001', '0000000000400001', 100, 4000.00, 400000.00, NULL);
INSERT INTO `sale_sheet_content` VALUES (32, 'XSD-20220524-00002', '0000000000400000', 100, 3000.00, 300000.00, '');
INSERT INTO `sale_sheet_content` VALUES (33, 'XSD-20220524-00002', '0000000000400001', 100, 4200.00, 420000.00, NULL);
INSERT INTO `sale_sheet_content` VALUES (34, 'XSD-20220524-00003', '0000000000400000', 100, 2800.00, 280000.00, '');
INSERT INTO `sale_sheet_content` VALUES (35, 'XSD-20220524-00003', '0000000000400001', 100, 3800.00, 380000.00, NULL);
INSERT INTO `sale_sheet_content` VALUES (36, 'XSD-20220524-00004', '0000000000400000', 300, 3000.00, 900000.00, '');
INSERT INTO `sale_sheet_content` VALUES (37, 'XSD-20220524-00004', '0000000000400001', 500, 4000.00, 2000000.00, NULL);

-- ----------------------------
-- Records of warehouse
-- ----------------------------
INSERT INTO `warehouse` VALUES (16, '0000000000400000', 0, 1000.00, 0, NULL);
INSERT INTO `warehouse` VALUES (17, '0000000000400000', 200, 1200.00, 1, NULL);
INSERT INTO `warehouse` VALUES (18, '0000000000400001', 400, 2000.00, 1, NULL);
INSERT INTO `warehouse` VALUES (19, '0000000000400000', 0, 1300.00, 2, NULL);
INSERT INTO `warehouse` VALUES (20, '0000000000400001', 200, 2800.00, 2, NULL);
INSERT INTO `warehouse` VALUES (21, '0000000000400000', 300, 1900.00, 3, NULL);
INSERT INTO `warehouse` VALUES (22, '0000000000400001', 400, 2700.00, 3, NULL);

-- ----------------------------
-- Records of warehouse_input_sheet
-- ----------------------------
INSERT INTO `warehouse_input_sheet` VALUES ('RKD-20220523-00000', 0, 'kucun', '2022-05-23 23:17:41', '审批完成', 'JHD-20220523-00000');
INSERT INTO `warehouse_input_sheet` VALUES ('RKD-20220523-00001', 1, 'kucun', '2022-05-23 23:17:42', '审批完成', 'JHD-20220523-00001');
INSERT INTO `warehouse_input_sheet` VALUES ('RKD-20220523-00002', 2, 'kucun', '2022-05-23 23:17:44', '审批完成', 'JHD-20220523-00002');
INSERT INTO `warehouse_input_sheet` VALUES ('RKD-20220524-00000', 3, 'kucun', '2022-05-24 01:02:31', '审批完成', 'JHD-20220524-00002');

-- ----------------------------
-- Records of warehouse_input_sheet_content
-- ----------------------------
INSERT INTO `warehouse_input_sheet_content` VALUES (47, 'RKD-20220523-00000', '0000000000400000', 1000, 1000.00, NULL, 'a');
INSERT INTO `warehouse_input_sheet_content` VALUES (48, 'RKD-20220523-00001', '0000000000400000', 1000, 1200.00, NULL, 'b');
INSERT INTO `warehouse_input_sheet_content` VALUES (49, 'RKD-20220523-00001', '0000000000400001', 500, 2000.00, NULL, 'b');
INSERT INTO `warehouse_input_sheet_content` VALUES (50, 'RKD-20220523-00002', '0000000000400000', 500, 1300.00, NULL, 'c');
INSERT INTO `warehouse_input_sheet_content` VALUES (51, 'RKD-20220523-00002', '0000000000400001', 1000, 2800.00, NULL, 'c');
INSERT INTO `warehouse_input_sheet_content` VALUES (52, 'RKD-20220524-00000', '0000000000400000', 300, 1900.00, NULL, '');
INSERT INTO `warehouse_input_sheet_content` VALUES (53, 'RKD-20220524-00000', '0000000000400001', 400, 2700.00, NULL, NULL);

-- ----------------------------
-- Records of warehouse_output_sheet
-- ----------------------------
INSERT INTO `warehouse_output_sheet` VALUES ('CKD-20220524-00000', 'kucun', '2022-05-24 00:05:32', 'XSD-20220524-00000', '审批完成');
INSERT INTO `warehouse_output_sheet` VALUES ('CKD-20220524-00001', 'kucun', '2022-05-24 00:33:12', 'XSD-20220524-00001', '审批完成');
INSERT INTO `warehouse_output_sheet` VALUES ('CKD-20220524-00002', 'kucun', '2022-05-24 00:45:38', 'XSD-20220524-00002', '审批完成');

-- ----------------------------
-- Records of warehouse_output_sheet_content
-- ----------------------------
INSERT INTO `warehouse_output_sheet_content` VALUES (28, '0000000000400000', 'CKD-20220524-00000', 2, 600, 3500.00, '');
INSERT INTO `warehouse_output_sheet_content` VALUES (29, '0000000000400000', 'CKD-20220524-00000', 1, 600, 3500.00, '');
INSERT INTO `warehouse_output_sheet_content` VALUES (30, '0000000000400001', 'CKD-20220524-00000', 2, 600, 3500.00, NULL);
INSERT INTO `warehouse_output_sheet_content` VALUES (35, '0000000000400000', 'CKD-20220524-00001', 1, 100, 2200.00, '');
INSERT INTO `warehouse_output_sheet_content` VALUES (36, '0000000000400001', 'CKD-20220524-00001', 2, 100, 4000.00, NULL);
INSERT INTO `warehouse_output_sheet_content` VALUES (37, '0000000000400000', 'CKD-20220524-00002', 1, 100, 3000.00, '');
INSERT INTO `warehouse_output_sheet_content` VALUES (38, '0000000000400001', 'CKD-20220524-00002', 2, 100, 4200.00, NULL);

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES (1, 'bank_account_1', 0.00);
INSERT INTO `account` VALUES (2, 'bank_account_2', 100000.00);
INSERT INTO `account` VALUES (3, 'test', 35.00);
INSERT INTO `account` VALUES (4, 'salary_payment_account', 40000000.00);

-- ----------------------------
-- Records of receipt_sheet
-- ----------------------------
INSERT INTO `receipt_sheet` VALUES ('SKD-20220703-00002', 1, 'caiwu', '100.00', 'xiaosi', '2022-07-03 20:34:58', '待审批');
INSERT INTO `receipt_sheet` VALUES ('SKD-20220705-00001', 1, 'caiwu', '50000.00', '银行到账，请查收', '2022-07-05 20:34:58', '审批完成');

-- ----------------------------
-- Records of receipt_sheet_content
-- ----------------------------
INSERT INTO `receipt_sheet_content` VALUES (46,'SKD-20220703-00002', 'bank_account_1', '100.00', '1');
INSERT INTO `receipt_sheet_content` VALUES (47,'SKD-20220705-00001', 'bank_account_2', '50000.00', '账户2收款');

-- ----------------------------
-- Records of payment_sheet
-- ----------------------------
INSERT INTO `payment_sheet` VALUES ('FKD-20220703-00002', 1, 'caiwu', '100.00', 'ee', '2022-07-03 20:34:58', '待审批');
INSERT INTO `payment_sheet` VALUES ('FKD-20220706-00001', 1, 'caiwu', '30000.00', 'yes', '2022-07-03 20:34:58', '审批完成');
INSERT INTO `payment_sheet` VALUES ('FKD-20220706-00002', 1, 'caiwu', '50000.00', 'no', '2022-07-03 20:34:58', '审批完成');

-- ----------------------------
-- Records of payment_sheet_content
-- ----------------------------
INSERT INTO `payment_sheet_content` VALUES (1, 'FKD-20220703-00002', 'bank_account_2', '100.00', '1');
INSERT INTO `payment_sheet_content` VALUES (2, 'FKD-20220706-00001', 'bank_account_2', '30000.00', '1');
INSERT INTO `payment_sheet_content` VALUES (3, 'FKD-20220706-00002', 'bank_account_2', '50000.00', '1');

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VAlUES ('A0001', 'seecoder', '男', '1900-01-01','00000000','INVENTORY_MANAGER','seecoder_account');
INSERT INTO `employee` VAlUES ('A0002', 'uncln', '女', '2000-02-01','12345667','INVENTORY_MANAGER','uncln_account');
INSERT INTO `employee` VAlUES ('A0003', '库存人员甲', '男', '1920-07-01','12345','INVENTORY_MANAGER','A0003_account');
INSERT INTO `employee` VAlUES ('A0004', '天', '男', '1900-01-01','119','ADMIN','sky_account');
INSERT INTO `employee` VAlUES ('A0005', 'zhaoxr', '男', '1999-01-01','911','SALE_MANAGER','zhaoxr_account');
INSERT INTO `employee` VAlUES ('A0006', '刘钦', '男', '1988-01-01','67','GM','67_account');
INSERT INTO `employee` VAlUES ('A0007', '销售人员乙', '女', '1999-11-01','120','SALE_STAFF','A0007_account');
INSERT INTO `employee` VAlUES ('A0008', 'Leone', '男', '2000-05-01','110','GM','Leone_account');
INSERT INTO `employee` VAlUES ('A0009', '销售经理丙', '男', '1987-05-01','100005','SALE_MANAGER','A0009_account');
INSERT INTO `employee` VAlUES ('A0010', '财务人员丁', '女', '2000-04-01','10085','FINANCIAL_STAFF','A0010_account');
INSERT INTO `employee` VAlUES ('A0011', 'HR戊', '女', '1998-08-01','10086','HR','hr_account');
INSERT INTO `employee` VAlUES ('A0012', '靳QQ', '男', '2002-01-01','1891112222','HR','jinqq_account');

-- ----------------------------
-- Records of employee_salary_info
-- ----------------------------
INSERT INTO `employee_salary_info` VAlUES ('A0001', '10000.00', 30000.00, '按月薪计算', '按月薪发放');
INSERT INTO `employee_salary_info` VAlUES ('A0002', '12000.00', 30000.00, '按月薪计算', '按月薪发放');
INSERT INTO `employee_salary_info` VAlUES ('A0003', '15000.00', 30000.00, '按月薪计算', '按月薪发放');
INSERT INTO `employee_salary_info` VAlUES ('A0004', '0.00', 0.00, '按月薪计算', '按月薪发放');
INSERT INTO `employee_salary_info` VAlUES ('A0005', '10000.00', 20000.00, '按月薪和提成计算', '按月薪发放');
INSERT INTO `employee_salary_info` VAlUES ('A0006', '150000.00', 200000.00, '按年薪计算', '按年薪发放');
INSERT INTO `employee_salary_info` VAlUES ('A0007', '4000.00', 3000.00, '按月薪和提成计算', '按月薪发放');
INSERT INTO `employee_salary_info` VAlUES ('A0008', '124000.00', 200000.00, '按年薪计算', '按年薪发放');
INSERT INTO `employee_salary_info` VAlUES ('A0009', '9000.00', 20000.00, '按月薪和提成计算', '按月薪发放');
INSERT INTO `employee_salary_info` VAlUES ('A0010', '8000.00', 6000.00, '按月薪计算', '按月薪发放');
INSERT INTO `employee_salary_info` VAlUES ('A0011', '90000.00', 7000.00, '按月薪计算', '按月薪发放');
INSERT INTO `employee_salary_info` VAlUES ('A0012', '9000.00', 7000.00, '按月薪计算', '按月薪发放');

-- ----------------------------
-- Records of employee_year_end_bonus_info
-- ----------------------------
INSERT INTO `employee_year_end_bonus_info` VALUES('A0001',null);
INSERT INTO `employee_year_end_bonus_info` VALUES('A0002',null);
INSERT INTO `employee_year_end_bonus_info` VALUES('A0003',null);
INSERT INTO `employee_year_end_bonus_info` VALUES('A0004',null);
INSERT INTO `employee_year_end_bonus_info` VALUES('A0005',null);
INSERT INTO `employee_year_end_bonus_info` VALUES('A0006',null);
INSERT INTO `employee_year_end_bonus_info` VALUES('A0007',null);
INSERT INTO `employee_year_end_bonus_info` VALUES('A0008',null);
INSERT INTO `employee_year_end_bonus_info` VALUES('A0009',null);
INSERT INTO `employee_year_end_bonus_info` VALUES('A0010',null);
INSERT INTO `employee_year_end_bonus_info` VALUES('A0011',null);
INSERT INTO `employee_year_end_bonus_info` VALUES('A0012',null);
-- ----------------------------
-- Records of employee_post_info
-- ----------------------------
INSERT INTO `employee_post_info` VAlUES ('A0001', true);
INSERT INTO `employee_post_info` VAlUES ('A0002', true);
INSERT INTO `employee_post_info` VAlUES ('A0003', true);
INSERT INTO `employee_post_info` VAlUES ('A0004', true);
INSERT INTO `employee_post_info` VAlUES ('A0005', true);
INSERT INTO `employee_post_info` VAlUES ('A0006', true);
INSERT INTO `employee_post_info` VAlUES ('A0007', true);
INSERT INTO `employee_post_info` VAlUES ('A0008', true);
INSERT INTO `employee_post_info` VAlUES ('A0009', true);
INSERT INTO `employee_post_info` VAlUES ('A0010', true);
INSERT INTO `employee_post_info` VAlUES ('A0011', true);
INSERT INTO `employee_post_info` VAlUES ('A0012', true);

-- ----------------------------
-- Records of employee_user
-- ----------------------------
INSERT INTO `employee_user` VAlUES ('A0001', 1);
INSERT INTO `employee_user` VAlUES ('A0002', 2);
INSERT INTO `employee_user` VAlUES ('A0003', 3);
INSERT INTO `employee_user` VAlUES ('A0004', 4);
INSERT INTO `employee_user` VAlUES ('A0005', 5);
INSERT INTO `employee_user` VAlUES ('A0006', 6);
INSERT INTO `employee_user` VAlUES ('A0007', 7);
INSERT INTO `employee_user` VAlUES ('A0008', 8);
INSERT INTO `employee_user` VAlUES ('A0009', 9);
INSERT INTO `employee_user` VAlUES ('A0010', 10);
INSERT INTO `employee_user` VAlUES ('A0011', 11);
INSERT INTO `employee_user` VAlUES ('A0012', 12);

-- ----------------------------
-- Records of clock_in_record
-- ----------------------------
INSERT INTO `clock_in_record` VALUES('CIR-A0001-20220706','A0001','2022-07-06','2022-07-06 00:03:00', true);
INSERT INTO `clock_in_record` VALUES('CIR-A0002-20220706','A0002','2022-07-06','2022-07-06 00:03:00', true);
INSERT INTO `clock_in_record` VALUES('CIR-A0003-20220706','A0003','2022-07-06',null, false);
INSERT INTO `clock_in_record` VALUES('CIR-A0004-20220706','A0004','2022-07-06','2022-07-06 00:03:00', true);
INSERT INTO `clock_in_record` VALUES('CIR-A0005-20220706','A0005','2022-07-06','2022-07-06 00:03:00', true);
INSERT INTO `clock_in_record` VALUES('CIR-A0006-20220706','A0006','2022-07-06','2022-07-06 00:03:10', true);
INSERT INTO `clock_in_record` VALUES('CIR-A0007-20220706','A0007','2022-07-06','2022-07-06 04:03:00', true);
INSERT INTO `clock_in_record` VALUES('CIR-A0008-20220706','A0008','2022-07-06',null, false);
INSERT INTO `clock_in_record` VALUES('CIR-A0009-20220706','A0009','2022-07-06',null, false);
INSERT INTO `clock_in_record` VALUES('CIR-A0010-20220706','A0010','2022-07-06','2022-07-06 00:03:00', true);
INSERT INTO `clock_in_record` VALUES('CIR-A0011-20220706','A0011','2022-07-06',null, false);
INSERT INTO `clock_in_record` VALUES('CIR-A0012-20220706','A0012','2022-07-06','2022-07-06 00:03:00', true);

INSERT INTO `clock_in_record` VALUES('CIR-A0001-20220707', 'A0001', '2022-07-07', '2022-07-07 00:13:00', true);
INSERT INTO `clock_in_record` VALUES('CIR-A0002-20220707', 'A0002', '2022-07-07', NULL, false);
INSERT INTO `clock_in_record` VALUES('CIR-A0003-20220707', 'A0003', '2022-07-07', '2022-07-07 06:03:00', true);
INSERT INTO `clock_in_record` VALUES('CIR-A0004-20220707', 'A0004', '2022-07-07', '2022-07-07 08:03:00', true);
INSERT INTO `clock_in_record` VALUES('CIR-A0005-20220707', 'A0005', '2022-07-07', NULL, false);
INSERT INTO `clock_in_record` VALUES('CIR-A0006-20220707', 'A0006', '2022-07-07', NULL, false);
INSERT INTO `clock_in_record` VALUES('CIR-A0007-20220707', 'A0007', '2022-07-07', '2022-07-07 03:03:00', true);
INSERT INTO `clock_in_record` VALUES('CIR-A0008-20220707', 'A0008', '2022-07-07', '2022-07-07 14:03:04', true);
INSERT INTO `clock_in_record` VALUES('CIR-A0009-20220707', 'A0009', '2022-07-07', NULL, false);
INSERT INTO `clock_in_record` VALUES('CIR-A0010-20220707', 'A0010', '2022-07-07', '2022-07-07 00:07:00', true);
INSERT INTO `clock_in_record` VALUES('CIR-A0011-20220707', 'A0011', '2022-07-07', '2022-07-07 05:23:00', true);
INSERT INTO `clock_in_record` VALUES('CIR-A0012-20220707', 'A0012', '2022-07-07', NULL, false);

INSERT INTO `clock_in_record` VALUES('CIR-A0001-20220708', 'A0001', '2022-07-08', '2022-07-08 00:13:00', true);
INSERT INTO `clock_in_record` VALUES('CIR-A0002-20220708', 'A0002', '2022-07-08', NULL, false);
INSERT INTO `clock_in_record` VALUES('CIR-A0003-20220708', 'A0003', '2022-07-08', '2022-07-08 06:03:00', true);
INSERT INTO `clock_in_record` VALUES('CIR-A0004-20220708', 'A0004', '2022-07-08', '2022-07-08 08:03:00', true);
INSERT INTO `clock_in_record` VALUES('CIR-A0005-20220708', 'A0005', '2022-07-08', NULL, false);
INSERT INTO `clock_in_record` VALUES('CIR-A0006-20220708', 'A0006', '2022-07-08', NULL, false);
INSERT INTO `clock_in_record` VALUES('CIR-A0007-20220708', 'A0007', '2022-07-08', '2022-07-08 03:03:00', true);
INSERT INTO `clock_in_record` VALUES('CIR-A0008-20220708', 'A0008', '2022-07-08', '2022-07-08 14:03:04', true);
INSERT INTO `clock_in_record` VALUES('CIR-A0009-20220708', 'A0009', '2022-07-08', NULL, false);
INSERT INTO `clock_in_record` VALUES('CIR-A0010-20220708', 'A0010', '2022-07-08', '2022-07-08 00:07:00', true);
INSERT INTO `clock_in_record` VALUES('CIR-A0011-20220708', 'A0011', '2022-07-08', '2022-07-08 05:23:00', true);
INSERT INTO `clock_in_record` VALUES('CIR-A0012-20220708', 'A0012', '2022-07-08', NULL, false);

INSERT INTO `clock_in_record` VALUES('CIR-A0001-20220709', 'A0001', '2022-07-09', NULL, false);
INSERT INTO `clock_in_record` VALUES('CIR-A0002-20220709', 'A0002', '2022-07-09', '2022-07-09 04:13:00', true);
INSERT INTO `clock_in_record` VALUES('CIR-A0003-20220709', 'A0003', '2022-07-09', '2022-07-09 12:03:00', true);
INSERT INTO `clock_in_record` VALUES('CIR-A0004-20220709', 'A0004', '2022-07-09', NULL, false);
INSERT INTO `clock_in_record` VALUES('CIR-A0005-20220709', 'A0005', '2022-07-09', '2022-07-09 08:23:00', true);
INSERT INTO `clock_in_record` VALUES('CIR-A0006-20220709', 'A0006', '2022-07-09', NULL, false);
INSERT INTO `clock_in_record` VALUES('CIR-A0007-20220709', 'A0007', '2022-07-09', '2022-07-09 03:34:00', true);
INSERT INTO `clock_in_record` VALUES('CIR-A0008-20220709', 'A0008', '2022-07-09', NULL, false);
INSERT INTO `clock_in_record` VALUES('CIR-A0009-20220709', 'A0009', '2022-07-09', '2022-07-09 11:03:54', true);
INSERT INTO `clock_in_record` VALUES('CIR-A0010-20220709', 'A0010', '2022-07-09', '2022-07-09 12:07:00', true);
INSERT INTO `clock_in_record` VALUES('CIR-A0011-20220709', 'A0011', '2022-07-09', NULL, false);
INSERT INTO `clock_in_record` VALUES('CIR-A0012-20220709', 'A0012', '2022-07-09', '2022-07-09 08:23:00', true);

-- ----------------------------
-- Records of salary_sheet
-- ----------------------------
INSERT INTO `salary_sheet` VALUES ('GZD-20220707-00000', '2022-05-01', '2022-05-30', '2022-07-07 12:49:47', 'A0001', 'seecoder', 'seecoder_account', '0', '0', '10000.00', '30000.00', '0.00', '0.00', '40000.00', '3940.00', '400.00', '1600.00', '5940.00', '34060.00', '待审批');
INSERT INTO `salary_sheet` VALUES ('GZD-20220707-00001', '2022-05-01', '2022-05-30', '2022-07-07 12:49:47', 'A0002', 'uncln', 'uncln_account', '0', '0', '12000.00', '30000.00', '0.00', '0.00', '42000.00', '4320.00', '420.00', '1680.00', '6420.00', '35580.00', '待审批');
INSERT INTO `salary_sheet` VALUES ('GZD-20220707-00002', '2022-05-01', '2022-05-30', '2022-07-07 12:49:47', 'A0003', '库存人员甲', 'A0003_account', '0', '0', '15000.00', '30000.00', '0.00', '0.00', '45000.00', '5027.50', '450.00', '1800.00', '7277.50', '37722.50', '待审批');
INSERT INTO `salary_sheet` VALUES ('GZD-20220707-00003', '2022-05-01', '2022-05-30', '2022-07-07 12:49:47', 'A0004', '天', 'sky_account', '0', '0', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '待审批');
INSERT INTO `salary_sheet` VALUES ('GZD-20220707-00004', '2022-05-01', '2022-05-30', '2022-07-07 12:49:47', 'A0005', 'zhaoxr', 'zhaoxr_account', '0', '0', '10000.00', '20000.00', '0.00', '0.00', '30000.00', '940.00', '300.00', '1200.00', '2440.00', '27560.00', '待审批');
INSERT INTO `salary_sheet` VALUES ('GZD-20220707-00005', '2019-12-16', '2020-12-15', '2022-07-07 12:49:47', 'A0006', '刘钦', '67_account', '0', '0', '1800000.00', '2400000.00', '0.00', '0.00', '4200000.00', '1193580.00', '42000.00', '168000.00', '1403580.00', '2796420.00', '待审批');
INSERT INTO `salary_sheet` VALUES ('GZD-20220707-00006', '2022-05-01', '2022-05-30', '2022-07-07 12:49:47', 'A0007', '销售人员乙', 'A0007_account', '0', '0', '4000.00', '3000.00', '0.00', '0.00', '7000.00', '0.00', '70.00', '280.00', '350.00', '6650.00', '待审批');
INSERT INTO `salary_sheet` VALUES ('GZD-20220707-00007', '2019-12-16', '2020-12-15', '2022-07-07 12:49:47', 'A0008', 'Leone', 'Leone_account', '0', '0', '1488000.00', '2400000.00', '0.00', '0.00', '3888000.00', '1089840.00', '38880.00', '155520.00', '1284240.00', '2603760.00', '待审批');
INSERT INTO `salary_sheet` VALUES ('GZD-20220707-00008', '2022-05-01', '2022-05-30', '2022-07-07 12:49:47', 'A0009', '销售经理丙', 'A0009_account', '0', '0', '9000.00', '20000.00', '44.31', '0.00', '29044.31', '849.21', '290.44', '1161.77', '2301.43', '26742.89', '待审批');
INSERT INTO `salary_sheet` VALUES ('GZD-20220707-00009', '2022-05-01', '2022-05-30', '2022-07-07 12:49:47', 'A0010', '财务人员丁', 'A0010_account', '0', '0', '8000.00', '6000.00', '0.00', '0.00', '14000.00', '39.00', '140.00', '560.00', '739.00', '13261.00', '待审批');
INSERT INTO `salary_sheet` VALUES ('GZD-20220707-00010', '2022-05-01', '2022-05-30', '2022-07-07 12:49:47', 'A0011', 'HR戊', 'hr_account', '0', '0', '90000.00', '7000.00', '0.00', '0.00', '97000.00', '15342.50', '970.00', '3880.00', '20192.50', '76807.50', '待审批');
INSERT INTO `salary_sheet` VALUES ('GZD-20220707-00011', '2022-05-01', '2022-05-30', '2022-07-07 12:49:47', 'A0012', '靳QQ', 'jinqq_account', '0', '0', '9000.00', '7000.00', '0.00', '0.00', '16000.00', '96.00', '160.00', '640.00', '896.00', '15104.00', '待审批');

INSERT INTO `salary_sheet` VALUES ('GZD-20220706-00000', '2022-04-01', '2022-04-30', '2022-07-06 12:49:47', 'A0001', 'seecoder', 'seecoder_account', '0', '0', '10000.00', '30000.00', '0.00', '0.00', '40000.00', '3940.00', '400.00', '1600.00', '5940.00', '34060.00', '审批完成');
INSERT INTO `salary_sheet` VALUES ('GZD-20220706-00001', '2022-04-01', '2022-04-30', '2022-07-06 12:49:47', 'A0002', 'uncln', 'uncln_account', '0', '0', '12000.00', '30000.00', '0.00', '0.00', '42000.00', '4320.00', '420.00', '1680.00', '6420.00', '35580.00', '审批完成');
INSERT INTO `salary_sheet` VALUES ('GZD-20220706-00002', '2022-04-01', '2022-04-30', '2022-07-06 12:49:47', 'A0003', '库存人员甲', 'A0003_account', '0', '0', '15000.00', '30000.00', '0.00', '0.00', '45000.00', '5027.50', '450.00', '1800.00', '7277.50', '37722.50', '审批完成');
INSERT INTO `salary_sheet` VALUES ('GZD-20220706-00010', '2022-04-01', '2022-04-30', '2022-07-06 12:49:47', 'A0011', 'HR戊', 'hr_account', '0', '0', '90000.00', '7000.00', '0.00', '0.00', '97000.00', '15342.50', '970.00', '3880.00', '20192.50', '76807.50', '待审批');
INSERT INTO `salary_sheet` VALUES ('GZD-20220706-00011', '2022-04-01', '2022-04-30', '2022-07-06 12:49:47', 'A0012', '靳QQ', 'jinqq_account', '0', '0', '9000.00', '7000.00', '0.00', '0.00', '16000.00', '96.00', '160.00', '640.00', '896.00', '15104.00', '待审批');

-- ----------------------------
-- Records of post_salary_info
-- ----------------------------
INSERT INTO `post_salary_info` VAlUES ('GM', 200000.00);
INSERT INTO `post_salary_info` VAlUES ('INVENTORY_MANAGER', 30000.00);
INSERT INTO `post_salary_info` VAlUES ('SALE_STAFF', 3000.00);
INSERT INTO `post_salary_info` VAlUES ('FINANCIAL_STAFF', 6000.00);
INSERT INTO `post_salary_info` VAlUES ('SALE_MANAGER', 20000.00);
INSERT INTO `post_salary_info` VAlUES ('HR', 7000.00);
INSERT INTO `post_salary_info` VAlUES ('ADMIN', 0.00);

-- ----------------------------
-- Records of commission_rate_info
-- ----------------------------
INSERT INTO `commission_rate_info` VAlUES (1, 0.00001);

-- ----------------------------
-- Records of promotion
-- ----------------------------
INSERT INTO `promotion` VALUES ('PRO-20220708-00000', '2022-06-01 00:00:00', '2022-10-01 00:00:00', '3', '10000.00', '0.70', NULL);
INSERT INTO `promotion` VALUES ('PRO-20220706-00000', '2022-01-01 00:00:00', '2022-12-01 00:00:00', NULL, '30000.00', NULL, '5000.00');

-- ----------------------------
-- Records of promotion_package
-- ----------------------------
INSERT INTO `promotion_package` VALUES (1,'PRO-20220706-00000','0000000000400000',10);

-- ----------------------------
-- Records of promotion_gift
-- ----------------------------
INSERT INTO `promotion_gift` VALUES (1,'PRO-20220708-00000','0000000000400000',30);
INSERT INTO `promotion_gift` VALUES (2,'PRO-20220708-00000','0000000000500001',10);