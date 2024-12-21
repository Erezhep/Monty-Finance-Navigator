package com.example.montyapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.montyapp.R;
import com.example.montyapp.db_sqlite.Card;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private List<Card> cardList;

    // Конструктор адаптера
    public CardAdapter(List<Card> cardList) {
        this.cardList = cardList;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false);
        return new CardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Card card = cardList.get(position);
        holder.cardTitle.setText(card.getCardTitle());
        holder.cardNumber.setText(card.getCardNumber());
        holder.cardPeriod.setText(card.getCardPeriod());
        holder.cardTotal.setText("₸" + String.valueOf(card.getCardTotal()));
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView cardTitle, cardNumber, cardPeriod, cardTotal;

        public CardViewHolder(View view) {
            super(view);
            cardTitle = view.findViewById(R.id.cardTitle);
            cardNumber = view.findViewById(R.id.cardNumber);
            cardPeriod = view.findViewById(R.id.cardPeriod);
            cardTotal = view.findViewById(R.id.cardTotal);
        }
    }
}
