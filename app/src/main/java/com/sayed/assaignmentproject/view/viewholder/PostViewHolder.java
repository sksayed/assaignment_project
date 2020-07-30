package com.sayed.assaignmentproject.view.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sayed.assaignmentproject.R;
import com.sayed.assaignmentproject.model.Post;

public class PostViewHolder extends RecyclerView.ViewHolder {
    TextView post_id;
    TextView post_title;
    TextView post_body;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);
        post_id = itemView.findViewById(R.id.post_id);
        post_title = itemView.findViewById(R.id.post_title);
        post_body = itemView.findViewById(R.id.post_body);
    }

   public void bindTo (Post post) {
        this.post_id.setText(post.getId().toString());
        this.post_body.setText(post.getBody());
        this.post_title.setText(post.getTitle());
   }

    public TextView getPost_id() {
        return post_id;
    }

    public TextView getPost_title() {
        return post_title;
    }

    public TextView getPost_body() {
        return post_body;
    }
}
