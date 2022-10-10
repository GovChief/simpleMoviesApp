package com.jakov.trakt.moviestraktapp.di;

import com.jakov.trakt.moviestraktapp.BuildConfig;
import com.jakov.trakt.moviestraktapp.data.remote.OmdbApiService;
import com.jakov.trakt.moviestraktapp.data.remote.TraktApiService;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory;

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
    private static final String TRAKT_BASE_URL = "https://api.trakt.tv/movies/";
    private static final String OMDB_API_URL = "http://omdbapi.com/";

    //Headers
    private static final String TRAKT_API_KEY_HEADER = "trakt-api-key";
    private static final String TRAKT_API_VERSION_HEADER = "trakt-api-version";
    private static final String TRAKT_API_VERSION_HEADER_VALUE = "2";
    private static final String TRAKT_CONTENT_TYPE_HEADER = "Content-Type";
    private static final String TRAKT_CONTENT_TYPE_HEADER_VALUE = "application/json";

    // Query params
    private static final String OMDB_API_KEY_QUERY_PARAM = "apikey";

    @Provides
    @Singleton
    public TraktApiService traktApiService(TraktOkHttpClientWrapper clientWrapper) {
        return (new Retrofit.Builder())
            .baseUrl(TRAKT_BASE_URL)
            .client(clientWrapper.getOkHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(
                MoshiConverterFactory.create()
            )
            .build()
            .create(TraktApiService.class);
    }

    @Provides
    @Singleton
    public TraktOkHttpClientWrapper traktOkHttpClient() {
        OkHttpClient.Builder okHttpBuilder = (new OkHttpClient.Builder())
            .connectTimeout(OK_HTTP_CLIENT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(OK_HTTP_CLIENT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(OK_HTTP_CLIENT_TIMEOUT, TimeUnit.SECONDS);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> Timber.tag("OkHttp").d(message));
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpBuilder.addInterceptor(loggingInterceptor);

        okHttpBuilder.addInterceptor(chain -> {
            Request request = chain.request().newBuilder()
                .addHeader(TRAKT_CONTENT_TYPE_HEADER, TRAKT_CONTENT_TYPE_HEADER_VALUE)
                .addHeader(TRAKT_API_VERSION_HEADER, TRAKT_API_VERSION_HEADER_VALUE)
                .addHeader(TRAKT_API_KEY_HEADER, BuildConfig.TRAKT_API_KEY)
                .build();
            return chain.proceed(request);
        });

        return new TraktOkHttpClientWrapper(okHttpBuilder.build());
    }

    @Provides
    @Singleton
    public OmdbApiService omdbApiService(OmdbOkHttpClientWrapper clientWrapper) {
        return (new Retrofit.Builder())
            .baseUrl(OMDB_API_URL)
            .client(clientWrapper.getOkHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(
                MoshiConverterFactory.create(
                    (new Moshi.Builder()).add(
                        new KotlinJsonAdapterFactory()
                    ).build()
                )
            )
            .build()
            .create(OmdbApiService.class);
    }

    @Provides
    @Singleton
    public OmdbOkHttpClientWrapper omdbOkHttpClient() {
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
                .addQueryParameter(OMDB_API_KEY_QUERY_PARAM, BuildConfig.OMDB_API_KEY).build();
            Request authorizedRequest = request.newBuilder().url(url).build();
            return chain.proceed(authorizedRequest);
        });

        return new OmdbOkHttpClientWrapper(okHttpBuilder.build());
    }
}
