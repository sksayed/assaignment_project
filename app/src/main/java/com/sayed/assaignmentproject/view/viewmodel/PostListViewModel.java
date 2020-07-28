package com.sayed.assaignmentproject.view.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.sayed.assaignmentproject.model.Post;
import com.sayed.assaignmentproject.services.Repository;

public class PostListViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private  final Repository repository ;
    private final LiveData<PagedList<Post>> pagedListLiveData ;

    public PostListViewModel() {
        repository = new Repository();
        pagedListLiveData = repository.getPagedListLiveData();
    }

    public LiveData<PagedList<Post>> getPagedListLiveData() {
        return pagedListLiveData;
    }
}