package com.lzm.Cajas;

import android.app.Fragment;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.lzm.Cajas.db.DbHelper;
import com.lzm.Cajas.detail.DetailFragment;
import com.lzm.Cajas.encyclopedia.EncyclopediaFragment;
import com.lzm.Cajas.help.HelpFragment;
import com.lzm.Cajas.helpers.FragmentHelper;
import com.lzm.Cajas.search.SearchFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        EncyclopediaFragment.OnFragmentInteractionListener {

    public static final int FRAGMENT_ENCYCLOPEDIA = 1;
    public static final int FRAGMENT_DETAILS = 2;
    public static final int FRAGMENT_HELP = 3;
    public static final int FRAGMENT_SEARCH = 4;

    int activeFragment = FRAGMENT_ENCYCLOPEDIA;
    private EncyclopediaFragment encyclopediaFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        DbHelper helper = new DbHelper(this);
        helper.getWritableDatabase();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        if (drawer != null) {
            drawer.setDrawerListener(toggle);
        }
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }

        encyclopediaFragment = EncyclopediaFragment.newInstance();
        FragmentHelper.openFragment(this, encyclopediaFragment, getString(R.string.title_encyclopedia), false);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem itemSearch = menu.findItem(R.id.action_search);
        Drawable searchIcon = DrawableCompat.wrap(itemSearch.getIcon());
        ColorStateList colorSelector = ContextCompat.getColorStateList(this, R.color.white);
        DrawableCompat.setTintList(searchIcon, colorSelector);
        itemSearch.setIcon(searchIcon);

        MenuItem itemSortFamily = menu.findItem(R.id.action_sort_family);
        MenuItem itemSortName = menu.findItem(R.id.action_sort_name);
        if (itemSortName != null) {
            if (activeFragment == FRAGMENT_ENCYCLOPEDIA) {
                String encyclopediaSort = encyclopediaFragment.getSort();
                if (encyclopediaSort.equals(EncyclopediaFragment.SORT_BY_FAMILY)) {
                    itemSortName.setVisible(true);
                    itemSortFamily.setVisible(false);
                } else {
                    itemSortName.setVisible(false);
                    itemSortFamily.setVisible(true);
                }
            } else {
                itemSortName.setVisible(false);
                itemSortFamily.setVisible(false);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_sort_name:
                encyclopediaFragment.setSort(EncyclopediaFragment.SORT_BY_NAME);
                break;
            case R.id.action_sort_family:
                encyclopediaFragment.setSort(EncyclopediaFragment.SORT_BY_FAMILY);
                break;
            case R.id.action_search:
                SearchFragment searchFragment = SearchFragment.newInstance();
                FragmentHelper.openFragment(this, searchFragment, getString(R.string.title_search));
                break;
        }
        invalidateOptionsMenu();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_encyclopedia:
                FragmentHelper.openFragment(this, encyclopediaFragment, getString(R.string.title_detail));
                break;
            case R.id.nav_help:
                HelpFragment helpFragment = HelpFragment.newInstance();
                FragmentHelper.openFragment(this, helpFragment, getString(R.string.title_help));
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    @Override
    public void onPlantSelected(Long speciesId) {
        Fragment newFragment = DetailFragment.newInstance(speciesId);
        FragmentHelper.openFragment(this, newFragment, getString(R.string.title_detail));
    }

    public void setActiveFragment(int activeFragment) {
        this.activeFragment = activeFragment;
        invalidateOptionsMenu();
    }
}
