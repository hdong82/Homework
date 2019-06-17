package com.leo.test.bookshelf.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.leo.test.bookshelf.databinding.ViewBookItemBinding;
import com.leo.test.bookshelf.model.Book;

public class SearchAdapter extends PagedListAdapter<Book, BookAdapter.BookViewHolder> {
    public SearchAdapter() {
        super(diff);
    }

    @NonNull
    @Override
    public BookAdapter.BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookAdapter.BookViewHolder(ViewBookItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.BookViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    private static DiffUtil.ItemCallback<Book> diff = new DiffUtil.ItemCallback<Book>() {
        @Override
        public boolean areItemsTheSame(@NonNull Book oldItem, @NonNull Book newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Book oldItem, @NonNull Book newItem) {
            return areItemsTheSame(oldItem, newItem);
        }
    };
}
