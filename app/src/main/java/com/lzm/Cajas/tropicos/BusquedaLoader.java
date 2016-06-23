package com.lzm.Cajas.tropicos;

import android.app.ProgressDialog;

import com.lzm.Cajas.MainActivity;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by luz on 6/22/16.
 */
public class BusquedaLoader implements Runnable {
    private final String name;
    private final String nameId;
    private final String family;
    private final String common;
    private final ProgressDialog dialog;
    private final MainActivity context;

    public BusquedaLoader(MainActivity context, String name, String nameId, String family, String common, ProgressDialog dialog) {
        this.context = context;
        this.name = name;
        this.nameId = nameId;
        this.family = family;
        this.common = common;
        this.dialog = dialog;
    }


    @Override
    public void run() {
        String urlstr = "http://services.tropicos.org/Name/Search";
        String key = "34a6225b-552c-4e0b-9937-fe12a2541176";

        URL url = null;
        try {
            url = new URL("http://some-server");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                conn.setRequestMethod("POST");
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        }

// read the response
        try {
            if (conn != null) {
                System.out.println("Response Code: " + conn.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream in = null;
        try {
            if (conn != null) {
                in = new BufferedInputStream(conn.getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String response = null;
        try {
            response = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(response);
    }
}
