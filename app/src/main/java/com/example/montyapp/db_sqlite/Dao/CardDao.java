package com.example.montyapp.db_sqlite.Dao;

import androidx.room.Insert;
import androidx.room.Dao;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.montyapp.db_sqlite.Card;

import java.util.List;

@Dao
public interface CardDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Card card);

    @Query("SELECT COUNT(*) FROM card WHERE card_number = :number")
    int checkIfCardExists(String number);

    @Query("UPDATE card SET card_total = card_total + :amount WHERE card_number = :cardNumber")
    void addFundsToCard(Double amount, String cardNumber);


    @Query("SELECT * FROM card WHERE cardID = :id")
    Card getCardByID(int id);

    @Query("SELECT * FROM card")
    List<Card> getAllCards();

}
