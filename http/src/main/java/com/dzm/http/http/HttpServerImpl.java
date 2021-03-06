package com.dzm.http.http;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.dzm.http.intercept.HttpInterceptInterface;
import com.dzm.http.server.HttpServer;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author 邓治民
 *         data 2018/3/8 上午9:28
 */

public class HttpServerImpl implements HttpServer {

    @Override
    public <T> T initService(Class<T> service, String url, TimeBuild timeout) {
        //设置缓存路径
//        File httpCacheDirectory = StorageUtils.getCustomCacheDirectory(context, "httpCache");
//        //设置缓存 10M
//        Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
//                .cache(cache)
                .connectTimeout(timeout.connectTimeout, TimeUnit.SECONDS)
                .readTimeout(timeout.readTimeout, TimeUnit.SECONDS)
                .writeTimeout(timeout.writeTimeout, TimeUnit.SECONDS);
        OkHttpClient client = builder.build();
        Retrofit retrofit = initRetrofit(client,url);
        return retrofit.create(service);
    }

    @Override
    public <T> T initService(Class<T> service, String url) {
        return this.initService(service,url,new TimeBuild(5,5,60));
    }

    @Override
    public void addIntercepte(HttpInterceptInterface interceptInterface) {
        HttpSir.getBuild().addIntercept(interceptInterface);
    }

    @Override
    public void addDefultIntercepte(HttpInterceptInterface interceptInterface) {
        HttpSir.getBuild().addDefultIntercept(interceptInterface);
    }

    @Override
    public HttpInterface http(Context context) {
        return HttpSir.initHttp(context);
    }

    @Override
    public HttpInterface http(Fragment fragment) {
        return HttpSir.initHttp(fragment);
    }

    @Override
    public HttpInterface http() {
        return HttpSir.initHttp();
    }

    private Retrofit initRetrofit(OkHttpClient client, String url){
        return new Retrofit.Builder()
                //设置OKHttpClient为网络客户端
                .client(client) // setup okHttp client
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .serializeNulls()
                        .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                        .create())) // GSON converter
                //配置回调库，采用RxJava
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // RxCallAdapter
                .baseUrl(url)
                .build();
    }
}
