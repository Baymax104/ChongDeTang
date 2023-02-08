package com.cdtde.chongdetang.controller;

import com.cdtde.chongdetang.pojo.Appointment;
import com.cdtde.chongdetang.pojo.ResponseResult;
import com.cdtde.chongdetang.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/8 22:13
 * @Version 1
 */
@RestController
@RequestMapping("/api/user/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    public ResponseResult<List<Appointment>> getAllAppointment() {
        return appointmentService.getAllAppointment();
    }

    @PostMapping
    public ResponseResult<Object> addAppointment(@RequestBody Appointment appointment) {
        return appointmentService.addAppointment(appointment);
    }
}
