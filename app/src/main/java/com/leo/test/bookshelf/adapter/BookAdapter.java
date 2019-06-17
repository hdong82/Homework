package com.leo.test.bookshelf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leo.test.bookshelf.databinding.ViewBookItemBinding;
import com.leo.test.bookshelf.model.Book;
import com.leo.test.bookshelf.view.BookDetailActivity;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder>{
    private List<Book> bookList;

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookViewHolder(ViewBookItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.bind(book);
    }

    @Override
    public int getItemCount() {
        return bookList == null ? 0 : bookList.size();
    }

    public void setItems(List<Book> bookList) {
        this.bookList = bookList;
        notifyDataSetChanged();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        ViewBookItemBinding binding;
        BookViewHolder(@NonNull ViewBookItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Book book) {
            binding.setBook(book);
            binding.riBookItem.setOnClickListener(v -> {
                Context context = v.getContext();
                context.startActivity(BookDetailActivity.newIntent(context, book));
            });
        }
    }
}
