package com.nju.edu.erp.strategy.salary.compute.Impl;

import com.nju.edu.erp.dao.EmployeeDao;
import com.nju.edu.erp.model.po.EmployeePO;
import com.nju.edu.erp.model.po.salary.SalaryComputeInfoNeeded;
import com.nju.edu.erp.model.po.salary.SalaryInfo;
import com.nju.edu.erp.service.ClockInService;
import com.nju.edu.erp.strategy.salary.compute.SalaryComputeStrategy;
import com.nju.edu.erp.strategy.salary.compute.TaxComputer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

@Component("按年薪计算")
public class AnnuallySalaryComputeStrategy implements SalaryComputeStrategy {

    private static final BigDecimal monthsCount = BigDecimal.valueOf(12);
    @Override
    public SalaryInfo compute(SalaryComputeInfoNeeded info) {
        EmployeePO employee = info.getEmployee();

        BigDecimal basicSalary = employee.getBasicSalary().multiply(monthsCount);
        BigDecimal postSalary = employee.getPostSalary().multiply(monthsCount);
        BigDecimal commission = info.getTotalSaleAmount().multiply(info.getCommissionRate());

        commission = commission.add(info.getYearEndBonus());

        //扣除工资 = 基本工资 * 缺席天数 / 365
        Integer absenceDays = info.getAbsencesDays();
        BigDecimal deductSalary = basicSalary.multiply(BigDecimal.valueOf(absenceDays)).divide(BigDecimal.valueOf(365));
        //应发工资 = 基本工资 + 岗位工资 + 提成 - 应扣工资
        BigDecimal expectedSalary = basicSalary.add(postSalary).add(commission).subtract(deductSalary);

        //计算失业保险和住房公积金和个人所得税
        BigDecimal unemploymentInsurance = TaxComputer.computeUnemploymentInsurance(expectedSalary);
        BigDecimal housingProvidentFund = TaxComputer.computeHousingProvidentFund(expectedSalary);
        BigDecimal insuranceAndFund = unemploymentInsurance.add(housingProvidentFund);
        BigDecimal personalIncomeTax = TaxComputer.computePersonalIncomeTax(expectedSalary.divide(monthsCount), insuranceAndFund.divide(monthsCount)).multiply(monthsCount);
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
