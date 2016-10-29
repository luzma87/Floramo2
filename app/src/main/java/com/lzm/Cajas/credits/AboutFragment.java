package com.lzm.Cajas.credits;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzm.Cajas.MainActivity;
import com.lzm.Cajas.R;
import com.lzm.Cajas.enums.FloramoFragment;
import com.lzm.Cajas.helpers.ResourcesHelper;
import com.lzm.Cajas.helpers.TextHelper;

import java.io.IOException;

public class AboutFragment extends Fragment {
    private MainActivity context;

    protected FloramoFragment aboutType;
    protected int textId;
    protected int photoTitleId;
    protected String path;

    public AboutFragment() {
        // Required empty public constructor
    }

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = (MainActivity) getActivity();
        context.setActiveFragment(aboutType);

        View view = inflater.inflate(R.layout.about_fragment, container, false);

        setImage(view);
        setBarTitle(view);
        setText(view);
        return view;
    }

    private void setText(View view) {
        String text = getString(textId);
        TextView textView = (TextView) view.findViewById(R.id.about_text);
        TextHelper.setTextWithLinks(context, textView, text);
    }

    private void setImage(View view) {
        try {
            ImageView imageView = (ImageView) view.findViewById(R.id.about_image);
            Bitmap bitmap = ResourcesHelper.getAboutAssetByName(context, path);
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setBarTitle(View view) {
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) view.findViewById(R.id.about_collapsing_toolbar);
        collapsingToolbar.setTitle(getString(photoTitleId));
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
    }

    @Override
    public void onResume() {
        super.onResume();
        context.setTitle(R.string.title_about);
    }
}
