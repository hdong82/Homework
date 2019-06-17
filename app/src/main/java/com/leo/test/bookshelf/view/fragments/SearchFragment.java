package com.leo.test.bookshelf.view.fragments;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.leo.test.bookshelf.R;
import com.leo.test.bookshelf.adapter.SearchAdapter;
import com.leo.test.bookshelf.adapter.SearchSuggestAdapter;
import com.leo.test.bookshelf.databinding.FragmentSearchBinding;
import com.leo.test.bookshelf.utils.DialogUtils;
import com.leo.test.bookshelf.vm.SearchViewModel;
import com.leo.test.bookshelf.vm.factory.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class SearchFragment extends BaseFragment {
    private FragmentSearchBinding binding;
    private SearchAdapter adapter;
    private SearchViewModel searchViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new SearchAdapter();
        binding.rvSearchList.setAdapter(adapter);
        binding.rvSearchList.setHasFixedSize(true);

        DividerItemDecoration decoration = new DividerItemDecoration(view.getContext(), LinearLayout.VERTICAL);
        Drawable divider = ContextCompat.getDrawable(view.getContext(), R.drawable.divider);
        decoration.setDrawable(divider);
        binding.rvSearchList.addItemDecoration(decoration);

        searchViewModel = ViewModelProviders.of(this, new ViewModelFactory(this)).get(SearchViewModel.class);
        searchViewModel.getPagedListLiveData().observe(this, books -> {
            Timber.d("__ search complete reulst size : %s", books.size());
            if (books.isEmpty()) DialogUtils.showToast(getContext(), R.string.text_message_no_search_result);
            adapter.submitList(books);
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);

        SearchView.SearchAutoComplete searchAutoComplete = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchAutoComplete.setHintTextColor(Color.WHITE);
        searchAutoComplete.setTextColor(Color.WHITE);
        setHistoryAdapter(searchAutoComplete);
        searchAutoComplete.setThreshold(1);
        searchAutoComplete.setDropDownHeight(1000);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Timber.d("__onQueryTextSubmit : %s", query);
                if (query != null) {
                    searchViewModel.search(query);
                    setHistoryAdapter(searchAutoComplete);
                    searchView.clearFocus();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void setHistoryAdapter(SearchView.SearchAutoComplete searchAutoComplete) {
        final SearchSuggestAdapter suggestionAdapter = new SearchSuggestAdapter(getContext(), searchViewModel.getSearchHistory(), (view, position, value) -> {
            searchAutoComplete.setText(value);
            searchAutoComplete.setSelection(value.length());
            searchAutoComplete.dismissDropDown();
        });
        searchAutoComplete.setAdapter(suggestionAdapter);
    }
}
