package com.example.cardiac_sensor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {

    private Button btnPulse;
    private Button btnChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.GREEN));

        btnPulse = (Button) findViewById(R.id.btnVerPulso);
        btnChart = (Button) findViewById(R.id.btnVerGraficos);

        btnPulse.setText("VER PULSO");
        btnChart.setText("VER GRAFICO");


        btnPulse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, VerPulso.class);
                startActivity(intent);
                finish();
        }

    });
        btnChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, VerImagen.class);
                startActivity(intent);
                finish();
            }

        });

}
}