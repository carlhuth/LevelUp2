package io.github.ynagarjuna1995.levelup2.ui.search;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.ynagarjuna1995.levelup2.R;
import io.github.ynagarjuna1995.levelup2.data.models.AddressPredictions;

public class AddressPredictionsAdapter extends RecyclerView.Adapter<AddressPredictionsAdapter.PredictionsViewHolder>  {

    List<AddressPredictions> mLandMarksList;
    private AddressPredictionsAdapter.AdapterClickListner mClickListner;
    private Context mContext;

    @Inject
    public AddressPredictionsAdapter(Context context, AdapterClickListner clickListner) {
        this.mLandMarksList = new ArrayList<>();
        this.mClickListner =  clickListner;
        mContext = context;
    }

    public void add(ArrayList<AddressPredictions> googleAddressPredictions) {
        mLandMarksList.clear();
        mLandMarksList.addAll(googleAddressPredictions);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PredictionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_address_prediction, parent, false);
        return new PredictionsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PredictionsViewHolder holder, int position) {
//        GooglePlacesGeocoding location = mLandMarksList.get(position);

        AddressPredictions location = mLandMarksList.get(position);
        holder.mLandmarkDescriptionTv.setText(location.description);
        holder.mLandmarkPlaceIdTv.setText(location.placeId);

//        holder.mLandmarkDescriptionTv.setText(location.results.get(0).formattedAddress);
//        holder.mLandmarkPlaceIdTv.setText(location.results.get(0).geometry.location.lat + "," + location.results.get(0).geometry.location.lat);

    }

    @Override
    public int getItemCount() {
        return mLandMarksList.size();
    }

    public void clear() {
        mLandMarksList.clear();
        notifyDataSetChanged();
    }

    public class PredictionsViewHolder  extends RecyclerView.ViewHolder{

        @BindView(R.id.landmark_description)
        TextView mLandmarkDescriptionTv;

        @BindView(R.id.landmark_placeid)
        TextView mLandmarkPlaceIdTv;

        public PredictionsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mClickListner.selectedAddress(mLandMarksList.get(getAdapterPosition()));
                }
            });
        }

    }

    public interface AdapterClickListner {
        //        void removeLandMark(int position, GooglePlacesGeocoding geocoding);
        void selectedAddress(AddressPredictions geocoding);
    }
}
