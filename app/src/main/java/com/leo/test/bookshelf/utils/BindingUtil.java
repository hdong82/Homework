package com.leo.test.bookshelf.utils;

import android.text.TextUtils;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.leo.test.bookshelf.adapter.BookAdapter;
import com.leo.test.bookshelf.adapter.DeletableBookAdapter;
import com.leo.test.bookshelf.model.Book;

import java.util.List;

public class BindingUtil {

    @BindingAdapter("bind:url")
    public static void loadImage(ImageView imageView, String url) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(imageView.getContext()).load(url).into(imageView);
        }
    }

    @BindingAdapter("bind:books")
    public static void bindItem(RecyclerView recyclerView, List<Book> books) {
        BookAdapter adapter =(BookAdapter)recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setItems(books);
        }
    }

    @BindingAdapter("bind:deletable_books")
    public static void bindDeletableBooks(RecyclerView recyclerView, List<Book> books) {
        DeletableBookAdapter adapter =(DeletableBookAdapter)recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setItems(books);
        }
    }

    @BindingAdapter("bind:rating")
    public static void setRating(SimpleRatingBar ratingBar, String rating) {
        if (rating != null) {
            ratingBar.setRating(Float.valueOf(rating));
        }
    }
}
