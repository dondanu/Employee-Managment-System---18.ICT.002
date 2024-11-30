package com.example.emsapp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import java.util.List;
import com.example.emsapp.Attendance;  // Correct import path


public interface ApiInterface {

    //Employee CRUD
    @POST("employees")
    Call<Void> addEmployee(@Body Employee employee);

    @GET("employees")
    Call<List<Employee>> getEmployees();

    @PUT("employees/{id}")
    Call<Void> updateEmployee(@Path("id") String id, @Body Employee employee);

    @DELETE("employees/{id}")
    Call<Void> deleteEmployee(@Path("id") String id);


    //Attendance
    @POST("/api/attendance")
    Call<Attendance> addAttendance(@Body Attendance attendance);

    @GET("/api/attendance")
    Call<List<Attendance>> getAllAttendance();

}
