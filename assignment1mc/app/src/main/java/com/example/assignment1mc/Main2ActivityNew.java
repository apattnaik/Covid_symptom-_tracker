package com.example.assignment1mc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;



public class Main2ActivityNew extends AppCompatActivity {
    int Fever=0;
    int Nausea=0;
    int Headache=0;
    int Cough=0;
    int Sour_Throat=0;
    int Diarrhea=0;
    int Muscle_Ache=0;
    int Smell_Taste=0;
    int Breath=0;
    int Tired=0;

    String spinner_text;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2_new);

        dbHelper = new DbHelper(this);
        Spinner mySpinner= (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> myAdapter= new ArrayAdapter<String>(Main2ActivityNew.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);


        final RatingBar ratingRatingBar= (RatingBar) findViewById(R.id.rating_rating_bar);
        Button buttonSubmitRating= (Button)findViewById(R.id.submit_rating);
        final TextView ratingTextView= (TextView) findViewById(R.id.ratingTextView);

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                spinner_text = parent.getItemAtPosition(position).toString(); //this is your selected item
            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        ratingRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                switch(spinner_text){
                    case "Fever":
                        Fever= (int) rating;
                        break;
                    case "Headache":
                        Headache= (int) rating;
                        break;
                    case "Nausea":
                        Nausea= (int) rating;
                        break;
                    case "Cough":
                        Cough= (int) rating;
                        break;
                    case "Sour_Throat":
                        Sour_Throat= (int) rating;
                        break;
                    case "Diarrhea":
                        Diarrhea= (int) rating;
                        break;
                    case "Muscle_Ache":
                        Muscle_Ache=(int)rating;
                        break;
                    case "Smell_taste":
                        Smell_Taste=(int) rating;
                        break;
                    case "Breath":
                        Breath=(int)rating;
                        break;
                    case "Tired":
                        Tired=(int) rating;
                        break;
                }
            }
        });


        buttonSubmitRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.addRatingDetail(Fever, Headache, Nausea, Sour_Throat, Cough, Diarrhea, Muscle_Ache, Smell_Taste, Breath);
                ratingTextView.setText("Ferver: "+ Fever + " , Nausea: " + Nausea+ " , Headache: "
                        + Headache+" , Sour Throat: "+ Sour_Throat+ " , Cough: "+ Cough
                        +" and the rest has been updated in the database");
            }
        });
        Button bt2 = (Button)findViewById(R.id.button2);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent int2 = new Intent(Main2ActivityNew.this, MainActivity.class);
                startActivity(int2);
            }
        });

    }//END OF ON CREATE


}
