package com.lzm.Cajas;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.lzm.Cajas.enums.FloramoFragment;

public class WebViewFragment extends Fragment {
    private static final String URL = "url";

    private String url;
    private MainActivity context;

    public WebViewFragment() {
        // Required empty public constructor
    }

    public static WebViewFragment newInstance(String url) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle args = new Bundle();
        args.putString(URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString(URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = (MainActivity) this.getActivity();
        context.setActiveFragment(FloramoFragment.WEB_VIEW);

        View view = inflater.inflate(R.layout.web_view_fragment, container, false);
        final WebView webView = (WebView) view.findViewById(R.id.webview);

        final LinearLayout spinnerLayout = (LinearLayout) view.findViewById(R.id.webview_progressBarLayout);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                webView.setVisibility(View.GONE);
                spinnerLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webView.setVisibility(View.VISIBLE);
                spinnerLayout.setVisibility(View.GONE);
            }
        });

        webView.loadUrl(url);

        FloatingActionButton openInBrowser = (FloatingActionButton) view.findViewById(R.id.webview_fab_browser);
        openInBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(myIntent);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        context.setTitle(FloramoFragment.WEB_VIEW.getTitleId());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
