package com.sayed.assaignmentproject.services.local;

import com.sayed.assaignmentproject.model.Post;

import java.util.List;
import java.util.function.Consumer;

public interface PostDao {

    List<Post> getAllposts () ;

    boolean insert (Post post) ;

    boolean update (Post post) ;

    boolean delete (Post post) ;

    void getAllPostAsync (Consumer<List<Post>> allPostCallBack) ;

    void insertAsync (Post post ,Consumer<Boolean> insertCallBack) ;

    void updateAsync (Post post ,Consumer<Boolean> updateCallback) ;

    void deleteAsync (Post post ,Consumer<Boolean> deleteCallback) ;

}
