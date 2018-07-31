package io.github.ynagarjuna1995.levelup2.ui.main;

import io.github.ynagarjuna1995.levelup2.data.models.AddressPredictions;
import io.github.ynagarjuna1995.levelup2.data.models.GooglePlacesGeocoding;
import io.github.ynagarjuna1995.levelup2.di.scopes.PerActivity;
import io.github.ynagarjuna1995.levelup2.ui.base.MvpPresenter;

@PerActivity
public interface MainMvpPresenter<V extends MainMvpView> extends MvpPresenter<V> {

   void openSearcActivityForResult();

   void showNearestLandMarkFragment();

   void showGoogleMapFragment();

   void parseLocationFromPlaceId(AddressPredictions addressPredictions);

   void onViewInitialized();

   void onLandMarkLimitReached();

   void removeLandMarkInList(int pos);

}
