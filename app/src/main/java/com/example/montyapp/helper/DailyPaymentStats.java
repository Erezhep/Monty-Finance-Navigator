package com.example.montyapp.helper;

public class DailyPaymentStats {
    private String paymentDate;
    private double income;
    private double expense;

    public DailyPaymentStats(String paymentDate, double income, double expense) {
        this.paymentDate = paymentDate;
        this.income = income;
        this.expense = expense;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public double getIncome() {
        return income;
    }

    public double getExpense() {
        return expense;
    }
}