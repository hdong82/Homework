package com.leo.test.bookshelf.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.leo.test.bookshelf.R;
import com.leo.test.bookshelf.consts.StringSet;
import com.leo.test.bookshelf.databinding.ActivityBookDetailBinding;
import com.leo.test.bookshelf.model.Book;
import com.leo.test.bookshelf.utils.DialogUtils;
import com.leo.test.bookshelf.utils.KeyboardUtil;
import com.leo.test.bookshelf.vm.BookDetailViewModel;
import com.leo.test.bookshelf.vm.factory.ViewModelFactory;

import timber.log.Timber;

public class BookDetailActivity extends BaseActivity {

    //    private boolean isMarked = false;
    private ActivityBookDetailBinding binding;

    public static Intent newIntent(Context context, Book book) {
        Intent intent = new Intent(context, BookDetailActivity.class);
        intent.putExtra(StringSet.KEY_BOOK, book);
        return intent;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_book_detail);

        Book book = getIntent().getParcelableExtra(StringSet.KEY_BOOK);
        BookDetailViewModel bookModel = ViewModelProviders.of(this, new ViewModelFactory(this)).get(BookDetailViewModel.class);
        binding.setBook(book);
        binding.ratingBar.setOnTouchListener((v, event) -> true);
        bookModel.getBook(book.getIsbn13()).observe(this, book1 -> binding.setBook(book1));

        binding.btLink.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(book.getUrl()));
            startActivity(intent);
        });

        binding.ivBookmark.setOnClickListener(v -> {
            Timber.d("++ bookMark Clicked");
            boolean isMarked = binding.getBook().isBookMark();
            DialogUtils.showConfirmDialog(this, isMarked ? R.string.text_confirm_msg_remove_bookmark : R.string.text_confirm_msg_add_bookmark,
                    (dialog, which) -> {
                        KeyboardUtil.hideSoftKeyboard(BookDetailActivity.this);
                        Editable memo = binding.etMemo.getText();
                        bookModel.updateBookMark(binding.getBook(), memo == null ? "" : memo.toString()).observe(BookDetailActivity.this, book12 -> {
                            showToast(getString(book12.isBookMark() ? R.string.text_complete_add_bookmark : R.string.text_complete_remove_bookmark));
                            binding.ivBookmark.setImageResource(book12.isBookMark() ? R.drawable.ico_bookmark_sel : R.drawable.ico_bookmark_nor);
                        });
                    });
        });
    }

    @BindingAdapter("bind:bookmark")
    public static void setBookMark(ImageView imageView, boolean isMarked) {
        imageView.setImageResource(isMarked ? R.drawable.ico_bookmark_sel : R.drawable.ico_bookmark_nor);
    }
}

