package com.lzm.Cajas.tropicos;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lzm.Cajas.MainActivity;
import com.lzm.Cajas.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TropicosSearchResultFragment extends Fragment {

    private static final String JSON_RESPONSE = "json_response";

    private OnTropicosSearchResultFragmentInteractionListener mListener;

    private String jsonResponse;

    private TextView emptyView;

    public TropicosSearchResultFragment() {
    }

    public static TropicosSearchResultFragment newInstance(String response) {
        TropicosSearchResultFragment fragment = new TropicosSearchResultFragment();
        Bundle args = new Bundle();
        args.putString(JSON_RESPONSE, response);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            jsonResponse = getArguments().getString(JSON_RESPONSE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MainActivity activity = (MainActivity) this.getActivity();
        activity.setActiveFragment(MainActivity.FRAGMENT_TROPICOS_RESULTS);

        View view = inflater.inflate(R.layout.tropicos_search_result_fragment, container, false);
        // Set the adapter
        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.tropicos_search_result_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        List<TropicosSearchResult> items = new ArrayList<>();

        try {
            JSONArray arr = new JSONArray(jsonResponse);
            items = new ArrayList<>();
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                TropicosSearchResult curent = new TropicosSearchResult(obj.getString("NameId"), obj.getString("ScientificName"), obj.getString("ScientificNameWithAuthors"), obj.getString("Family"), obj.getString("RankAbbreviation"), obj.getString("Author"), obj.getString("DisplayReference"), obj.getString("DisplayDate"));
                items.add(curent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        TropicosSearchResultItemRecyclerViewAdapter adapter = new TropicosSearchResultItemRecyclerViewAdapter(items, mListener);
        recyclerView.setAdapter(adapter);

        recyclerView.setVisibility((adapter.isEmpty()) ? View.GONE : View.VISIBLE);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTropicosSearchResultFragmentInteractionListener) {
            mListener = (OnTropicosSearchResultFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnTropicosSearchResultFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnTropicosSearchResultFragmentInteractionListener {
        void onTropicosItemClicked(TropicosSearchResult item);
    }
}
