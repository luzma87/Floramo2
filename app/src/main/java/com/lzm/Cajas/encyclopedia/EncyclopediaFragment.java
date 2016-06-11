package com.lzm.Cajas.encyclopedia;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.lzm.Cajas.MainActivity;
import com.lzm.Cajas.R;
import com.lzm.Cajas.db.Especie;
import com.lzm.Cajas.encyclopedia.indexableList.IndexableListView;

import java.util.List;

public class EncyclopediaFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private List<Especie> especies;
    private MainActivity context;

    public static final String SORT_BY_NAME = "n";
    public static final String SORT_BY_FAMILY = "f";
    public static final String SORT_ASCENDING = "a";

    private String sort = SORT_BY_NAME;
    private EncyclopediaListAdapter adapter;
    private IndexableListView listView;

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
        context.setActiveFragment(MainActivity.FRAGMENT_ENCYCLOPEDIA);

        View view = inflater.inflate(R.layout.fragment_encyclopedia, container, false);
        listView = (IndexableListView) view.findViewById(R.id.listview);

        loadData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Especie selected = especies.get(position);
                Long selectedId = selected.id;
                if (mListener != null) {
                    mListener.onPlantSelected(selectedId);
                }
            }
        });
        return view;
    }

    private void loadData() {
        String order = SORT_ASCENDING;
        especies = Especie.sortedList(context, sort, order);

        listView.setAdapter(null);

        adapter = new EncyclopediaListAdapter(context, especies, sort);
        listView.setAdapter(adapter);
        listView.setFastScrollEnabled(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onPlantSelected(Long speciesId);
    }
}
