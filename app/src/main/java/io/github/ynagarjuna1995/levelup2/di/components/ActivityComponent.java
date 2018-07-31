package io.github.ynagarjuna1995.levelup2.di.components;

import dagger.Component;
import io.github.ynagarjuna1995.levelup2.di.modules.ActivityModule;
import io.github.ynagarjuna1995.levelup2.di.scopes.PerActivity;
import io.github.ynagarjuna1995.levelup2.ui.map.GoogleMapFragment;
import io.github.ynagarjuna1995.levelup2.ui.nearest.NearestLandmarkFragment;
import io.github.ynagarjuna1995.levelup2.ui.main.MainActivity;
import io.github.ynagarjuna1995.levelup2.ui.search.SearchActivity;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);
    void inject(SearchActivity searchActivity);

    void inject(NearestLandmarkFragment nearestLandmarkFragment);

    void inject (GoogleMapFragment googleMapFragment);

}
