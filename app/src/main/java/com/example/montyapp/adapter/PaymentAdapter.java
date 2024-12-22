package com.example.montyapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.montyapp.R;
import com.example.montyapp.helper.PaymentWithIcon;

import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder> {

    private List<PaymentWithIcon> paymentList;

    public PaymentAdapter(List<PaymentWithIcon> paymentList) {
        this.paymentList = paymentList;
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_payment, parent, false);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {
        PaymentWithIcon payment = paymentList.get(position);
        holder.paymentName.setText(payment.getPaymentTitle());
        holder.paymentDate.setText(payment.getPaymentDate());
        if (payment.getIconResId() == R.drawable.payment_bank){
            holder.paymentSumma.setText(String.format("+ ₸%.2f", payment.getPaymentSumma()));
            holder.paymentSumma.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.income));
        }else{
            holder.paymentSumma.setText(String.format("- ₸%.2f", payment.getPaymentSumma()));
            holder.paymentSumma.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.expense));
        }

        holder.paymentImage.setImageResource(payment.getIconResId());
    }

    @Override
    public int getItemCount() {
        return paymentList.size();
    }

    public static class PaymentViewHolder extends RecyclerView.ViewHolder {

        TextView paymentName;
        TextView paymentDate;
        TextView paymentSumma;
        ImageView paymentImage;

        public PaymentViewHolder(@NonNull View itemView) {
            super(itemView);
            paymentName = itemView.findViewById(R.id.paymentName);
            paymentDate = itemView.findViewById(R.id.paymentDate);
            paymentSumma = itemView.findViewById(R.id.paymentSumma);
            paymentImage = itemView.findViewById(R.id.paymentImage);
        }
    }
}
