package com.cdtde.chongdetang.service;

import com.cdtde.chongdetang.pojo.Appointment;
import com.cdtde.chongdetang.pojo.Result;

import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/8 22:14
 * @Version 1
 */
public interface AppointmentService {
    Result<List<Appointment>> getAllAppointment();

    Result<Object> addAppointment(Appointment appointment);

    Result<Object> changeStatus(String id, String status);

    // 获取审核列表
    Result<List<Appointment>> getAppointmentCheckList(String filter);

}
