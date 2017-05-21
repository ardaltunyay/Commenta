package tr.edu.dogus.commenta.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import tr.edu.dogus.commenta.R;
import tr.edu.dogus.commenta.model.Comment;

public class CommentActivity extends AppCompatActivity implements View.OnClickListener {

    private String venueId,name, country,city;
    private double lat, lng;
    private int distance;
    private TextView tvLocationTitle,tvLocationName,tvLocation, tvCountry, tvDistance;
    private EditText etComment;
    private ImageButton btnMaps;
    private Button btnComment;
    private FirebaseAuth auth;
    private FirebaseDatabase db;

    public CommentActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        venueId = getIntent().getExtras().getString("venueId");
        name = getIntent().getExtras().getString("name");
        country = getIntent().getExtras().getString("country");
        city = getIntent().getExtras().getString("city");
        lat = getIntent().getExtras().getDouble("lat");
        lng = getIntent().getExtras().getDouble("lng");
        distance = getIntent().getExtras().getInt("distance");

        tvLocationTitle = (TextView) findViewById(R.id.tvLocationTitle);
        tvLocationName = (TextView) findViewById(R.id.tvLocationName);
        btnMaps = (ImageButton) findViewById(R.id.btnMaps);
        tvCountry = (TextView) findViewById(R.id.tvCountry);
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        tvDistance = (TextView) findViewById(R.id.tvDistance);
        etComment = (EditText) findViewById(R.id.etComment);
        btnComment = (Button) findViewById(R.id.btnComment);

        tvLocationTitle.setText(name);
        tvLocationName.setText(name.toUpperCase());
        tvLocation.setText(String.valueOf(lat)+"\n"+String.valueOf(lng));
        tvCountry.setText(city.equals("") == true ? country :city+"/"+country);
        tvDistance.setText(distance < 1000 ? String.valueOf(distance)+" m" : String.valueOf((float) distance/1000)+" km");

        btnMaps.setOnClickListener(this);
        btnComment.setOnClickListener(this);


        // VENUE AİT YORUMLARI LİSTELEME





    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if(id == R.id.btnMaps) {
            Intent goToMap = new Intent(CommentActivity.this, MapsActivity.class);
            goToMap.putExtra("name", name);
            goToMap.putExtra("lat",lat );
            goToMap.putExtra("lng", lng);
            startActivity(goToMap);
        }
        else if(id == R.id.btnComment) {
            String comment = etComment.getText().toString().trim();

            if(!comment.equals("")){

                String userId = auth.getCurrentUser().getUid();
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String date= sdf.format(cal.getTime());

                DatabaseReference dbRef = db.getReference("Comments");
                String key = dbRef.push().getKey();
                DatabaseReference dbRefWithKey = db.getReference("Comments/"+key);

                dbRefWithKey.setValue(new Comment(userId,venueId,comment,date));

                Toast.makeText(CommentActivity.this, "Yorumunuz gönderildi.", Toast.LENGTH_SHORT).show();
                etComment.setText("");


            } else {
                Toast.makeText(CommentActivity.this, "Lütfen boş bırakmayın", Toast.LENGTH_SHORT).show();
            }

        }

    }
}
