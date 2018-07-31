package io.github.ynagarjuna1995.levelup2.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.MapsInitializer;

import org.w3c.dom.Text;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.ynagarjuna1995.MyApplication;
import io.github.ynagarjuna1995.levelup2.R;
import io.github.ynagarjuna1995.levelup2.data.models.AddressPredictions;
import io.github.ynagarjuna1995.levelup2.data.models.GooglePlacesGeocoding;
import io.github.ynagarjuna1995.levelup2.data.models.LandMarkLocations;
import io.github.ynagarjuna1995.levelup2.di.components.ActivityComponent;
import io.github.ynagarjuna1995.levelup2.di.components.DaggerActivityComponent;
import io.github.ynagarjuna1995.levelup2.di.modules.ActivityModule;
import io.github.ynagarjuna1995.levelup2.ui.base.BaseActivity;
import io.github.ynagarjuna1995.levelup2.ui.map.GoogleMapFragment;
import io.github.ynagarjuna1995.levelup2.ui.nearest.NearestLandmarkFragment;
import io.github.ynagarjuna1995.levelup2.ui.search.SearchActivity;

public class MainActivity extends BaseActivity implements MainMvpView , LandMarksAdapter.AdapterClickListner{


    @Inject
    MainPresenter<MainMvpView> mMainActivityPresenter;

    @Inject
    LandMarksAdapter mLandMarksAdapter;

    @BindView(R.id.landmarks_recyclerview)
    RecyclerView mLandmarksRecyclerview;

    @BindView(R.id.user_action_info_text)
    TextView mUserActionTv;

    ActivityComponent mActivityComponent;

    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        injectActivityComponent();
        mActivityComponent.inject(this);
        setUnBinder(ButterKnife.bind(this));
        setupAdddressPredictionRecycler();
        showUserActionInfoText();
        MapsInitializer.initialize(getApplicationContext());
        mMainActivityPresenter.onAttach(this);

    }


    @OnClick({R.id.nearest_points_btn, R.id.animate_on_map_btn, R.id.landmark_search_view})
    public void handleClickEvents(Button button) {
        switch (button.getId()) {
            case R.id.nearest_points_btn:
                mMainActivityPresenter.showNearestLandMarkFragment();
                break;
            case R.id.animate_on_map_btn:
                mMainActivityPresenter.showGoogleMapFragment();
                break;
            case R.id.landmark_search_view:
                mMainActivityPresenter.openSearcActivityForResult();
                break;
        }
    }

    private void setupAdddressPredictionRecycler() {
        mLandmarksRecyclerview.setAdapter(mLandMarksAdapter);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLandmarksRecyclerview.setLayoutManager(mLinearLayoutManager);
    }

    private void injectActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(((MyApplication)getApplication()).getComponent())
                    .build();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data !=null) {
            AddressPredictions addressPredictions = data.getExtras().getParcelable("address");
            mMainActivityPresenter.parseLocationFromPlaceId(addressPredictions);
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment f = fragmentManager.findFragmentById(R.id.cl_root_view);
        if (f == null) {
            super.onBackPressed();
        } else if (f instanceof NearestLandmarkFragment) {
            onFragmentDetached(NearestLandmarkFragment.TAG);
        } else if (f instanceof GoogleMapFragment) {
            onFragmentDetached(GoogleMapFragment.TAG);
        }
    }

    protected void onDestroy() {
        mMainActivityPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void onFragmentAttached() {
    }

    @Override
    public void onFragmentDetached(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            fragmentManager
                    .beginTransaction()
                    .disallowAddToBackStack()
                    .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                    .remove(fragment)
                    .commitNow();
        }
    }

    @Override
    public void showNearestLandmarkFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                .add(R.id.cl_root_view, NearestLandmarkFragment.newInstance(mMainActivityPresenter.getLandMarkLocationsArrayList()), NearestLandmarkFragment.TAG)
                .commit();
    }

    @Override
    public void showGoogleMapFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                .add(R.id.cl_root_view, GoogleMapFragment.newInstance(mMainActivityPresenter.getLandMarkLocationsArrayList()), GoogleMapFragment.TAG)
                .commit();
    }

    @Override
    public void openSearchActivity() {
        startActivityForResult(SearchActivity.getStartIntent(this),400);
    }

    @Override
    public void notifyLandMarkDeletion(int pos) {
        mLandMarksAdapter.removeAt(pos);
    }

    @Override
    public void showAddedLandMarkInList(LandMarkLocations landMarkLocations) {
        mLandMarksAdapter.add(landMarkLocations);
    }

    @Override
    public void showLimitedReachedMsg() {
        Toast.makeText(this, "You have already added 10 locations", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUserActionInfoText() {
        if (mUserActionTv.getVisibility()== View.GONE)
             mUserActionTv.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideUserActionInfoText() {
        if (mUserActionTv.getVisibility()== View.VISIBLE)
            mUserActionTv.setVisibility(View.GONE);
    }

    @Override
    public void removeLandMark(int position) {
        mMainActivityPresenter.removeLandMarkInList(position);
    }
}
