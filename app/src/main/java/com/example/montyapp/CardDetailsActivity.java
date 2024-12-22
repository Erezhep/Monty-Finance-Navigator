package com.example.montyapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Calendar;

public class CardDetailsActivity extends Activity {

    TextView card_title;
    TextView card_total;

    EditText edTotalCardAdd;
    EditText edDateCardAdd;
    EditText edInfoCardAdd;

    String cardNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_details_layout);
        init();

        Intent intent = getIntent();
        card_title.setText(intent.getStringExtra("cardTitle"));
        card_total.setText("₸" + String.valueOf(intent.getDoubleExtra("cardTotal", 0.0)));
        cardNumber = intent.getStringExtra("cardNumber");
        choose_calendar();
    }

    private void init(){
        card_title = findViewById(R.id.card_title);
        card_total = findViewById(R.id.card_total);

        edTotalCardAdd = findViewById(R.id.edTotalCardAdd);
        edDateCardAdd = findViewById(R.id.edDateCardAdd);
        edInfoCardAdd = findViewById(R.id.edInfoCardAdd);
    }

    public boolean isEmptyField(EditText edit){
        String text = edit.getText().toString().trim();
        if (TextUtils.isEmpty(text)){
            edit.setBackgroundResource(R.drawable.edit_file_error_background);
            return true;
        }
        else {
            edit.setBackgroundResource(R.drawable.edit_field_background);
            return false;
        }
    }

    private void choose_calendar(){
        EditText editTextDate = findViewById(R.id.edDateCardAdd);

        editTextDate.setOnClickListener(v -> {
            // Получаем текущую дату
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            // Создаем DatePickerDialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    CardDetailsActivity.this,
                    (view, year1, month1, dayOfMonth) -> {
                        // Устанавливаем выбранную дату в EditText
                        String selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                        editTextDate.setText(selectedDate);
                    },
                    year, month, day);

            // Показываем диалог
            datePickerDialog.show();
        });
    }

    public void onClickBack(View view){
        onBackPressed();
    }

    public void onClickAddTotalToCard(View view){
        boolean isEmptyTotal = isEmptyField(edTotalCardAdd);
        boolean isEmptyDate = isEmptyField(edDateCardAdd);
        if (!isEmptyTotal && !isEmptyDate){
            Toast.makeText(this, "Succesfully!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }
}
