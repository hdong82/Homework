package com.leo.test.bookshelf.vm;

import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseViewModel extends ViewModel {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    DialogProvider provider;

    public BaseViewModel(DialogProvider provider) {
        this.provider = provider;
    }

    void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @Override
    public void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }
}
