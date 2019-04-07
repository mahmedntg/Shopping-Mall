package com.example.anas.shoppingmall.utillity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.anas.shoppingmall.LocationActivity;
import com.example.anas.shoppingmall.R;

import java.util.Date;
import java.util.List;

/**
 * Created by mhamedsayed on 3/15/2019.
 */

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {
    private List<LocationModel> locationModels;
    private Activity activity;


    public LocationAdapter(List<LocationModel> locationModels, Activity activity) {
        this.locationModels = locationModels;
        this.activity = activity;
    }

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_item, parent, false);

        return new LocationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LocationViewHolder viewHolder, int position) {
        LocationModel model = locationModels.get(position);
        viewHolder.setName(model.getPlaceName());
        viewHolder.setTitle(model.getTitle());
        viewHolder.setDate(model.getDate());
        viewHolder.setDelete();
        viewHolder.position = position;
    }


    @Override
    public int getItemCount() {
        return locationModels.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public class LocationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View mView;
        public int position;

        public LocationViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setName(String name) {
            TextView textView = (TextView) mView.findViewById(R.id.name);
            textView.setOnClickListener(this);
            textView.setText(name);
        }

        public void setTitle(String title) {
            TextView textView = (TextView) mView.findViewById(R.id.title);
            textView.setOnClickListener(this);
            textView.setText(title);
        }

        public void setDate(Date date) {
            TextView textView = (TextView) mView.findViewById(R.id.date);
            textView.setOnClickListener(this);
            textView.setText(SharedUtils.formatDate(date));
        }

        public void setDelete() {
            TextView textView = (TextView) mView.findViewById(R.id.delete);
            textView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            LocationModel locationModel = locationModels.get(position);
            switch (v.getId()) {
                case R.id.delete:
                    LocationActivity locationActivity = (LocationActivity) activity;
                    locationActivity.deleteTodoItem(position, locationModel.getId());
                    break;
                default:
                    openLocation(locationModel.getLatitude(), locationModel.getLongitude(), locationModel.getPlaceName());
                    break;

            }
        }

        private void openLocation(double latitude, double longitude, String name) {
            String strUri = "http://maps.google.com/maps?q=loc:" + latitude + "," + longitude + " (" + name + ")";
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));

            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            activity.startActivity(intent);
        }
    }

}
