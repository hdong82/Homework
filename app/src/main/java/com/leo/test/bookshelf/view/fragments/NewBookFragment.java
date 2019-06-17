package com.leo.test.bookshelf.view.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.leo.test.bookshelf.R;
import com.leo.test.bookshelf.adapter.BookAdapter;
import com.leo.test.bookshelf.databinding.FragmentNewbookBinding;
import com.leo.test.bookshelf.vm.NewBookViewModel;
import com.leo.test.bookshelf.vm.factory.ViewModelFactory;

public class NewBookFragment extends BaseFragment {
    private FragmentNewbookBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_newbook, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BookAdapter adapter = new BookAdapter();
        binding.rvNewBookList.setAdapter(adapter);
        binding.rvNewBookList.setHasFixedSize(true);

        DividerItemDecoration decoration = new DividerItemDecoration(view.getContext(), LinearLayout.VERTICAL);
        Drawable divider = ContextCompat.getDrawable(view.getContext(), R.drawable.divider);
        decoration.setDrawable(divider);
        binding.rvNewBookList.addItemDecoration(decoration);

        NewBookViewModel newBookModel = ViewModelProviders.of(this, new ViewModelFactory(this)).get(NewBookViewModel.class);
        newBookModel.getNewBooks().observe(this, binding::setBookList);
    }
}
