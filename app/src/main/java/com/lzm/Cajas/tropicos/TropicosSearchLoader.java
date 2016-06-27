package com.lzm.Cajas.tropicos;

import android.app.ProgressDialog;

import com.lzm.Cajas.MainActivity;
import com.lzm.Cajas.R;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * <p/>
 * Here is your API key:
 * 34a6225b-552c-4e0b-9937-fe12a2541176
 * Best wishes,
 * Heather
 * Missouri Botanical Garden
 * <p/>
 * Help is here:
 * http://services.tropicos.org/help
 * Example calls (using your api key):
 * name search:
 * http://services.tropicos.org/Name/Search?name=poa+annua&type=wildcard&apikey=34a6225b-552c-4e0b-9937-fe12a2541176&format=xml
 * <p/>
 * name detail:
 * http://services.tropicos.org/Name/25509881?apikey=34a6225b-552c-4e0b-9937-fe12a2541176&format=xml
 * <p/>
 * synonyms:
 * http://services.tropicos.org/Name/25509881/Synonyms?apikey=34a6225b-552c-4e0b-9937-fe12a2541176&format=xml
 * <p/>
 * accepted names:
 * http://services.tropicos.org/Name/25503923/AcceptedNames?apikey=34a6225b-552c-4e0b-9937-fe12a2541176&format=xml
 */
public class TropicosSearchLoader implements Runnable {
    private final String name;
    private final String nameId;
    private final String family;
    private final String common;
    private final ProgressDialog dialog;
    private final MainActivity context;

    public TropicosSearchLoader(MainActivity context, String name, String nameId, String family, String common, ProgressDialog dialog) {
        this.context = context;
        this.name = name;
        this.nameId = nameId;
        this.family = family;
        this.common = common;
        this.dialog = dialog;
    }

    @Override
    public void run() {
        String response = "";
        try {
            URL url = new URL(getUrl());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (conn != null) {
                conn.setRequestMethod("GET");
                if (conn.getResponseCode() == 200) {
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    response = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        context.onTropicosSearchPerformed(response, dialog);
    }

    private String getUrl() {
        String urlstr = context.getString(R.string.appInfo_tropicos_url);
        String key = context.getString(R.string.appInfo_tropicos_api_key);
        String parameters = getParams();
        if (parameters.contains("nameid")) {
            parameters += "&type=exact";
        } else {
            parameters += "&type=wildcard";
        }
        parameters += "&format=json";
        parameters += "&apikey=" + key;
        parameters += "&pagesize=" + context.getString(R.string.appInfo_tropicos_max_results);
        urlstr += "?" + parameters;
        return urlstr;
    }

    private String getParams() {
        String parameters = "";
        parameters = updateParams(parameters, "name", name);
        parameters = updateParams(parameters, "nameid", nameId);
        parameters = updateParams(parameters, "commonname", common);
        parameters = updateParams(parameters, "family", family);
        return parameters;
    }

    private String updateParams(String parameters, String paramName, String property) {
        property = property.trim();
        if (!property.equals("")) {
            if (!parameters.equals("")) {
                parameters += "&";
            }
            parameters += paramName + "=" + property;
        }
        return parameters;
    }
}
