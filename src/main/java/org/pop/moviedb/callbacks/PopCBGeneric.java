package org.pop.moviedb.callbacks;

public interface PopCBGeneric<T> {

    void onResponse(boolean success, T response);
}
