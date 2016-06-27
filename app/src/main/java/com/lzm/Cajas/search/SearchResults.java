package com.lzm.Cajas.search;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.lzm.Cajas.MainActivity;
import com.lzm.Cajas.db.Especie;

import java.util.ArrayList;

public class SearchResults implements Parcelable {

    public static final String SORT_BY_NAME = "n";
    public static final String SORT_BY_FAMILY = "f";
    public static final String ORDER_ASCENDING = "a";
    private MainActivity context;

    ArrayList<Long> colors;
    ArrayList<Long> lifeForms;
    String text;
    String conditional;
    String sort;
    String order;

    ArrayList<Especie> results;

    public SearchResults(MainActivity context) {
        this.context = context;
        this.colors = new ArrayList<>();
        this.lifeForms = new ArrayList<>();
        this.text = "";
        this.conditional = "";
        this.sort = SORT_BY_NAME;
        this.order = ORDER_ASCENDING;
    }

    public SearchResults(MainActivity context, ArrayList<Long> colors, ArrayList<Long> lifeForms, String text, String conditional) {
        this.context = context;
        this.colors = colors;
        this.lifeForms = lifeForms;
        this.text = text;
        this.conditional = conditional;
        this.sort = SORT_BY_NAME;
        this.order = ORDER_ASCENDING;
    }

    public SearchResults(Parcel in) {
        String[] data = new String[6];
        in.readStringArray(data);

        String strColors = data[0];
        String strLifeForms = data[1];

        colors = new ArrayList<>();
        String[] cls = strColors.split(",");
        for (String cl : cls) {
            colors.add(Long.parseLong(cl));
        }

        lifeForms = new ArrayList<>();
        String[] lfs = strLifeForms.split(",");
        for (String lf : lfs) {
            lifeForms.add(Long.parseLong(lf));
        }

        text = data[2];
        conditional = data[3];
        sort = data[4];
        order = data[5];
    }

    public void setContext(MainActivity context) {
        this.context = context;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public ArrayList<Especie> getResults() {
        results = (ArrayList<Especie>) Especie.busqueda(context, lifeForms, colors, text, conditional, sort, order);
        return results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        String strColors = TextUtils.join(",", colors);
        String strLifeForms = TextUtils.join(",", lifeForms);
        dest.writeStringArray(new String[]{
                strColors,
                strLifeForms,
                text,
                conditional,
                sort,
                order
        });
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public SearchResults createFromParcel(Parcel in) {
            return new SearchResults(in);
        }

        public SearchResults[] newArray(int size) {
            return new SearchResults[size];
        }
    };
}
