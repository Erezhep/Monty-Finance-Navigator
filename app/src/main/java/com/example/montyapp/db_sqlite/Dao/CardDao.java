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

    @Query("SELECT COUNT(*) FROM card WHERE card_number = :number OR card_title = :name")
    int checkIfCardExists(String number, String name);

    @Query("SELECT * FROM card WHERE card_title = :title")
    Card getIdToTitle(String title);

    @Query("DELETE FROM card WHERE card_title = :title")
    void deleteByTitle(String title);

    @Query("UPDATE card SET card_total = :amount WHERE card_number = :cardNumber")
    void addFundsToCard(Double amount, String cardNumber);

    @Query("UPDATE card SET card_total = :amount WHERE card_number = :cardNumber")
    void subtractAmountToCard(Double amount, String cardNumber);

    @Query("SELECT * FROM card WHERE cardID = :id")
    Card getCardByID(int id);

    @Query("SELECT * FROM card")
    List<Card> getAllCards();

}
