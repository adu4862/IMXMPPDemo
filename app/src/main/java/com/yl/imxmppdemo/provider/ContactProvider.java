package com.yl.imxmppdemo.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Administrator on 2016/10/28 0028.
 */

public class ContactProvider extends ContentProvider {

    private static final String TABLE = "contacts";
    private static final String authority = "com.yl.imxmppdemo.provider.ContactProvider";
    public static final Uri CONTACT_URI = Uri.parse("content://" + authority + "/" + TABLE);
    private static final String DB = "contact3.db";
    private static final String TAG = "ContactProvider";
    private String sql = "create table contacts(_id integer primary key autoincrement,account text,nick text,avatar integer,sort Text)";
    private MyHelper myHelper;
    /**
     * uri匹配器
     */
    private static UriMatcher uriMatcher;
    private SQLiteDatabase db;

    public static class CONTACT implements BaseColumns {//联系人表,封装一些基本字段
        public static final String ACCOUNT = "account";
        public static final String NICK = "nick";
        public static final String AVATAR = "avatar";
        public static final String SORT = "sort";
    }

    public class MyHelper extends SQLiteOpenHelper {

        public MyHelper(Context context) {
            super(context, DB, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(sql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }


    @Override
    public boolean onCreate() {
        myHelper = new MyHelper(getContext());
        return myHelper != null;
    }



    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    //the code that is returned when a URI is matched against the given components. Must be positive.uri匹配成功返回的值
    private static final int CONTACT_CODE = 0;

    //匹配uri是否正确,错误将不执行crud
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(authority, TABLE, CONTACT_CODE);
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int match = uriMatcher.match(uri);
            if (match == CONTACT_CODE) {//匹配成功
                Log.d(TAG, "insert: insert---");
                db = myHelper.getWritableDatabase();
                /**
                 the row ID of the newly inserted row, or -1 if an error occurred
                 */
                long id = db.insert(TABLE, "", values);
                if (id != -1) {
                    ContentUris.withAppendedId(uri, id);//Appends the given ID to the end of the path.
                    getContext().getContentResolver().notifyChange(uri, null);//通知所有内容观察者数据变化
                }

        }

        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        int match = uriMatcher.match(uri);
        if (match == CONTACT_CODE) {//匹配成功
            Log.d(TAG, "delete: delete---");
            db = myHelper.getWritableDatabase();
             count =  db.delete(TABLE, selection, selectionArgs);
            if (count>0) {//删除成功
                getContext().getContentResolver().notifyChange(uri, null);//通知所有内容观察者数据变化
            }

        }
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = 0;
        int match = uriMatcher.match(uri);
        if (match == CONTACT_CODE) {//匹配成功
            Log.d(TAG, "update: update---");
            db = myHelper.getWritableDatabase();
            count = db.update(TABLE, values, selection, selectionArgs);
            if (count>0) {//删除成功
                getContext().getContentResolver().notifyChange(uri, null);//通知所有内容观察者数据变化
            }

        }
        return count;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor query = null;
        int count = 0;
        int match = uriMatcher.match(uri);
        if (match == CONTACT_CODE) {//匹配成功
            Log.d(TAG, "update: update---");
            db = myHelper.getReadableDatabase();
            query= db.query(TABLE, projection, selection, selectionArgs, null, null, sortOrder);
        }
        return query;
    }
}
