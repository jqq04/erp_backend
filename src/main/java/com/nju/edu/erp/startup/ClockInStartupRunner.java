package com.nju.edu.erp.startup;

import com.nju.edu.erp.service.ClockInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 *  系统启动时，自动执行
 *  打卡系统的启动器
 */
@Component
public class ClockInStartupRunner implements CommandLineRunner {

    private final ClockInService clockInService;

    @Autowired
    public ClockInStartupRunner(ClockInService clockInService) {
        this.clockInService = clockInService;
    }

    @Override
    public void run(String... args){
        clockInService.makeDefaultRecordOnStartup();
    }
}
