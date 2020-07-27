package com.sayed.assaignmentproject.services.web.paging;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.sayed.assaignmentproject.model.Post;
import com.sayed.assaignmentproject.services.web.api.PostInterfaceAPI;
import com.sayed.assaignmentproject.services.web.api.PostInterfaceApiImpl;

import java.util.List;
import java.util.function.Consumer;

class PostPageKeyDataSourceWeb extends PageKeyedDataSource<String , Post> {
    private static final String TAG = PostPageKeyDataSourceWeb.class.getSimpleName();
    private final PostInterfaceAPI webServices ;

    public PostPageKeyDataSourceWeb() {
        webServices = PostInterfaceApiImpl.getInstance();
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull LoadInitialCallback<String, Post> callback) {
        webServices.getAllPostsFromWeb(new Consumer<List<Post>>() {
            @Override
            public void accept(List<Post> posts) {
                callback.onResult(posts , String.valueOf(1) , String.valueOf(2));
            }
        });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, Post> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, Post> callback) {

    }
}
