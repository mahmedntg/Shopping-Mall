package com.example.anas.shoppingmall;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.anas.shoppingmall.utillity.Mall;
import com.example.anas.shoppingmall.utillity.Store;
import com.example.anas.shoppingmall.utillity.StoreAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StoreActivity extends BaseActivity {
    ArrayList<Store> storeList;
    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    private StoreAdapter mAdapter;
    private ProgressDialog progressDialog;
    private boolean adminUser = false;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("store");
        storeList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.myRecyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new StoreAdapter(storeList, StoreActivity.this);
        recyclerView.setAdapter(mAdapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        String email = firebaseAuth.getCurrentUser().getEmail();
        displayMenu(R.id.viewLocation);
        if (email.equals("m@m.com")) {
            fab.setVisibility(View.VISIBLE);
            displayMenu(R.id.addEventItemMenu);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(StoreActivity.this, AddStoreActivity.class);
                startActivity(newIntent);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fab.getVisibility() == View.VISIBLE) {
                    fab.hide();
                } else if (dy < 0 && fab.getVisibility() != View.VISIBLE) {
                    fab.show();
                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        databaseReference.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        storeList.clear();

                        Log.w("TodoApp", "getUser:onCancelled " + dataSnapshot.toString());
                        Log.w("TodoApp", "count = " + String.valueOf(dataSnapshot.getChildrenCount()) + " values " + dataSnapshot.getKey());
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            Store store = data.getValue(Store.class);
                            store.setKey(data.getKey());
                            storeList.add(store);

                        }
                        final LayoutAnimationController controller =
                                AnimationUtils.loadLayoutAnimation(getApplicationContext(), R.anim.layout_animation_fall_down);

                        recyclerView.setLayoutAnimation(controller);
                        mAdapter.notifyDataSetChanged();
                        recyclerView.scheduleLayoutAnimation();
                        progressDialog.hide();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("TodoApp", "getUser:onCancelled", databaseError.toException());
                        progressDialog.hide();
                    }
                });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }


}
