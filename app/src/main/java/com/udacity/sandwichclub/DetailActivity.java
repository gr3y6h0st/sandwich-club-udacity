package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        //insert the sandwich object into populateUI
        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich theSandwich) {

        //create variables to the TextViews using the Sandwich class
        TextView sandwich_iv_label = findViewById(R.id.image_view_label);
        TextView origin_label = findViewById(R.id.place_of_origin_label);
        TextView origin_text_view = findViewById(R.id.origin_tv);
        TextView description_label = findViewById(R.id.description_label);
        TextView description_text_view = findViewById(R.id.description_tv);
        TextView ingredients_label = findViewById(R.id.ingredients_label);
        TextView ingredients_text_view = findViewById(R.id.ingredients_tv);
        TextView aka_label = findViewById(R.id.aka_label);
        TextView aka_text_view = findViewById(R.id.also_known_tv);

        /*
        ** Get the values for the variables by using get methods from Sandwich class
        ** Set values to their respective TextViews
        ** Hide the textViews if they are empty/null
         */
        sandwich_iv_label.setText(theSandwich.getMainName());

        if(theSandwich.getAlsoKnownAs() == null){
            aka_text_view.setVisibility(View.GONE);
            aka_label.setVisibility(View.GONE);
        } else{
            aka_text_view.setText(theSandwich.getAlsoKnownAs().toString());
        }

        if(theSandwich.getDescription().isEmpty()){
            description_text_view.setVisibility(View.GONE);
            description_label.setVisibility(View.GONE);
        } else {
            description_text_view.setText(theSandwich.getDescription());
        }

        if(theSandwich.getIngredients() == null){
            ingredients_text_view.setVisibility(View.GONE);
            ingredients_label.setVisibility(View.GONE);
        } else {
            ingredients_text_view.setText(theSandwich.getIngredients().toString());
        }

        if(theSandwich.getPlaceOfOrigin().isEmpty()){
            origin_text_view.setVisibility(View.GONE);
            origin_label.setVisibility(View.GONE);
        } else {
            origin_text_view.setText(theSandwich.getPlaceOfOrigin());
        }
    }
}
