package com.example.montyapp.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.montyapp.R;

import java.util.Arrays;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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

    TextView profileName;
    TextView profileEmail;
    View rootView;
    FrameLayout button_lang;
    FrameLayout frame_choose_lang;
    Spinner spinner_lang;
    SharedPreferences data;
    String lang;
    Button saveLang;
    SharedPreferences.Editor editor;
    boolean is_click = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        SharedPreferences data = getActivity().getSharedPreferences("AppData", MODE_PRIVATE);
        String user = data.getString("username", "default");
        String email = data.getString("email", "default");
        init();
        profileName.setText(user);
        profileEmail.setText(email);

        button_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!is_click){
                    frame_choose_lang.setVisibility(View.VISIBLE);
                    is_click = true;
                }else{
                    frame_choose_lang.setVisibility(View.GONE);
                    is_click = false;
                }
            }
        });

        String[] languages = rootView.getResources().getStringArray(R.array.lang);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, languages);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner_lang.setAdapter(adapter);
        lang = data.getString("language", "Қазақша");
        int index = Arrays.asList(languages).indexOf(lang);
        spinner_lang.setSelection(index);

        saveLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Получаем выбранный язык
                String selectedLang = spinner_lang.getSelectedItem().toString();

                // Сохраняем язык в SharedPreferences
                SharedPreferences.Editor editor = data.edit();
                editor.putString("language", selectedLang);
                editor.apply();

                String lg = "kk";
                switch (selectedLang){
                    case "Қазақша":
                        lg = "kk";
                        break;
                    case "Русский":
                        lg = "ru";
                        break;
                    case "English":
                        lg = "en";
                }

                // Изменяем язык
                setLocale(lg);

                // Закрываем список языков
                frame_choose_lang.setVisibility(View.GONE);
                is_click = false;
            }
        });

        return rootView;
    }

      public void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

        // Обновляем конфигурацию
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        // Перезапускаем активность
        getActivity().recreate();
    }

    private void init(){
        profileName = rootView.findViewById(R.id.textNameProfile);
        profileEmail = rootView.findViewById(R.id.textGmailProfile);
        button_lang = rootView.findViewById(R.id.button_lang);
        frame_choose_lang = rootView.findViewById(R.id.frame_choose_lang);
        spinner_lang = rootView.findViewById(R.id.spinner_lang);
        saveLang = rootView.findViewById(R.id.saveLang);

        data = getActivity().getSharedPreferences("AppData", MODE_PRIVATE);
        editor = data.edit();
    }

}