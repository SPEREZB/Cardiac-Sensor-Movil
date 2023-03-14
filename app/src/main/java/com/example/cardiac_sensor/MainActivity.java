package com.example.cardiac_sensor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cardiac_sensor.client.reotrfit.RetrofitService;
import com.example.cardiac_sensor.client.reotrfit.UserApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    EditText etUsername, etPassword;
    Button btnLogin;
    boolean result;
    int val=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.GREEN));


        etUsername = (EditText) findViewById(R.id.usname);
        etPassword = (EditText) findViewById(R.id.passw);
        btnLogin = (Button) findViewById(R.id.button);

      //Establece el color de fondo
        btnLogin.setBackgroundColor(Color.WHITE);
        etUsername.setTextColor(Color.BLACK);
        etPassword.setTextColor(Color.BLACK);
        btnLogin.setTextColor(Color.BLACK);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                // aqui se debe hacer el proceso de autenticacion
                // para verificar si el usuario existe o no
                try {
                    authenticateUser(username, password);

                        if (result==true) {
                            // Autenticación exitosa
                            // Abre la siguiente actividad
                            //Inicializar una nueva actividad


                            Intent intent = new Intent(MainActivity.this, Menu.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(MainActivity.this, "BIENN", Toast.LENGTH_SHORT).show();

                        } else {
                            // Autenticación fallida
                            Toast.makeText(MainActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                        }


                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private boolean authenticateUser(String username, String password) throws IOException {
        RetrofitService retrofitService = new RetrofitService();
        UserApi usApi = retrofitService.getRetrofit().create(UserApi.class);
        Call<Boolean> call = usApi.verifimovil(username, password);
            call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    // aquí obtienes la respuesta
                    result = response.body();
                    val=1;
                } else {
                    // aquí obtienes un código de error
                    int errorCode = response.code();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                // aquí obtienes una excepción
                t.printStackTrace();
            }

        });

         return true;

    }


}