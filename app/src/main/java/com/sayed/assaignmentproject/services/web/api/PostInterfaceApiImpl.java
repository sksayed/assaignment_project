package com.sayed.assaignmentproject.services.web.api;

import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sayed.assaignmentproject.model.Post;
import com.sayed.assaignmentproject.utilities.Constants;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class PostInterfaceApiImpl implements PostInterfaceAPI {
    private static final String TAG = PostInterfaceApiImpl.class.getSimpleName();
    private static PostInterfaceApiImpl instance;
    private static final long CONNECT_TIMEOUT = 20000;   // 2 seconds
    private static final long READ_TIMEOUT = 20000;      // 2 seconds
    private static OkHttpClient okHttpClient = null;
    private HttpLoggingInterceptor httpLoggingInterceptor;
    private Executor executorService;
    private List<Post> resultList;

    public PostInterfaceApiImpl() {
    }

    /*
     * make this class Singleton
     * handle all the complexity inside this class
     * */
    private OkHttpClient getOkHttpClient() {
        if (okHttpClient != null) {
            return okHttpClient;
        }
        /*
         * if else er logic
         * first e negetive gula define kore
         * last e actual kaj korte hoy
         * */

        //ExecutorService Initialization
        executorService = Executors.newFixedThreadPool(Constants.NUMBER_OF_THREADS);

        //create LoggingInterCeptor
        httpLoggingInterceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS);

        //look if other things are needed or not
        return okHttpClientBuilder.build();
    }


    @Override
    public void getAllPostsFromWeb( Consumer<List<Post>> post) {
        //declare dummy List with null

        getOkHttpClient().newCall(getPostsRequest()).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, e.getMessage());
                resultList = new ArrayList<>();
                post.accept(resultList);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                //when the value returns
                //parse it in a background thread and send it back to
                //ui
                if (response.isSuccessful()) {
                    Log.d(TAG , "Running on " +Thread.currentThread().getName());
                    try {
                        Log.d(TAG , "Running on inside executor " +Thread.currentThread().getName());
                        getPostsObjectFromJackson(response.body().string(), posts -> {
                            resultList = posts;
                            post.accept(resultList);
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private void getPostsObjectFromJackson(String jsonString, Consumer<List<Post>> posts) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Post> resultPost = objectMapper.readValue(jsonString, new TypeReference<ArrayList<Post>>() {
        });
        posts.accept(resultPost);
    }

    private Request getPostsRequest() {
        Request.Builder builder = new Request.Builder()
                .url(Constants.BASE_URL + Constants.POSTS);
        Request request = builder.build();
        return request;
    }

    public static PostInterfaceApiImpl getInstance() {
        if (instance == null) {
            instance = new PostInterfaceApiImpl();
        }
        return instance;
    }

}
