package com.leo.test.bookshelf.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.leo.test.bookshelf.model.Book;


@Database(entities = {Book.class}, version = 1, exportSchema = false)
public abstract class DB extends RoomDatabase {
    private static volatile DB INSTANCE;
    public abstract BookDao getBookDao();

    public static DB getDB(Context context) {
        if (INSTANCE == null) {
            synchronized (DB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DB.class, "bookshelf.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
