package com.example.montyapp.db_sqlite.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.montyapp.db_sqlite.Payments;
import com.example.montyapp.helper.DailyPaymentStats;
import com.example.montyapp.helper.PaymentWithIcon;

import java.util.List;

@Dao
public interface PaymentsDao {

    @Insert
    void insert(Payments payments);

    @Query("SELECT * FROM payments WHERE paymentID = :id")
    Payments getPaymentById(int id);

    @Query("SELECT * FROM payments")
    List<Payments> getAllPayments();

    @Query("SELECT * FROM payments WHERE type_payment_id = :typePaymentId")
    List<Payments> getPaymentsBank(int typePaymentId);

    @Query("SELECT * FROM payments WHERE NOT type_payment_id = :typePaymentId")
    List<Payments> getPaymentsNotBank(int typePaymentId);

    @Query("SELECT payments.payment_title, payments.payment_date, payments.payment_summa, type_payments.icon_res_id " +
            "FROM payments " +
            "INNER JOIN type_payments ON payments.type_payment_id = type_payments.typePaymentID " +
            "LIMIT 50")
    List<PaymentWithIcon> getPaymentsWithIcons();

}
