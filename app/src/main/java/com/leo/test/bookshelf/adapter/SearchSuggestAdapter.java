package com.leo.test.bookshelf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.leo.test.bookshelf.R;
import com.leo.test.bookshelf.databinding.ViewBookItemBinding;
import com.leo.test.bookshelf.interfaces.OnSuggestionSelected;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SearchSuggestAdapter extends ArrayAdapter<String> implements Filterable {

    private final List<String> histories;
    private final OnSuggestionSelected listener;

    public SearchSuggestAdapter(Context context, List<String> histories, OnSuggestionSelected onSelectedListener) {
        super(context, 0, histories);
        this.histories = histories;
        this.listener = onSelectedListener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View layout = convertView;
        if (convertView == null) {
            layout = LayoutInflater.from(getContext()).inflate(R.layout.view_suggestion_textview, parent, false);
        }
        String history = histories.get(position);
        TextView tvText = layout.findViewById(R.id.text);
        tvText.setText(history);
        layout.setOnClickListener(v -> listener.onSelected(v, position, history));

        return layout;
    }

    @NotNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults result = new FilterResults();
                result.values = histories;
                result.count = histories.size();
                return result;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results.count > 0) {
                    notifyDataSetChanged();
                }
            }
        };
    }
}
