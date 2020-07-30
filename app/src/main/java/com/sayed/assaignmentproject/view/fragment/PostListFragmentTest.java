package com.sayed.assaignmentproject.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sayed.assaignmentproject.R;
import com.sayed.assaignmentproject.model.Post;

import com.sayed.assaignmentproject.view.adapter.PostNormalAdapter;
import com.sayed.assaignmentproject.view.viewmodel.PostListViewModel;

public class PostListFragmentTest extends Fragment {
    private RecyclerView recyclerView;
    private PostListViewModel mViewModel;
    private PostNormalAdapter normalAdapter;
    private PostListFragmentTest instance;

    public static PostListFragment newInstance() {
        return new PostListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.post_list_fragment, container, false);
        initView(view);
        loadItems();
        handleObservable();
        instance = this;
        return view;
    }

    private void loadItems() {
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(normalAdapter);
        normalAdapter.notifyDataSetChanged();
    }

    private void initView(View view) {
        this.recyclerView = view.findViewById(R.id.post_recycler_view);
        mViewModel = ViewModelProviders.of(this).get(PostListViewModel.class);
        normalAdapter = new PostNormalAdapter(mViewModel.getAllPostSync());
    }


    public void handleObservable() {
        // TODO: Use the ViewModel
        mViewModel.getPagedListLiveData().observe(getViewLifecycleOwner(), new Observer<PagedList<Post>>() {
            @Override
            public void onChanged(PagedList<Post> posts) {
                //  pageAdapter.submitList(posts);
            }
        });

        mViewModel.getValueReturnedFromDb().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Toast.makeText(instance.getActivity(), "value returned From db" + aBoolean, Toast.LENGTH_LONG)
                        .show();
            }
        });
    }
}
