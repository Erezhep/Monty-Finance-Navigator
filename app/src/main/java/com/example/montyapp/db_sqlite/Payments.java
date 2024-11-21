package com.example.montyapp.db_sqlite;

import androidx.core.text.util.LocalePreferences;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "payments",
        indices = {@Index(value = {"card_id"}), @Index(value = "type_payment_id")},
        foreignKeys = {
        @ForeignKey(entity = Card.class,
                    parentColumns = "cardID",
                    childColumns = "card_id",
                    onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = TypePayments.class,
                    parentColumns = "typePaymentID",
                    childColumns = "type_payment_id",
                    onDelete = ForeignKey.CASCADE)
        })
public class Payments {

    @PrimaryKey(autoGenerate = true)
    private int paymentID;

    @ColumnInfo(name = "payment_summa")
    private double paymentSumma;

    @ColumnInfo(name = "payment_date")
    private String paymentDate;

    @ColumnInfo(name = "payment_time")
    private int paymentTime;

    @ColumnInfo(name = "card_id")
    private int cardID;

    @ColumnInfo(name = "type_payment_id")
    private int typePaymentID;

    //Setters
    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public void setPaymentSumma(double paymentSumma) {
        this.paymentSumma = paymentSumma;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setPaymentTime(int paymentTime) {
        this.paymentTime = paymentTime;
    }

    public void setCardID(int cardID) {
        this.cardID = cardID;
    }

    public void setTypePaymentID(int typePaymentID) {
        this.typePaymentID = typePaymentID;
    }

    // Getters
    public int getPaymentID() {
        return paymentID;
    }

    public double getPaymentSumma() {
        return paymentSumma;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public int getPaymentTime() {
        return paymentTime;
    }

    public int getCardID() {
        return cardID;
    }

    public int getTypePaymentID() {
        return typePaymentID;
    }
}
