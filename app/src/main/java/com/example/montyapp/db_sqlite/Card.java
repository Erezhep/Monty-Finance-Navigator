package com.example.montyapp.db_sqlite;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(tableName = "card", indices = {@Index(value = {"card_title"}, unique = true)})
public class Card {

    @PrimaryKey(autoGenerate = true)
    private int cardID;

    @ColumnInfo(name = "card_title")
    private String cardTitle;

    @ColumnInfo(name = "card_number")
    private String cardNumber;

    @ColumnInfo(name = "card_period")
    private String cardPeriod;

    @ColumnInfo(name = "card_total")
    private double cardTotal;

    // Setters
    public void setCardID(int cardID) { this.cardID = cardID; }

    public void setCardTitle(String cardTitle) {
        this.cardTitle = cardTitle;
    }

    public void setCardPeriod(String cardPeriod) { this.cardPeriod = cardPeriod; }

    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public void setCardTotal(double cardTotal) {
        this.cardTotal = cardTotal;
    }

    // Getters
    public int getCardID() {
        return cardID;
    }

    public String getCardTitle() {
        return cardTitle;
    }

    public String getCardNumber() { return cardNumber; }

    public String getCardPeriod() { return cardPeriod; }

    public double getCardTotal() {
        return cardTotal;
    }
}
