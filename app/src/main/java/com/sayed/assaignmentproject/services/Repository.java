package com.sayed.assaignmentproject.services;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.sayed.assaignmentproject.model.Post;
import com.sayed.assaignmentproject.services.web.PostWebManager;
import com.sayed.assaignmentproject.services.web.paging.PostDataSourceFactory;

public class Repository {
    private static final String TAG = Repository.class.getSimpleName();
    private final PostWebManager webDataManager ;
    private final PostDataSourceFactory postDataSourceFactory ;
    private final LiveData<PagedList<Post>> pagedListLiveData ;

    public Repository() {
        postDataSourceFactory = new PostDataSourceFactory();
        webDataManager = new PostWebManager(postDataSourceFactory , postBoundaryCallback);

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
    };
}
