package com.leo.test.bookshelf.interfaces;

import android.view.View;

import com.leo.test.bookshelf.model.Book;

public interface OnSuggestionSelected {
    void onSelected(View view, int position, String value);
}
