package com.zhongtie.work.ui.file.select;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;

import com.zhongtie.work.network.Network;
import com.zhongtie.work.util.Util;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Flowable;

import static android.provider.BaseColumns._ID;
import static android.provider.MediaStore.Files.FileColumns.MIME_TYPE;
import static android.provider.MediaStore.MediaColumns.DATA;
import static android.provider.MediaStore.MediaColumns.DATE_ADDED;
import static android.provider.MediaStore.MediaColumns.SIZE;
import static android.provider.MediaStore.MediaColumns.TITLE;

public class FileLoaderCallbacks implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final int TYPE_IMAGE = 0;
    public static final int TYPE_VIDEO = 1;
    public static final int TYPE_AUDIO = 2;
    public static final int TYPE_FILE = 3;

    private WeakReference<Context> context;
    private FilterResultCallback resultCallback;

    private int mType = TYPE_IMAGE;
    private String[] mSuffixArgs;
    private CursorLoader mLoader;
    private String mSuffixRegex;

    public FileLoaderCallbacks(Context context, FilterResultCallback resultCallback, int type) {
        this(context, resultCallback, type, null);
    }

    public FileLoaderCallbacks(Context context, FilterResultCallback resultCallback, int type, String[] suffixArgs) {
        this.context = new WeakReference<>(context);
        this.resultCallback = resultCallback;
        this.mType = type;
        this.mSuffixArgs = suffixArgs;
        if (suffixArgs != null && suffixArgs.length > 0) {
            mSuffixRegex = obtainSuffixRegex(suffixArgs);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        mLoader = new FileLoader(context.get());
        Log.e("-------------", "onCreateLoader: ");
        return mLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data == null) return;
        Log.e("-------------", "onLoadFinished: ");
        onFileResult(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @SuppressWarnings("unchecked")
    private void onFileResult(Cursor data) {
        Log.e("-------------", "onFileResult: ");
        Flowable.fromCallable(new Callable<List<Directory<NormalFile>>>() {
            @Override
            public List<Directory<NormalFile>> call() throws Exception {
                List<Directory<NormalFile>> directories = new ArrayList<>();
                if (data.getPosition() != -1) {
                    data.moveToPosition(-1);
                }
                while (data.moveToNext()) {
                    String path = data.getString(data.getColumnIndexOrThrow(DATA));
                    if (path != null && contains(path)) {
                        //Create a File instance
                        NormalFile file = new NormalFile();
                        file.setId(data.getLong(data.getColumnIndexOrThrow(_ID)));
                        file.setName(data.getString(data.getColumnIndexOrThrow(TITLE)));
                        file.setPath(data.getString(data.getColumnIndexOrThrow(DATA)));
                        file.setSize(data.getLong(data.getColumnIndexOrThrow(SIZE)));
                        file.setDate(data.getLong(data.getColumnIndexOrThrow(DATE_ADDED)));

                        file.setMimeType(data.getString(data.getColumnIndexOrThrow(MIME_TYPE)));

                        //Create a Directory
                        Directory<NormalFile> directory = new Directory<>();
                        directory.setName(Util.extractFileNameWithSuffix(Util.extractPathWithoutSeparator(file.getPath())));
                        directory.setPath(Util.extractPathWithoutSeparator(file.getPath()));

                        if (!directories.contains(directory)) {
                            directory.addFile(file);
                            directories.add(directory);
                        } else {
                            directories.get(directories.indexOf(directory)).addFile(file);
                        }
                    }
                }
                return directories;
            }
        }).compose(Network.netorkIO())
                .subscribe(directories -> {
                    if (resultCallback != null) {
                        resultCallback.onResult(directories);
                    }
                }, throwable -> {

                });


    }

    private boolean contains(String path) {
        String name = Util.extractFileNameWithSuffix(path);
        Pattern pattern = Pattern.compile(mSuffixRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    private String obtainSuffixRegex(String[] suffixes) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < suffixes.length; i++) {
            if (i == 0) {
                builder.append(suffixes[i].replace(".", ""));
            } else {
                builder.append("|\\.");
                builder.append(suffixes[i].replace(".", ""));
            }
        }
        return ".+(\\." + builder.toString() + ")$";
    }
}