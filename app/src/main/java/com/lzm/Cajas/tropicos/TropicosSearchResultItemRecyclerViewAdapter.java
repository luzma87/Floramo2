package com.lzm.Cajas.tropicos;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lzm.Cajas.R;
import com.lzm.Cajas.tropicos.TropicosSearchResultFragment.OnTropicosSearchResultFragmentInteractionListener;

import java.util.List;

public class TropicosSearchResultItemRecyclerViewAdapter extends RecyclerView.Adapter<TropicosSearchResultItemRecyclerViewAdapter.ViewHolder> {

    private final List<TropicosSearchResult> mValues;
    private final OnTropicosSearchResultFragmentInteractionListener mListener;

    public TropicosSearchResultItemRecyclerViewAdapter(List<TropicosSearchResult> items, OnTropicosSearchResultFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tropicos_search_result_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.name.setText(mValues.get(position).getScientificName());
        holder.family.setText(mValues.get(position).getFamily());
        holder.author.setText(mValues.get(position).getAuthor());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onTropicosItemClicked(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView name;
        public final TextView family;
        public final TextView author;
        public TropicosSearchResult mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            name = (TextView) view.findViewById(R.id.tropicos_search_result_row_name);
            family = (TextView) view.findViewById(R.id.tropicos_search_result_row_family);
            author = (TextView) view.findViewById(R.id.tropicos_search_result_row_author);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + family.getText() + "'";
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }
}
