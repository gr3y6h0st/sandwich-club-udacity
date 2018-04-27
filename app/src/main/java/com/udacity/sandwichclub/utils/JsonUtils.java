package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        //Create Sandwich Object, set as null for now.
        Sandwich theSandwich = null;

        /** Use JSON objects created need to address/create these variables created in Sandwich.java:
         *private String mainName;
         *private List<String> alsoKnownAs
         *private String placeOfOrigin
         *private String description
         *private String image
         *private List<String> ingredients
         */
        try{
            //Create the jsonObject from "json" parameter
            JSONObject jsonSandwichObject = new JSONObject(json);

            //begin extracting the specific details of the sandwich by calling on jsonSandwichObject
            JSONObject sandwichName = jsonSandwichObject.getJSONObject("name");
            String mainName = sandwichName.getString("mainName");
            JSONArray alsoKnownAs = sandwichName.getJSONArray("alsoKnownAs");
            String placeOfOrigin = jsonSandwichObject.getString("placeOfOrigin");
            String description = jsonSandwichObject.getString("description");
            String imageLink = jsonSandwichObject.getString("image");
            JSONArray ingredientsList = jsonSandwichObject.getJSONArray("ingredients");

            //Declare theSandwich as a new Sandwich Object
            theSandwich = new Sandwich();

            //set theSandwich properties using variables above
            theSandwich.setMainName(mainName);
            theSandwich.setPlaceOfOrigin(placeOfOrigin);
            theSandwich.setDescription(description);
            theSandwich.setImage(imageLink);

            /** In order to set the JSONArray objects (alsoKnownAs, ingredientsList) properly,
             * declare a new ArrayList variable and iterate over their respective JSONArray Objects.
             * Add each array item to the new list using a for loop.
             * set the respective Sandwich variable to their respective lists.
             */
            if(alsoKnownAs.length()>0){
                List<String> alsoKnownAsList = new ArrayList<>();
                for(int i=0; i<alsoKnownAs.length(); i++){
                    alsoKnownAsList.add(alsoKnownAs.getString(i));
                }
                theSandwich.setAlsoKnownAs(alsoKnownAsList);
            }

            if(ingredientsList.length()>0){
                List<String> ingredients = new ArrayList<>();
                for(int i=0; i<ingredientsList.length(); i++){
                    ingredients.add(ingredientsList.getString(i));
                }
                theSandwich.setIngredients(ingredients);
            }

        } catch (JSONException e){
            e.printStackTrace();
        }
        return theSandwich;
    }
}
