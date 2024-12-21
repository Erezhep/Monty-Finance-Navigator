package com.example.montyapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.util.Patterns;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FirstPageActivity extends AppCompatActivity {

    SharedPreferences data;

    TextView textName;
    TextView textEmail;
    EditText edName;
    EditText edEmail;
    String name;
    String email;

    String hint_name;
    String hint_email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_page_activity);
        init();
        clickEditText();
    }

    private void init(){
        data = getSharedPreferences("AppData", MODE_PRIVATE);
        textName = findViewById(R.id.textName);
        textEmail = findViewById(R.id.textEmail);
        edName = findViewById(R.id.edName);
        edEmail = findViewById(R.id.edEmail);

        hint_name = getString(R.string.first_page_hint_name);
        hint_email = getString(R.string.first_page_hint_gmail);
    }

    private void clickEditText(){
        edName.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    edName.setBackgroundResource(R.drawable.edit_field_background);
                    textName.setVisibility(View.VISIBLE);
                    edName.setHint("");
                    edName.setTextSize(18);
                }else{
                    edName.setBackgroundResource(R.drawable.edit_file_deactivate_background);
                    textName.setVisibility(View.GONE);
                    edName.setHint(hint_name);
                    name = edName.getText().toString();
                    if (TextUtils.isEmpty(name)){
                        edName.setTextSize(12);
                    }else{
                        edName.setTextSize(18);
                        edName.setBackgroundResource(R.drawable.edit_field_background);
                        textName.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        edEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    edEmail.setBackgroundResource(R.drawable.edit_field_background);
                    textEmail.setVisibility(View.VISIBLE);
                    edEmail.setHint("");
                    edEmail.setTextSize(18);
                }else{
                    edEmail.setBackgroundResource(R.drawable.edit_file_deactivate_background);
                    textEmail.setVisibility(View.GONE);
                    edEmail.setHint(hint_email);
                    email = edEmail.getText().toString();
                    if (TextUtils.isEmpty(email)){
                        edEmail.setTextSize(12);
                    }else{
                        edEmail.setTextSize(18);
                        edEmail.setBackgroundResource(R.drawable.edit_field_background);
                        textEmail.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    public void onClickNextPage(View view){
        name = edName.getText().toString();
        email = edEmail.getText().toString().trim();
        if (TextUtils.isEmpty(name)){
            Toast.makeText(this, "Есіміңізді енгізіңіз", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Почтаңызды енгізіңіз", Toast.LENGTH_SHORT).show();
        }
        else{
            if (name.length() < 3){
                Toast.makeText(this, "Есімнің ұзындығы 3 символдан көп болуы керек", Toast.LENGTH_SHORT).show();
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                Toast.makeText(this, "Почта форматы қате, қайта енгізіңіз!", Toast.LENGTH_SHORT).show();
            }
            else{
                SharedPreferences.Editor editor = data.edit();
                editor.putString("username", name);
                editor.putString("email", email);
                editor.putBoolean("payment", false);
                editor.putInt("is_first", 1);
                editor.apply();
                Intent intent = new Intent(FirstPageActivity.this, HomePageActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

}
