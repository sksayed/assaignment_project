package com.sayed.assaignmentproject.services.web.paging;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

public class PostDataSourceFactory extends DataSource.Factory {
    private static final String TAG = PostDataSourceFactory.class.getSimpleName();
    private final PostPageKeyDataSourceWeb postPageKeyDataSourceWeb ;

    public PostDataSourceFactory() {
        postPageKeyDataSourceWeb = new PostPageKeyDataSourceWeb();
    }

    @NonNull
    @Override
    public DataSource create() {
        return postPageKeyDataSourceWeb;
    }
}
