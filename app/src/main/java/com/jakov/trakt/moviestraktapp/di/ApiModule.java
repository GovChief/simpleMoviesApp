package com.jakov.trakt.moviestraktapp.di;

import com.jakov.trakt.moviestraktapp.BuildConfig;
import com.jakov.trakt.moviestraktapp.data.remote.ApiService;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;
import timber.log.Timber;

@Module
@InstallIn(SingletonComponent.class)
public class ApiModule {

    private static final Long OK_HTTP_CLIENT_TIMEOUT = 15L;

    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    // Query params
    private static final String THE_MOVIE_DB_LANGUAGE_QUERY_PARAM = "language";
    private static final String THE_MOVIE_DB_API_KEY_QUERY_PARAM = "api_key";

    @Provides
    @Singleton
    public ApiService apiService(OkHttpClient client) {
        return (new Retrofit.Builder())
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(
                MoshiConverterFactory.create(
                    (new Moshi.Builder()).add(
                        new KotlinJsonAdapterFactory()
                    ).build()
                )
            )
            .build()
            .create(ApiService.class);
    }

    @Provides
    @Singleton
    public OkHttpClient okHttpClient() {
        OkHttpClient.Builder okHttpBuilder = (new OkHttpClient.Builder())
            .connectTimeout(OK_HTTP_CLIENT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(OK_HTTP_CLIENT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(OK_HTTP_CLIENT_TIMEOUT, TimeUnit.SECONDS);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> Timber.tag("OkHttp").d(message));
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpBuilder.addInterceptor(loggingInterceptor);

        okHttpBuilder.addInterceptor(chain -> {
            Request request = chain.request();
            HttpUrl url = request.url().newBuilder()
                .addQueryParameter(THE_MOVIE_DB_API_KEY_QUERY_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY)
                .addQueryParameter(THE_MOVIE_DB_LANGUAGE_QUERY_PARAM, Locale.getDefault().toLanguageTag())
                .build();
            Request authorizedRequest = request.newBuilder().url(url).build();
            return chain.proceed(authorizedRequest);
        });

        return okHttpBuilder.build();
    }
}
