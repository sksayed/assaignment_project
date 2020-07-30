package com.sayed.assaignmentproject.services.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.sayed.assaignmentproject.model.Post;
import com.sayed.assaignmentproject.utilities.Constants;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import static com.sayed.assaignmentproject.services.local.DbUtilities.COLUMN_BODY;
import static com.sayed.assaignmentproject.services.local.DbUtilities.COLUMN_ID;
import static com.sayed.assaignmentproject.services.local.DbUtilities.COLUMN_TIMESTAMP;
import static com.sayed.assaignmentproject.services.local.DbUtilities.COLUMN_TITLE;
import static com.sayed.assaignmentproject.services.local.DbUtilities.COLUMN_USER_ID;
import static com.sayed.assaignmentproject.services.local.DbUtilities.POST_TABLE_NAME;

public class PostDaoImpl implements PostDao {
    private static final String TAG = PostDaoImpl.class.getSimpleName();
    private final DBhelper dBhelper;
    private Executor executorService;
    private static PostDaoImpl instance;
    private Context context;
    private final SQLiteDatabase mDatabase;
    private ContentValues contentValues;


    public PostDaoImpl(Context context) {
        this.context = context;
        this.dBhelper = new DBhelper(context);
        this.mDatabase = dBhelper.getWritableDatabase();
        executorService = Executors.newFixedThreadPool(Constants.NUMBER_OF_THREADS);
    }

    @Override
    public List<Post> getAllposts() {
        List<Post> postListResult = new ArrayList<>();
        Cursor resultCursor = mDatabase.query(POST_TABLE_NAME
                , null
                , null
                , null
                , null
                , null
                , COLUMN_ID + " ASC"
                , null
        );

        if (resultCursor.moveToFirst()) {
            do {
                Post post = new Post()
                        .setId(resultCursor.getInt(resultCursor.getColumnIndex(COLUMN_ID)))
                        .setUserId(resultCursor.getInt(resultCursor.getColumnIndex(COLUMN_USER_ID)))
                        .setTitle(resultCursor.getString(resultCursor.getColumnIndex(COLUMN_TITLE)))
                        .setBody(resultCursor.getString((resultCursor.getColumnIndex(COLUMN_BODY))));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                try {
                    Date date = dateFormat.parse(resultCursor.getString
                            (resultCursor.getColumnIndex(COLUMN_TIMESTAMP)));
                    Timestamp timestamp = new Timestamp(date.getTime());
                } catch (ParseException e) {
                    Log.d(TAG , "Parsing e somossa"+e.getMessage());
                }
                postListResult.add(post);

            } while (resultCursor.moveToNext());
        }
        return postListResult;
    }

    @Override
    public boolean insert(Post post) {
        ContentValues insertCV = new ContentValues();
        insertCV.put(COLUMN_ID, post.getId());
        insertCV.put(COLUMN_USER_ID, post.getUserId());
        insertCV.put(COLUMN_TITLE, post.getTitle());
        insertCV.put(COLUMN_BODY, post.getBody());
        try {
            long id = mDatabase.insertOrThrow(POST_TABLE_NAME, null, insertCV);
            Toast.makeText(context, "inserted id is" + id, Toast.LENGTH_SHORT);
            return true;
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT);
            return false;
        }
    }

    @Override
    public boolean update(Post post) {
        return false;
    }

    @Override
    public boolean delete(Post post) {
        return false;
    }

    @Override
    public void getAllPostAsync(Consumer<List<Post>> allPostCallBack) {

    }

    @Override
    public void insertAsync(Post post, Consumer<Boolean> insertCallBack) {
        executorService.execute(() -> {
            ContentValues insertCV = new ContentValues();
            insertCV.put(COLUMN_ID, post.getId());
            insertCV.put(COLUMN_USER_ID, post.getUserId());
            insertCV.put(COLUMN_TITLE, post.getTitle());
            insertCV.put(COLUMN_BODY, post.getBody());
            try {
                long id = mDatabase.insertOrThrow(POST_TABLE_NAME, null, insertCV);
                Log.d(TAG, "id is " + id);
                insertCallBack.accept(true);
            } catch (Exception e) {
                insertCallBack.accept(false);
            }
        });

    }

    @Override
    public void updateAsync(Post post, Consumer<Boolean> updateCallback) {

    }

    @Override
    public void deleteAsync(Post post, Consumer<Boolean> deleteCallback) {

    }

}
