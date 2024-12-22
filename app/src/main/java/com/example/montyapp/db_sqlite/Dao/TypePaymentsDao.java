package com.example.montyapp.db_sqlite.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.montyapp.db_sqlite.TypePayments;

import java.util.List;

@Dao
public interface TypePaymentsDao {

    @Insert
    void insert(TypePayments typePayments);

    @Query("SELECT * FROM type_payments WHERE icon_res_id = :icon_id")
    TypePayments getTypePaymentById(int icon_id);

    @Query("SELECT * FROM type_payments")
    List<TypePayments> getAllTypePayments();
}
