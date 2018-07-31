package io.github.ynagarjuna1995.levelup2.ui.nearest;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.ynagarjuna1995.MyApplication;
import io.github.ynagarjuna1995.levelup2.R;
import io.github.ynagarjuna1995.levelup2.data.models.GooglePlacesGeocoding;
import io.github.ynagarjuna1995.levelup2.data.models.LandMarkLocations;
import io.github.ynagarjuna1995.levelup2.di.components.ActivityComponent;
import io.github.ynagarjuna1995.levelup2.di.modules.ActivityModule;
import io.github.ynagarjuna1995.levelup2.ui.base.BaseFragment;
import io.github.ynagarjuna1995.levelup2.ui.main.LandMarksAdapter;

public class NearestLandmarkFragment extends BaseFragment implements NearestMVPView {

    public static final String TAG = "nearestLandmarkFragment" ;

    private static final String LANDMARK_LIST = "landmarkList";
    private ArrayList<LandMarkLocations> mLandMarkList;

    @Inject
    LandMarksAdapter mLandMarksAdapter;

    @BindView(R.id.nearest_landmarks_rv)
    RecyclerView mNearestLandmarksRV;

    private LinearLayoutManager mLinearLayoutManager;

    @Inject
    NearestPresenter<NearestMVPView> mNearestLocPresenter;

    public NearestLandmarkFragment() {
        // Required empty public constructor
    }

    public static NearestLandmarkFragment newInstance(ArrayList<LandMarkLocations> mLandMarkList) {
        NearestLandmarkFragment fragment = new NearestLandmarkFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(LANDMARK_LIST, mLandMarkList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLandMarkList = getArguments().getParcelableArrayList(LANDMARK_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_nearest_landmark, container, false);
        ButterKnife.bind(this,view);
        initInjector(view);
        setupNearestLandMarksRV();
        mNearestLocPresenter.sortLocations(mLandMarkList);
        return view;
    }

    private void setupNearestLandMarksRV() {
        mNearestLandmarksRV.setAdapter(mLandMarksAdapter);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mNearestLandmarksRV.setLayoutManager(mLinearLayoutManager);
    }

    private void initInjector(View view) {
        ActivityComponent component = getActivityComponent();
        if (component !=null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mNearestLocPresenter.onAttach(this);
        }
    }

    @OnClick(R.id.nav_back_btn)
    void onNavBackClick() {
        getBaseActivity().onFragmentDetached(TAG);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showNearestLocations(ArrayList<LandMarkLocations> landMarkLocations) {
        mLandMarksAdapter.addAll(landMarkLocations);
    }
}
