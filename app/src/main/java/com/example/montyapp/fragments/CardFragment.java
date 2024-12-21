package com.example.montyapp.fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import com.example.montyapp.R;
import com.example.montyapp.adapter.CardAdapter;
import com.example.montyapp.db_sqlite.AppDatabase;
import com.example.montyapp.db_sqlite.Card;
import com.example.montyapp.db_sqlite.Dao.CardDao;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private List<Card> cardList;

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

        // Найдите кнопку и установите слушатель
        // rootView.findViewById(R.id.button_add_card).setOnClickListener(v -> showCustomDialog());

        recyclerView = rootView.findViewById(R.id.recyclerView);

        loadCardData();

        cardAdapter = new CardAdapter(cardList);
        recyclerView.setAdapter(cardAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        return rootView;
    }

    private void loadCardData() {
        // Здесь тебе нужно загрузить данные из базы данных (например, используя Room)
        // Это пример, ты можешь использовать Repository и ViewModel для загрузки данных
        cardList = new ArrayList<>();
        for (int i = 1; i < 8; i++){
            Card card = new Card();
            card.setCardTitle("Card " + i);
            card.setCardNumber("1234  ****  ****  5432");
            card.setCardPeriod("12/2" + (i + 5));
            card.setCardTotal(520.22 * i);
            cardList.add(card);
        }
    }

}