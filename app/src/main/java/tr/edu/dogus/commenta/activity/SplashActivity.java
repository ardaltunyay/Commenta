package tr.edu.dogus.commenta.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tr.edu.dogus.commenta.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread timer=new Thread()
        {
            public void run()
            {
                try{
                    sleep(2800);
                    Intent i=new Intent(SplashActivity.this,LoginActivity.class);
                    startActivity(i);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally {
                    finish();
                }
            }
        };
        timer.start();

    }
}
