package com.cdtde.chongdetang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cdtde.chongdetang.mapper.AppointmentMapper;
import com.cdtde.chongdetang.mapper.UserMapper;
import com.cdtde.chongdetang.pojo.Appointment;
import com.cdtde.chongdetang.pojo.ResponseResult;
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
            throw new RuntimeException("??????????????????");
        }

        result.setStatus("success");
        return result;
    }

    @Override
    public ResponseResult<Object> changeStatus(String id, String status) {
        ResponseResult<Object> result = new ResponseResult<>();

        QueryWrapper<Appointment> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        Appointment appointment = appointmentMapper.selectOne(wrapper);
        appointment.setStatus(status);
        int i = appointmentMapper.update(appointment, wrapper);
        if (i != 1) {
            throw new RuntimeException("????????????????????????");
        }
        result.setStatus("success");
        return result;
    }

    @Override
    public ResponseResult<List<Appointment>> getAppointmentCheckList(String filter) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int id = loginUser.getUser().getId();
        // ????????????, ?????????admin
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id)
                .eq("admin", "1");
        User user = userMapper.selectOne(wrapper);
        ResponseResult<List<Appointment>> result = new ResponseResult<>();
        // ???admin
        if (user != null) {
            List<Appointment> appointments;
            // ?????????????????????????????????
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
            result.setStatus("error").setMessage("?????????????????????");
        }

        return result;
    }
}
