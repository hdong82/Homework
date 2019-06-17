package com.leo.test.bookshelf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leo.test.bookshelf.databinding.ViewDeletableBookItemBinding;
import com.leo.test.bookshelf.interfaces.OnBookItemClicked;
import com.leo.test.bookshelf.model.Book;
import com.leo.test.bookshelf.view.BookDetailActivity;

import java.util.List;

public class DeletableBookAdapter extends RecyclerView.Adapter<DeletableBookAdapter.DeletableBookViewHolder>{
    private List<Book> bookList;
    private OnBookItemClicked listener;

    public DeletableBookAdapter(OnBookItemClicked listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public DeletableBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDeletableBookItemBinding binding = ViewDeletableBookItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new DeletableBookViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DeletableBookViewHolder holder, int position) {
        final Book book = bookList.get(position);
        holder.bind(book);
        holder.binding.riDeletableBookItem.setOnClickListener(v -> {
            Context context = v.getContext();
            context.startActivity(BookDetailActivity.newIntent(context, book));
        });
        holder.binding.tvDelete.setOnClickListener(v -> listener.onItemClicked(v, book));
    }

    @Override
    public int getItemCount() {
        return bookList == null ? 0 : bookList.size();
    }

    public void setItems(List<Book> bookList) {
        this.bookList = bookList;
        notifyDataSetChanged();
    }

    static class DeletableBookViewHolder extends RecyclerView.ViewHolder {
        ViewDeletableBookItemBinding binding;
        DeletableBookViewHolder(@NonNull ViewDeletableBookItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Book book) {
            binding.setBook(book);
        }
    }
}
