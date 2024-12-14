package com.example.montyapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.montyapp.db_sqlite.AppDatabase;
import com.example.montyapp.db_sqlite.Dao.TypePaymentsDao;
import com.example.montyapp.fragments.CardFragment;
import com.example.montyapp.fragments.HomeFragment;
import com.example.montyapp.fragments.ProfileFragment;
import com.example.montyapp.fragments.StatisticFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_activity);

        // Находим BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Устанавливаем слушатель для выбора пунктов меню
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        AppDatabase db = AppDatabase.getDatabase(this);


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
            String[] paymentTypes = {};
        });
    }
}
