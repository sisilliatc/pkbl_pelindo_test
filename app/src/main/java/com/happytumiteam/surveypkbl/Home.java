package com.happytumiteam.surveypkbl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home);

        ImageView imgInfo = (ImageView) findViewById(R.id.btn_info);
                imgInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent info = new Intent(getApplicationContext(), Info.class);
                        startActivity(info);
                    }
                });
        ImageView imgDataMitra = (ImageView) findViewById(R.id.btn_datamitra);
        imgDataMitra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent formdata = new Intent(getApplicationContext(), FormData.class);
                startActivity(formdata);
            }
        });
        ImageView imgFormData = (ImageView) findViewById(R.id.btn_formdata);
        imgFormData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent datamitra = new Intent(getApplicationContext(), DataMitra.class);
                startActivity(datamitra);
            }
        });

    }
}
