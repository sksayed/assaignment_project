package com.sayed.assaignmentproject.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.sayed.assaignmentproject.R;
import com.sayed.assaignmentproject.model.Post;
import com.sayed.assaignmentproject.view.viewholder.PostViewHolder;


import java.util.zip.Inflater;

import static com.sayed.assaignmentproject.model.Post.*;

public class PostPageAdapter extends PagedListAdapter<Post , RecyclerView.ViewHolder> {


    public PostPageAdapter() {
        super(postItemCallback);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item , parent , false);
      return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof PostViewHolder){
            ((PostViewHolder)holder).bindTo(getItem(position));
        }
    }

}
