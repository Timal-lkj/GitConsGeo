package com.example.consgeo;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.consgeo.model.Country;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Main2Activity extends AppCompatActivity {

    private RecyclerView recyclerView;// la vue
    private RecyclerView.Adapter adapter; // l'adaptateur
    private RecyclerView.LayoutManager layoutManager;

    private List<Country> showCountries = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;

    String jsonResponseString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                getOnlineCountryData();
            }
        });


        // on récupère le RecyclerView que l'on a placé dans le layout main_activity2.xml
        recyclerView = (RecyclerView)findViewById(R.id.rvCountries);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CountryAdapter(showCountries, Glide.with(this));
        recyclerView.setAdapter(adapter);

        getOnlineCountryData();

    }

    // Méthode pour générer des données fictives
    private void getCountryData() {
        //on crée un entier entre 1 et 5
        int number = new Random().nextInt(4)+1;


        List<Country> countries = new ArrayList<Country>();
        countries.add(new Country( "Japan", "Japan", "Tokyo", "JPY", "Japanese Yen",
                "¥", 36.0, 138.0, ""));
        countries.add(new Country("France", "French Republic", "Paris", "EUR", "Euro",
                "€", 46.0, 2.0, ""));
        countries.add(new Country("United Kingdom",
                "United Kingdom of Great Britain and Northern Ireland",
                "London", "GBP", "British Pound", "£", 54.0, -2.0, ""));
        countries.add(new Country("United States", "United States of America",
                "Washington, D.C.", "USD", "United States Dollar",
                "$", 38.0, -97.0, ""));

        showCountries.clear();
        for (int i =0; i<number; i++){
            showCountries.add(countries.get(0));
            showCountries.add(countries.get(1));
            showCountries.add(countries.get(2));
            showCountries.add(countries.get(3));
        }
        refresh();
    }

    private void getOnlineCountryData() {
        showCountries.clear();

        Runnable myRunnable = () -> {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://restcountries.com/v3.1/all") //l'API qu'on utilise
                    .get()
                    .build();
            Log.d("response", "going to make the call");
            Response response = null;

            try {
                //On exécute la requête pour obtenir la réponse
                response = client.newCall(request).execute();
                //puis on récupère la réponse sous forme de String
                jsonResponseString = response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Log.d("response", test);
        };
        Thread t = (new Thread(myRunnable));
        t.start();
        try {
            //permet de s'assurer que le thread termine avant de continuer
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        inflateDataFromJSON(jsonResponseString);
        refresh();
    }

    private void inflateDataFromJSON(String jsonResponse){
        //Décodage JSON
        // get JSONArray from JSON file
        JSONArray arr = null;
        try {
            arr = new JSONArray(jsonResponse);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (arr != null) {
            // Pour chaque Pays
            for (int i = 0; i < arr.length(); i++) {
                String commonName = "Unknown";
                String officialName = "Unknown";
                String capitalCity = "Unknown";
                String currencyTrigram = "???";
                String currencyName = "Unknown";
                String currencySymbol = "?";
                Double lat = 0.0;
                Double lng = 0.0;
                String flagUrl =
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b0/No_flag.svg/338pxNo_flag.svg.png";
                // Je récupère l'objet pays
                JSONObject obj = null;
                try {
                    obj = arr.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (obj != null) {
                    // on récupère les informations de nom
                    JSONObject nameObject = null;
                    try {
                        nameObject = obj.getJSONObject("name");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        commonName = nameObject.getString("common");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        officialName = nameObject.getString("official");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //On récupère la capitale
                    try {
                        capitalCity = obj.getJSONArray("capital").getString(0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //TODO : On récupère les informations de monnaie
                    try {
                        JSONObject currencyObject = obj.getJSONObject("currencies");
                        currencyTrigram = currencyObject.keys().next();
                        JSONObject currencyObject2 = currencyObject.getJSONObject(currencyTrigram);
                        currencyName = currencyObject2.getString("name");
                        currencySymbol = currencyObject2.getString("symbol");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //String currencyString = currencyObject.toString();
                    // On récupère le code à deux lettres pour avoir l'adresse du drapeau
                    String cca2 = null;
                    try {
                        cca2 = obj.getString("cca2").toLowerCase();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    flagUrl = "https://raw.githubusercontent.com/hampusborgos/countryflags/main/png100px/" + cca2 + ".png";
                    JSONArray latlng = null;
                    try {
                        latlng = obj.getJSONArray("latlng");
                        lat = latlng.getDouble(0);
                        lng = latlng.getDouble(1);
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                }
                showCountries.add(new Country(commonName, officialName, capitalCity,
                        currencyTrigram, currencyName, currencySymbol, lat, lng, flagUrl));
            }
        }
    }

    private void refresh() {
        swipeRefreshLayout.setRefreshing(false);
        adapter.notifyDataSetChanged();
    }

}