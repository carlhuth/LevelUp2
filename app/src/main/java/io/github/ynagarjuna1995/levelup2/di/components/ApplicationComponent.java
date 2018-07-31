package io.github.ynagarjuna1995.levelup2.di.components;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import io.github.ynagarjuna1995.MyApplication;
import io.github.ynagarjuna1995.levelup2.data.AppDataManager;
import io.github.ynagarjuna1995.levelup2.di.modules.ApplicationModule;
import io.github.ynagarjuna1995.levelup2.di.modules.NetworkModule;
import io.github.ynagarjuna1995.levelup2.di.scopes.ApplicationContext;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {

    Application application();

    void inject(MyApplication app);

    @ApplicationContext
    Context context();

    AppDataManager dataManager();
}


