package com.unicorn.modem.service;

import android.text.TextUtils;
import com.unicorn.modem.BuildConfig;
import com.unicorn.modem.util.PreferenceHelper;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by Arash on 2017-03-31.
 * Generate different Retrofit services to call REST services
 */

public class ServiceGenerator {

//  public static final String API_BASE_URL = "http://www.qcabs.co.uk";//BuildConfig.HOST;

  private static Retrofit retrofit;
  private static OkHttpClient.Builder httpClient =
      new OkHttpClient.Builder()
          .readTimeout(60, TimeUnit.SECONDS)
          .connectTimeout(60, TimeUnit.SECONDS);
  //Change different level of logging here
  private static HttpLoggingInterceptor logging =
      new HttpLoggingInterceptor()
          .setLevel(Level.BODY);
  private static Retrofit.Builder builder;

  public static <S> S createService(Class<S> serviceClass, boolean isJson) {
    return createService(serviceClass, null, null, isJson);
  }

  public static <S> S createService(Class<S> serviceClass, String username, String password,
      boolean isJson) {
    if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
      String authToken = Credentials.basic(username, password);
      return createService(serviceClass, authToken, isJson);
    }

    return createService(serviceClass, null, isJson);
  }

  public static <S> S createService(Class<S> serviceClass, final String authToken, boolean isJson) {
    if (!TextUtils.isEmpty(authToken)) {
      AuthenticationInterceptor interceptor =
          new AuthenticationInterceptor(authToken);

      if (!httpClient.interceptors().contains(interceptor)) {
        httpClient.addInterceptor(interceptor);
      }
    }
    if (BuildConfig.DEBUG && !httpClient.interceptors().contains(logging)) {
      httpClient.addInterceptor(logging);
    }

    String API_BASE_URL = PreferenceHelper.getServerUrl();
    if (API_BASE_URL.isEmpty()) {
      API_BASE_URL = "http://www.google.com";
    }
    if (!API_BASE_URL.startsWith("http") && API_BASE_URL.startsWith("www")) {
      API_BASE_URL = "http://" + API_BASE_URL;
    }
    API_BASE_URL = API_BASE_URL + ":" + PreferenceHelper.getBizPort();
    builder = new Retrofit.Builder()
        .baseUrl(API_BASE_URL);
    if (isJson) {
      builder.addConverterFactory(GsonConverterFactory.create());
    } else {
      builder.addConverterFactory(
          SimpleXmlConverterFactory.createNonStrict(new Persister(new AnnotationStrategy())));
    }

    builder.client(httpClient.build());
    retrofit = builder.build();

    return retrofit.create(serviceClass);
  }

  static class AuthenticationInterceptor implements Interceptor {

    private String authToken;

    public AuthenticationInterceptor(String token) {
      this.authToken = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
      Request original = chain.request();

      Request.Builder builder = original.newBuilder()
          .header("Authorization", authToken);

      Request request = builder.build();
      return chain.proceed(request);
    }
  }
}
