package edu.gatech.cs2340_68b.donationtracker.Controllers;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.entity.ByteArrayEntity;

public class HttpUtils {
    private static final String BASE_URL = "http://10.0.2.2:5000";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void postForm(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void postJson(Context c, String url, ByteArrayEntity entity, AsyncHttpResponseHandler handler) {
        client.post(c, getAbsoluteUrl(url), entity, "application/json", handler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
