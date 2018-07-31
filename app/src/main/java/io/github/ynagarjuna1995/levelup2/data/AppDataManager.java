package io.github.ynagarjuna1995.levelup2.data;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.github.ynagarjuna1995.levelup2.data.api.GoogleAPIHelper;
import io.github.ynagarjuna1995.levelup2.di.scopes.ApplicationContext;

@Singleton
public class AppDataManager {

    private static final String TAG = "AppDataManager";

    private final Context mContext;
    private final GoogleAPIHelper mApiHelper;

    @Inject
    public AppDataManager(@ApplicationContext Context context,
                          GoogleAPIHelper apiHelper) {
        mContext = context;
        mApiHelper = apiHelper;
    }

    public GoogleAPIHelper getApiHelper() {
        return mApiHelper;
    }
}
