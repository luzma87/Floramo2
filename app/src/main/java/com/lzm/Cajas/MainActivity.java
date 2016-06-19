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
import com.lzm.Cajas.db.Especie;
import com.lzm.Cajas.detail.DetailFragment;
import com.lzm.Cajas.encyclopedia.EncyclopediaFragment;
import com.lzm.Cajas.feedback.FeedbackFragment;
import com.lzm.Cajas.helpers.FragmentHelper;
import com.lzm.Cajas.search.SearchFragment;
import com.lzm.Cajas.search.SearchResults;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        EncyclopediaFragment.OnFragmentInteractionListener,
        SearchFragment.OnFragmentInteractionListener {

    public static final int FRAGMENT_ENCYCLOPEDIA = 1;
    public static final int FRAGMENT_DETAILS = 2;
    public static final int FRAGMENT_FEEDBACK = 3;
    public static final int FRAGMENT_SEARCH = 4;
    public static final String SAVED_ACTIVE_FRAGMENT = "activeFragment";
    private static final String SAVED_DETAIL_SPECIES_ID = "detailSpeciesId";
    private static final String SAVED_SEARCH_RESULTS = "searchResults";

    int activeFragment = FRAGMENT_ENCYCLOPEDIA;
    private EncyclopediaFragment encyclopediaFragment;
    private SearchResults searchResults;
    private SearchFragment searchFragment;
    private Long detailSpeciesId;

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
        searchFragment = SearchFragment.newInstance();

        searchResults = new SearchResults(this);
        encyclopediaFragment = EncyclopediaFragment.newInstance();

        openFragment(FRAGMENT_ENCYCLOPEDIA);
    }

    public void setActiveFragment(int activeFragment) {
        this.activeFragment = activeFragment;
        invalidateOptionsMenu();
    }

    public ArrayList<Especie> getEspeciesBusqueda(String sort, String order) {
        searchResults.setSort(sort);
        searchResults.setOrder(order);
        return searchResults.getResults();
    }

    private void openFragment(int fragmentToOpen) {
        openFragment(fragmentToOpen, true);
    }

    private void openFragment(int fragmentToOpen, boolean resetSearch) {
        Fragment fragment = encyclopediaFragment;
        int titleRes = R.string.title_encyclopedia;
        switch (fragmentToOpen) {
            case FRAGMENT_ENCYCLOPEDIA:
                if (resetSearch) {
                    searchResults = new SearchResults(this);
                }
                encyclopediaFragment = EncyclopediaFragment.newInstance();
                fragment = encyclopediaFragment;
                titleRes = R.string.title_encyclopedia;
                break;
            case FRAGMENT_DETAILS:
                fragment = DetailFragment.newInstance(detailSpeciesId);
                titleRes = R.string.title_detail;
                break;
            case FRAGMENT_FEEDBACK:
                fragment = FeedbackFragment.newInstance();
                titleRes = R.string.title_help;
                break;
            case FRAGMENT_SEARCH:
                fragment = searchFragment;
                titleRes = R.string.title_search;
                break;
        }
        FragmentHelper.openFragment(this, fragment, getString(titleRes), true);
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
                if (encyclopediaSort.equals(SearchResults.SORT_BY_FAMILY)) {
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
                encyclopediaFragment.setSort(SearchResults.SORT_BY_NAME);
                break;
            case R.id.action_sort_family:
                encyclopediaFragment.setSort(SearchResults.SORT_BY_FAMILY);
                break;
            case R.id.action_search:
                if (activeFragment == FRAGMENT_SEARCH) {
                    searchFragment.buttonSearchClick();
                } else {
                    openFragment(FRAGMENT_SEARCH);
                }
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
                openFragment(FRAGMENT_ENCYCLOPEDIA);
                break;
            case R.id.nav_search:
                openFragment(FRAGMENT_SEARCH);
                break;
            case R.id.nav_feedback:
                openFragment(FRAGMENT_FEEDBACK);
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
        detailSpeciesId = speciesId;
        openFragment(FRAGMENT_DETAILS);
    }

    @Override
    public void onSearchPerformed(ArrayList<Long> colors, ArrayList<Long> lifeForms, String text, String conditional) {
        searchResults = new SearchResults(this, colors, lifeForms, text, conditional);
        openFragment(FRAGMENT_ENCYCLOPEDIA, false);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable(SAVED_ACTIVE_FRAGMENT, activeFragment);
        savedInstanceState.putParcelable(SAVED_SEARCH_RESULTS, searchResults);
        if (activeFragment == FRAGMENT_DETAILS) {
            savedInstanceState.putSerializable(SAVED_DETAIL_SPECIES_ID, detailSpeciesId);
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        activeFragment = Integer.parseInt(savedInstanceState.getSerializable(SAVED_ACTIVE_FRAGMENT).toString());
        if (activeFragment == FRAGMENT_DETAILS) {
            detailSpeciesId = Long.parseLong(savedInstanceState.getSerializable(SAVED_DETAIL_SPECIES_ID).toString());
        }
        if (activeFragment == FRAGMENT_ENCYCLOPEDIA) {
            searchResults = savedInstanceState.getParcelable(SAVED_SEARCH_RESULTS);
        }
        openFragment(activeFragment, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
