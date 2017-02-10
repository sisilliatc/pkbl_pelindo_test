package com.happytumiteam.surveypkbl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FormData extends AppCompatActivity {

    private Button btn_Simpan, btn_Ambil_Lokasi, btn_Ambil_Gambar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_data);

        btn_Simpan = (Button) findViewById(R.id.btn_simpan);
        btn_Simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FormData.this, "Data Tersimpan", Toast.LENGTH_LONG).show();
            }
        });

        btn_Ambil_Lokasi = (Button) findViewById(R.id.btn_ambil_lokasi);
        btn_Ambil_Lokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AmbilLokasi = new Intent(getApplicationContext(), AmbilLokasi.class);
                startActivity(AmbilLokasi);
            }
        });

        btn_Ambil_Gambar = (Button) findViewById(R.id.btn_ambil_gambar);
        btn_Ambil_Gambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AmbilGambar = new Intent(getApplicationContext(), AmbilGambar.class);
                        startActivity(AmbilGambar);
            }
        });
    }
}
