package com.lzm.Cajas.tropicos;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzm.Cajas.MainActivity;
import com.lzm.Cajas.R;
import com.lzm.Cajas.enums.FloramoFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TropicosSearchResultFragment extends Fragment {

    private static final String JSON_RESPONSE = "json_response";
    private OnTropicosSearchResultInteractionListener mListener;
    private String jsonResponse;
    private MainActivity activity;

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
        activity = (MainActivity) this.getActivity();
        activity.setActiveFragment(FloramoFragment.TROPICOS_RESULTS);

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
                JSONObject row = arr.getJSONObject(i);
                TropicosSearchResult curent = new TropicosSearchResult(row);
                items.add(curent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TropicosSearchResultItemAdapter adapter = new TropicosSearchResultItemAdapter(items, mListener);
        recyclerView.setAdapter(adapter);

        recyclerView.setVisibility((adapter.isEmpty()) ? View.GONE : View.VISIBLE);
        return view;
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
        if (context instanceof OnTropicosSearchResultInteractionListener) {
            mListener = (OnTropicosSearchResultInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() +
                    " must implement OnTropicosSearchResultInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.setTitle(FloramoFragment.TROPICOS_RESULTS.getTitleId());
    }

    public interface OnTropicosSearchResultInteractionListener {
        void onTropicosItemClicked(TropicosSearchResult item);
    }
}
