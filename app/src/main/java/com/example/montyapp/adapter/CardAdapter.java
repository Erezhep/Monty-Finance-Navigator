package com.example.montyapp.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.montyapp.R;
import com.example.montyapp.db_sqlite.Card;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private List<Card> cardList;
    private OnCardClickListener onCardClickListener;

    // Конструктор адаптера
    public CardAdapter(List<Card> cardList, OnCardClickListener listener) {
        this.cardList = (cardList != null) ? cardList : new ArrayList<>();;
        this.onCardClickListener = listener;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false);
        return new CardViewHolder(itemView);
    }

    public String formatCardNumber(String cardNumber){
        return cardNumber.substring(0, 4) + "  " + "****  ****" + "  " + cardNumber.substring(12, 16);
    }

    public String formatDate(String date) {
        String[] parts = date.split("/");
        return String.format("%02d/%02d", Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }

    public String formatNumber(double number){
        DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
        return decimalFormat.format(number);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Card card = cardList.get(position);
        holder.cardTitle.setText(card.getCardTitle());
        holder.cardNumber.setText(formatCardNumber(card.getCardNumber()));
        holder.cardPeriod.setText(formatDate(card.getCardPeriod()));
        holder.cardTotal.setText(String.format("₸ %,1.2f", card.getCardTotal()));

        holder.itemView.setOnClickListener(v -> {
            if (onCardClickListener != null) {
                onCardClickListener.onCardClick(card); // Передаем выбранную карточку
            }
        });
    }

    public void updateData(List<Card> newCardList) {
        if (newCardList == null) {
            newCardList = new ArrayList<>();
        }
        else if(newCardList.size() == 0){
            Card def = new Card();
            def.setCardTitle("------");
            def.setCardNumber("****************");
            def.setCardPeriod("00/00");
            def.setCardTotal(0.0);
            newCardList.add(def);// Создаем пустой список, чтобы избежать проблем
        }
        this.cardList.clear();
        this.cardList.addAll(newCardList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public interface OnCardClickListener {
        void onCardClick(Card card);
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
