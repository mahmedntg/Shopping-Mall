package com.example.anas.shoppingmall;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.anas.shoppingmall.utillity.Mall;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity implements MenuItem.OnMenuItemClickListener {
    private FrameLayout view_stub; //This is the framelayout to keep your content view
    private NavigationView navigation_view; // The new navigation view from Android Design Library. Can inflate menu resources. Easy
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Menu drawerMenu;
    protected FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.drawer);// The base layout that contains your navigation drawer.
        firebaseAuth = FirebaseAuth.getInstance();
        view_stub = (FrameLayout) findViewById(R.id.view_stub);
        navigation_view = (NavigationView) findViewById(R.id.navigation_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, 0, 0);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerMenu = navigation_view.getMenu();
        for (int i = 0; i < drawerMenu.size(); i++) {
            drawerMenu.getItem(i).setOnMenuItemClickListener(this);
        }

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /* Override all setContentView methods to put the content view to the FrameLayout view_stub
     * so that, we can make other activity implementations looks like normal activity subclasses.
     */
    @Override
    public void setContentView(int layoutResID) {
        if (view_stub != null) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            View stubView = inflater.inflate(layoutResID, view_stub, false);
            view_stub.addView(stubView, lp);
        }
    }

    @Override
    public void setContentView(View view) {
        if (view_stub != null) {
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            view_stub.addView(view, lp);
        }
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if (view_stub != null) {
            view_stub.addView(view, params);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mDrawerLayout.closeDrawers();
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        progressDialog.show();

        switch (item.getItemId()) {
            case R.id.signOutItemMenu:
                firebaseAuth.signOut();
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.addEventItemMenu:
                mDrawerLayout.closeDrawers();
                startActivity(new Intent(this, AddEventActivity.class));
                break;
            case R.id.viewLocation:
                mDrawerLayout.closeDrawers();
                startActivity(new Intent(this, LocationActivity.class));
                break;
            case R.id.addLocation:
                mDrawerLayout.closeDrawers();
                startActivity(new Intent(this, AddMapActivity.class));
                break;
            case R.id.viewStore:
                mDrawerLayout.closeDrawers();
                startActivity(new Intent(this, StoreActivity.class));
                break;

            case R.id.openLocationItemMenu:
                mDrawerLayout.closeDrawers();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                database.getReference("mall").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Mall mall = dataSnapshot.getValue(Mall.class);
                        openLocation(mall.getLatitude(), mall.getLongitude(), mall.getName());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                break;
        }
        progressDialog.hide();
        return super.onOptionsItemSelected(item);
    }

    private void openLocation(double latitude, double longitude, String name) {
        String strUri = "http://maps.google.com/maps?q=loc:" + latitude + "," + longitude + " (" + name + ")";
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));

        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

        startActivity(intent);
    }

    protected void hideMenu(int id) {

        drawerMenu.findItem(id).setVisible(false);
    }

    protected void displayMenu(int id) {

        drawerMenu.findItem(id).setVisible(true);
    }

    public void setNavigationDrawerState(boolean isEnabled) {
        if (isEnabled) {
            if (!mDrawerToggle.isDrawerIndicatorEnabled()) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                mDrawerLayout.closeDrawer(DrawerLayout.LOCK_MODE_UNLOCKED);
                mDrawerToggle.setDrawerIndicatorEnabled(true);
                mDrawerToggle.syncState();
            }
        } else {

            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            mDrawerToggle.setDrawerIndicatorEnabled(false);
            mDrawerToggle.syncState();

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //       setNavigationDrawerState(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //   setNavigationDrawerState(false);
    }


}
