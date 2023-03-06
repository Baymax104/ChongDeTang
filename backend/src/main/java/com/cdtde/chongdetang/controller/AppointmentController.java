package com.cdtde.chongdetang.controller;

import com.cdtde.chongdetang.pojo.Appointment;
import com.cdtde.chongdetang.pojo.ResponseResult;
import com.cdtde.chongdetang.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/checklist")
    public ResponseResult<List<Appointment>> getAppointmentCheckList(@RequestParam String filter){
        return appointmentService.getAppointmentCheckList(filter);
    }


    @PostMapping
    public ResponseResult<Object> addAppointment(@RequestBody Appointment appointment) {
        return appointmentService.addAppointment(appointment);
    }

    @PostMapping("/status")
    public ResponseResult<Object> changeStatus(@RequestBody Map<String, String> map){
        String id = map.get("id");
        String status = map.get("status");
        return appointmentService.changeStatus(id,status);
    }

}
