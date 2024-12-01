package com.example.montyapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    SharedPreferences data;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();

        if (user.equals("default")){
            new Handler().postDelayed(() -> {
                Intent i = new Intent(MainActivity.this, FirstPageActivity.class);
                startActivity(i);
                finish();
            }, 1200);
        }else {
            new Handler().postDelayed(() -> {
                Intent i = new Intent(MainActivity.this, HomePageActivity.class);
                startActivity(i);
                finish();
            }, 1200);
        }
    }

    private void init(){
        data = getSharedPreferences("AppData", MODE_PRIVATE);
        user = data.getString("username", "default");
    }

}