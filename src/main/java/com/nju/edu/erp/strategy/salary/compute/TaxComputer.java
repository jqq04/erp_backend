package com.nju.edu.erp.strategy.salary.compute;

import java.math.BigDecimal;

/**
 * 税率和五险一金计算器
 */
public class TaxComputer {

    private static final BigDecimal[] preTaxableIncomeArray = {BigDecimal.valueOf(0), BigDecimal.valueOf(3000), BigDecimal.valueOf(12000), BigDecimal.valueOf(25000), BigDecimal.valueOf(35000), BigDecimal.valueOf(55000), BigDecimal.valueOf(80000)};

    private static final BigDecimal[] postTaxableIncomeArray = {BigDecimal.valueOf(3000), BigDecimal.valueOf(12000), BigDecimal.valueOf(25000), BigDecimal.valueOf(35000), BigDecimal.valueOf(55000), BigDecimal.valueOf(80000), BigDecimal.valueOf(Integer.MAX_VALUE)};
    /** 税率 **/
    private static final BigDecimal[] taxRate = {BigDecimal.valueOf(0.03), BigDecimal.valueOf(0.1), BigDecimal.valueOf(0.2), BigDecimal.valueOf(0.25), BigDecimal.valueOf(0.3), BigDecimal.valueOf(0.35), BigDecimal.valueOf(0.45)};
    /** 速算扣除数 **/
    private static final BigDecimal[] quickCalculateDeductions = {BigDecimal.valueOf(0), BigDecimal.valueOf(210), BigDecimal.valueOf(1410), BigDecimal.valueOf(2660), BigDecimal.valueOf(4410), BigDecimal.valueOf(7160), BigDecimal.valueOf(15160)};

    private static final int[] levelArray = {0,1,2,3,4,5,6};
    /** 个人所得税起征点 = 5000元 **/
    private static final BigDecimal threshold = BigDecimal.valueOf(5000);

    /** 失业保险缴费比例 = 1% **/
    private static final BigDecimal unemploymentInsuranceRate = BigDecimal.valueOf(0.01);
    /** 职工住房公积金缴存比例 = 4% **/
    private static final BigDecimal housingProvidentFundRate = BigDecimal.valueOf(0.04);

    /**
     * 表驱动 计算个人所得税
     * 应纳税所得额 = 税前工资收入金额 - 五险一金(个人缴纳部分) - 专项扣除 - 起征点(5000元)
     * 应纳税额 = 应纳税所得额 * 税率 - 速算扣除数
     * @param expectedSalary 税前工资收入金额
     * @param insuranceAndFund 五险一金，实际只有一险一金
     * @return 应纳税额
     */
    public static BigDecimal computePersonalIncomeTax(BigDecimal expectedSalary, BigDecimal insuranceAndFund){
        BigDecimal taxableIncome = expectedSalary.subtract(insuranceAndFund).subtract(threshold);
        BigDecimal tax = BigDecimal.ZERO;
        for (int i : levelArray)
            if (preTaxableIncomeArray[i].compareTo(taxableIncome) < 0 && taxableIncome.compareTo(postTaxableIncomeArray[i]) < 0) {
                tax = taxableIncome.multiply(taxRate[i]).subtract(quickCalculateDeductions[i]);
            }
        return tax;
    }

    /**
     * 个人缴纳失业保险缴费额 = 税前工资收入金额 * 失业保险缴费比例(1%)
     * @param expectedSalary 税前工资收入金额
     * @return 失业保险缴费额
     */
    public static BigDecimal computeUnemploymentInsurance(BigDecimal expectedSalary){
        return expectedSalary.multiply(unemploymentInsuranceRate);
    }

    /**
     * 个人缴纳住房公积金额 = 税前工资收入金额 * 职工住房公积金缴存比例
     * @param expectedSalary 税前工资收入金额
     * @return 缴纳住房公积金额
     */
    public static BigDecimal computeHousingProvidentFund(BigDecimal expectedSalary){
        return expectedSalary.multiply(housingProvidentFundRate);
    }
}
