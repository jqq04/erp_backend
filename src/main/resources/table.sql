/*
 Navicat Premium Data Transfer

 Source Server         : mysql5.7
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : localhost:3306
 Source Schema         : seec_erp

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 24/05/2022 01:11:23
*/
 create database `seec_erp`;
 SET NAMES utf8mb4;
 SET FOREIGN_KEY_CHECKS = 0;

use `seec_erp`;
-- ----------------------------
-- drop Table
-- ----------------------------
DROP TABLE IF EXISTS `payment_sheet_content`;
DROP TABLE IF EXISTS `payment_sheet`;
DROP TABLE if EXISTS `receipt_sheet_content`;
DROP TABLE IF EXISTS `receipt_sheet`;
DROP TABLE IF EXISTS `account`; 
-- ----------------------------
-- Table structure for user
-- 用户账号表
-- ----------------------------
DROP TABLE IF EXISTS `user` ;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户密码',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户身份',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for category
-- 商品分类信息表
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名字',
  `parent_id` int(11) NOT NULL COMMENT '父节点id',
  `is_leaf` tinyint(4) NOT NULL COMMENT '是否为叶节点',
  `item_count` int(11) NOT NULL COMMENT '商品个数',
  `item_index` int(11) NOT NULL COMMENT '插入的下一个index',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for customer
