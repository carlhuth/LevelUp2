package io.github.ynagarjuna1995.levelup2.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.ynagarjuna1995.levelup2.R;
import io.github.ynagarjuna1995.levelup2.data.models.AddressPredictions;
import io.github.ynagarjuna1995.levelup2.data.models.LandMarkLocations;

public class LandMarksAdapter extends RecyclerView.Adapter<LandMarksAdapter.LandMarkViewHolder> {

    List<LandMarkLocations> mLandMarksList;
    private LandMarksAdapter.AdapterClickListner mClickListner;
    private Context mContext;
    private boolean deleteAllowed = true;

    @Inject
    public LandMarksAdapter(Context mContext, AdapterClickListner clickListner) {
        this.mLandMarksList = new ArrayList<>();
        this.mClickListner =  clickListner;
        this.mContext = mContext;
    }

    public void add(LandMarkLocations landMarkLocation) {
        mLandMarksList.add(landMarkLocation);
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<LandMarkLocations> landMarkLocations) {
        mLandMarksList.clear();
        deleteAllowed = false;
        mLandMarksList.addAll(landMarkLocations);
        notifyDataSetChanged();
    }

    public void removeAt(int pos) {
        mLandMarksList.remove(pos);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos, mLandMarksList.size());
    }

    @NonNull
    @Override
    public LandMarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_landmarks_view, parent, false);
        return new LandMarkViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LandMarkViewHolder holder, int position) {
        LandMarkLocations location = mLandMarksList.get(position);
        holder.mLandmarkDescriptionTv.setText(location.description);
        holder.mLandmarkPointsTv.setText(location.lat + " , " + location.lng);

        if (!deleteAllowed) {
            holder.mLandmarkRemoveBtn.setVisibility(View.GONE);
        } else {
            holder.mLandmarkRemoveBtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mLandMarksList.size();
    }

    public class LandMarkViewHolder  extends RecyclerView.ViewHolder{

        @BindView(R.id.landmark_description)
        TextView mLandmarkDescriptionTv;

        @BindView(R.id.landmark_latlong)
        TextView mLandmarkPointsTv;

        @BindView(R.id.landmark_remove_btn)
        ImageButton mLandmarkRemoveBtn;

        public LandMarkViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            mLandmarkRemoveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mClickListner.removeLandMark(getAdapterPosition());
                }
            });
        }

    }

    public interface AdapterClickListner {
                void removeLandMark(int position);
    }
}
