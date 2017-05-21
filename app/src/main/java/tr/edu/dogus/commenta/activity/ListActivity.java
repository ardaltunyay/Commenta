package tr.edu.dogus.commenta.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tr.edu.dogus.commenta.R;
import tr.edu.dogus.commenta.adapter.VenueAdapter;
import tr.edu.dogus.commenta.model.SearchResponse;
import tr.edu.dogus.commenta.model.Venue;
import tr.edu.dogus.commenta.service.RetroClient;
import tr.edu.dogus.commenta.service.RetroInterface;

public class ListActivity extends AppCompatActivity {
    ProgressDialog pd;
    ListView lvPlaces;
    VenueAdapter venueAdapter;
    String client_id = "EZKNZG51UZPJA0E2WV44NHMAC2MASCXPJYAZ1YSGYV20TP1H";
    String client_secret = "OJIMD4XZRXNFZF4AZ0E11X0FHMC1R42H4R0XOCNYGEHSZOUI";
    String api_version ="20161010";
    String ll;
    SearchResponse searchResponse;
    List<Venue> venue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        lvPlaces = (ListView) findViewById(R.id.lvPlaces);
        double lat = getIntent().getExtras().getDouble("lat");
        double lng = getIntent().getExtras().getDouble("lng");
        ll = String.valueOf(lat)+","+lng;

        Log.i("INFO", "HAZIR = "+ll);

        pd = new ProgressDialog(ListActivity.this);
        pd.setMessage("Lütfen bekleyin.");
        pd.show();

        RetroInterface retroInterface = RetroClient.getClient().create(RetroInterface.class);
        Call<SearchResponse> call = retroInterface.getVenueJson(client_id,client_secret,api_version,ll);
        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                searchResponse = response.body();
                int code = searchResponse.getMeta().getCode();
                if(code == 200) {
                    venue = searchResponse.getResponse().getVenues();
                    venueAdapter = new VenueAdapter(getApplicationContext(),venue);
                    lvPlaces.setAdapter(venueAdapter);
                } else {
                    Toast.makeText(getApplication(), "Veri alınamadı!", Toast.LENGTH_SHORT).show();
                }
                pd.dismiss();
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(), "BAĞLANTI KURULAMADI", Toast.LENGTH_SHORT).show();
            }
        });

        lvPlaces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String venueId = venue.get(position).getId();
                String name = venue.get(position).getName();
                String country = venue.get(position).getLocation().getCountry();
                String city = venue.get(position).getLocation().getCity();
                double lat = venue.get(position).getLocation().getLat();
                double lng = venue.get(position).getLocation().getLng();
                int distance = venue.get(position).getLocation().getDistance();

                Intent goToCommentActivity = new Intent(ListActivity.this, CommentActivity.class);
                goToCommentActivity.putExtra("venueId", venueId);
                goToCommentActivity.putExtra("name", name);
                goToCommentActivity.putExtra("country", country);
                goToCommentActivity.putExtra("city", city);
                goToCommentActivity.putExtra("lat", lat);
                goToCommentActivity.putExtra("lng", lng);
                goToCommentActivity.putExtra("distance", distance);
                startActivity(goToCommentActivity);

                Toast.makeText(ListActivity.this,"Distance: "+String.valueOf(distance),Toast.LENGTH_SHORT).show();
            }
        });



    }
}
