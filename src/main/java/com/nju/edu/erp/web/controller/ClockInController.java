package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.service.ClockInService;
import com.nju.edu.erp.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/clock-in")
public class ClockInController {

    private final ClockInService clockInService;

    @Autowired
    public ClockInController(ClockInService clockInService) {
        this.clockInService = clockInService;
    }

    /**
     * 用户打卡
     * @param userVO 用户个人信息
     * if code = "H0001" then msg = “找不到用户对应的员工信息”
     * if code = "H0002" then msg = “该用户今日已完成打卡”
     */
    @Authorized(roles = {Role.ADMIN,Role.GM,Role.HR,Role.INVENTORY_MANAGER,Role.SALE_MANAGER,Role.FINANCIAL_STAFF,Role.SALE_STAFF})
    @PostMapping("/clock-in")
    public Response clockIn(UserVO userVO){
        clockInService.clockIn(userVO);
        return Response.buildSuccess();
    }

    /**
     * 获取用户个人的全部打卡记录
     * @param userVO 用户个人信息
     * @return 用户个人全部打卡记录 List<ClockInRecord>
     * if code = "H0001" then msg = “找不到用户对应的员工信息”
     */
    @Authorized(roles = {Role.ADMIN,Role.GM,Role.HR,Role.INVENTORY_MANAGER,Role.SALE_MANAGER,Role.FINANCIAL_STAFF,Role.SALE_STAFF})
    @GetMapping("/queryAll-personal")
    public Response getAllPersonalRecord(UserVO userVO){
        return Response.buildSuccess(clockInService.getAllPersonalRecord(userVO));
    }

    /**
     * 获取用户的当日打卡记录
     * @param userVO 用户个人信息
     * @return 用户当日打卡记录 ClockInRecord
     * if code = "H0001" then msg = “找不到用户对应的员工信息”
     */
    @Authorized(roles = {Role.ADMIN,Role.GM,Role.HR,Role.INVENTORY_MANAGER,Role.SALE_MANAGER,Role.FINANCIAL_STAFF,Role.SALE_STAFF})
    @GetMapping("/get-personal")
    public Response getPersonalRecordOnCurrentDay(UserVO userVO){
        return Response.buildSuccess(clockInService.getPersonalRecordOnCurrentDay(userVO));
    }

    /**
     * 获取用户一个时间段内的打卡记录
     * @param userVO 用户个人信息
     * @param beginDateStr 开始日期 "yyyy-MM-dd"
     * @param endDateStr 结束日期 "yyyy-MM-dd"
     * @return 用户该时间段内的打卡记录
     *
     * if code = "H0001" then msg = “找不到用户对应的员工信息”
     * 如果日期格式错误，或开始时间大于结束时间，response中的result均为null
     */
    @Authorized(roles = {Role.ADMIN,Role.GM,Role.HR,Role.INVENTORY_MANAGER,Role.SALE_MANAGER,Role.FINANCIAL_STAFF,Role.SALE_STAFF})
    @GetMapping("/getByDate-personal")
    public Response getPersonalRecordByDate(UserVO userVO, @RequestParam String beginDateStr, @RequestParam String endDateStr){
        return Response.buildSuccess(clockInService.getPersonalRecordByDate(userVO,beginDateStr,endDateStr));
    }
    /**
     * 获取所有员工的全部打卡记录
     * @return 所有员工全部打卡记录 List<ClockInRecord>
     */
    @GetMapping("/queryAll")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.HR})
    public Response getAllRecord(){
        return Response.buildSuccess(clockInService.getAllRecord());
    }

    /**
     * 获取所有员工的全部打卡记录
     * @return 所有员工全部打卡记录 List<ClockInRecord>
     */
    @GetMapping("/get")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.HR})
    public Response getAllRecordOnCurrentDay(){
        return Response.buildSuccess(clockInService.getAllRecordOnCurrentDay());
    }

}
