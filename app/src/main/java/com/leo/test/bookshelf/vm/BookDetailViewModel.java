package com.leo.test.bookshelf.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.leo.test.bookshelf.model.Book;
import com.leo.test.bookshelf.repository.BookRepository;
import com.leo.test.bookshelf.widget.WaitingDialog;

import java.util.List;

import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class BookDetailViewModel extends BaseViewModel {

    private final BookRepository bookRepo = BookRepository.getInstance();
    public BookDetailViewModel(DialogProvider provider) {
        super(provider);
    }
    final MutableLiveData<Book> liveDataBook = new MutableLiveData<>();
    final MutableLiveData<Book> result = new MutableLiveData<>();

    public LiveData<Book> getBook(String isnb13) {

        addDisposable(bookRepo.getBookDetail(isnb13)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> provider.showWaitingDialog())
                .doFinally(WaitingDialog::dismiss)
                .map(book -> {
                    Book cache = bookRepo.get(book.getIsbn13());
                    if (cache != null) {
                        book.setHistory(true);
                        book.setBookMark(cache.isBookMark());
                        book.setMemo(cache.getMemo());
                    }
                    book.setCreatedAt(System.currentTimeMillis());
                    return book;
                })
                .doOnSuccess(bookRepo::add)
                .subscribe(data -> {
                    Timber.d("success : %s", liveDataBook);
                    liveDataBook.postValue(data);
                }, throwable -> {
                    Timber.e("error occur %s", throwable.getMessage());
                    throwable.printStackTrace();
                }));
        return liveDataBook;
    }

    public LiveData<Book> updateBookMark(Book book, String memo) {
        boolean bookMark = !book.isBookMark();
        addDisposable(bookRepo.updateBookMark(book, bookMark, bookMark ? memo : "")
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> provider.showWaitingDialog())
                .doFinally(WaitingDialog::dismiss)
                .doOnSuccess(aBoolean -> {
                    book.setBookMark(bookMark);
                    book.setMemo(bookMark ? memo : "");
                })
                .subscribe(o -> result.postValue(book), throwable -> {
                    Timber.e("error occur %s", throwable.getMessage());
                }));
        return result;
    }
}
