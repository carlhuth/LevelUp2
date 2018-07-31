package io.github.ynagarjuna1995.levelup2.di.modules;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.github.ynagarjuna1995.levelup2.common.Constants;
import io.github.ynagarjuna1995.levelup2.data.api.GoogleAPIHelper;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    private static final long CONNECT_TIMEOUT = 15;
    private static final long WRITE_TIMEOUT =15 ;
    private static final long TIMEOUT = 15;

    @Provides
    @Singleton
    GoogleAPIHelper provideGoogleApi() {


        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS);


        return new Retrofit.Builder()
                .baseUrl(Constants.GOOGLE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build())
                .build().create(GoogleAPIHelper.class);

    }

//    @Provides
//    @Singleton
//    public OkHttpClient providesOkHttpClient(Cache cache) {
////        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
////        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        OkHttpClient.Builder builder = new OkHttpClient.Builder()
//                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
//                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
//                .readTimeout(TIMEOUT, TimeUnit.SECONDS);
//
//        return builder.build();
//    }
//
//    @Provides
//    @Singleton
//    public Retrofit providesRetrofit(OkHttpClient okHttpClient) {
//
//        return new Retrofit.Builder()
//                .baseUrl(Constants.GOOGLE_BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .client(okHttpClient)
//                .build();
//    }

}
