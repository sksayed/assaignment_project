package com.sayed.assaignmentproject.services.web.api;

import com.sayed.assaignmentproject.model.Post;

import java.util.List;
import java.util.function.Consumer;

import okhttp3.Call;

public interface PostInterfaceAPI {
    void getAllPostsFromWeb (Consumer<List<Post>> postCallBackFromWeb);
}
