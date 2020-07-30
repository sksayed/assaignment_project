package com.sayed.assaignmentproject.services;

import android.content.Context;

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

public class Repository {
    private static final String TAG = Repository.class.getSimpleName();
    private final PostWebManager webDataManager;
    private final PostDataSourceFactory postDataSourceFactory;
    private final LiveData<PagedList<Post>> pagedListLiveData;
    private final PostDao dbService;

    public Repository(Context context) {
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
}
