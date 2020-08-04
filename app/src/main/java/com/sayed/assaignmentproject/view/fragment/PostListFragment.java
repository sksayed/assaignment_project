package com.sayed.assaignmentproject.view.fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sayed.assaignmentproject.R;
import com.sayed.assaignmentproject.model.Post;
import com.sayed.assaignmentproject.view.adapter.PostPageAdapter;
import com.sayed.assaignmentproject.view.viewmodel.PostListViewModel;

public class PostListFragment extends Fragment {

    private RecyclerView recyclerView ;
    private PostListViewModel mViewModel;
    private PostPageAdapter pageAdapter ;
    private PostListFragment instance ;

    public static PostListFragment newInstance() {
        return new PostListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.post_list_fragment, container, false);
        initView(view);
        loadItems();
        instance = this ;
        return view ;
    }

    private void loadItems() {
        recyclerView.setItemAnimator( new DefaultItemAnimator());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(pageAdapter);
    }

    private void initView(View view) {
        this.recyclerView = view.findViewById(R.id.post_recycler_view);
        pageAdapter = new PostPageAdapter();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PostListViewModel.class);
        // TODO: Use the ViewModel
        mViewModel.getPagedListLiveData().observe(getViewLifecycleOwner(), new Observer<PagedList<Post>>() {
            @Override
            public void onChanged(PagedList<Post> posts) {
                pageAdapter.submitList(posts);
            }
        });

        mViewModel.getValueReturnedFromDb().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Toast.makeText(instance.getActivity() , "value returned From db"+aBoolean , Toast.LENGTH_LONG);
            }
        });


    }



}