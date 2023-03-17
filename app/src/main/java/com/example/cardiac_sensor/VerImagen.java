package com.example.cardiac_sensor;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.cardiac_sensor.client.reotrfit.Pulso;
import com.example.cardiac_sensor.client.reotrfit.PulsoApi;
import com.example.cardiac_sensor.client.reotrfit.RetrofitService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VerImagen extends AppCompatActivity {

    ImageView img;
    Pulso result= new Pulso();
    int cont=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_imagen);

         img= findViewById(R.id.imageView);

        //Llamar al método cada segundo aquí
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    getDataMovil();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 0, 1000);
    }

    private List getDataMovil() throws IOException {
        List lista= new ArrayList<>();
        RetrofitService retrofitService2 = new RetrofitService();
        PulsoApi plApi = retrofitService2.getRetrofit().create(PulsoApi.class);
        Call<Pulso> call = plApi.getDataMovil();
        call.enqueue(new Callback<Pulso>() {
            @Override
            public void onResponse(Call<Pulso> call, Response<Pulso> response) {
                if (response.isSuccessful()) {
                    if(cont!=0) {
                        // aquí obtienes la respuesta
                        result = response.body();
                        if(result.getCantpulsaciones()<90)
                        {
                            //presion arterial
                            img.setImageResource(R.drawable.caraamrilla);
                        }else if(result.getCantpulsaciones()>91 && result.getCantpulsaciones()<100)
                        {
                            //bien
                            img.setImageResource(R.drawable.caraverde);
                        }else
                        {
                            //paro cardiaco
                            img.setImageResource(R.drawable.cararoja);
                        }

                    }
                    else
                    {
                        cont++;
                    }

                } else {
                    // aquí obtienes un código de error
                    int errorCode = response.code();
                }
            }

            @Override
            public void onFailure(Call<Pulso> call, Throwable t) {
                // aquí obtienes una excepción
                t.printStackTrace();
            }

        });

        return lista;
    }
}