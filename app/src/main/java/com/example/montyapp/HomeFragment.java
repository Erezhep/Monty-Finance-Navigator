package com.example.montyapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.montyapp.templates.Item;
import com.example.montyapp.templates.ItemsHistory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
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
    TextView textView1;
    private List<Item> payments;
    private ListView listView;
    private ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        sharedPreferences = getActivity().getSharedPreferences("AppData", Context.MODE_PRIVATE);
        user = sharedPreferences.getString("username", "default");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        payments = new ArrayList<>();
        payments.add(new Item("Kaspi Bank", "+ 1500,00"));
        payments.add(new Item("Halyk Bank", "- 1200,50"));
        payments.add(new Item("Jusan Bank", "+ 3000,75"));
        payments.add(new Item("Bank #1", "1100.25"));
        payments.add(new Item("Bank #2", "1200.50"));
        payments.add(new Item("Bank #3", "1300.75"));
        payments.add(new Item("Bank #4", "1401.00"));
        payments.add(new Item("Bank #5", "1501.25"));
        payments.add(new Item("Bank #6", "1601.50"));
        payments.add(new Item("Bank #7", "1701.75"));
        payments.add(new Item("Bank #8", "1802.00"));
        payments.add(new Item("Bank #9", "1902.25"));
        payments.add(new Item("Bank #10", "2002.50"));
        payments.add(new Item("Bank #11", "2102.75"));
        payments.add(new Item("Bank #12", "2203.00"));
        payments.add(new Item("Bank #13", "2303.25"));
        payments.add(new Item("Bank #14", "2403.50"));
        payments.add(new Item("Bank #15", "2503.75"));

        listView = rootView.findViewById(R.id.listHistory);

        ItemsHistory adapter = new ItemsHistory(getContext(), payments);
        listView.setAdapter(adapter);


        // TextView text = rootView.findViewById(R.id.nameText1);
        // text.setText(user);

        Button buttonAdd = rootView.findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Button +", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }


}