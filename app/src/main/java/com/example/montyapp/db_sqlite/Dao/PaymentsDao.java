package com.example.montyapp.db_sqlite.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.montyapp.db_sqlite.Payments;

import java.util.List;

@Dao
public interface PaymentsDao {

    @Insert
    void insert(Payments payments);

    @Query("SELECT * FROM payments WHERE paymentID = :id")
    Payments getPaymentById(int id);

    @Query("SELECT * FROM payments WHERE card_id = :cardId")
    List<Payments> getPaymentsByCardId(int cardId);
}
