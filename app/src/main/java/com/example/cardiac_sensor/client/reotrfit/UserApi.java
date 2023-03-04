package com.example.cardiac_sensor.client.reotrfit;

import com.example.cardiac_sensor.client.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserApi {
    @GET("/usario/Verificarmovil")
    Call<List<User>> getAllEmployees();

    @POST("/usuario/Verificarmovil/")
    Call<Boolean> verifimovil(@Query("usmovil") String usmovil, @Query("cnmovil") String cnmovil);

}
