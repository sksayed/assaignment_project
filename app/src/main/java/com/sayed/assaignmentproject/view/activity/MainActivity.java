package com.sayed.assaignmentproject.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.sayed.assaignmentproject.R;
import com.sayed.assaignmentproject.services.web.api.PostInterfaceAPI;
import com.sayed.assaignmentproject.view.fragment.PostListFragmentLocalDb;

public class MainActivity extends AppCompatActivity {

    private PostInterfaceAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.fragment_container) != null) {
            PostListFragmentLocalDb postListFragment = new PostListFragmentLocalDb();
              getSupportFragmentManager().beginTransaction()
                      .add(R.id.fragment_container , postListFragment)
                      .addToBackStack(null)
                      .commit();
        }
    }

}