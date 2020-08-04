package com.sayed.assaignmentproject.services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.sayed.assaignmentproject.model.Post;
import com.sayed.assaignmentproject.services.local.PostDao;
import com.sayed.assaignmentproject.services.local.PostDaoImpl;
import com.sayed.assaignmentproject.services.web.PostWebManager;
import com.sayed.assaignmentproject.services.web.paging.PostDataSourceFactory;

import java.util.List;
import java.util.function.Consumer;

/**
 * This repository will act as single source of truth and all values will be recieved from
 * Database , and all database values will be reccieved from web
 */
public class Repository {
    private static final String TAG = Repository.class.getSimpleName();
    private final PostWebManager webDataManager;
    private final PostDataSourceFactory postDataSourceFactory;
    private final LiveData<PagedList<Post>> pagedListLiveData;
    private final PostDao dbService;
    private final Context context ;

    public Repository(Context context) {
        this.context = context ;
        postDataSourceFactory = new PostDataSourceFactory();
        webDataManager = new PostWebManager(postDataSourceFactory, postBoundaryCallback);
        dbService = new PostDaoImpl(context);
        pagedListLiveData = webDataManager.getPostPaged();
    }

    private final PagedList.BoundaryCallback<Post> postBoundaryCallback = new PagedList.BoundaryCallback<Post>() {
        @Override
        public void onZeroItemsLoaded() {
            super.onZeroItemsLoaded();
        }

        @Override
        public void onItemAtFrontLoaded(@NonNull Post itemAtFront) {
            super.onItemAtFrontLoaded(itemAtFront);
        }

        @Override
        public void onItemAtEndLoaded(@NonNull Post itemAtEnd) {
            super.onItemAtEndLoaded(itemAtEnd);
        }
    };

    public LiveData<PagedList<Post>> getPagedListLiveData() {
        return pagedListLiveData;
    }

    ;

    public boolean insertPostToDb(Post post) {
        return dbService.insert(post);
    }

    public void insertAsync(Post post, Consumer<Boolean> insertCallBack) {
        dbService.insertAsync(post, value -> insertCallBack.accept(value));
    }

    public List<Post> getAllPostSync() {
        return dbService.getAllposts();
    }

    public void getAllPostFrommDbAsync(Consumer<List<Post>> allPostList) {

         dbService.getAllPostAsync(posts -> {
             if(posts.size() == 0) {
                 //call web api
                 webDataManager.getAllpostFromWebAsync(allPost -> {
                     //insert them into db
                    allPost.stream().forEach(post -> {
                        dbService.insertAsync( post, value->{
                            Log.d(TAG , "value inserted inside repo "+value);
                        });
                    });
                    //after inserting all of them inside db , sent back to ui
                     allPostList.accept(allPost);
                 });
             }else {
                allPostList.accept(posts);
             }
         });
    }
}
