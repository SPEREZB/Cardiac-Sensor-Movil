package com.example.cardiac_sensor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cardiac_sensor.client.reotrfit.Pulso;
import com.example.cardiac_sensor.client.reotrfit.PulsoApi;
import com.example.cardiac_sensor.client.reotrfit.RetrofitService;
import com.example.cardiac_sensor.client.reotrfit.UserApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VerPulso extends AppCompatActivity {
    private EditText et_pulso;
    private EditText et_fecha;
    private EditText et_riesgoInfarto;
    Pulso result= new Pulso();
    List lista= new ArrayList<>();
    TextView tv_pulso,tv_fecha,tv_riesgoInfarto;
    int cont=0;

    //Crear una instancia de Timer
    Timer timer = new Timer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_pulso);

        tv_pulso = findViewById(R.id.textView_pulso);
        tv_fecha = findViewById(R.id.textView_fecha);
        tv_riesgoInfarto = findViewById(R.id.textView_riesgo);

        et_pulso = (EditText) findViewById(R.id.editText_pulso);
        et_fecha = (EditText) findViewById(R.id.editText_fecha);
        et_riesgoInfarto = (EditText) findViewById(R.id.editText_riesgo);



      //Llamar al método cada segundo aquí

            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    try {
                        if(cont!=0) {
                            getDataMovil();
                        }
                        else {
                            cont++;
                        }
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

                        // aquí obtienes la respuesta
                        result = response.body();

                        if(result.getCantpulsaciones()<90)
                        {
                            lanzarNotificacion(89);
                        }
                        else if(result.getCantpulsaciones()>91 && result.getCantpulsaciones()<100)
                        {
                            lanzarNotificacion(100);
                        }else
                        {
                            //paro cardiaco
                            lanzarNotificacion(100);
                        }


                            et_pulso.setText(result.getCantpulsaciones().toString());
                            et_fecha.setText(result.getFechademedicion().toString());
                            et_riesgoInfarto.setText(result.getRiesgoDeInfarto().toString());



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


    public void lanzarNotificacion(Integer cant){
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        if(cant==89)
        {
            // Configuración de la notificación
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "default")
                    .setSmallIcon(R.drawable.alerta)
                    .setContentTitle("Notificación")
                    .setContentText("CUIDADO CON LA PRESION ARTERIAL")
                    .setAutoCancel(true);
            // Enviar la notificación
            notificationManager.notify(0, notificationBuilder.build());
        }
        else if(cant==100)
        {
            // Configuración de la notificación
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "default")
                    .setSmallIcon(R.drawable.alerta)
                    .setContentTitle("Notificación")
                    .setContentText("CUIDADO PELIGRO DE INFARTO")
                    .setAutoCancel(true);
            // Enviar la notificación
            notificationManager.notify(0, notificationBuilder.build());
        }

    }
}