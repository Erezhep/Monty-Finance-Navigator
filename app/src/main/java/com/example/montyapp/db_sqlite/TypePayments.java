package com.example.montyapp.db_sqlite;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "type_payments", indices = {@Index(value={"type_payment_name"}, unique = true)})
public class TypePayments {

    @PrimaryKey(autoGenerate = true)
    private int typePaymentID;

    @ColumnInfo(name = "type_payment_name")
    private String typePaymentName;

    @ColumnInfo(name = "icon_res_id")
    private int iconResId;

    // Setters
    public void setTypePaymentID(int typePaymentID) {
        this.typePaymentID = typePaymentID;
    }

    public void setTypePaymentName(String typePaymentName) { this.typePaymentName = typePaymentName; }

    public void setIconResId(int iconResId) { this.iconResId = iconResId; }

    // Getters
    public int getTypePaymentID() {
        return typePaymentID;
    }

    public String getTypePaymentName() { return typePaymentName; }

    public int getIconResId() { return iconResId; }
}
