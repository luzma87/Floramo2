package com.lzm.Cajas.helpers;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

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
        if (args != null) {
            fragment.setArguments(args);
        }
        if (backstack) {
            fragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack("")
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
