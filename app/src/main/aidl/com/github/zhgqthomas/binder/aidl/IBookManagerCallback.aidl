// IBookManagerCallback.aidl
package com.github.zhgqthomas.binder.aidl;
import com.github.zhgqthomas.binder.aidl.Book;

interface IBookManagerCallback {
    void onNewBookArrived(in Book book);
}
