package com.example.montyapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.montyapp.R;
import com.example.montyapp.adapter.PaymentAdapter;
import com.example.montyapp.db_sqlite.AppDatabase;
import com.example.montyapp.db_sqlite.Dao.PaymentsDao;
import com.example.montyapp.db_sqlite.Payments;
import com.example.montyapp.helper.PaymentWithIcon;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatisticFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatisticFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StatisticFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatisticFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatisticFragment newInstance(String param1, String param2) {
        StatisticFragment fragment = new StatisticFragment();
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

    RecyclerView recyclerView;
    TextView textsee;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_statistic, container, false);
        recyclerView = rootView.findViewById(R.id.recyclerHistoryView);
        textsee = rootView.findViewById(R.id.textsee);
        create_recycle();

        return rootView;
    }

    private void create_recycle(){
        ExecutorService exec = Executors.newSingleThreadExecutor();
        exec.execute(() -> {
            AppDatabase db = AppDatabase.getDatabase(requireActivity());
            PaymentsDao paymentsDao = db.paymentsDao();

            List<PaymentWithIcon> paymentList = paymentsDao.getPaymentsWithIcons();

            requireActivity().runOnUiThread(() -> {
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                PaymentAdapter adapter = new PaymentAdapter(paymentList);
                if (adapter.getItemCount() == 0){
                    textsee.setVisibility(View.VISIBLE);
                }else{
                    textsee.setVisibility(View.GONE);
                }
                recyclerView.setAdapter(adapter);
            });
            exec.shutdown();
        });
    }
}