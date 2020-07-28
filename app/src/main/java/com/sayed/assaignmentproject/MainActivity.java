package com.sayed.assaignmentproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.sayed.assaignmentproject.model.Post;
import com.sayed.assaignmentproject.services.web.api.PostInterfaceAPI;
import com.sayed.assaignmentproject.services.web.api.PostInterfaceApiImpl;
import com.sayed.assaignmentproject.view.fragment.PostListFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PostInterfaceAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.fragment_container) != null) {
            PostListFragment postListFragment = new PostListFragment();
              getSupportFragmentManager().beginTransaction()
                      .add(R.id.fragment_container , postListFragment)
                      .addToBackStack(null)
                      .commit();
        }
    }

}