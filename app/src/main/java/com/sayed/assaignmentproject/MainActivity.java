package com.sayed.assaignmentproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.sayed.assaignmentproject.model.Post;
import com.sayed.assaignmentproject.services.web.api.PostInterfaceAPI;
import com.sayed.assaignmentproject.services.web.api.PostInterfaceApiImpl;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PostInterfaceAPI api ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        api =  PostInterfaceApiImpl.getInstance();
        api.getAllPostsFromWeb(posts -> {
            Log.d("value" , posts.toString());
        });
        /*while (post == null ){
            Log.d("value" , "value ase nai");
        }*/

    }
}