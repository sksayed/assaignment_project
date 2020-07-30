package com.sayed.assaignmentproject.view.viewmodel;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.sayed.assaignmentproject.model.Post;
import com.sayed.assaignmentproject.services.Repository;

import java.util.List;

public class PostListViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private  final Repository repository ;
    private final LiveData<PagedList<Post>> pagedListLiveData ;
    private MutableLiveData<Boolean> valueReturnedFromDb ;

    public PostListViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        pagedListLiveData = repository.getPagedListLiveData();
        valueReturnedFromDb = new MutableLiveData<>();
    }

    public LiveData<PagedList<Post>> getPagedListLiveData() {
        return pagedListLiveData;
    }

    public boolean insertPost (Post post) {
      return   repository.insertPostToDb(post);
    }

    public void insertPostAsync (Post post) {
           repository.insertAsync(post , value -> {
              valueReturnedFromDb.postValue(value);
           });
    }

    public LiveData<Boolean> getValueReturnedFromDb() {
        return valueReturnedFromDb;
    }

    public List<Post> getAllPostSync () {
        return repository.getAllPostSync();
    }

}