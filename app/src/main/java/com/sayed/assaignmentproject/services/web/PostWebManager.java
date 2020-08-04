package com.sayed.assaignmentproject.services.web;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.sayed.assaignmentproject.model.Post;
import com.sayed.assaignmentproject.services.web.api.PostInterfaceApiImpl;
import com.sayed.assaignmentproject.services.web.paging.PostDataSourceFactory;
import com.sayed.assaignmentproject.utilities.Constants;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class PostWebManager {
    private static final String TAG = PostWebManager.class.getSimpleName();
    private final LiveData<PagedList<Post>> postPaged ;
    private final PostInterfaceApiImpl interfaceApi = new PostInterfaceApiImpl();

    public PostWebManager(PostDataSourceFactory postDataSourceFactory ,
                          PagedList.BoundaryCallback<Post> postBoundaryCallback) {
        PagedList.Config config = new PagedList.Config.Builder()
                .setMaxSize(Constants.MAX_SIZE)
                .setEnablePlaceholders(false)
                .setPageSize(10)
                .build();
        Executor executor = Executors.newFixedThreadPool(Constants.NUMBER_OF_THREADS);
        LivePagedListBuilder pagedListBuilder = new LivePagedListBuilder(postDataSourceFactory , config)
                .setBoundaryCallback(postBoundaryCallback)
                .setFetchExecutor(executor);

        postPaged = pagedListBuilder.build();
    }

    public LiveData<PagedList<Post>> getPostPaged() {
        return postPaged;
    }

    //this method is for retriving data from web
    public void getAllpostFromWebAsync (Consumer<List<Post>> allPostCallBAck) {
        interfaceApi.getAllPostsFromWeb(posts -> allPostCallBAck.accept(posts));
    }

}
