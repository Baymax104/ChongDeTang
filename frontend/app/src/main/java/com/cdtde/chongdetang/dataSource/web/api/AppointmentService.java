package com.cdtde.chongdetang.dataSource.web.api;

import com.cdtde.chongdetang.entity.Appointment;
import com.cdtde.chongdetang.entity.Result;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * @Description
 * @Author John
 * @email
 * @Date 2023/2/8 22:19
 * @Version 1
 */
public interface AppointmentService {

    @GET("/api/user/appointment")
    Single<Result<List<Appointment>>> getAllAppointment(@Header("Authorization") String token);

    @POST("/api/user/appointment")
    Single<Result<Object>> addAppointment(@Header("Authorization") String token,
                                          @Body Appointment appointment);
}
