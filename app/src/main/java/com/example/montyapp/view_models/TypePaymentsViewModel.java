package com.example.montyapp.view_models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.montyapp.db_sqlite.AppDatabase;
import com.example.montyapp.db_sqlite.Dao.TypePaymentsDao;
import com.example.montyapp.db_sqlite.TypePayments;

import java.util.List;

public class TypePaymentsViewModel extends AndroidViewModel {

    private final TypePaymentsDao typePaymentsDao;
    private final LiveData<List<TypePayments>> allPayments;

    public TypePaymentsViewModel(Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        typePaymentsDao = db.typePaymentsDao();
        allPayments = (LiveData<List<TypePayments>>) typePaymentsDao.getAllTypePayments();  // Получаем все типы платежей
    }

    public LiveData<List<TypePayments>> getAllPayments() {
        return allPayments;
    }
}
