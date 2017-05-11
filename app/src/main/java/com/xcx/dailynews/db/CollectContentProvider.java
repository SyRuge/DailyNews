package com.xcx.dailynews.db;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.xcx.dailynews.MyApplication;
import com.xcx.dailynews.bean.CollectBean;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.StandardDatabase;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/* Copy this code snippet into your AndroidManifest.xml inside the <application> element:
    <provider
        android:name="de.greenrobot.daotest.SimpleEntityContentProvider"
        android:authorities="de.greenrobot.daotest" />
*/

public class CollectContentProvider extends ContentProvider {
    public CollectContentProvider() {
    }

    public static final String AUTHORITY = "com.xcx.CollectContentProvider";
    public static final String BASE_PATH = "COLLECT_BEAN";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/" + BASE_PATH;
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/" + BASE_PATH;

    private static final String TABLENAME = CollectBeanDao.TABLENAME;
    private static final String PK = CollectBeanDao.Properties.Id.columnName;

    private static final int COLLECTBEANDAO_DIR = 0;
    private static final int COLLECTBEANDAO_ID = 1;

    private static final UriMatcher sURIMatcher;

    static {
        sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH, COLLECTBEANDAO_DIR);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", COLLECTBEANDAO_ID);
    }

    /**
     * This must be set from outside, it's recommended to do this inside your Application object.
     * Subject to change (static isn't nice).
     */
    //  public static DaoSession daoSession;
    @Override
    public boolean onCreate() {
        // if(daoSession == null) {
        //     throw new IllegalStateException("DaoSession must be set before content provider is
        // created");
        // }
        return true;
    }

    protected Database getDatabase() {
        /*if (daoSession == null) {
            throw new IllegalStateException("DaoSession must be set during content provider is " +
                    "active");
        }*/
        return MyApplication.getMyApplication().getDaoSession().getDatabase();
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case COLLECTBEANDAO_DIR:
                DaoSession session = MyApplication.getMyApplication().getDaoSession();
                QueryBuilder<CollectBean> qb = session.getCollectBeanDao().queryBuilder();
                qb.where(CollectBeanDao.Properties.Url.eq(values.getAsString("url")));
                List<CollectBean> list = qb.list();
                if (list == null || list.size() == 0) {
                    CollectBean cb = new CollectBean(null,
                            values.getAsString("url"),
                            values.getAsString("channelid"),
                            values.getAsInteger("position"),
                            values.getAsString("title"),
                            values.getAsString("source"),
                            values.getAsString("digest"),
                            values.getAsString("lmodify"));
                    long insert = session.getCollectBeanDao().insert(cb);
                }

                break;
            case COLLECTBEANDAO_ID:
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);

        }
        getContext().getContentResolver().notifyChange(uri, null);
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        DaoSession session = MyApplication.getMyApplication().getDaoSession();
        QueryBuilder<CollectBean> qb = session.getCollectBeanDao().queryBuilder();
        qb.where(CollectBeanDao.Properties.Url.eq(selectionArgs[0]));
        List<CollectBean> list = qb.list();
        if (list != null || list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                session.getCollectBeanDao().delete(list.get(i));

            }
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case COLLECTBEANDAO_DIR:
                queryBuilder.setTables(TABLENAME);
                break;
            case COLLECTBEANDAO_ID:
                queryBuilder.setTables(TABLENAME);
                queryBuilder.appendWhere(PK + "="
                        + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        Database db = getDatabase();
        Cursor cursor = queryBuilder.query(((StandardDatabase) db).getSQLiteDatabase(),
                projection, selection,
                selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public final String getType(Uri uri) {
        switch (sURIMatcher.match(uri)) {
            case COLLECTBEANDAO_DIR:
                return CONTENT_TYPE;
            case COLLECTBEANDAO_ID:
                return CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }
}
