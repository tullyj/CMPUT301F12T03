package ca.ualberta.cs.c301_teamproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

/**
 * This class is the welcome page, any click on the screen will move yor forward
 *
 */
public class WelcomePage extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void enterTaskForce(View view){
   
        Intent intent = new Intent(this, MainPage.class);
        startActivity(intent);

    }
}
