package com.leo.test.bookshelf.interfaces;

import android.view.View;

import com.leo.test.bookshelf.model.Book;

public interface OnBookItemClicked {
    void onItemClicked(View view, Book book);
}
