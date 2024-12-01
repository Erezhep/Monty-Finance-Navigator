package com.example.montyapp.db_sqlite;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.montyapp.db_sqlite.Dao.CardDao;
import com.example.montyapp.db_sqlite.Dao.TypePaymentsDao;
import com.example.montyapp.db_sqlite.Dao.PaymentsDao;
@Database(entities = {Card.class, TypePayments.class, Payments.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase{

    // Методы для доступа к DAOs
    public abstract CardDao cardDao();
    public abstract TypePaymentsDao typePaymentsDao();
    public abstract PaymentsDao paymentsDao();

    public static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (AppDatabase.class) {
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "monty").fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
