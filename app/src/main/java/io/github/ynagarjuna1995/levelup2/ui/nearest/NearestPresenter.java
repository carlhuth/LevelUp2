package io.github.ynagarjuna1995.levelup2.ui.nearest;

import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.inject.Inject;

import io.github.ynagarjuna1995.levelup2.data.AppDataManager;
import io.github.ynagarjuna1995.levelup2.data.models.LandMarkLocations;
import io.github.ynagarjuna1995.levelup2.ui.base.BasePresenter;
import io.reactivex.disposables.CompositeDisposable;

public class NearestPresenter<V extends NearestMVPView> extends BasePresenter<V> implements NearestMVPPresenter<V> {



    @Inject
    public NearestPresenter(AppDataManager appDataManager,
                            CompositeDisposable compositeDisposable) {

        super(appDataManager, compositeDisposable);

    }


    @Override
    public void sortLocations(ArrayList<LandMarkLocations> landMarkLocations) {


        getMvpView().showNearestLocations(genericSort(landMarkLocations));
    }

    private ArrayList<LandMarkLocations> genericSort(ArrayList<LandMarkLocations> landMarkLocations) {
        Double shortestDistance = null;
        Integer iSmallest = null;
        Integer jSmallest = null;

        for(Integer i = 0; i < landMarkLocations.size()-1; i++) {
            for (Integer j = 0; j < landMarkLocations.size(); j++) {
                if (i == j) continue;
                Double distance = SphericalUtil.computeDistanceBetween(
                        landMarkLocations.get(i).latLng,
                        landMarkLocations.get(j).latLng);

                if (shortestDistance == null || shortestDistance > distance) {
                    shortestDistance = distance;
                    iSmallest = i;
                    jSmallest = j;
                }
            }
        }

        ArrayList<LandMarkLocations> nearestLocations = new ArrayList<LandMarkLocations>();

        nearestLocations.add(landMarkLocations.get(iSmallest));
        nearestLocations.add(landMarkLocations.get(jSmallest));

        return nearestLocations;
    }


    // Merges sort implementation

}