-- 客户信息表
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `type` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类(供应商\\销售商)',
  `level` int(11) NULL DEFAULT NULL COMMENT '级别（五级，一级普通用户，五级VIP客户）',
  `name` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话号码',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
  `zipcode` char(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮编',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电子邮箱',
  `line_of_credit` decimal(10, 2) NULL DEFAULT NULL COMMENT '应收额度本公司给客户的信用额度，客户欠本公司的钱的总额不能超过应收额度）',
  `receivable` decimal(10, 2) NULL DEFAULT NULL COMMENT '应收（客户还应付给本公司但还未付的钱， 即本公司应收的钱）',
  `payable` decimal(10, 2) NULL DEFAULT NULL COMMENT '应付（本公司欠客户的钱）',
  `operator` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '默认业务员',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product
-- 商品信息表
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `id` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分类id(11位) + 位置(5位) = 编号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名字',
  `category_id` int(11) NOT NULL COMMENT '商品分类id',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品型号',
  `quantity` int(11) NOT NULL COMMENT '商品数量',
  `purchase_price` decimal(10, 2) NOT NULL COMMENT '进价',
  `retail_price` decimal(10, 2) NOT NULL COMMENT '零售价',
  `recent_pp` decimal(10, 2) NULL DEFAULT NULL COMMENT '最近进价',
  `recent_rp` decimal(10, 2) NULL DEFAULT NULL COMMENT '最近零售价',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for purchase_sheet
-- 进货单表
-- ----------------------------
DROP TABLE IF EXISTS `purchase_sheet`;
CREATE TABLE `purchase_sheet`  (
  `id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '进货单单据编号（格式为：JHD-yyyyMMdd-xxxxx',
  `supplier` int(11) NULL DEFAULT NULL COMMENT '供应商',
  `operator` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `total_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '总额合计',
  `state` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单据状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for purchase_sheet_content
-- 进货单具体内部表
-- ----------------------------
DROP TABLE IF EXISTS `purchase_sheet_content`;
CREATE TABLE `purchase_sheet_content`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `purchase_sheet_id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '进货单id',
  `pid` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品id',
  `quantity` int(11) NULL DEFAULT NULL COMMENT '数量',
  `unit_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '单价',
  `total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '金额',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 62 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for purchase_returns_sheet
-- 进货退货单表
-- ----------------------------
DROP TABLE IF EXISTS `purchase_returns_sheet`;
CREATE TABLE `purchase_returns_sheet`  (
  `id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '进货退货单id',
  `purchase_sheet_id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关联的进货单id',
  `operator` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员',
  `state` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单据状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `total_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '退货的总金额',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for purchase_returns_sheet_content
-- 进货退货单具体内容表
-- ----------------------------
DROP TABLE IF EXISTS `purchase_returns_sheet_content`;
CREATE TABLE `purchase_returns_sheet_content`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `purchase_returns_sheet_id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '进货退货单id',
  `pid` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品id',
  `quantity` int(11) NULL DEFAULT NULL COMMENT '数量',
  `total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '该商品的总金额',
  `unit_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '该商品的单价',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sale_sheet
-- 销售单表
-- ----------------------------
DROP TABLE IF EXISTS `sale_sheet`;
CREATE TABLE `sale_sheet`  (
  `id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '销售单单据编号（格式为：XSD-yyyyMMdd-xxxxx',
  `supplier` int(11) NULL DEFAULT NULL COMMENT '客户/销售商',
  `operator` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `state` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单据状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `salesman` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '业务员',
  `raw_total_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '折让前总金额',
  `discount` decimal(10, 2) NULL DEFAULT NULL COMMENT '折扣',
  `final_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '折让后金额',
  `voucher_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '代金券金额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sale_sheet_content
-- 销售单具体内容表
-- ----------------------------
DROP TABLE IF EXISTS `sale_sheet_content`;
CREATE TABLE `sale_sheet_content`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `sale_sheet_id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售单id',
  `pid` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品id',
  `quantity` int(11) NULL DEFAULT NULL COMMENT '数量',
  `unit_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '单价',
  `total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '金额',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for sale_returns_sheet
-- 销售退货单表
-- ----------------------------
DROP TABLE IF EXISTS `sale_returns_sheet`;
CREATE TABLE `sale_returns_sheet`  (
  `id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '销售退货单单据编号（格式为：XSTHD-yyyyMMdd-xxxxx',
  `sale_sheet_id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关联的销售单id',
  `supplier` int(11) NULL DEFAULT NULL COMMENT '客户/销售商',
  `operator` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `state` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单据状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `salesman` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '业务员',
  `returns_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '退回总金额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for sale_returns_sheet_content
-- 销售退货单具体内容表
-- ----------------------------
DROP TABLE IF EXISTS `sale_returns_sheet_content`;
CREATE TABLE `sale_returns_sheet_content`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `sale_returns_sheet_id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售退货单id',
  `pid` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品id',
  `quantity` int(11) NULL DEFAULT NULL COMMENT '数量',
  `unit_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '原单价',
  `returns_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '退货单价',
  `returns_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '退货总额',
   `batch_id` decimal(10, 2) NULL DEFAULT NULL COMMENT '对应批次',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for warehouse
-- 库存表
-- ----------------------------
DROP TABLE IF EXISTS `warehouse`;
CREATE TABLE `warehouse`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '库存id',
  `pid` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品编号',
  `quantity` int(11) NOT NULL COMMENT '数量',
  `purchase_price` decimal(10, 2) NOT NULL COMMENT '进价',
  `batch_id` int(11) NOT NULL COMMENT '批次',
  `production_date` datetime(0) NULL DEFAULT NULL COMMENT '出厂日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for warehouse_input_sheet
-- 入库单表
-- ----------------------------
DROP TABLE IF EXISTS `warehouse_input_sheet`;
CREATE TABLE `warehouse_input_sheet`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'RKD + 日期 + index = 入库单编号',
  `batch_id` int(11) NOT NULL COMMENT '批次',
  `operator` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `state` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单据状态',
  `purchase_sheet_id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关联的进货单id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for warehouse_input_sheet_content
-- 入库单具体内容表
-- ----------------------------
DROP TABLE IF EXISTS `warehouse_input_sheet_content`;
CREATE TABLE `warehouse_input_sheet_content`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `wi_id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '入库单编号',
  `pid` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品id',
  `quantity` int(11) NOT NULL COMMENT '商品数量',
  `purchase_price` decimal(10, 2) NOT NULL COMMENT '单价',
  `production_date` datetime(0) NULL DEFAULT NULL COMMENT '出厂日期',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 54 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for warehouse_output_sheet
-- 出库单表
-- ----------------------------
DROP TABLE IF EXISTS `warehouse_output_sheet`;
CREATE TABLE `warehouse_output_sheet`  (
  `id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'CKD + date + index = 出库单id',
  `operator` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员名字',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `sale_sheet_id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售单id',
  `state` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单据状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for warehouse_output_sheet_content
-- 出库单具体内容表
-- ----------------------------
DROP TABLE IF EXISTS `warehouse_output_sheet_content`;
CREATE TABLE `warehouse_output_sheet_content`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '出库商品列表id',
  `pid` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品id',
  `wo_id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '出库单单据编号',
  `batch_id` int(11) NULL DEFAULT NULL COMMENT '批次',
  `quantity` int(11) NOT NULL COMMENT '数量',
  `sale_price` decimal(10, 2) NOT NULL COMMENT '对应批次的单价',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for account
-- 公司账户表
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
	 `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '账户id',
	 `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账户名称' UNIQUE,
     `amount` decimal(10, 2) COMMENT '银行余额',
      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table structure for receipt_sheet
-- 收款单表
-- ----------------------------
DROP TABLE IF EXISTS `receipt_sheet`;
CREATE TABLE `receipt_sheet` (
	`id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '收款单单据编号（格式为：SKD-yyyyMMdd-xxxxx）',
	`supplier` int(11) NULL DEFAULT NULL COMMENT '客户/供应商/销售商',
	`operator` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员',
    `total_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '收款总额',
	`remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
	`create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
	`state` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单据状态',
  PRIMARY KEY (`id`) USING BTREE,
  FOREIGN KEY (`supplier`)  REFERENCES customer(`id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table structure for receipt_sheet_content
-- 收款单转账列表表
-- ----------------------------
DROP TABLE IF EXISTS `receipt_sheet_content`;
CREATE TABLE `receipt_sheet_content` (
	`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
	`receipt_sheet_id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关联的收款单id',
	`account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '银行账户',
	`amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '转账金额',
	`remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  FOREIGN KEY (`receipt_sheet_id`) REFERENCES receipt_sheet(`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table structure for payment_sheet
-- 付款单表
-- ----------------------------
DROP TABLE IF EXISTS `payment_sheet`;
CREATE TABLE `payment_sheet` (
	`id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '付款单单据编号（格式为：SFD-yyyyMMdd-xxxxx）',
	`supplier` int(11) NULL DEFAULT NULL COMMENT '客户/供应商/销售商',
	`operator` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员',
    `total_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '付款总额',
	`remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
	`create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
	`state` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单据状态',
  PRIMARY KEY (`id`) USING BTREE,
  FOREIGN KEY (`supplier`)  REFERENCES customer(`id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table structure for payment_sheet_content
-- 付款单转账列表表
-- ----------------------------
DROP TABLE IF EXISTS `payment_sheet_content`;
CREATE TABLE `payment_sheet_content` (
	`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
	`payment_sheet_id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关联的付款单id',
	`account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '银行账户',
	`amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '转账金额',
	`remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  FOREIGN KEY (`payment_sheet_id`) REFERENCES payment_sheet(`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table structure for employee
-- 员工基本信息表
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
    CREATE TABLE `employee` (
	`id` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '员工编号：一位字母+四位数字',
	`name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
	`gender` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
	`birthday` date NULL DEFAULT NULL COMMENT '出生日期',
	`phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话号码',
	`role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '工作岗位',
	`account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工资卡账户',
     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table structure for employee_salary_info
-- 员工薪资信息表
-- ----------------------------
DROP TABLE IF EXISTS `employee_salary_info`;
CREATE TABLE `employee_salary_info` (
	`id` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '员工编号：一位字母+四位数字',
	`basic_salary` decimal(10, 2) NULL DEFAULT NULL COMMENT '基本工资',
	`post_salary` decimal(10, 2) NULL DEFAULT NULL COMMENT '岗位工资',
	`salary_compute_strategy` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工资计算方式',
	`salary_payment_strategy` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工资发放方式',
     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
SET FOREIGN_KEY_CHECKS = 1;

-- ---------------------------
-- Table structure for employee_year_end_bonus_info
-- 员工年终奖信息表
-- ---------------------------
DROP TABLE IF EXISTS `employee_year_end_bonus_info`;
CREATE TABLE `employee_year_end_bonus_info`(
    `id` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '员工编号：一位字母+四位数字',
    `amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '年终奖金额',
    PRIMARY KEY (`id`) USING BTREE
)ENGINE = InnoDB  CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
 SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table structure for employee_post_info
-- 员工在岗表，记录员工在岗还是离职
-- ----------------------------
DROP TABLE IF EXISTS `employee_post_info`;
CREATE TABLE `employee_post_info` (
	`employee_id` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '员工编号：一位字母+四位数字',
	`on_post` bool NOT NULL COMMENT '是否在岗',
     PRIMARY KEY (`employee_id`) USING BTREE
) ENGINE = InnoDB  CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table structure for employee_user
-- 关联员工与用户账号表
-- ----------------------------
DROP TABLE IF EXISTS `employee_user`;
CREATE TABLE `employee_user` (
	`employee_id` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '员工编号：一位字母+四位数字',
	`user_id` int(11) NOT NULL COMMENT '用户id',
     PRIMARY KEY (`employee_id`) USING BTREE
) ENGINE = InnoDB  CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table structure for clock_in_record
-- 打卡记录表
-- ----------------------------
DROP TABLE IF EXISTS `clock_in_record`;
    CREATE TABLE `clock_in_record` (
    `id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '打卡记录编号: CIR-A0001-yyyyMMdd',
    `employee_id`  char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '员工编号：一位字母+四位数字',
    `date` date NOT NULL COMMENT '打卡日期',
    `time` datetime(0) NULL DEFAULT NULL COMMENT '打卡时间',
    `has_clock_in` bool NOT NULL COMMENT '是否打卡',
     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table structure for salary_sheet
-- 工资单表
-- ----------------------------
DROP TABLE IF EXISTS `salary_sheet`;
CREATE TABLE `salary_sheet`(
    `id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '工资单单据编号: GZD-A0001-yyyyMMdd',
    `begin_date` date NOT NULL COMMENT '工资计算的起始日期',
    `end_date` date NOT NULL COMMENT '工资计算的截止日期',
    `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `employee_id`  char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '员工编号：一位字母+四位数字',
    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '员工姓名',
    `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '员工工资卡账户',
    `attendance_days` int NULL DEFAULT NULL COMMENT '出勤天数',
    `absence_days` int NULL DEFAULT NULL COMMENT '缺勤天数',
    `basic_salary` decimal(10, 2) NULL DEFAULT NULL COMMENT '基本工资',
    `post_salary` decimal(10, 2) NULL DEFAULT NULL COMMENT '岗位工资',
    `commission` decimal(10, 2) NULL DEFAULT NULL COMMENT '提成',
    `deduct_salary` decimal(10, 2) NULL DEFAULT NULL COMMENT '扣除工资',
    `expected_salary` decimal(10, 2) NULL DEFAULT NULL COMMENT '应发工资',
    `personal_income_tax` decimal(10, 2) NULL DEFAULT NULL COMMENT '个人所得税',
    `unemployment_insurance` decimal(10, 2) NULL DEFAULT NULL COMMENT '失业保险',
    `housing_provident_fund` decimal(10, 2) NULL DEFAULT NULL COMMENT '住房公积金',
    `total_tax` decimal(10, 2) NULL DEFAULT NULL COMMENT '应扣税款',
    `final_salary` decimal(10, 2) NULL DEFAULT NULL COMMENT '实发工资',
    `state` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单据状态',
     PRIMARY KEY (`id`) USING BTREE
)ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table structure for post_salary_info
-- 岗位工资信息表
-- ----------------------------
DROP TABLE IF EXISTS `post_salary_info`;
CREATE TABLE `post_salary_info`(
   `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '工作岗位',
   `post_salary` decimal(10, 2) NULL DEFAULT NULL COMMENT '岗位工资',
    PRIMARY KEY (`role`)
)ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table structure for commission_rate_info
-- 提成比例信息表
-- 只使用表中id=1的项
-- ----------------------------
DROP TABLE IF EXISTS `commission_rate_info`;
CREATE TABLE `commission_rate_info`(
   `id` int NOT NULL COMMENT '无用id',
   `commission_rate` decimal(15, 8) NOT NULL COMMENT '提成比例',
   PRIMARY KEY (`id`)
)ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table structure for promotion
-- 促销策略表
-- ----------------------------
DROP TABLE IF EXISTS `promotion`;
CREATE TABLE `promotion`(
    `id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '促销策略编号（格式为：PRO-yyyyMMdd-xxxxx）',
    `begin_time` datetime(0) NULL DEFAULT NULL COMMENT '活动开始时间',
    `end_time` datetime(0) NULL DEFAULT NULL COMMENT '活动结束时间',
    `customer_level` int(11) NULL DEFAULT NULL COMMENT '促销针对的客户的最低级别，null表示针对所有客户,其中级别（五级，一级普通用户，五级VIP客户）',
    `total_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '促销需求的最低总价，null表示对总价不做要求',
    `discount` decimal(10, 2) NULL DEFAULT NULL COMMENT '促销提供的折扣，null表示没有折扣',
    `voucher` decimal(10, 2) NULL DEFAULT NULL COMMENT '促销提供的代金券，null表示没有代金券',
    PRIMARY KEY (`id`) USING BTREE
)ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table structure for promotion_package
-- 促销商品要求包表
-- ----------------------------
DROP TABLE IF EXISTS `promotion_package`;
CREATE TABLE `promotion_package`(
    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
    `promotion_id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '属于的促销策略编号（格式为：PRO-yyyyMMdd-xxxxx）',
    `product_id` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品编号，分类id(11位) + 位置(5位) = 编号',
    `quantity` int(11) NOT NULL COMMENT '数量',
    PRIMARY KEY (`id`) USING BTREE
)ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table structure for promotion_gift
-- 促销赠品包表
-- ----------------------------
DROP TABLE IF EXISTS `promotion_gift`;
CREATE TABLE `promotion_gift`(
    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
    `promotion_id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '属于的促销策略编号（格式为：PRO-yyyyMMdd-xxxxx）',
    `product_id` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品编号，分类id(11位) + 位置(5位) = 编号',
    `quantity` int(11) NOT NULL COMMENT '数量',
    PRIMARY KEY (`id`) USING BTREE
)ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table structure for book
-- 套账表
-- 一套账由一个标识套账(book)和三套分账(account_book,customer_book,product_book)组成
-- 其它具体套装表都会去关联此表
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`(
       `id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '套帐编号（格式为：ZD-yyyyMMdd-xxxxx）',
       `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间，同时也是这套帐所记录的时间点',
       PRIMARY KEY (`id`) USING BTREE
)ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table structure for account_book
-- 套账银行账户信息表
-- ----------------------------
DROP TABLE IF EXISTS `account_book`;
CREATE TABLE `account_book`(
       `book_id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '对应的套帐编号',
       `id` int(11) NOT NULL COMMENT '账户id, 与实际账户要对上',
       `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账户名称',
       `amount` decimal(10, 2) COMMENT '银行余额',
       PRIMARY KEY (`book_id`,`id`) USING BTREE
)ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table structure for customer_book
-- 套账客户信息表
-- ----------------------------
DROP TABLE IF EXISTS `customer_book`;
CREATE TABLE `customer_book`  (
  `book_id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '对应的套帐编号',
  `id` int(11) NOT NULL COMMENT '编号',
  `type` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类(供应商\\销售商)',
  `level` int(11) NULL DEFAULT NULL COMMENT '级别（五级，一级普通用户，五级VIP客户）',
  `name` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话号码',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
  `zipcode` char(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮编',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电子邮箱',
  `line_of_credit` decimal(10, 2) NULL DEFAULT NULL COMMENT '应收额度本公司给客户的信用额度，客户欠本公司的钱的总额不能超过应收额度）',
  `receivable` decimal(10, 2) NULL DEFAULT NULL COMMENT '应收（客户还应付给本公司但还未付的钱， 即本公司应收的钱）',
  `payable` decimal(10, 2) NULL DEFAULT NULL COMMENT '应付（本公司欠客户的钱）',
  PRIMARY KEY (`book_id`,`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table structure for product_book
-- 套装商品信息表
-- ----------------------------
DROP TABLE IF EXISTS `product_book`;
CREATE TABLE `product_book`  (
  `book_id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '对应的套帐编号',
  `id` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分类id(11位) + 位置(5位) = 编号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名字',
  `category_id` int(11) NOT NULL COMMENT '商品分类id',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品型号',
  `quantity` int(11) NOT NULL COMMENT '商品数量',
  `purchase_price` decimal(10, 2) NOT NULL COMMENT '进价，默认为上年平均',
  `retail_price` decimal(10, 2) NOT NULL COMMENT '零售价，默认为上年平均',
  `recent_pp` decimal(10, 2) NULL DEFAULT NULL COMMENT '最近进价，留空',
  `recent_rp` decimal(10, 2) NULL DEFAULT NULL COMMENT '最近零售价，留空',
  PRIMARY KEY (`book_id`,`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
SET FOREIGN_KEY_CHECKS = 1;

