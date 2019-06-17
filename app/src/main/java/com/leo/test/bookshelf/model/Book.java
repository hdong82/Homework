package com.leo.test.bookshelf.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.leo.test.bookshelf.consts.StringSet;

@Entity(tableName = StringSet.book)
public class Book implements Parcelable {

    @PrimaryKey @NonNull
    public String isbn13;
    public String title;
    public String subtitle;
    public String price;
    public String url;
    public String image;
    public boolean isBookMark;
    public boolean isHistory;
    public String memo;
    public long createdAt;
    @Ignore public String error;
    @Ignore public String authors;
    @Ignore public String publisher;
    @Ignore public String language;
    @Ignore public String isbn10;
    @Ignore public String pages;
    @Ignore public String year;
    @Ignore public String rating;
    @Ignore public String desc;

    public Book(@NonNull String isbn13, String title, String subtitle, String price, String url, String image) {
        this.isbn13 = isbn13;
        this.title = title;
        this.subtitle = subtitle;
        this.price = price;
        this.url = url;
        this.image = image;
    }

    protected Book(Parcel in) {
        isbn13 = in.readString();
        title = in.readString();
        subtitle = in.readString();
        price = in.readString();
        url = in.readString();
        image = in.readString();
        isBookMark = in.readByte() != 0;
        isHistory = in.readByte() != 0;
        memo = in.readString();
        error = in.readString();
        authors = in.readString();
        publisher = in.readString();
        language = in.readString();
        isbn10 = in.readString();
        pages = in.readString();
        year = in.readString();
        rating = in.readString();
        desc = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public boolean isSuccess() {
        return error != null && error.equals("0");
    }

//    public boolean isHistory() {
//        return isHistory;
//    }
//
//    public void setHistory(boolean history) {
//        isHistory = history;
//    }
//
//    public boolean isBookMark() {
//        return isBookMark;
//    }
//
//    public void setBookMark(boolean bookMark) {
//        isBookMark = bookMark;
//    }
//
//    public String getMemo() {
//        return memo;
//    }
//
//    public void setMemo(String memo) {
//        this.memo = memo;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getSubTitle() {
//        return subtitle;
//    }
//
//    public String getSubtitle() {
//        return subtitle;
//    }
//
//    public void setSubtitle(String subtitle) {
//        this.subtitle = subtitle;
//    }
//
//    public String getIsbn13() {
//        return isbn13;
//    }
//
//    public void setIsbn13(String isbn13) {
//        this.isbn13 = isbn13;
//    }
//
//    public String getPrice() {
//        return price;
//    }
//
//    public void setPrice(String price) {
//        this.price = price;
//    }
//
//    public String getAuthors() {
//        return authors;
//    }
//
//    public void setAuthors(String authors) {
//        this.authors = authors;
//    }
//
//    public String getPublisher() {
//        return publisher;
//    }
//
//    public void setPublisher(String publisher) {
//        this.publisher = publisher;
//    }
//
//    public String getLanguage() {
//        return language;
//    }
//
//    public void setLanguage(String language) {
//        this.language = language;
//    }
//
//    public String getIsbn10() {
//        return isbn10;
//    }
//
//    public void setIsbn10(String isbn10) {
//        this.isbn10 = isbn10;
//    }
//
//    public String getPages() {
//        return pages;
//    }
//
//    public void setPages(String pages) {
//        this.pages = pages;
//    }
//
//    public String getYear() {
//        return year;
//    }
//
//    public void setYear(String year) {
//        this.year = year;
//    }
//
//    public String getRating() {
//        return rating;
//    }
//
//    public void setRating(String rating) {
//        this.rating = rating;
//    }
//
//    public String getDesc() {
//        return desc;
//    }
//
//    public void setDesc(String desc) {
//        this.desc = desc;
//    }
//
//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }
//
//    public String getImage() {
//        return image;
//    }
//
//    public void setImage(String image) {
//        this.image = image;
//    }


    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public void setBookMark(boolean bookMark) {
        isBookMark = bookMark;
    }

    public void setHistory(boolean history) {
        isHistory = history;
    }

    @NonNull
    public String getIsbn13() {
        return isbn13;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subtitle;
    }

    public String getPrice() {
        return price;
    }

    public String getUrl() {
        return url;
    }

    public String getImage() {
        return image;
    }

    public boolean isBookMark() {
        return isBookMark;
    }

    public boolean isHistory() {
        return isHistory;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getError() {
        return error;
    }

    public String getAuthors() {
        return authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getLanguage() {
        return language;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public String getPages() {
        return pages;
    }

    public String getYear() {
        return year;
    }

    public String getRating() {
        return rating;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(isbn13);
        dest.writeString(title);
        dest.writeString(subtitle);
        dest.writeString(price);
        dest.writeString(url);
        dest.writeString(image);
        dest.writeByte((byte) (isBookMark ? 1 : 0));
        dest.writeByte((byte) (isHistory ? 1 : 0));
        dest.writeString(memo);
        dest.writeString(error);
        dest.writeString(authors);
        dest.writeString(publisher);
        dest.writeString(language);
        dest.writeString(isbn10);
        dest.writeString(pages);
        dest.writeString(year);
        dest.writeString(rating);
        dest.writeString(desc);
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn13='" + isbn13 + '\'' +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", price='" + price + '\'' +
                ", url='" + url + '\'' +
                ", image='" + image + '\'' +
                ", isBookMark=" + isBookMark +
                ", isHistory=" + isHistory +
                ", memo='" + memo + '\'' +
                ", error='" + error + '\'' +
                ", authors='" + authors + '\'' +
                ", publisher='" + publisher + '\'' +
                ", language='" + language + '\'' +
                ", isbn10='" + isbn10 + '\'' +
                ", pages='" + pages + '\'' +
                ", year='" + year + '\'' +
                ", rating='" + rating + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
