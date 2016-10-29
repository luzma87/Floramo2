package com.lzm.Cajas.detail;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.lzm.Cajas.MainActivity;
import com.lzm.Cajas.R;
import com.lzm.Cajas.models.Especie;
import com.lzm.Cajas.models.Foto;
import com.lzm.Cajas.enums.FloramoFragment;
import com.lzm.Cajas.helpers.ResourcesHelper;
import com.lzm.Cajas.helpers.Utils;
import com.lzm.Cajas.models.Lugar;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.IOException;
import java.util.ArrayList;

public class DetailFragment extends Fragment {
    private OnDetailInteractionListener mListener;

    public static final String SPECIES_ID = "especieId";
    private MainActivity context;

    private Especie especie;
    private Long especieId;
    private ArrayList<Foto> photos;

    private ArrayList<Lugar> places;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(Long speciesId) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putLong(SPECIES_ID, speciesId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            especieId = getArguments().getLong(SPECIES_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = (MainActivity) getActivity();
        context.setActiveFragment(FloramoFragment.DETAILS);

        especie = Especie.getDatos(context, especieId);
        photos = (ArrayList<Foto>) Foto.findAllByEspecie(context, especie);

        places = (ArrayList<Lugar>)Lugar.findAllByEspecie(context, especie);

        View view = inflater.inflate(R.layout.detail_fragment, container, false);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) view.findViewById(R.id.detail_collapsing_toolbar);
        setBarTitle(collapsingToolbar);

        setDetailImage(view);
        setTropicosClick(view);
        setFamily(view);
        setGenus(view);
        setSpecies(view);
        setColors(view);
        setLifeForms(view);
        setPlaces(view);
        setDescription(view);

        initImageGallery(view);

        return view;
    }

    private void setPlaces(View view) {
        LinearLayout placeIconsLayout = (LinearLayout) view.findViewById(R.id.detail_places_layout);

        for (Lugar place : places) {
            TextView textView = new TextView(context);
            if (Build.VERSION.SDK_INT < 23) {
                textView.setTextAppearance(context, R.style.detail_info);
            } else {
                textView.setTextAppearance(R.style.detail_info);
            }
            textView.setText(place.getNombre());

            LinearLayout placeLayout = new LinearLayout(context);
            placeLayout.addView(textView);
            placeIconsLayout.addView(placeLayout);
        }
    }

    private void setSpecies(View view) {
        setTextView(view, R.id.detail_species, especie.getNombre());
    }

    private void setGenus(View view) {
        setTextView(view, R.id.detail_genus, especie.getGenero());
    }

    private void setFamily(View view) {
        setTextView(view, R.id.detail_family, especie.getFamilia());
    }

    private void setDescription(View view) {
        setTextView(view, R.id.detail_description, especie.getDescripcion());
    }

    private void setTextView(View view, int textViewId, String text) {
        TextView detailSpecies = (TextView) view.findViewById(textViewId);
        detailSpecies.setText(text);
    }

    private void setColors(View view) {
        ImageView detailColor1 = (ImageView) view.findViewById(R.id.detail_color1_image);
        ImageView detailColor2 = (ImageView) view.findViewById(R.id.detail_color2_image);
        setColor(especie.getColor1(), detailColor1);
        setColor(especie.getColor2(), detailColor2);
    }

    private void setLifeForms(View view) {
        TextView detailLifeForm1 = (TextView) view.findViewById(R.id.detail_life_form1_compund);
        TextView detailLifeForm2 = (TextView) view.findViewById(R.id.detail_life_form2_compund);
        setFormaVida(especie.getFormaVida1(), detailLifeForm1);
        setFormaVida(especie.getFormaVida2(), detailLifeForm2);
    }

