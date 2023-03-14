package com.example.cardiac_sensor.client.reotrfit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PulsoApi {

    @GET("/frecuenciacardiaca/getDataMovil")
    Call<Pulso> getDataMovil();
}
