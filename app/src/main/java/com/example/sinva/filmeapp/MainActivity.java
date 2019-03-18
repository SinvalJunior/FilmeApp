package com.example.sinva.filmeapp;

import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView FilmeAno, FilmeAtores,FilmeDiretor, FilmeOrigem,FilmeIdioma,FilmeSinopse;
    ImageView CapaFilme;

    EditText editFNome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editFNome = findViewById(R.id.editFNome);
        FilmeAno = findViewById(R.id.FilmeAno);
        FilmeAtores = findViewById(R.id.FilmeAtores);
        FilmeDiretor = findViewById(R.id.FilmeDiretor);
        FilmeOrigem = findViewById(R.id.FilmeOrigem);
        FilmeIdioma = findViewById(R.id.FilmeIdioma);
        FilmeSinopse = findViewById(R.id.FilmeSinopse);
        CapaFilme = findViewById(R.id.CapaFilme);
    }

    public void procurar(View view) {

        String FNome = editFNome.getText().toString();
        if ( FNome.isEmpty())
        {
            editFNome.setError("Por favor, insira o nome do filme");
            return;
        }

        String url = "http://www.omdbapi.com/?t=" + FNome + "&plot=full&apikey=9d191a0f";

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, url,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject movie = new JSONObject(response);

                            String result = movie.getString("Response");

                            if ( result.equals("True"))
                            {
                                Toast.makeText(MainActivity.this, "Filme encontrado", Toast.LENGTH_SHORT).show();
                                String Ano = movie.getString("Year");
                                FilmeAno.setText("Ano: " + Ano);
                                String Atores = movie.getString("Actors");
                                FilmeAtores.setText("Atores: " + Atores);
                                String Diretor = movie.getString("Director");
                                FilmeDiretor.setText("Diretor: " + Diretor);
                                String Pais = movie.getString("Country");
                                FilmeOrigem.setText("País: " + Pais);
                                String Idioma = movie.getString("Language");
                                FilmeIdioma.setText("Idioma: " + Idioma);
                                String Sinopse = movie.getString("Plot");
                                FilmeSinopse.setText("Sinopse: " + Sinopse);

                                String posterUrl = movie.getString("Poster");
                                if(posterUrl.equals("N/A"))
                                {

                                }
                                else
                                {
                                    Picasso.get().load(posterUrl).into(CapaFilme);
                                }
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this, "Filme não encontrado!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        queue.add(request);
    }
}
