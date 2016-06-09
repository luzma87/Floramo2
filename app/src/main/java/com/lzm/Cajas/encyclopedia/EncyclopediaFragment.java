package com.lzm.Cajas.encyclopedia;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
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
        MainActivity context = (MainActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_encyclopedia, container, false);

        String sort = "n";
        String order = "a";
        especies = Especie.sortedList(context, sort, order);

        IndexableListView listView = (IndexableListView) view.findViewById(R.id.listview);
        EncyclopediaListAdapter adapter = new EncyclopediaListAdapter(context, especies, sort);

        listView.setAdapter(adapter);
        listView.setFastScrollEnabled(true);

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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onPlantSelected(Long speciesId);
    }
}
