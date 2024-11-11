package com.example.montyapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FirstPageActivity extends AppCompatActivity {

    SharedPreferences data;
    EditText edName;
    String name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_page_activity);
        init();
    }

    private void init(){
        data = getSharedPreferences("AppData", MODE_PRIVATE);
        edName = findViewById(R.id.edName);
    }

    public void onClickNextPage(View view){
        name = edName.getText().toString();
        if (TextUtils.isEmpty(name)){
            Toast.makeText(this, "Есіміңізді енгізіңіз", Toast.LENGTH_SHORT).show();
        }else{
            if (name.length() < 3){
                Toast.makeText(this, "Есімнің ұзындығы 3 символдан көп болуы керек", Toast.LENGTH_SHORT).show();
            }else{
                SharedPreferences.Editor editor = data.edit();
                editor.putString("username", name);
                editor.apply();
                Intent intent = new Intent(FirstPageActivity.this, HomePageActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

}
