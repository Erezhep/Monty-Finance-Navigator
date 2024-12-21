package com.example.montyapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class AddExpenceActivity extends AppCompatActivity {

    String paymentName;
    TextView namePayType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_expence);

        init();
        namePayType.setText(paymentName);
        choose_calendar();
        dropDownSpinner();

    }

    private void dropDownSpinner(){
        Spinner spinner = findViewById(R.id.spinnerCards);

        // Данные для Spinner
        List<String> spinnerData = Arrays.asList("Таңдаңыз", "Kaspi Bank", "Halyk Bank", "Jusan Bank", "ForteBank");

        // Адаптер с пользовательскими макетами
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_item, // Макет для основного элемента
                spinnerData
        );
        adapter.setDropDownViewResource(R.layout.spinner_item_dropdown); // Макет для выпадающих элементов

        spinner.setAdapter(adapter);
        spinner.setSelection(0);
    }

    private void choose_calendar(){
        EditText editTextDate = findViewById(R.id.editTextDate);

        editTextDate.setOnClickListener(v -> {
            // Получаем текущую дату
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            // Создаем DatePickerDialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    AddExpenceActivity.this,
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

    private void init(){
        namePayType = findViewById(R.id.namePayType);
        paymentName = getIntent().getStringExtra("payment_name");
    }

    public void onClickBack(View view){
        onBackPressed();
    }
}