    private void setDetailImage(View view) {
        ImageView detailImage = (ImageView) view.findViewById(R.id.detail_image);
        if (photos.size() > 0) {
            Foto foto = photos.get(0);
            if (foto != null) {
                String path = "full_size/" + foto.getPath().replaceAll("-", "_").toLowerCase();
                try {
                    Bitmap bitmap = ResourcesHelper.getEncyclopediaAssetByName(context, path);
                    detailImage.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setTropicosClick(View view) {
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.detail_fab_tropicos);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String baseUrl = context.getString(R.string.appInfo_tropicos_base_url);
                String url = baseUrl + especie.getIdTropicos();
                if (mListener != null) {
                    mListener.onDetailTropicosClicked(url);
                }
            }
        });
    }

    private float getPadding() {
        int gridPadding = 8;
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                gridPadding, context.getResources().getDisplayMetrics());
    }

    private void initImageGallery(View view) {
        int padding = (int) getPadding();
        int columnWidthDp = 150;
        int columnWidth = Utils.dp2px(context, columnWidthDp);

        setLinearLayoutProperties(view, padding, columnWidth);

        GridView gridView = (GridView) view.findViewById(R.id.detail_grid_view);
        setGridViewProperties(padding, columnWidth, gridView);

        DetailGridViewAdapter adapter = new DetailGridViewAdapter(context, photos, columnWidth);
        gridView.setAdapter(adapter);

        setGridViewItemClick(gridView);
    }

    private void setLinearLayoutProperties(View view, int padding, int columnWidth) {
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.detail_linear_layout);

        int cantPhotos = photos.size();
        int photosWidth = columnWidth * cantPhotos;
        int paddingWidth = padding * cantPhotos;
        linearLayout.getLayoutParams().width = photosWidth + paddingWidth;
        linearLayout.requestLayout();
    }

    private void setGridViewItemClick(GridView gridView) {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Intent i = new Intent(context, FullScreenViewActivity.class);
                Long fotoId = photos.get(position).getId();
                i.putExtra("position", position);
                i.putExtra("photoId", fotoId);
                context.startActivity(i);
            }
        });
    }

    private void setGridViewProperties(int padding, int columnWidth, GridView gridView) {
        gridView.setNumColumns(photos.size());
        gridView.setColumnWidth(columnWidth);
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setPadding(padding, padding, padding, padding);
        gridView.setHorizontalSpacing(padding);
        gridView.setVerticalSpacing(padding);
    }

    private void setBarTitle(CollapsingToolbarLayout collapsingToolbar) {
        collapsingToolbar.setTitle(especie.getNombreCientifico());
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
    }

    private void setColor(String color, ImageView colorImage) {
        if (color != null && !color.equals("none")) {
            color = "cl_" + color;
            String resourceName = "ic_" + color;
            int imageResource = ResourcesHelper.getImageResourceByName(context, resourceName);
            colorImage.setImageResource(imageResource);
            colorImage.setVisibility(View.VISIBLE);
        } else {
            colorImage.setVisibility(View.GONE);
        }
    }

    private void setFormaVida(String formaVida, TextView formaVidaCompound) {
        if (formaVida != null && !formaVida.equals("none")) {
            formaVida = "fv_" + formaVida;
            String resourceName = "ic_" + formaVida;
            String formaVidaName = ResourcesHelper.getStringResourceByName(context, formaVida);
            int imageResource = ResourcesHelper.getImageResourceByName(context, resourceName);
            Drawable icon = ContextCompat.getDrawable(context, imageResource);

            int drawableDp = 20;
            int drawablePx = Utils.dp2px(context, drawableDp);
            Bitmap bitmap = ((BitmapDrawable) icon).getBitmap();
            Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, drawablePx, drawablePx, true));

            formaVidaCompound.setCompoundDrawablesWithIntrinsicBounds(d, null, null, null);
            formaVidaCompound.setText(formaVidaName);
            formaVidaCompound.setVisibility(View.VISIBLE);
        } else {
            formaVidaCompound.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onAttachAction(context);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onAttachAction(activity);
        }
    }

    private void onAttachAction(Context context) {
        if (context instanceof OnDetailInteractionListener) {
            mListener = (OnDetailInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnEncyclopediaInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        context.setTitle(FloramoFragment.DETAILS.getTitleId());
    }

    public interface OnDetailInteractionListener {
        void onDetailTropicosClicked(String url);
    }
}
