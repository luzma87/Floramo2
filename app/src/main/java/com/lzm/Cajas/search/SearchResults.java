package com.lzm.Cajas.search;

import com.lzm.Cajas.MainActivity;
import com.lzm.Cajas.db.Especie;

import java.util.ArrayList;

/**
 * Created by luz on 6/19/16.
 */
public class SearchResults {

    public static final String SORT_BY_NAME = "n";
    public static final String SORT_BY_FAMILY = "f";
    public static final String ORDER_ASCENDING = "a";
    private final MainActivity context;

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

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getSort() {
        return sort;
    }

    public ArrayList<Especie> getResults() {
        results = (ArrayList<Especie>) Especie.busqueda(context, lifeForms, colors, text, conditional, sort, order);
        return results;
    }


}
