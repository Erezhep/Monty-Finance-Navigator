package com.example.montyapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.montyapp.AddExpenceActivity;
import com.example.montyapp.R;
import com.example.montyapp.adapter.TypePaymentsAdapter;
import com.example.montyapp.db_sqlite.AppDatabase;
import com.example.montyapp.db_sqlite.Dao.TypePaymentsDao;
import com.example.montyapp.db_sqlite.TypePayments;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public HomeFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    SharedPreferences sharedPreferences;
    String user;
    Integer is_first;
    TextView username;
    TextView textWelcomeBack;
    private List<TypePayments> typePaymentsList = new ArrayList<>();

    private RecyclerView recyclerView;
    private TypePaymentsAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        sharedPreferences = getActivity().getSharedPreferences("AppData", Context.MODE_PRIVATE);
        user = sharedPreferences.getString("username", "default");
        is_first = sharedPreferences.getInt("is_first", 0);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        username = rootView.findViewById(R.id.username);
        textWelcomeBack = rootView.findViewById(R.id.textWelcomeBack);
        username.setText(user);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        String welcome_back = getString(R.string.welcome_back);
        String welcome_try = getString(R.string.welcome_try);

        if (is_first == 1){
            textWelcomeBack.setText(welcome_back);
            editor.putInt("is_first", 2);
            editor.apply();
        }else{
            textWelcomeBack.setText(welcome_try);
        }

        recyclerView = rootView.findViewById(R.id.recyclerViewTypePayments);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Инициализация адаптера с передачей listener для обработки кликов
        adapter = new TypePaymentsAdapter(typePaymentsList, this::onItemClick);
        recyclerView.setAdapter(adapter);

        loadPaymentsFromDatabase();

        return rootView;
    }

    private void loadPaymentsFromDatabase() {
        // Используем ExecutorService для работы с базой данных в фоновом потоке
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // Получаем все записи из базы данных
                List<TypePayments> allPayments = getAllPayments();

                List<TypePayments> filteredPayments = new ArrayList<>();
                for (TypePayments typePayments : allPayments) {
                    if (!typePayments.getTypePaymentName().equals("Банк")) {
                        filteredPayments.add(typePayments);  // Добавляем в новый список только те элементы, которые не "Банк"
                    }
                }

                // Обновляем список в основном потоке
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Обновляем данные адаптера
                        typePaymentsList.clear();
                        typePaymentsList.addAll(filteredPayments);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private List<TypePayments> getAllPayments() {
        // Список для хранения всех платежей
        List<TypePayments> allPayments = new ArrayList<>();

        // Открываем базу данных
        AppDatabase db = AppDatabase.getDatabase(getContext());
        TypePaymentsDao typePaymentsDao = db.typePaymentsDao();

        // Получаем все данные из базы данных
        allPayments = typePaymentsDao.getAllTypePayments(); // или ваш метод получения данных

        return allPayments;
    }

    private void onItemClick(TypePayments typePayments) {
        // Действие при клике по элементу
        // Toast.makeText(getContext(), "Clicked: " + typePayments.getTypePaymentName(), Toast.LENGTH_SHORT).show();

        // Создаем Intent для открытия нового Activity
        Intent intent = new Intent(getContext(), AddExpenceActivity.class);

        // Передаем данные через геттеры (например, имя типа платежа)
        intent.putExtra("payment_name", typePayments.getTypePaymentName());

        startActivity(intent);
    }


}