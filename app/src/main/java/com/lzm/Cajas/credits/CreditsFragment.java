package com.lzm.Cajas.credits;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzm.Cajas.MainActivity;
import com.lzm.Cajas.R;
import com.lzm.Cajas.enums.FloramoFragment;

public class CreditsFragment extends Fragment {

    private MainActivity context;

    public CreditsFragment() {
        // Required empty public constructor
    }

    public static CreditsFragment newInstance() {
        return new CreditsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = (MainActivity) getActivity();
        context.setActiveFragment(FloramoFragment.CREDITS);
        return inflater.inflate(R.layout.credits_fragment, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        context.setTitle(FloramoFragment.CREDITS.getTitleId());
    }
}
