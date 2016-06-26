package com.lzm.Cajas;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.lzm.Cajas.enums.Fragments;
import com.lzm.Cajas.feedback.FeedbackFragment;
import com.lzm.Cajas.helpers.FragmentHelper;
import com.lzm.Cajas.search.SearchFragment;
import com.lzm.Cajas.search.SearchResults;
import com.lzm.Cajas.tropicos.TropicosSearchResult;
import com.lzm.Cajas.tropicos.TropicosSearchResultFragment;
import com.lzm.Cajas.tropicos.TropicosFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        EncyclopediaFragment.OnFragmentInteractionListener,
        DetailFragment.OnFragmentInteractionListener,
        SearchFragment.OnFragmentInteractionListener,
        TropicosSearchResultFragment.OnTropicosSearchResultFragmentInteractionListener {

    public static final String SAVED_ACTIVE_FRAGMENT = "activeFragment";
    private static final String SAVED_DETAIL_SPECIES_ID = "detailSpeciesId";
    private static final String SAVED_SEARCH_RESULTS = "searchResults";
    private static final String SAVED_URL = "savedUrl";

    Fragments activeFragment = Fragments.ENCYCLOPEDIA;
    private EncyclopediaFragment encyclopediaFragment;
    private SearchResults searchResults;
    private SearchFragment searchFragment;
    private Long detailSpeciesId;
    private String url;

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

        openFragment(Fragments.ENCYCLOPEDIA);
    }

    public void setActiveFragment(Fragments activeFragment) {
        this.activeFragment = activeFragment;
        invalidateOptionsMenu();
    }

    public ArrayList<Especie> getEspeciesBusqueda(String sort, String order) {
        searchResults.setSort(sort);
        searchResults.setOrder(order);
        return searchResults.getResults();
    }

    private void openFragment(Fragments fragmentToOpen) {
        openFragment(fragmentToOpen, true);
    }

    private void openFragment(Fragments fragmentToOpen, boolean resetSearch) {
        Fragment fragment = encyclopediaFragment;
        int titleRes = R.string.title_encyclopedia;
        switch (fragmentToOpen) {
            case ENCYCLOPEDIA:
                if (resetSearch) {
                    searchResults = new SearchResults(this);
                }
                encyclopediaFragment = EncyclopediaFragment.newInstance();
                fragment = encyclopediaFragment;
                titleRes = Fragments.ENCYCLOPEDIA.getTitleId();
                break;
            case DETAILS:
                fragment = DetailFragment.newInstance(detailSpeciesId);
                titleRes = Fragments.DETAILS.getTitleId();
                break;
            case FEEDBACK:
                fragment = FeedbackFragment.newInstance();
                titleRes = Fragments.FEEDBACK.getTitleId();
                break;
            case SEARCH:
                fragment = searchFragment;
                titleRes = Fragments.SEARCH.getTitleId();
                break;
            case TROPICOS:
                fragment = TropicosFragment.newInstance();
                titleRes = Fragments.TROPICOS.getTitleId();
                break;
            case WEB_VIEW:
                fragment = WebViewFragment.newInstance(url);
                titleRes = Fragments.WEB_VIEW.getTitleId();
        }
//        navigationView.getMenu().getItem(fragmentToOpen).setChecked(true);
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
            if (activeFragment == Fragments.ENCYCLOPEDIA) {
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
                if (activeFragment == Fragments.SEARCH) {
                    searchFragment.buttonSearchClick();
                } else {
                    openFragment(Fragments.SEARCH);
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
                openFragment(Fragments.ENCYCLOPEDIA);
                break;
            case R.id.nav_search:
                openFragment(Fragments.SEARCH);
                break;
            case R.id.nav_tropicos:
                openFragment(Fragments.TROPICOS);
                break;
            case R.id.nav_feedback:
                openFragment(Fragments.FEEDBACK);
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
        openFragment(Fragments.DETAILS);
    }

    @Override
    public void onSearchPerformed(ArrayList<Long> colors, ArrayList<Long> lifeForms, String text, String conditional) {
        searchResults = new SearchResults(this, colors, lifeForms, text, conditional);
        openFragment(Fragments.ENCYCLOPEDIA, false);
    }

    @Override
    public void onDetailTropicosClicked(String url) {
        this.url = url;
        openFragment(Fragments.WEB_VIEW, false);
    }

    public void onTropicosSearchPerformed(final String response, final ProgressDialog dialog) {
        final MainActivity activity = this;
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                Fragment fragment = TropicosSearchResultFragment.newInstance(response);
                FragmentHelper.openFragment(activity, fragment, getString(R.string.title_tropicos));
            }
        });
    }

    @Override
    public void onTropicosItemClicked(TropicosSearchResult item) {
        String baseUrl = getString(R.string.tropicos_base_url);
        url = baseUrl + item.getNameId();
        openFragment(Fragments.WEB_VIEW, false);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable(SAVED_ACTIVE_FRAGMENT, activeFragment);
        savedInstanceState.putParcelable(SAVED_SEARCH_RESULTS, searchResults);
        if (activeFragment == Fragments.DETAILS) {
            savedInstanceState.putSerializable(SAVED_DETAIL_SPECIES_ID, detailSpeciesId);
        } else if (activeFragment == Fragments.WEB_VIEW) {
            savedInstanceState.putString(SAVED_URL, url);
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        activeFragment = (Fragments) savedInstanceState.getSerializable(SAVED_ACTIVE_FRAGMENT);
        if (activeFragment == Fragments.DETAILS) {
            detailSpeciesId = Long.parseLong(savedInstanceState.getSerializable(SAVED_DETAIL_SPECIES_ID).toString());
        }
        if (activeFragment == Fragments.ENCYCLOPEDIA) {
            searchResults = savedInstanceState.getParcelable(SAVED_SEARCH_RESULTS);
        }
        if (activeFragment == Fragments.WEB_VIEW) {
            url = savedInstanceState.getString(SAVED_URL);
        }
        openFragment(activeFragment, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
