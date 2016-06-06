package com.lzm.Cajas;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzm.Cajas.adapters.EncyclopediaListAdapter;
import com.lzm.Cajas.db.Especie;
import com.lzm.Cajas.indexableList.IndexableListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

public class EncyclopediaFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private List<Especie> especies;
    private MainActivity context;
    private String sort = "n";
    private String order = "a";
    private LinkedHashMap<String, Integer> mapIndex;
    private EncyclopediaListAdapter adapter;
    private IndexableListView listView;

    public EncyclopediaFragment() {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.fragment_encyclopedia, container, false);

        especies = Especie.sortedList(context, sort, order);

        listView = (IndexableListView) view.findViewById(R.id.listview);
        adapter = new EncyclopediaListAdapter(context, especies, sort);

        listView.setAdapter(adapter);
        listView.setFastScrollEnabled(true);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
