package com.cdtde.chongdetang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cdtde.chongdetang.mapper.AppointmentMapper;
import com.cdtde.chongdetang.mapper.UserMapper;
import com.cdtde.chongdetang.pojo.Appointment;
import com.cdtde.chongdetang.pojo.Result;
import com.cdtde.chongdetang.pojo.User;
import com.cdtde.chongdetang.service.AppointmentService;
import com.cdtde.chongdetang.service.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
    @Autowired
    private UserMapper userMapper;

    @Override
    public Result<List<Appointment>> getAllAppointment() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int id = loginUser.getUser().getId();

        QueryWrapper<Appointment> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", id);
        List<Appointment> appointments = appointmentMapper.selectList(wrapper);

        Result<List<Appointment>> result = new Result<>();
        result.setStatus("success").setData(appointments);
        return result;
    }

    @Override
    public Result<Object> addAppointment(Appointment appointment) {
        Result<Object> result = new Result<>();
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

    @Override
    public Result<Object> changeStatus(String id, String status) {
        Result<Object> result = new Result<>();

        QueryWrapper<Appointment> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        Appointment appointment = appointmentMapper.selectOne(wrapper);
        appointment.setStatus(status);
        int i = appointmentMapper.update(appointment, wrapper);
        if (i != 1) {
            throw new RuntimeException("预约状态更新失败");
        }
        result.setStatus("success");
        return result;
    }

    @Override
    public Result<List<Appointment>> getAppointmentCheckList(String filter) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int id = loginUser.getUser().getId();
        // 查用户表, 是不是admin
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id)
                .ne("admin", "0");
        User user = userMapper.selectOne(wrapper);
        Result<List<Appointment>> result = new Result<>();
        // 是admin
        if (user != null) {
            List<Appointment> appointments;
            // 有请求参数，按类型查找
            if (!Objects.equals(filter, "")) {
                QueryWrapper<Appointment> appointmentQueryWrapper = new QueryWrapper<>();
                appointmentQueryWrapper.eq("status", filter);
                appointments = appointmentMapper.selectList(appointmentQueryWrapper);
            }
            // select *
            else {
                appointments = appointmentMapper.selectList(null);
            }
            result.setStatus("success").setData(appointments);
        } else {
            result.setStatus("error").setMessage("没有管理员权限");
        }

        return result;
    }
}
