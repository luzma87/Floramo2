package com.lzm.Cajas.credits;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzm.Cajas.MainActivity;
import com.lzm.Cajas.R;
import com.lzm.Cajas.enums.FloramoFragment;
import com.lzm.Cajas.helpers.ResourcesHelper;
import com.lzm.Cajas.helpers.TextHelper;

import java.io.IOException;

public class AboutFragment extends Fragment {
    public static final String ABOUT_TYPE = "aboutType";
    private MainActivity context;

    private FloramoFragment aboutType;

    public AboutFragment() {
        // Required empty public constructor
    }

    public static AboutFragment newInstance(FloramoFragment aboutType) {
        AboutFragment fragment = new AboutFragment();
        Bundle args = new Bundle();
        args.putSerializable(ABOUT_TYPE, aboutType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            aboutType = (FloramoFragment) getArguments().getSerializable(ABOUT_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = (MainActivity) getActivity();
        context.setActiveFragment(aboutType);

        View view = inflater.inflate(R.layout.about_fragment, container, false);

        String path = "";
        int textId = 0;

        switch (aboutType) {
            case ABOUT_PARAMO:
                path = "paramo.jpg";
                textId = R.string.about_paramo;
                break;
            case ABOUT_CAJAS:
                path = "cajas.jpg";
                textId = R.string.about_cajas;
                break;
            case ABOUT_QUITO:
                path = "quito.jpg";
                textId = R.string.about_quito;
                break;
            case ABOUT_APP:
                path = "paramo.jpg";
                textId = R.string.about_app;
                break;
        }
        try {
            ImageView imageView = (ImageView) view.findViewById(R.id.about_image);
            Bitmap bitmap = ResourcesHelper.getAboutAssetByName(context, path);
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setBarTitle(view);

        String text = getString(textId);
        TextView textView = (TextView) view.findViewById(R.id.about_text);
        TextHelper.setTextWithLinks(context, textView, text);
        return view;
    }

    private void setBarTitle(View view) {
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) view.findViewById(R.id.about_collapsing_toolbar);
        collapsingToolbar.setTitle(getString(aboutType.getTitleId()));
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
    }

    @Override
    public void onResume() {
        super.onResume();
        context.setTitle(R.string.title_about);
    }
}
