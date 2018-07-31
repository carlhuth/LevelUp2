package io.github.ynagarjuna1995.levelup2.ui.main;


import io.github.ynagarjuna1995.levelup2.data.models.LandMarkLocations;
import io.github.ynagarjuna1995.levelup2.ui.base.MvpView;

public interface MainMvpView extends MvpView {

    void showNearestLandmarkFragment();

    void showGoogleMapFragment();

    void openSearchActivity();

    void notifyLandMarkDeletion(int pos);

    void showAddedLandMarkInList(LandMarkLocations landMarkLocations);

    void showLimitedReachedMsg();

    void showUserActionInfoText();

    void hideUserActionInfoText();
}
