package com.example.consgeo;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.consgeo.model.Country;

public class CountryViewHolder extends RecyclerView.ViewHolder {
    private final TextView name;
    private final TextView capital;
    private final TextView currency;
    private final ImageView flag;

    private Country country;

    //Constructeur, on récupère tous les éléments graphiques
    // définis dans country_layout.xml
    public CountryViewHolder(final View itemView)
    {
        super(itemView);

        name = ((TextView)itemView.findViewById(R.id.tvc_name));
        capital = ((TextView)itemView.findViewById(R.id.tvc_capital));
        currency = ((TextView)itemView.findViewById(R.id.tvc_currency));
        flag = ((ImageView)itemView.findViewById(R.id.ivc_flag));

    }

    //Méthode permettant de mettre à jour la vue connaissant un Country
    public void afficher(Country country, RequestManager glide)
    {
        this.country = country;
        name.setText(country.getCommonName()+ " ("+country.getOfficialName()+ ")");
        capital.setText(country.getCapitalCity());
        currency.setText(country.getCurrencyName() + " ("+ country.getCurrencyTrigram()+ ", " + country.getCurrencySymbol() + ")");
        // Remplacez l'URL fixe par country.getFlagUrl()
        glide.load(country.getFlagUrl()).apply(RequestOptions.circleCropTransform()).into(flag);

    }
}


