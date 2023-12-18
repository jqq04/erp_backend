package com.nju.edu.erp.strategy.salary.compute.Impl;

import com.nju.edu.erp.model.po.EmployeePO;
import com.nju.edu.erp.model.po.salary.SalaryComputeInfoNeeded;
import com.nju.edu.erp.model.po.salary.SalaryInfo;
import com.nju.edu.erp.strategy.salary.compute.SalaryComputeStrategy;
import com.nju.edu.erp.strategy.salary.compute.TaxComputer;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

@Component("按月薪和提成计算")
public class MonthlyAndCommissionSalaryComputeStrategy implements SalaryComputeStrategy {

    @Override
    public SalaryInfo compute(SalaryComputeInfoNeeded info) {
        EmployeePO employee = info.getEmployee();

        BigDecimal basicSalary = employee.getBasicSalary();
        BigDecimal postSalary = employee.getPostSalary();
        BigDecimal commission = info.getTotalSaleAmount().multiply(info.getCommissionRate());

        //如果是1月份(发的是12月薪水)，那么还会在提成上加上年终奖
        Date date = new Date();
        if (date.getMonth() == Calendar.JANUARY)
            commission = commission.add(info.getYearEndBonus());

        //扣除工资 = 基本工资 * 缺席天数 / 30
        Integer absenceDays = info.getAbsencesDays();
        BigDecimal deductSalary = basicSalary.multiply(BigDecimal.valueOf(absenceDays)).divide(BigDecimal.valueOf(30));
        //应发工资 = 基本工资 + 岗位工资 + 提成 - 应扣工资
        BigDecimal expectedSalary = basicSalary.add(postSalary).add(commission).subtract(deductSalary);

        //计算失业保险和住房公积金和个人所得税
        BigDecimal unemploymentInsurance = TaxComputer.computeUnemploymentInsurance(expectedSalary);
        BigDecimal housingProvidentFund = TaxComputer.computeHousingProvidentFund(expectedSalary);
        BigDecimal insuranceAndFund = unemploymentInsurance.add(housingProvidentFund);
        BigDecimal personalIncomeTax = TaxComputer.computePersonalIncomeTax(expectedSalary, insuranceAndFund);
        BigDecimal totalTax = personalIncomeTax.add(insuranceAndFund);

        BigDecimal finalSalary = expectedSalary.subtract(totalTax);

        SalaryInfo salaryInfo = SalaryInfo.builder()
                .basicSalary(basicSalary)
                .postSalary(postSalary)
                .commission(commission)
                .deductSalary(deductSalary)
                .expectedSalary(expectedSalary)
                .personalIncomeTax(personalIncomeTax)
                .unemploymentInsurance(unemploymentInsurance)
                .housingProvidentFund(housingProvidentFund)
                .totalTax(totalTax)
                .finalSalary(finalSalary)
                .build();

        return salaryInfo;
    }
}
