package com.example.montyapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.montyapp.db_sqlite.AppDatabase;
import com.example.montyapp.db_sqlite.Card;
import com.example.montyapp.db_sqlite.Dao.CardDao;
import com.example.montyapp.db_sqlite.Dao.PaymentsDao;
import com.example.montyapp.db_sqlite.Dao.TypePaymentsDao;
import com.example.montyapp.db_sqlite.Payments;
import com.example.montyapp.db_sqlite.TypePayments;

import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CardDetailsActivity extends Activity {

    TextView card_title;
    TextView card_total;

    EditText edTotalCardAdd;
    EditText edDateCardAdd;
    EditText edInfoCardAdd;

    String cardNumber;
    Button button2;

    String cardTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_details_layout);
        init();

        Intent intent = getIntent();
        card_title.setText(intent.getStringExtra("cardTitle"));
        card_total.setText(String.format("₸ %,1.2f", intent.getDoubleExtra("cardTotal", 0.0)));
        cardNumber = intent.getStringExtra("cardNumber");
        cardTitle = getIntent().getStringExtra("cardTitle");

        String c_name = getIntent().getStringExtra("cardTitle").toString();
        if (c_name.equals("------")){
            // button2.setEnabled(false);
            button2.setOnClickListener(v -> {
                Toast.makeText(this, R.string.error_card, Toast.LENGTH_SHORT).show();
            });
        }
        choose_calendar();
    }

    private void init(){
        card_title = findViewById(R.id.card_title);
        card_total = findViewById(R.id.card_total);

        edTotalCardAdd = findViewById(R.id.edTotalCardAdd);
        edDateCardAdd = findViewById(R.id.edDateCardAdd);
        edInfoCardAdd = findViewById(R.id.edInfoCardAdd);
        button2 = findViewById(R.id.button2);
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

    public void onDeleteCard(View view){
        ExecutorService exec = Executors.newSingleThreadExecutor();
        exec.execute(() -> {
            try{
                AppDatabase db = AppDatabase.getDatabase(this);
                CardDao cardDao = db.cardDao();

                cardDao.deleteByTitle(cardTitle);
                runOnUiThread(() -> {
                    Toast.makeText(this, R.string.successfull_delete, Toast.LENGTH_SHORT).show();
                    onBackPressed();
                });
            }catch (Exception e){
                runOnUiThread(() -> {
                    Log.e("Delete Database Error", e.getMessage());
                    Toast.makeText(this, "Error while delete card", Toast.LENGTH_SHORT).show();
                });
            }finally{
                exec.shutdown();
            }
        });
    }

    public void onClickAddTotalToCard(View view){
        boolean isEmptyTotal = isEmptyField(edTotalCardAdd);
        boolean isEmptyDate = isEmptyField(edDateCardAdd);
        if (!isEmptyTotal && !isEmptyDate){
            Double summa = Double.parseDouble(edTotalCardAdd.getText().toString());
            String date = edDateCardAdd.getText().toString();
            String info = edInfoCardAdd.getText().toString().trim();
            ExecutorService exec = Executors.newSingleThreadExecutor();
            exec.execute(() -> {
                try{
                    AppDatabase db = AppDatabase.getDatabase(this);
                    CardDao cardDao = db.cardDao();
                    TypePaymentsDao typePaymentsDao = db.typePaymentsDao();
                    PaymentsDao paymentsDao = db.paymentsDao();

                    Card card = cardDao.getIdToTitle(cardTitle);
                    if (card != null){
                        Double card_summa = card.getCardTotal();
                        Double res_summa = summa + card_summa;
                        cardDao.addFundsToCard(res_summa, card.getCardNumber());

                        TypePayments type = typePaymentsDao.getTypePaymentById(R.drawable.payment_bank);
                        Payments payment = new Payments();
                        payment.setPaymentTitle(card.getCardTitle());
                        payment.setPaymentSumma(summa);
                        payment.setPaymentDate(date);
                        payment.setPaymentInfo(info);
                        payment.setCardID(card.getCardID());
                        payment.setTypePaymentID(type.getTypePaymentID());
                        payment.setIncome(true);
                        paymentsDao.insert(payment);

                        runOnUiThread(() -> {
                            Toast.makeText(this, R.string.successfull_add_total_to_card, Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        });

                    }
                }
                catch (Exception e){
                    runOnUiThread(() -> {
                        Log.e("Delete Database Error", e.getMessage());
                        Toast.makeText(this, "Error while add summa card", Toast.LENGTH_SHORT).show();
                    });
                }
                finally{
                    exec.shutdown();
                }
            });
        }else{
            Toast.makeText(this, R.string.please_write, Toast.LENGTH_SHORT).show();
        }
    }
}
