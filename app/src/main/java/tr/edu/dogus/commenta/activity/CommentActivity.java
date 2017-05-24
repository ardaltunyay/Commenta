package tr.edu.dogus.commenta.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import tr.edu.dogus.commenta.R;
import tr.edu.dogus.commenta.adapter.CommentAdapter;
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
    private LinearLayout llComment;


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

        llComment = (LinearLayout) findViewById(R.id.llComment);

        tvLocationTitle.setText(name);
        tvLocationName.setText(name.toUpperCase());
        tvLocation.setText(String.valueOf(lat)+"\n"+String.valueOf(lng));
        tvCountry.setText(city.equals("") == true ? country :city+"/"+country);
        tvDistance.setText(distance < 1000 ? String.valueOf(distance)+" m" : String.valueOf((float) distance/1000)+" km");

        btnMaps.setOnClickListener(this);
        btnComment.setOnClickListener(this);


        // VENUE AİT YORUMLARI LİSTELEME

        comment_list();



    }

    private void comment_list() {
        DatabaseReference dbCom = db.getReference("Comments");
        dbCom.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot comment:dataSnapshot.getChildren()) {
                    if(comment.getValue(Comment.class).getVenueId().equals(venueId)) {
                        createTv(comment.getValue(Comment.class).getUserMail(),
                                comment.getValue(Comment.class).getComment(),
                                comment.getValue(Comment.class).getDate());
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void createTv(String name, String comment, String date) {
        TextView[] tv = new TextView[3];

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,0,0,50);

        for(int i=0;i<tv.length;i++) {
            tv[i] = new TextView(CommentActivity.this);
        }


        tv[0].setText(name);
        tv[0].setTypeface(Typeface.DEFAULT_BOLD);
        tv[0].setTextSize(18);
        tv[1].setText(comment);
        tv[1].setTextSize(15);
        tv[2].setText(date);
        tv[2].setTextSize(12);
        tv[2].setLayoutParams(params);

        for(int i=0;i< tv.length;i++) {

            tv[i].setPadding(60,15,20,15);
            tv[i].setBackgroundColor(Color.parseColor("#ffffff"));
            llComment.addView(tv[i]);
        }

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
                String userMail = auth.getCurrentUser().getEmail();

                DatabaseReference dbRef = db.getReference("Comments");
                String key = dbRef.push().getKey();
                DatabaseReference dbRefWithKey = db.getReference("Comments/"+key);

                dbRefWithKey.setValue(new Comment(userId,venueId,comment,date,userMail));

                Toast.makeText(CommentActivity.this, "Yorumunuz gönderildi.", Toast.LENGTH_SHORT).show();
                etComment.setText("");

                comment_list();


            } else {
                Toast.makeText(CommentActivity.this, "Lütfen boş bırakmayın", Toast.LENGTH_SHORT).show();
            }

        }

    }
}
