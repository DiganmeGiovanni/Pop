package org.pop.moviedb.client;

import org.pop.moviedb.callbacks.PopCBGeneric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class RetrofitCBGeneric<T> implements Callback<T> {
    private String tag;
    private PopCBGeneric<T> popCallback;

    RetrofitCBGeneric(String tag, PopCBGeneric<T> popCallback) {
        this.tag = tag;
        this.popCallback = popCallback;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            popCallback.onResponse(true, response.body());
        } else {
            System.err.println(tag + " request fail without server crash");
            popCallback.onResponse(false, null);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        System.err.println(tag + " request fail: " + t.getMessage());
        popCallback.onResponse(false, null);
    }
}
