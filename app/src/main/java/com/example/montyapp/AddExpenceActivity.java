package com.example.montyapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class AddExpenceActivity extends AppCompatActivity {

    List<String> spinnerData = new ArrayList<>();

    String paymentName;
    TextView namePayType;
    EditText edName;
    EditText edSumma;
    EditText editTextDate;
    Spinner spinnerCards;
    EditText edInfo;

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
        spinnerData.add("Таңдаңыз");
        spinnerData.add("Kaspi Bank");
        spinnerData.add("Halyk Bank");
        spinnerData.add("ForteBank");

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

        edName = findViewById(R.id.edName);
        edSumma = findViewById(R.id.edSumma);
        editTextDate = findViewById(R.id.editTextDate);
        spinnerCards = findViewById(R.id.spinnerCards);
        edInfo = findViewById(R.id.edInfo);
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

    public void onClickBack(View view){
        onBackPressed();
    }

    public void onClickAddExpensive(View view){
        boolean isEmptyName = isEmptyField(edName);
        boolean isEmptySumma = isEmptyField(edSumma);
        boolean isEmptyDate = isEmptyField(editTextDate);
        boolean isEmptySpinner = false;
        if (spinnerData.size() < 2){
            Toast.makeText(this, R.string.spinner_error, Toast.LENGTH_SHORT).show();
            isEmptySpinner = true;
        }
        if (spinnerCards.getSelectedItemPosition() == 0){
            Toast.makeText(this, R.string.spinner_choose, Toast.LENGTH_SHORT).show();
            isEmptySpinner = true;
        }
        if (!isEmptySpinner){
            spinnerCards.setBackgroundResource(R.drawable.edit_field_background);
        }
        if (!isEmptyName && !isEmptySumma && !isEmptyDate && !isEmptySpinner){
            Toast.makeText(this, "Succesfully!", Toast.LENGTH_SHORT).show();
        }
    }
}
