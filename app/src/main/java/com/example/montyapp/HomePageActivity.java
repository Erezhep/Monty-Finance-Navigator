package com.example.montyapp;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.montyapp.db_sqlite.AppDatabase;
import com.example.montyapp.db_sqlite.Dao.TypePaymentsDao;
import com.example.montyapp.db_sqlite.TypePayments;
import com.example.montyapp.fragments.CardFragment;
import com.example.montyapp.fragments.HomeFragment;
import com.example.montyapp.fragments.ProfileFragment;
import com.example.montyapp.fragments.StatisticFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomePageActivity extends AppCompatActivity {
    SharedPreferences data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_activity);

        // Находим BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Устанавливаем слушатель для выбора пунктов меню
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        AppDatabase db = AppDatabase.getDatabase(this);

        data = getSharedPreferences("AppData", MODE_PRIVATE);
        Boolean added = data.getBoolean("payment", false);

        if (!added){
            add_payment_types_in_db();
        }


        // Загружаем фрагмент по умолчанию
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment()) // Фрагмент по умолчанию
                    .commit();
        }
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        Fragment selectedFragment = null;
        int itemId = item.getItemId();
        if (itemId == R.id.home) {
            selectedFragment = new HomeFragment();
        } else if (itemId == R.id.card) {
            selectedFragment = new CardFragment();
        } else if (itemId == R.id.statistic) {
            selectedFragment = new StatisticFragment();
        } else if (itemId == R.id.profile) {
            selectedFragment = new ProfileFragment();
        }

        // Если выбран фрагмент, заменяем текущий фрагмент
        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit();
            return true;
        }
        return false;
    };

    private void add_payment_types_in_db(){
        // Использование ExecutorService для работы с базой данных в фоновом потоке
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            AppDatabase db = AppDatabase.getDatabase(this);
            TypePaymentsDao typePaymentsDao = db.typePaymentsDao();
            if (typePaymentsDao.getAllTypePayments().isEmpty()) {
                String[] paymentTypes = getResources().getStringArray(R.array.type_payments);
                Integer[] iconIDs = {
                        R.drawable.payment_home,
                        R.drawable.payment_car,
                        R.drawable.payment_internet,
                        R.drawable.payment_education,
                        R.drawable.payment_apartment,
                        R.drawable.payment_help,
                        R.drawable.payment_shopping,
                        R.drawable.payment_game,
                        R.drawable.payment_other
                };

                for (int i = 0; i < iconIDs.length; i++) {
                    TypePayments type = new TypePayments();
                    type.setTypePaymentName(paymentTypes[i]);
                    type.setIconResId(iconIDs[i]);
                    typePaymentsDao.insert(type);  // Вставляем только если пусто
                }

                // Обновляем флаг, что типы платежей были добавлены
                SharedPreferences.Editor editor = data.edit();
                editor.putBoolean("payment", true);
                editor.apply();
            }
        });
    }
}
