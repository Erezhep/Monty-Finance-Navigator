package com.example.montyapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddNewCardActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_card_layout);

        choose_calendar();
    }


    private void choose_calendar(){
        EditText editTextDate = findViewById(R.id.edNewCardDate);

        editTextDate.setOnClickListener(v -> {
            // Получаем текущую дату
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            // Создаем DatePickerDialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    AddNewCardActivity.this,
                    (view, year1, month1, dayOfMonth) -> {
                        // Устанавливаем выбранную дату в EditText
                        String selectedDate = dayOfMonth + "/" + (month1 + 1);
                        editTextDate.setText(selectedDate);
                    },
                    year, month, day);

            // Показываем диалог
            datePickerDialog.show();
        });
    }

    public void onClickBackAddNewCard(View view){
        onBackPressed();
    }

    public void onClickAddNewCard(View view){
        Toast.makeText(this, "All worked", Toast.LENGTH_SHORT).show();
    }
}
