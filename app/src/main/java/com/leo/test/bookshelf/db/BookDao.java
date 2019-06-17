package com.leo.test.bookshelf.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.leo.test.bookshelf.model.Book;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface BookDao {

    @Insert(onConflict = REPLACE)
    void insert(Book book);

    @Update
    void update(Book book);

    @Delete
    void delete(Book book);

    @Query("select * from book order by createdAt desc")
    LiveData<List<Book>> getAllHistories();

    @Query("select * from book where isBookMark = 1 order by createdAt desc")
    LiveData<List<Book>> getAllBookMark();

    @Query("select * from book where isbn13 = :isbn13")
    Book findByIsbn13(String isbn13);

    @Query("update book set isBookMark = :isBookMark, memo = :memo where isbn13 = :isbn13")
    void updateBookMark(String isbn13, boolean isBookMark, String memo);
}
