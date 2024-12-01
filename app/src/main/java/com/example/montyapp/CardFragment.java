package com.example.montyapp;

import android.app.AlertDialog;
import android.os.Bundle;

import com.example.montyapp.db_sqlite.AppDatabase;
import com.example.montyapp.db_sqlite.Card;
import com.example.montyapp.db_sqlite.Dao.CardDao;
import com.example.montyapp.db_sqlite.Dao.PaymentsDao;
import com.example.montyapp.db_sqlite.Dao.TypePaymentsDao;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CardFragment newInstance(String param1, String param2) {
        CardFragment fragment = new CardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_card, container, false);
        Button button_add = rootView.findViewById(R.id.button_add_card);

        // Найдите кнопку и установите слушатель
        rootView.findViewById(R.id.button_add_card).setOnClickListener(v -> showCustomDialog());

        return rootView;
    }

    private void showCustomDialog() {
        // Используйте LayoutInflater для загрузки пользовательского макета
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.card_alert_dialog, null);

        // Найдите Spinner и EditText в пользовательском макете
        EditText editText = dialogView.findViewById(R.id.edit_text_input);
        EditText editText2 = dialogView.findViewById(R.id.edit_text_input_2);



        // Создайте и настройте AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Ваш выбор")
                .setView(dialogView) // Установите пользовательский макет
                .setPositiveButton("ОК", (dialog, which) -> {
                    // Получите данные из EditText и Spinner

                    String card = editText2.getText().toString();
                    String summa = editText.getText().toString();

                    if (!TextUtils.isEmpty(card)){
                        new Thread(() -> {
                            AppDatabase db = AppDatabase.getDatabase(requireContext());
                            CardDao cardDao = db.cardDao();
                            int res_is_card = cardDao.checkIfCardExists(card);
                            requireActivity().runOnUiThread(() -> {
                                if (res_is_card > 0) {
                                    Toast.makeText(getContext(), "Card with this title already exists.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "Card HAVE", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }).start();
                    }else{
                        Toast.makeText(getContext(), "Field is empty", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Отмена", (dialog, which) -> {
                    // Закрытие диалога
                    dialog.dismiss();
                })
                .create()
                .show();
    }
}