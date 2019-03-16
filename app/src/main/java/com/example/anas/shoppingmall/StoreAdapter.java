package com.example.mhamedsayed.shoppingmall;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by mhamedsayed on 3/15/2019.
 */

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreViewHolder> {
    private List<Store> stores;
    private Activity activity;


    public StoreAdapter(List<Store> stores, Activity activity) {
        this.stores = stores;
        this.activity = activity;
    }

    @Override
    public StoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.store_item, parent, false);

        return new StoreViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StoreViewHolder viewHolder, int position) {
        Store model = stores.get(position);
        viewHolder.setName(model.getName());
        viewHolder.setImage(activity, model.getImage());
        viewHolder.setName(model.getName());
        viewHolder.setOpenTime(model.getOpenTime());
        viewHolder.setCloseTime(model.getCloseTime());
        viewHolder.setCategory(model.getCategory());
        viewHolder.position = position;
    }


    @Override
    public int getItemCount() {
        return stores.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public class StoreViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View mView;
        public int position;

        public StoreViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setName(String name) {
            TextView textView = (TextView) mView.findViewById(R.id.storeName);
            textView.setOnClickListener(this);
            textView.setText(name);
        }

        public void setOpenTime(String openTime) {
            TextView textView = (TextView) mView.findViewById(R.id.openTime);
            textView.setOnClickListener(this);
            textView.setText("Opening: " + openTime);
        }

        public void setCloseTime(String closeTime) {
            TextView textView = (TextView) mView.findViewById(R.id.closeTime);
            textView.setOnClickListener(this);
            textView.setText("Closing: " + closeTime);
        }


        public void setImage(Context ctx, String image) {
            ImageView imageView = (ImageView) mView.findViewById(R.id.storeImage);
            imageView.setOnClickListener(this);
            Picasso.with(ctx).load(image).into(imageView);
        }


        public void setCategory(String category) {
            TextView textView = (TextView) mView.findViewById(R.id.category);
            textView.setOnClickListener(this);
            textView.setText(category);
        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(activity, StoreDetailsActivity.class);
            intent.putExtra("store", stores.get(position));
            activity.startActivity(intent);
        }

    }

}
