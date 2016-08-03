package com.lzm.Cajas;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterManager;
import com.lzm.Cajas.credits.AboutFragment;
import com.lzm.Cajas.credits.CreditsFragment;
import com.lzm.Cajas.credits.FeedbackFragment;
import com.lzm.Cajas.db.DbHelper;
import com.lzm.Cajas.db.Especie;
import com.lzm.Cajas.detail.DetailFragment;
import com.lzm.Cajas.encyclopedia.EncyclopediaFragment;
import com.lzm.Cajas.enums.FloramoFragment;
import com.lzm.Cajas.helpers.FragmentHelper;
import com.lzm.Cajas.map.EspecieLoader;
import com.lzm.Cajas.map.EspecieMarker;
import com.lzm.Cajas.map.EspecieRenderer;
import com.lzm.Cajas.search.SearchFragment;
import com.lzm.Cajas.search.SearchResults;
import com.lzm.Cajas.tropicos.TropicosFragment;
import com.lzm.Cajas.tropicos.TropicosSearchResult;
import com.lzm.Cajas.tropicos.TropicosSearchResultFragment;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.lzm.Cajas.enums.FloramoFragment.*;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback,
        EncyclopediaFragment.OnEncyclopediaInteractionListener,
        DetailFragment.OnDetailInteractionListener,
        SearchFragment.OnSearchInteractionListener,
        TropicosSearchResultFragment.OnTropicosSearchResultInteractionListener {

    public static final String SAVED_ACTIVE_FRAGMENT = "activeFragment";
    private static final String SAVED_DETAIL_SPECIES_ID = "detailSpeciesId";
    private static final String SAVED_SEARCH_RESULTS = "searchResults";
    private static final String SAVED_URL = "savedUrl";

    private static final String VERSION_FOR_DIALOG = "versionForDialog";

    FloramoFragment activeFragment = ENCYCLOPEDIA;
    private EncyclopediaFragment encyclopediaFragment;
    private SearchResults searchResults;
    private SearchFragment searchFragment;
    private Long detailSpeciesId;
    private String url;

    private GoogleMap googleMap;

    private ClusterManager<EspecieMarker> clusterManager;
    private float clusterZoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);

        manageNewsDialog();

        DbHelper helper = new DbHelper(this);
        helper.getWritableDatabase();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        if (drawer != null) {
            drawer.addDrawerListener(toggle);
        }
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }
        searchFragment = SearchFragment.newInstance();

        searchResults = new SearchResults(this);
        encyclopediaFragment = EncyclopediaFragment.newInstance();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        int titleRes = ENCYCLOPEDIA.getTitleId();
        FragmentHelper.openFragment(this, encyclopediaFragment, getString(titleRes), false);
    }

    private void manageNewsDialog() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String currentVersion = getAppVersion();
        String lastVersionStored = sharedPref.getString(VERSION_FOR_DIALOG, "0");

        if (!lastVersionStored.equalsIgnoreCase(currentVersion)) {
            showNewsDialog();
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(VERSION_FOR_DIALOG, currentVersion);
            editor.apply();
        }
    }

    private String getAppVersion() {
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            return getString(R.string.app_version, version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void showNewsDialog() {
        AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
        alertbox.setTitle(getString(R.string.aa_new_title));
        alertbox.setMessage(getString(R.string.aa_new));
        alertbox.setNegativeButton(getString(R.string.global_close), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        alertbox.show();
    }

    public void setActiveFragment(FloramoFragment activeFragment) {
        this.activeFragment = activeFragment;
        invalidateOptionsMenu();
    }

    public ArrayList<Especie> getEspeciesBusqueda(String sort, String order) {
        searchResults.setSort(sort);
        searchResults.setOrder(order);
        return searchResults.getResults();
    }

    private void openFragment(FloramoFragment fragmentToOpen) {
        openFragment(fragmentToOpen, true);
    }

    private void openFragment(FloramoFragment fragmentToOpen, boolean resetSearch) {
        Fragment fragment = encyclopediaFragment;
        int titleRes = fragmentToOpen.getTitleId();
        switch (fragmentToOpen) {
            case ENCYCLOPEDIA:
                if (resetSearch) {
                    searchResults = new SearchResults(this);
                }
                encyclopediaFragment = EncyclopediaFragment.newInstance();
                fragment = encyclopediaFragment;
                break;
            case DETAILS:
                fragment = DetailFragment.newInstance(detailSpeciesId);
                break;
            case FEEDBACK:
                fragment = FeedbackFragment.newInstance();
                break;
            case SEARCH:
                fragment = searchFragment;
                break;
            case TROPICOS:
                fragment = TropicosFragment.newInstance();
                break;
            case ABOUT_PARAMO:
                fragment = AboutFragment.newInstance(fragmentToOpen);
                titleRes = R.string.title_about;
                break;
            case ABOUT_CAJAS:
                fragment = AboutFragment.newInstance(fragmentToOpen);
                titleRes = R.string.title_about;
                break;
            case ABOUT_QUITO:
                fragment = AboutFragment.newInstance(fragmentToOpen);
                titleRes = R.string.title_about;
                break;
            case ABOUT_APP:
                fragment = AboutFragment.newInstance(fragmentToOpen);
                titleRes = R.string.title_about;
                break;
            case CREDITS:
                fragment = CreditsFragment.newInstance();
                break;
            case WEB_VIEW:
                fragment = WebViewFragment.newInstance(url);
                break;
            case MAP:
                fragment = null;
                setActiveFragment(fragmentToOpen);
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
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem itemSearch = menu.findItem(R.id.action_search);
        Drawable searchIcon = DrawableCompat.wrap(itemSearch.getIcon());
        ColorStateList colorSelector = ContextCompat.getColorStateList(this, R.color.white);
        DrawableCompat.setTintList(searchIcon, colorSelector);
        itemSearch.setIcon(searchIcon);

        MenuItem itemSortFamily = menu.findItem(R.id.action_sort_family);
        MenuItem itemSortName = menu.findItem(R.id.action_sort_name);

        if (itemSortName != null) {
            if (activeFragment == ENCYCLOPEDIA) {
                String encyclopediaSort = encyclopediaFragment.getSort();
                if (encyclopediaSort.equals(SearchResults.SORT_BY_FAMILY)) {
                    itemSortName.setVisible(true);
                    itemSortFamily.setVisible(false);
                } else {
                    itemSortName.setVisible(false);
                    itemSortFamily.setVisible(true);
                }
            } else if (activeFragment == MAP) {
                itemSortName.setVisible(false);
                itemSortFamily.setVisible(false);
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
                if (activeFragment == SEARCH) {
                    searchFragment.buttonSearchClick();
                } else {
                    openFragment(SEARCH);
                }
                break;
        }
        invalidateOptionsMenu();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_encyclopedia:
                openFragment(ENCYCLOPEDIA);
                break;
            case R.id.nav_search:
                openFragment(SEARCH);
                break;
            case R.id.nav_tropicos:
                openFragment(TROPICOS);
                break;
            case R.id.nav_about_paramo:
                openFragment(ABOUT_PARAMO);
                break;
            case R.id.nav_about_cajas:
                openFragment(ABOUT_CAJAS);
                break;
            case R.id.nav_about_quito:
                openFragment(ABOUT_QUITO);
                break;
            case R.id.nav_about_app:
                openFragment(ABOUT_APP);
                break;
            case R.id.nav_credits:
                openFragment(CREDITS);
                break;
            case R.id.nav_feedback:
                openFragment(FEEDBACK);
                break;
            case R.id.nav_map:
                openFragment(MAP);
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
        openFragment(DETAILS);
    }

    @Override
    public void onSearchPerformed(ArrayList<Long> colors, ArrayList<Long> lifeForms, String text, String conditional) {
        searchResults = new SearchResults(this, colors, lifeForms, text, conditional);
        openFragment(ENCYCLOPEDIA, false);
    }

    @Override
    public void onDetailTropicosClicked(String url) {
        this.url = url;
        openFragment(WEB_VIEW, false);
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
        String baseUrl = getString(R.string.appInfo_tropicos_base_url);
        url = baseUrl + item.getNameId();
        openFragment(WEB_VIEW, false);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable(SAVED_ACTIVE_FRAGMENT, activeFragment);
        savedInstanceState.putParcelable(SAVED_SEARCH_RESULTS, searchResults);
        if (activeFragment == DETAILS) {
            savedInstanceState.putSerializable(SAVED_DETAIL_SPECIES_ID, detailSpeciesId);
        } else if (activeFragment == WEB_VIEW) {
            savedInstanceState.putString(SAVED_URL, url);
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        activeFragment = (FloramoFragment) savedInstanceState.getSerializable(SAVED_ACTIVE_FRAGMENT);
        if (activeFragment == DETAILS) {
            detailSpeciesId = Long.parseLong(savedInstanceState.getSerializable(SAVED_DETAIL_SPECIES_ID).toString());
        }
        if (activeFragment == ENCYCLOPEDIA) {
            searchResults = savedInstanceState.getParcelable(SAVED_SEARCH_RESULTS);
        }
        if (activeFragment == WEB_VIEW) {
            url = savedInstanceState.getString(SAVED_URL);
        }
        openFragment(activeFragment, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        LatLng latLng = new LatLng(-2.84360424, -79.2282486);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
        setUpClusterer();
    }

    private void setUpClusterer() {
        clusterManager = new ClusterManager<>(this, googleMap);
        clusterManager.setRenderer(new EspecieRenderer<>(this, googleMap, clusterManager));
        googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                clusterZoom = cameraPosition.zoom;
                clusterManager.onCameraChange(cameraPosition);
            }
        });
        googleMap.setOnMarkerClickListener(clusterManager);
        createSpeciesMarkers();
    }

    private void createSpeciesMarkers() {
        ArrayList<Especie> especies = (ArrayList<Especie>) Especie.list(this);
        for (Especie especie : especies) {
            ExecutorService queue = Executors.newSingleThreadExecutor();
            queue.execute(new EspecieLoader(this, especie));
        }
    }

    public float getClusterZoom() {
        return clusterZoom;
    }

    public void addSpeciesMarker(final EspecieMarker especieMarker) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                clusterManager.addItem(especieMarker);
            }
        });
    }
}
