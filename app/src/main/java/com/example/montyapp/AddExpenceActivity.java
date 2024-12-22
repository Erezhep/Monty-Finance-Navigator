package com.example.montyapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.montyapp.db_sqlite.AppDatabase;
import com.example.montyapp.db_sqlite.Card;
import com.example.montyapp.db_sqlite.Dao.CardDao;
import com.example.montyapp.db_sqlite.Dao.PaymentsDao;
import com.example.montyapp.db_sqlite.Payments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddExpenceActivity extends AppCompatActivity {

    String paymentName;
    TextView namePayType;
    EditText edName;
    EditText edSumma;
    EditText editTextDate;
    Spinner spinnerCards;
    EditText edInfo;

    int type_payment_id;
    int count = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_expence);

        init();
        namePayType.setText(paymentName);
        type_payment_id = getIntent().getIntExtra("payment_id", 0);
        choose_calendar();
        getCardsToSpinner();

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

    public void getCardsToSpinner(){
        ExecutorService exec = Executors.newSingleThreadExecutor();
        exec.execute(() -> {
            try{
                AppDatabase db = AppDatabase.getDatabase(this);
                CardDao cardDao = db.cardDao();

                List<Card> cards = cardDao.getAllCards();
                ArrayList<String> cardNames = new ArrayList<>();
                cardNames.add("Картаны таңдаңыз");
                for (Card card: cards){
                    cardNames.add(card.getCardTitle());
                }
                runOnUiThread(() -> {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, cardNames);
                    count = adapter.getCount();
                    adapter.setDropDownViewResource(R.layout.spinner_item);
                    spinnerCards.setAdapter(adapter);
                });
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                exec.shutdown();
            }
        }
        );
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
        if (count == 1){
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
            String name = edName.getText().toString().trim();
            Double summa = Double.parseDouble(edSumma.getText().toString().trim());
            String date = editTextDate.getText().toString().trim();
            String spin = spinnerCards.getSelectedItem().toString();
            String info = edInfo.getText().toString();

            ExecutorService exec = Executors.newSingleThreadExecutor();
            exec.execute(() -> {
                try{
                    AppDatabase db = AppDatabase.getDatabase(this);
                    CardDao cardDao = db.cardDao();
                    PaymentsDao paymentsDao = db.paymentsDao();

                    Card card = cardDao.getIdToTitle(spin);
                    if (card.getCardTotal() < summa){
                        runOnUiThread(() -> {
                            Toast.makeText(this, R.string.less_sum, Toast.LENGTH_SHORT).show();
                        });
                    }else{
                        try {
                            Double endTotal = card.getCardTotal() - summa;
                            cardDao.subtractAmountToCard(endTotal, card.getCardNumber());
                            Payments new_payment = new Payments();
                            new_payment.setPaymentTitle(name);
                            new_payment.setPaymentSumma(summa);
                            new_payment.setPaymentDate(date);
                            new_payment.setPaymentInfo(info);
                            new_payment.setCardID(card.getCardID());
                            new_payment.setTypePaymentID(type_payment_id);

                            paymentsDao.insert(new_payment);

                            runOnUiThread(() -> {
                                Toast.makeText(this, R.string.successfull_payment, Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            });
                        }
                        catch (Exception e){
                            runOnUiThread(() -> {
                                Log.e("AddExpenceActivity", "Error while changing data in database", e);
                                Toast.makeText(this, "Error while changing data in database", Toast.LENGTH_SHORT).show();
                            });
                        }
                    }
                }
                catch (Exception e){
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Database Error -> AddExpenceActivity.java", Toast.LENGTH_SHORT).show();
                    });
                }
                finally {
                    exec.shutdown();
                }
            });
        }
    }
}
