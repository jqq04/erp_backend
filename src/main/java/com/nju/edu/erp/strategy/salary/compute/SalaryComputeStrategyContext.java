package com.nju.edu.erp.strategy.salary.compute;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用以调度薪资计算策略的上下文/环境
 */
@Service
public class SalaryComputeStrategyContext {

    private final Map<String, SalaryComputeStrategy> salaryComputeStrategyMap = new ConcurrentHashMap<>();

    public SalaryComputeStrategyContext(Map<String, SalaryComputeStrategy> strategyMap){
        this.salaryComputeStrategyMap.clear();
        strategyMap.forEach((k,v)->this.salaryComputeStrategyMap.put(k,v));
    }

    public SalaryComputeStrategy getResource(String strategyName){
        return salaryComputeStrategyMap.get(strategyName);
    }
}
