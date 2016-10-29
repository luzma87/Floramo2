package com.lzm.Cajas.encyclopedia;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.lzm.Cajas.MainActivity;
import com.lzm.Cajas.R;
import com.lzm.Cajas.customComponents.indexableList.StringMatcher;
import com.lzm.Cajas.models.Especie;
import com.lzm.Cajas.models.Foto;
import com.lzm.Cajas.helpers.ResourcesHelper;
import com.lzm.Cajas.helpers.Utils;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EncyclopediaListAdapter extends ArrayAdapter<Especie> implements SectionIndexer {

    private final String sort;
    MainActivity context;
    List<Especie> especies;

    private String indexSections = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public EncyclopediaListAdapter(MainActivity context, List<Especie> especies, String sort) {
        super(context, R.layout.encyclopedia_row, especies);
        this.context = context;
        this.especies = especies;
        this.sort = sort;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.encyclopedia_row, null);
        }

        Especie especie = especies.get(position);

        List<Foto> fotos = Foto.findAllByEspecie(context, especie);
        Foto foto = null;
        if (fotos.size() > 0) {
            foto = fotos.get(0);
            LinearLayout layoutLugares = (LinearLayout) convertView.findViewById(R.id.encyclopedia_row_layout_lugares);
            layoutLugares.removeAllViews();
        }

        String labelNombreCientifico = especie.getGenero() + " " + especie.getNombre();
        String labelNombreFamilia = especie.getFamilia();

        ImageView itemFoto = (ImageView) convertView.findViewById(R.id.encyclopedia_row_image);

        TextView itemNombreCientifico = (TextView) convertView.findViewById(R.id.encyclopedia_row_nombre_cientifico);
        TextView itemNombreFamilia = (TextView) convertView.findViewById(R.id.encyclopedia_row_familia);
        ImageView itemColor1 = (ImageView) convertView.findViewById(R.id.encyclopedia_row_cl_1);
        ImageView itemColor2 = (ImageView) convertView.findViewById(R.id.encyclopedia_row_cl_2);
        ImageView itemFormaVida1 = (ImageView) convertView.findViewById(R.id.encyclopedia_row_fv_1);
        ImageView itemFormaVida2 = (ImageView) convertView.findViewById(R.id.encyclopedia_row_fv_2);

        itemNombreCientifico.setText(labelNombreCientifico);
        itemNombreFamilia.setText(labelNombreFamilia);

        itemColor1.setImageResource(ResourcesHelper.getImageResourceByName(context, "ic_cl_" + especie.getColor1()));
        if (especie.getColor2() != null && !especie.getColor2().equals("none")) {
            itemColor2.setImageResource(ResourcesHelper.getImageResourceByName(context, "ic_cl_" + especie.getColor2()));
            itemColor2.setVisibility(View.VISIBLE);
        } else {
            itemColor2.setVisibility(View.GONE);
        }

        itemFormaVida1.setImageResource(ResourcesHelper.getImageResourceByName(context, "ic_fv_" + especie.getFormaVida1()));
        if (especie.getFormaVida2() != null && !especie.getFormaVida2().equals("none")) {
            itemFormaVida2.setImageResource(ResourcesHelper.getImageResourceByName(context, "ic_fv_" + especie.getFormaVida2()));
            itemFormaVida2.setVisibility(View.VISIBLE);
        } else {
            itemFormaVida2.setVisibility(View.GONE);
        }

        if (foto != null) {
            String path = "new/" + foto.getPath().replaceAll("-", "_").toLowerCase();
            try {
                Bitmap bitmap = ResourcesHelper.getEncyclopediaAssetByName(context, path);
                itemFoto.setImageBitmap(bitmap);
            } catch (IOException e) {
                System.out.println("*********************************************************");
                e.printStackTrace();
                System.out.println("*********************************************************");
            }
        }

        return convertView;
    }

    @Override
    public Object[] getSections() {
        String[] sections = new String[indexSections.length()];
        for (int i = 0; i < indexSections.length(); i++)
            sections[i] = String.valueOf(indexSections.charAt(i));
        return sections;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        // If there is no item for current section, previous section will be selected
        for (int i = sectionIndex; i >= 0; i--) {
            for (int j = 0; j < getCount(); j++) {
                String nombre;
                if (sort.equalsIgnoreCase("f")) {
                    nombre = getItem(j).getFamilia();
                } else if (sort.equalsIgnoreCase("n")) {
                    nombre = getItem(j).getGenero();
                } else {
                    nombre = getItem(j).getGenero();
                }
                String index = String.valueOf(nombre.charAt(0));
                String keyword = String.valueOf(indexSections.charAt(i));
                if (StringMatcher.match(index, keyword))
                    return j;
            }
        }
        return 0;
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }
}