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

    @Query("SELECT * FROM type_payments WHERE typePaymentID = :id")
    TypePayments getTypePaymentById(int id);

    @Query("SELECT * FROM type_payments")
    List<TypePayments> getAllTypePayments();
}
