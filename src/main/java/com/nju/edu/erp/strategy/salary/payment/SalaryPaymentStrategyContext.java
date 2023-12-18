package com.nju.edu.erp.strategy.salary.payment;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用以调度薪资发放策略的上下文/环境
 */
@Service
public class SalaryPaymentStrategyContext {

    private final Map<String,SalaryPaymentStrategy> salaryPaymentStrategyMap = new ConcurrentHashMap<>();

    public SalaryPaymentStrategyContext(Map<String,SalaryPaymentStrategy> strategyMap){
        this.salaryPaymentStrategyMap.clear();
        strategyMap.forEach((k,v)->this.salaryPaymentStrategyMap.put(k,v));
    }

    public SalaryPaymentStrategy getResource(String strategyName){return salaryPaymentStrategyMap.get(strategyName);}
}
