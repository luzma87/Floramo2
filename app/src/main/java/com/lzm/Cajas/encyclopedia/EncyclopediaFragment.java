package com.lzm.Cajas.encyclopedia;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.lzm.Cajas.MainActivity;
import com.lzm.Cajas.R;
import com.lzm.Cajas.db.Especie;
import com.lzm.Cajas.customComponents.indexableList.IndexableListView;
import com.lzm.Cajas.enums.Fragments;
import com.lzm.Cajas.search.SearchResults;

import java.util.List;

public class EncyclopediaFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private List<Especie> especies;
    private MainActivity context;

    private IndexableListView listView;

    private String sort = SearchResults.SORT_BY_NAME;
    private String order = SearchResults.ORDER_ASCENDING;

    public EncyclopediaFragment() {
        // Required empty public constructor
    }

    public void setSort(String sort) {
        this.sort = sort;
        loadData();
    }

    public static EncyclopediaFragment newInstance() {
        return new EncyclopediaFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = (MainActivity) getActivity();
        context.setActiveFragment(Fragments.ENCYCLOPEDIA);

        View view = inflater.inflate(R.layout.encyclopedia_fragment, container, false);
        listView = (IndexableListView) view.findViewById(R.id.listview);

        loadData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Especie selected = especies.get(position);
                Long selectedId = selected.getId();
                if (mListener != null) {
                    mListener.onPlantSelected(selectedId);
                }
            }
        });
        return view;
    }

    public void loadData() {
        especies = context.getEspeciesBusqueda(sort, order);
        listView.setAdapter(null);

        EncyclopediaListAdapter adapter = new EncyclopediaListAdapter(context, especies, sort);
        listView.setAdapter(adapter);
        listView.setFastScrollEnabled(true);

        listView.setVisibility((adapter.isEmpty()) ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onAttachAction(context);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onAttachAction(activity);
        }
    }

    private void onAttachAction(Context context) {
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSearchFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public String getSort() {
        return sort;
    }

    @Override
    public void onResume() {
        super.onResume();
        context.setTitle(Fragments.ENCYCLOPEDIA.getTitleId());
    }

    public interface OnFragmentInteractionListener {
        void onPlantSelected(Long speciesId);
    }
}
