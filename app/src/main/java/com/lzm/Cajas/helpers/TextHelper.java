package com.lzm.Cajas.helpers;

import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.widget.TextView;

import com.lzm.Cajas.MainActivity;
import com.lzm.Cajas.customComponents.ClickableLinkSpan;

public class TextHelper {
    public static void setTextWithLinks(MainActivity context, TextView texto, String str) {
        String[] parts = str.split("<a href='");

        // la primera parte es lo que hay antes del primer link
        texto.setText(Html.fromHtml(parts[0]));
        texto.setMovementMethod(LinkMovementMethod.getInstance());

        //System.out.println("*****************************************");
        //System.out.println("*" + parts[0] + "*");
        //System.out.println("*****************************************");

        //cada una de las siguientes partes contiene un link
        for (int i = 1; i < parts.length; i++) {
            String part = parts[i];
            // info::rosette'>rosettes</a> and
            // link::http://www.mobot.org/MOBOT/Research/paramo_flora.shtml'>Páramo flora project</a></p>
            // mailto::carmen.ulloa@mobot.org'>C. Ulloa</a></p>

            // 1ro: split en </a> para tener la parte link (pos 0) y la parte texto (pos 1)
            String[] textParts = part.split("</a>");

            //la parte en pos 0 tiene 1ro el tipo y el url del link, despues el texto del link (split en '>)
            String[] linkParts = textParts[0].split("'>");

            String[] urlParts = linkParts[0].split("::");

            String spanTipo = urlParts[0];
            String spanUrl = urlParts[1];
            String spanText = linkParts[1];

            SpannableString ss = new SpannableString(spanText);
            ClickableSpan cs = new ClickableLinkSpan(context, spanTipo, spanUrl);
            ss.setSpan(cs, 0, spanText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            texto.append(ss);
            //System.out.println("++++++++++++++++++++++++++++++++++++++++++");
            //System.out.println("+" + spanText + "+");
            //System.out.println("++++++++++++++++++++++++++++++++++++++++++");

            if (textParts.length == 2) {
                if (textParts[1].startsWith("</p>")) {
                    textParts[1] = textParts[1].replaceFirst("</p>", "<br/>");
                }
                if (!textParts[1].startsWith(",") && !textParts[1].startsWith(";") &&
                        !textParts[1].startsWith(".") && !textParts[1].startsWith(":")) {
                    texto.append(" ");
                }
                texto.append(Html.fromHtml(textParts[1]));
                //System.out.println("-------------------------------------------");
                //System.out.println("-" + textParts[1] + "-");
                //System.out.println("-------------------------------------------");
            }
        }
    }
}
