package com.lzm.Cajas.customComponents;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzm.Cajas.MainActivity;
import com.lzm.Cajas.R;
import com.lzm.Cajas.WebViewFragment;
import com.lzm.Cajas.enums.FloramoFragment;
import com.lzm.Cajas.helpers.FragmentHelper;
import com.lzm.Cajas.helpers.ResourcesHelper;

import java.io.IOException;

public class ClickableLinkSpan extends ClickableSpan { //clickable span

    String spanTipo;
    String spanUrl;
    MainActivity context;

    public ClickableLinkSpan(MainActivity context, String spanTipo, String spanUrl) {
        this.context = context;
        this.spanTipo = spanTipo;
        this.spanUrl = spanUrl;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        switch (spanTipo) {
            case "info":
                ds.setColor(ContextCompat.getColor(context, R.color.link_info));
                break;
            case "link":
                ds.setColor(ContextCompat.getColor(context, R.color.link_web));
                break;
            case "mailto":
                ds.setColor(ContextCompat.getColor(context, R.color.link_mailto));
                break;
        }
    }

    public void onClick(final View textView) {
        TextView tv = (TextView) textView;
        Spanned s = (Spanned) tv.getText();
        int start = s.getSpanStart(this);
        int end = s.getSpanEnd(this);
        String text = s.subSequence(start, end).toString();

        handleLinks(text);
    }

    private void handleLinks(String text) {
        switch (spanTipo) {
            case "info":
                handleInfoLink(text);
                break;
            case "link":
                handleWebLink();
                break;
            case "mailto":
                handleMailLink();
                break;
        }
    }

    private void handleInfoLink(String text) {
        LayoutInflater inflater = context.getLayoutInflater();
        View v = inflater.inflate(R.layout.about_dialog_definitions, null);

        try {
            Bitmap image = ResourcesHelper.getAboutAssetByName(context, spanUrl + ".jpg");
            ImageView imgAbout = (ImageView) v.findViewById(R.id.about_definition_img);
            imgAbout.setImageBitmap(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(context).setView(v)
                .setNeutralButton(R.string.dialog_button_close, null) //Set to null. We override the onclick
                .setTitle(text);

        final AlertDialog d = builder.create();

        d.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button cerrar = d.getButton(AlertDialog.BUTTON_NEUTRAL);
                cerrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.dismiss();
                    }
                });
            }
        });
        d.show();
    }

    private void handleMailLink() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", spanUrl, null));
        context.startActivity(Intent.createChooser(emailIntent, context.getString(R.string.about_contact)));
    }

    private void handleWebLink() {
        context.setOpenUrl(spanUrl);
        Fragment fragment = WebViewFragment.newInstance(spanUrl);
        int titleRes = FloramoFragment.WEB_VIEW.getTitleId();
        FragmentHelper.openFragment(context, fragment, context.getString(titleRes), true);
    }
}