package com.lzm.Cajas.helpers;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.lzm.Cajas.MainActivity;
import com.lzm.Cajas.R;

public class FragmentHelper {

    public static void openFragment(MainActivity context, Fragment fragment, String title) {
        openFragment(context, fragment, title, null);
    }

    public static void openFragment(MainActivity context, Fragment fragment, String title, Bundle args) {
        openFragment(context, fragment, title, args, true);
    }

    public static void openFragment(MainActivity context, Fragment fragment, String title, boolean backstack) {
        openFragment(context, fragment, title, null, backstack);
    }

    public static void openFragment(MainActivity context, Fragment fragment, String title, Bundle args, boolean backstack) {
        context.setTitle(title);
        FragmentManager fragmentManager = context.getFragmentManager();

        RelativeLayout mapLayout = (RelativeLayout) context.findViewById(R.id.map_layout);
        if (mapLayout == null) {
            return;
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        if (fragment == null) {
            mapLayout.setVisibility(View.VISIBLE);
            fragmentTransaction.hide(fragmentManager.findFragmentById(R.id.fragment_container));
        } else {
            mapLayout.setVisibility(View.GONE);
            if (args != null) {
                fragment.setArguments(args);
            }
            fragmentTransaction.replace(R.id.fragment_container, fragment);
        }
        if (backstack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }
}
