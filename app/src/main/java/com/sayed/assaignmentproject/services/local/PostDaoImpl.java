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
                    Log.d(TAG, "Parsing e somossa" + e.getMessage());
                }
                postListResult.add(post);

            } while (resultCursor.moveToNext());
        }
        return postListResult;
    }

    /**
     * I dont need to take care of
     * timestamp as it will be handled by database
     * itself
     *
     * @param post this post will come from web or user input
     * @return boolean on the basis of insert
     */
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
        ContentValues updateValues = new ContentValues();
        updateValues.put(COLUMN_USER_ID, post.getUserId());
        updateValues.put(COLUMN_TITLE, post.getTitle());
        updateValues.put(COLUMN_BODY, post.getBody());

        int result = mDatabase.update(POST_TABLE_NAME,
                updateValues,
                COLUMN_ID + " = ?",
                new String[]{post.getId().toString()}
        );

        if (result > 0) {
            Log.d(TAG, "Update result" + result);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(Post post) {
        int result = mDatabase.delete(POST_TABLE_NAME,
                COLUMN_ID + " = ?",
                new String[]{post.getId().toString()}
        );
        if (result > 0) {
            Log.d(TAG, "Delete result" + result);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void getAllPostAsync(Consumer<List<Post>> allPostCallBack) {
        executorService.execute(() -> {
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
                        Log.d(TAG, "Parsing e somossa" + e.getMessage());
                    }
                    postListResult.add(post);

                } while (resultCursor.moveToNext());
            }
            allPostCallBack.accept(postListResult);
        });
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
        executorService.execute(() -> {
            ContentValues updateValues = new ContentValues();
            updateValues.put(COLUMN_USER_ID, post.getUserId());
            updateValues.put(COLUMN_TITLE, post.getTitle());
            updateValues.put(COLUMN_BODY, post.getBody());

            int result = mDatabase.update(POST_TABLE_NAME,
                    updateValues,
                    COLUMN_ID + " = ?",
                    new String[]{post.getId().toString()}
            );

            if (result > 0) {
                Log.d(TAG, "Update result" + result);
                updateCallback.accept(true);
            } else {
                updateCallback.accept(false);
            }
        });

    }

    @Override
    public void deleteAsync(Post post, Consumer<Boolean> deleteCallback) {
        executorService.execute(() -> {
            int result = mDatabase.delete(POST_TABLE_NAME,
                    COLUMN_ID + " = ?",
                    new String[]{post.getId().toString()}
            );
            if (result > 0) {
                Log.d(TAG, "Delete result" + result);
                deleteCallback.accept(true);
            } else {
                deleteCallback.accept(false);
            }
        });
    }

}
