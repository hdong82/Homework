package com.leo.test.bookshelf.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.leo.test.bookshelf.model.Book;
import com.leo.test.bookshelf.repository.BookRepository;
import com.leo.test.bookshelf.widget.WaitingDialog;

import java.util.Collections;
import java.util.List;

import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class BookMarkViewModel extends BaseViewModel {
    public enum Order {
        Latest, Price
    }

    private final BookRepository bookRepo = BookRepository.getInstance();
    private final LiveData<List<Book>> dbBookMarks;
    private final MutableLiveData<Order> orderLiveData = new MutableLiveData<>();
    private final MediatorLiveData<List<Book>> bookMarkList = new MediatorLiveData<>();

    private List<Book> sort() {
        List<Book> list = dbBookMarks.getValue();
        Order order = orderLiveData.getValue();
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }

        Collections.sort(list, (o1, o2) -> {
            int ret = 0;
            if (order != null) {
                switch (order) {
                    case Price:
                        ret = o1.getPrice().compareTo(o2.getPrice());
                        break;
                    case Latest:
                        ret = Long.compare(o1.getCreatedAt(), o2.getCreatedAt()) * -1;
                        break;
                }
            }
            return ret;
        });
        return list;
    }

    public BookMarkViewModel(DialogProvider provider) {
        super(provider);

        orderLiveData.setValue(Order.Latest);
        dbBookMarks = bookRepo.getAllBookMark();
        bookMarkList.addSource(orderLiveData, order -> bookMarkList.setValue(sort()));
        bookMarkList.addSource(dbBookMarks, books -> bookMarkList.setValue(sort()));
    }

    public LiveData<List<Book>> getBookMarkList () {
        return bookMarkList;
    }
    public void onOrderChanged(Order order) {
        Timber.d("__onOrderChanged()");
        orderLiveData.setValue(order);
    }

    public void removeBookMark(Book book) {
        boolean bookMark = !book.isBookMark();
        addDisposable(bookRepo.updateBookMark(book, bookMark, "")
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> provider.showWaitingDialog())
                .doFinally(WaitingDialog::dismiss)
                .subscribe(o -> {}, throwable -> {
                    Timber.e("error occur %s", throwable.getMessage());
                }));
    }
}
