package com.lzm.Cajas.tropicos;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by luz on 6/25/16.
 */
public class TropicosSearchResult {
    /*
    [
       {
         "NameId": 40026990,
         "ScientificName": "Rosea",
         "ScientificNameWithAuthors": "Rosea Mart.",
         "Family": "Amaranthaceae",
         "RankAbbreviation": "gen.",
         "NomenclatureStatusName": "No opinion",
         "Author": "Mart.",
         "DisplayReference": "Nov. Gen. Sp. Pl. 2: 58",
         "DisplayDate": "1826",
         "TotalRows": 59
       }
   ]
     */
    private String nameId;
    private String scientificName;
    private String scientificNameWithAuthors;
    private String family;
    private String rankAbbreviation;
    private String author;
    private String displayReference;
    private String displayDate;

    public TropicosSearchResult(JSONObject row) {
        try {
            nameId = row.getString("NameId");
            scientificName = row.getString("ScientificName");
            scientificNameWithAuthors = row.getString("ScientificNameWithAuthors");
            family = row.getString("Family");
            rankAbbreviation = row.getString("RankAbbreviation");
            author = row.getString("Author");
            displayReference = row.getString("DisplayReference");
            displayDate = row.getString("DisplayDate");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getNameId() {
        return nameId;
    }

    public String getScientificName() {
        return scientificName;
    }

    public String getFamily() {
        return family;
    }

    public String getAuthor() {
        return author;
    }
}
