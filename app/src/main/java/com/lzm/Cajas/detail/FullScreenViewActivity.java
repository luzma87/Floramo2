package com.lzm.Cajas.detail;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lzm.Cajas.R;
import com.lzm.Cajas.db.Foto;

import java.util.ArrayList;

public class FullScreenViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_full_screen_view_activity);

        ViewPager viewPager = (ViewPager) findViewById(R.id.detail_full_screen_pager);

        Intent i = getIntent();
        int position = i.getIntExtra("position", 0);
        long fotoId = i.getLongExtra("photoId", 0);

        Foto foto = Foto.get(this, fotoId);
        ArrayList<Foto> fotos = (ArrayList<Foto>) foto.findAllSameEspecie(this);

        FullScreenImageAdapter adapter = new FullScreenImageAdapter(FullScreenViewActivity.this, fotos);

        if (viewPager != null) {
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(position);

            //Bind the viewpagerindicator to the adapter
            CircleView circleView = (CircleView) findViewById(R.id.detail_full_screen_circle_view);
            if (circleView != null) {
                circleView.setColorAccent(ContextCompat.getColor(this, R.color.accent)); //Optional
                circleView.setColorBase(ContextCompat.getColor(this, R.color.primary)); //Optional
                circleView.setViewPager(viewPager);
            }
        }

        final Activity thisActivity = this;
        Button buttonClose = (Button) findViewById(R.id.detail_full_screen_btn_close);
        // close button click event
        if (buttonClose != null) {
            buttonClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    thisActivity.finish();
                }
            });
        }
    }
}
