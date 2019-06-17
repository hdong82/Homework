package com.leo.test.bookshelf.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseAndroidViewModel extends AndroidViewModel {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    DialogProvider provider;

    public BaseAndroidViewModel(@NonNull Application application, DialogProvider provider) {
        super(application);
        this.compositeDisposable = compositeDisposable;
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
