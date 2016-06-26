package com.lzm.Cajas.tropicos;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.lzm.Cajas.MainActivity;
import com.lzm.Cajas.R;
import com.lzm.Cajas.helpers.Utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TropicosFragment extends Fragment {

    private MainActivity context;

    public TropicosFragment() {
        // Required empty public constructor
    }

    public static TropicosFragment newInstance() {
        return new TropicosFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = (MainActivity) this.getActivity();
        context.setActiveFragment(MainActivity.FRAGMENT_TROPICOS);
        View view = inflater.inflate(R.layout.tropicos_fragment, container, false);
        setTropicosSearchClick(view);
        return view;
    }

    private void setTropicosSearchClick(final View view) {
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.tropicos_fab_search);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = getEditTextString(view, R.id.tropicos_name);
                String common = getEditTextString(view, R.id.tropicos_common_name);
                String family = getEditTextString(view, R.id.tropicos_family);

                Utils.hideSoftKeyboard(context);

                if (name.length() == 0 && common.length() == 0 && family.length() == 0) {
                    Snackbar.make(v, R.string.tropicos_search_warning, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    String loadingTitle = context.getString(R.string.loading_title);
                    String loadingMessage = context.getString(R.string.loading_message);
                    ProgressDialog dialog = ProgressDialog.show(context, loadingTitle, loadingMessage, true);
                    ExecutorService queue = Executors.newSingleThreadExecutor();
                    queue.execute(new TropicosSearchLoader(context, name, "", family, common, dialog));
                }
            }
        });
    }

    private String getEditTextString(View view, int editText) {
        return ((EditText) view.findViewById(editText)).getText().toString().trim();
    }

    @Override
    public void onResume() {
        super.onResume();
        context.setTitle(MainActivity.FRAGMENT_TROPICOS_TITLE);
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
