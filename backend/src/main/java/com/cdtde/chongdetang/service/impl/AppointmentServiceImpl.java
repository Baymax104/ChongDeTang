package com.cdtde.chongdetang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cdtde.chongdetang.mapper.AppointmentMapper;
import com.cdtde.chongdetang.pojo.Appointment;
import com.cdtde.chongdetang.pojo.ResponseResult;
import com.cdtde.chongdetang.service.AppointmentService;
import com.cdtde.chongdetang.service.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/8 22:15
 * @Version 1
 */
@Service
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Override
    public ResponseResult<List<Appointment>> getAllAppointment() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int id = loginUser.getUser().getId();

        QueryWrapper<Appointment> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", id);
        List<Appointment> appointments = appointmentMapper.selectList(wrapper);

        ResponseResult<List<Appointment>> result = new ResponseResult<>();
        result.setStatus("success").setData(appointments);
        return result;
    }

    @Override
    public ResponseResult<Object> addAppointment(Appointment appointment) {
        ResponseResult<Object> result = new ResponseResult<>();
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int id = loginUser.getUser().getId();
        appointment.setUserId(id);

        int insert = appointmentMapper.insert(appointment);
        if (insert != 1) {
            throw new RuntimeException("添加预约失败");
        }

        result.setStatus("success");
        return result;
    }
}
