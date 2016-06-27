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
import android.widget.TextView;

import com.lzm.Cajas.MainActivity;
import com.lzm.Cajas.R;
import com.lzm.Cajas.enums.FloramoFragment;
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
        context.setActiveFragment(FloramoFragment.TROPICOS);
        View view = inflater.inflate(R.layout.tropicos_fragment, container, false);

        String max = context.getString(R.string.appInfo_tropicos_max_results);
        String info = context.getString(R.string.tropicos_info, max);

        TextView infoView = (TextView) view.findViewById(R.id.tropicos_info);
        infoView.setText(info);

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
                    ProgressDialog dialog = getProgressDialog();
                    ExecutorService queue = Executors.newSingleThreadExecutor();
                    queue.execute(new TropicosSearchLoader(context, name, "", family, common, dialog));
                }
            }
        });
    }

    private ProgressDialog getProgressDialog() {
        String loadingTitle = context.getString(R.string.loading_title);
        String loadingMessage = context.getString(R.string.loading_message);
        return ProgressDialog.show(context, loadingTitle, loadingMessage, true);
    }

    private String getEditTextString(View view, int editText) {
        return ((EditText) view.findViewById(editText)).getText().toString().trim();
    }

    @Override
    public void onResume() {
        super.onResume();
        context.setTitle(FloramoFragment.TROPICOS.getTitleId());
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
