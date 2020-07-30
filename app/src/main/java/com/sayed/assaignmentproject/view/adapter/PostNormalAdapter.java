package com.sayed.assaignmentproject.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sayed.assaignmentproject.R;
import com.sayed.assaignmentproject.model.Post;
import com.sayed.assaignmentproject.view.viewholder.PostViewHolder;

import java.util.List;

public class PostNormalAdapter extends RecyclerView.Adapter<PostViewHolder> {

    private final List<Post> postList  ;

    public PostNormalAdapter(List<Post> postList) {
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item,
                parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post retrivedPost = postList.get(position);
        holder.getPost_id().setText(retrivedPost.getId().toString());
        holder.getPost_title().setText(retrivedPost.getTitle());
        holder.getPost_body().setText(retrivedPost.getBody());
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
}
