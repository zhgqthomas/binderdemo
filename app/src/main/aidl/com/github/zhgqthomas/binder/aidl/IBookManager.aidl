// IBookManager.aidl
package com.github.zhgqthomas.binder.aidl;
import com.github.zhgqthomas.binder.aidl.Book;
import com.github.zhgqthomas.binder.aidl.IBookManagerCallback;

interface IBookManager {

    List<Book> getBooks();
    void addBook(in Book book);
    void registerBookManagerCallback(IBookManagerCallback callback);
    void unregisterBookManagerCallback(IBookManagerCallback callback);
}
