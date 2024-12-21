package com.example.montyapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.montyapp.R;
import com.example.montyapp.db_sqlite.TypePayments;

import java.util.List;

public class TypePaymentsAdapter extends RecyclerView.Adapter<TypePaymentsAdapter.TypePaymentsViewHolder> {

    private List<TypePayments> typePaymentsList;
    private final OnItemClickListener listener;

    // Интерфейс для обработки кликов по элементам списка
    public interface OnItemClickListener {
        void onItemClick(TypePayments typePayments);
    }

    // Constructor
    public TypePaymentsAdapter(List<TypePayments> typePaymentsList, OnItemClickListener listener) {
        this.typePaymentsList = typePaymentsList;
        this.listener = listener;
    }

    // Метод для обновления списка данных
    public void setTypePayments(List<TypePayments> typePaymentsList) {
        this.typePaymentsList = typePaymentsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TypePaymentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Создаем новый View для элемента списка
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_type_payments, parent, false);
        return new TypePaymentsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TypePaymentsViewHolder holder, int position) {
        // Получаем объект TypePayments для текущей позиции
        TypePayments typePayments = typePaymentsList.get(position);

        // Привязываем данные к элементам UI
        holder.paymentNameTextView.setText(typePayments.getTypePaymentName());
        holder.iconImageView.setImageResource(typePayments.getIconResId());

        // Устанавливаем обработчик кликов для элемента
        holder.itemView.setOnClickListener(v -> listener.onItemClick(typePayments));
    }

    @Override
    public int getItemCount() {
        return typePaymentsList != null ? typePaymentsList.size() : 0;
    }

    // ViewHolder для элементов списка
    static class TypePaymentsViewHolder extends RecyclerView.ViewHolder {
        TextView paymentNameTextView;
        ImageView iconImageView;

        public TypePaymentsViewHolder(View itemView) {
            super(itemView);
            paymentNameTextView = itemView.findViewById(R.id.textType);
            iconImageView = itemView.findViewById(R.id.iconType);
        }
    }
}
