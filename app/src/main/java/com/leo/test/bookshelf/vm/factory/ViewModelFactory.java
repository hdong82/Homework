package com.leo.test.bookshelf.vm.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.leo.test.bookshelf.App;
import com.leo.test.bookshelf.vm.BookDetailViewModel;
import com.leo.test.bookshelf.vm.BookMarkViewModel;
import com.leo.test.bookshelf.vm.DialogProvider;
import com.leo.test.bookshelf.vm.HistoryViewModel;
import com.leo.test.bookshelf.vm.NewBookViewModel;
import com.leo.test.bookshelf.vm.SearchViewModel;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final DialogProvider provider;
    public ViewModelFactory(DialogProvider provider) {
        this.provider = provider;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(NewBookViewModel.class)) {
            return (T) new NewBookViewModel(provider);
        } else if (modelClass.isAssignableFrom(SearchViewModel.class)) {
            return (T) new SearchViewModel(App.getInstance(), provider);
        } else if (modelClass.isAssignableFrom(BookMarkViewModel.class)) {
            return (T) new BookMarkViewModel(provider);
        } else if (modelClass.isAssignableFrom(BookDetailViewModel.class)) {
            return (T) new BookDetailViewModel(provider);
        } else if (modelClass.isAssignableFrom(HistoryViewModel.class)) {
            return (T) new HistoryViewModel(provider);
        } else {
            return super.create(modelClass);
        }
    }
}
