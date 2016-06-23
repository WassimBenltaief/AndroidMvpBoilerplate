package com.wassim.androidmvpbase.data.remote;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by beltaief on 2/25/2016.
 */
public interface OkHttpHelper {

    class Factory {

        /**
         * @return an OkHttpClient with or without header
         */
        public static OkHttpClient getClient() {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client =
                    new OkHttpClient.Builder()

                            // uncomment when using headers
                    /*.addInterceptor(
                            new Interceptor() {
                                @Override
                                public Response intercept(Interceptor.Chain chain) throws
                                        IOException {
                                    Request request = chain.request().newBuilder()
                                            .addHeader("Accept", "application/json")
                                            .addHeader("Content-Type", "application/json")
                                            .addHeader("Authorization", "Bearer " + "token")
                                            .build();
                                    return chain.proceed(request);
                                }
                            })*/
                            .addInterceptor(interceptor).build();
            return client;
        }
    }
}
