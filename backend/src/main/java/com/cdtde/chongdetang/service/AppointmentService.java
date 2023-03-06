package com.cdtde.chongdetang.service;

import com.cdtde.chongdetang.pojo.Appointment;
import com.cdtde.chongdetang.pojo.ResponseResult;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/8 22:14
 * @Version 1
 */
public interface AppointmentService {
    ResponseResult<List<Appointment>> getAllAppointment();

    ResponseResult<Object> addAppointment(Appointment appointment);

    ResponseResult<Object> changeStatus(String id,String status);

    // 获取审核列表
    ResponseResult<List<Appointment>> getAppointmentCheckList(String filter);

}
