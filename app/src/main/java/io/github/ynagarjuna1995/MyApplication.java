package io.github.ynagarjuna1995;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.maps.MapsInitializer;

import io.github.ynagarjuna1995.levelup2.di.components.ApplicationComponent;
import io.github.ynagarjuna1995.levelup2.di.components.DaggerApplicationComponent;
import io.github.ynagarjuna1995.levelup2.di.modules.ApplicationModule;
import io.github.ynagarjuna1995.levelup2.di.modules.NetworkModule;

public class MyApplication extends Application {

    ApplicationComponent mApplicationComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        initInjector();
    }

    private void initInjector() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .networkModule(new NetworkModule())
                    .build();
            mApplicationComponent.inject(this);
        }
    }

    public static MyApplication get(Context context) {
        return (MyApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }


}
