package com.example.montyapp.helper;

import androidx.room.ColumnInfo;

public class PaymentWithIcon {

    @ColumnInfo(name = "payment_title")
    private String paymentTitle;

    @ColumnInfo(name = "payment_date")
    private String paymentDate;

    @ColumnInfo(name = "payment_summa")
    private double paymentSumma;

    @ColumnInfo(name = "icon_res_id")
    private int iconResId;

    // Getters and Setters
    public String getPaymentTitle() {
        return paymentTitle;
    }

    public void setPaymentTitle(String paymentTitle) {
        this.paymentTitle = paymentTitle;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getPaymentSumma() {
        return paymentSumma;
    }

    public void setPaymentSumma(double paymentSumma) {
        this.paymentSumma = paymentSumma;
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }
}
