package io.github.ynagarjuna1995.levelup2.di.modules;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;
import io.github.ynagarjuna1995.levelup2.di.scopes.ActivityContext;
import io.github.ynagarjuna1995.levelup2.di.scopes.PerActivity;
import io.github.ynagarjuna1995.levelup2.ui.main.LandMarksAdapter;
import io.github.ynagarjuna1995.levelup2.ui.main.MainActivity;
import io.github.ynagarjuna1995.levelup2.ui.main.MainMvpPresenter;
import io.github.ynagarjuna1995.levelup2.ui.main.MainMvpView;
import io.github.ynagarjuna1995.levelup2.ui.main.MainPresenter;
import io.github.ynagarjuna1995.levelup2.ui.nearest.NearestLandmarkFragment;
import io.github.ynagarjuna1995.levelup2.ui.nearest.NearestMVPPresenter;
import io.github.ynagarjuna1995.levelup2.ui.nearest.NearestMVPView;
import io.github.ynagarjuna1995.levelup2.ui.nearest.NearestPresenter;
import io.github.ynagarjuna1995.levelup2.ui.search.AddressPredictionsAdapter;
import io.github.ynagarjuna1995.levelup2.ui.search.ISearchMVPView;
import io.github.ynagarjuna1995.levelup2.ui.search.ISearchPresenter;
import io.github.ynagarjuna1995.levelup2.ui.search.SearchActivity;
import io.github.ynagarjuna1995.levelup2.ui.search.SearchPresenter;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    NearestLandmarkFragment nearestLandmarkFragment;

    public ActivityModule(AppCompatActivity mActivity) {
        this.mActivity = mActivity;
    }

    @Provides
    @PerActivity
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    @PerActivity
    MainMvpPresenter<MainMvpView> provideMainPresenter(
            MainPresenter<MainMvpView> presenter) {
        return presenter;
    }

    @Provides
    NearestMVPPresenter<NearestMVPView> provideNearestPresenter(
            NearestPresenter<NearestMVPView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    public LandMarksAdapter.AdapterClickListner getRemoveClickListener(AppCompatActivity mainActivity) {
        return (MainActivity) mainActivity;
    }

    @Provides
    @PerActivity
    LandMarksAdapter provideLandMarkAdapter(LandMarksAdapter.AdapterClickListner clickListner) {
        return new LandMarksAdapter(mActivity,clickListner);
    }


    /*Search Screen*/

    @Provides
    ISearchPresenter<ISearchMVPView> provideSearchPresenter(
            SearchPresenter<ISearchMVPView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    public AddressPredictionsAdapter.AdapterClickListner getSelectionClickListener(AppCompatActivity searchactivity) {
        return (SearchActivity) searchactivity;
    }

    @Provides
    @PerActivity
    AddressPredictionsAdapter provideAddressPredictionsAdapter(AddressPredictionsAdapter.AdapterClickListner clickListner) {
        return new AddressPredictionsAdapter(mActivity,clickListner);
    }


}
