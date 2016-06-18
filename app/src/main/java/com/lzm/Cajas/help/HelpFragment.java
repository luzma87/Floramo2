package com.lzm.Cajas.help;

import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.lzm.Cajas.R;
import com.lzm.Cajas.MainActivity;

import org.apache.commons.lang.WordUtils;

public class HelpFragment extends Fragment {

    private MainActivity context;
    private EditText userInputTxt;

    public HelpFragment() {
        // Required empty public constructor
    }

    public static HelpFragment newInstance() {
        return new HelpFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = (MainActivity) getActivity();
        context.setActiveFragment(MainActivity.FRAGMENT_HELP);
        View view = inflater.inflate(R.layout.help_fragment, container, false);

        TextView appVersionLbl = (TextView) view.findViewById(R.id.app_version);
        TextView androidVersionLbl = (TextView) view.findViewById(R.id.android_version);
        TextView deviceInfoLbl = (TextView) view.findViewById(R.id.device_info);
        userInputTxt = (EditText) view.findViewById(R.id.help_comments);

        appVersionLbl.setText(getAppVersion());
        androidVersionLbl.setText(getAndroidVersion());
        deviceInfoLbl.setText(getDeviceName());

        FloatingActionButton sendHelp = (FloatingActionButton) view.findViewById(R.id.help_send);
        sendHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendFeedbackAction(view);
            }
        });
        return view;
    }

    private void sendFeedbackAction(View view) {
        String userInput = userInputTxt.getText().toString().trim();
        if (userInput.equals("")) {
            String errorMessage = context.getString(R.string.help_comments_error);
            Snackbar.make(view, errorMessage, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            sendEmail(getEmailContents(userInput));
        }
    }

    private void sendEmail(String contents) {
        String address = getString(R.string.contact_mail);
        String subject = getString(R.string.help_subject);
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", address, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, contents);
        startActivity(Intent.createChooser(emailIntent, getString(R.string.title_help)));
    }

    private String getEmailContents(String userInput) {
        return getAppVersion() + "\n" +
                getAndroidVersion() + "\n" +
                getDeviceName() + "\n\n" +
                userInput;
    }

    private String getAndroidVersion() {
        String myVersion = Build.VERSION.RELEASE; // e.g. myVersion := "1.6"
        String sdkVersion = String.valueOf(Build.VERSION.SDK_INT); // e.g. sdkVersion := 8;
        return getString(R.string.android_version, myVersion, sdkVersion);
    }

    private String getAppVersion() {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            String version = pInfo.versionName;
            return getString(R.string.app_version, version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        String product = Build.PRODUCT;
        String brand = Build.BRAND;
        if (model.startsWith(manufacturer)) {
            return WordUtils.capitalizeFully(model) + " (" + brand + " " + product + ")";
        } else {
            return WordUtils.capitalizeFully(manufacturer) + " " + model + " (" + brand + " " + product + ")";
        }
    }
}
