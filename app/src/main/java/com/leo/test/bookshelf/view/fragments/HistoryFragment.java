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
import com.leo.test.bookshelf.adapter.DeletableBookAdapter;
import com.leo.test.bookshelf.databinding.FragmentHistoryBinding;
import com.leo.test.bookshelf.interfaces.OnBookItemClicked;
import com.leo.test.bookshelf.model.Book;
import com.leo.test.bookshelf.utils.DialogUtils;
import com.leo.test.bookshelf.view.BookDetailActivity;
import com.leo.test.bookshelf.vm.HistoryViewModel;
import com.leo.test.bookshelf.vm.factory.ViewModelFactory;

import timber.log.Timber;

public class HistoryFragment extends BaseFragment implements OnBookItemClicked {
    private FragmentHistoryBinding binding;
    private HistoryViewModel bookModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DeletableBookAdapter adapter = new DeletableBookAdapter(this);
        binding.rvHistory.setAdapter(adapter);
        binding.rvHistory.setHasFixedSize(true);

        DividerItemDecoration decoration = new DividerItemDecoration(view.getContext(), LinearLayout.VERTICAL);
        Drawable divider = ContextCompat.getDrawable(view.getContext(), R.drawable.divider);
        decoration.setDrawable(divider);
        binding.rvHistory.addItemDecoration(decoration);

        bookModel = ViewModelProviders.of(this, new ViewModelFactory(this)).get(HistoryViewModel.class);
        bookModel.getHistoryList().observe(this, books -> {
            Timber.i("__history changed(%s)", books.size());
            binding.setHistoryList(books);
        });
    }

    @Override
    public void onItemClicked(View view, Book book) {
        switch (view.getId()) {
            case R.id.riDeletableBookItem:
                startActivity(BookDetailActivity.newIntent(getContext(), book));
                break;
            case R.id.tvDelete:
                DialogUtils.showConfirmDialog(getContext(), R.string.text_confirm_msg_remove_history, (dialog, which) -> bookModel.removeHistory(book));
                break;
        }
    }
}
