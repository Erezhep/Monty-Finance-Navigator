package com.example.montyapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.montyapp.db_sqlite.AppDatabase;
import com.example.montyapp.db_sqlite.Card;
import com.example.montyapp.db_sqlite.Dao.CardDao;

import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddNewCardActivity extends AppCompatActivity {

    EditText edNameCard;
    EditText edNumberCard;
    EditText edDateCard;
    EditText edBalanceCard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_card_layout);

        init();
        choose_calendar();

    }

    private void init(){
        edNameCard = findViewById(R.id.edNameCard);
        edNumberCard = findViewById(R.id.edNumberCard);
        edDateCard = findViewById(R.id.edDateCard);
        edBalanceCard = findViewById(R.id.edBalanceCard);
    }

    private void choose_calendar(){
        EditText editTextDate = findViewById(R.id.edDateCard);

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

    private boolean checkField(String text, EditText edit){
        if (!TextUtils.isEmpty(text)){
            edit.setBackgroundResource(R.drawable.edit_field_background);
            return true;
        }else{
            edit.setBackgroundResource(R.drawable.edit_file_error_background);
            return false;
        }
    }

    private boolean add_card_to_bd(String name, String number, String date, Double balance){
        AppDatabase db = AppDatabase.getDatabase(this);
        CardDao cardDao = db.cardDao();

        int res = cardDao.checkIfCardExists(number, name);
        if (res != 0){
            return false;
        }else{
            Card card = new Card();
            card.setCardTitle(name);
            card.setCardNumber(number);
            card.setCardPeriod(date);
            card.setCardTotal(balance);
            cardDao.insert(card);
            return true;
        }
    }

    public void onClickAddNewCard(View view){
        String card_name = edNameCard.getText().toString();
        String card_number = edNumberCard.getText().toString();
        String card_date = edDateCard.getText().toString();
        String card_balance = edBalanceCard.getText().toString();

        boolean isName = checkField(card_name, edNameCard);
        boolean isNumber = checkField(card_number, edNumberCard);
        boolean isDate = checkField(card_date, edDateCard);
        boolean isBalance = checkField(card_balance, edBalanceCard);

        if (isName && isNumber && isDate && isBalance){
            if (card_number.length() != 16){
                Toast.makeText(this, R.string.card_number_error, Toast.LENGTH_SHORT).show();
            }
            else{
                Double balance = Double.parseDouble(card_balance);
                ExecutorService exec = Executors.newSingleThreadExecutor();
                exec.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            boolean bd_res = add_card_to_bd(card_name, card_number, card_date, balance);
                            if (bd_res){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(AddNewCardActivity.this, R.string.succesfully, Toast.LENGTH_SHORT).show();
                                        finish();
                                        onBackPressed();
                                    }
                                });
                            }else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(AddNewCardActivity.this, R.string.error_db, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }catch (Exception e){
                            runOnUiThread(() -> {
                                Toast.makeText(AddNewCardActivity.this, "Database error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                        }
                    }
                });
            }
        }
    }
}
