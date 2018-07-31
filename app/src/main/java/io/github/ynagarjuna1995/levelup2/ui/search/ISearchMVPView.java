package io.github.ynagarjuna1995.levelup2.ui.search;

import java.util.ArrayList;

import io.github.ynagarjuna1995.levelup2.data.models.AddressPredictions;
import io.github.ynagarjuna1995.levelup2.ui.base.MvpView;

public interface ISearchMVPView  extends MvpView{

    void showAddressPredictions(ArrayList<AddressPredictions> addressPredictionsArrayList);
    void sendAddressAsResult(AddressPredictions predictions);

}
