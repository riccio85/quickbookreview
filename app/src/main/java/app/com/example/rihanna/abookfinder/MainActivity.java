package app.com.example.rihanna.abookfinder;

import android.content.Intent;
import android.app.*;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Act created & content view attached", "1");

        ImageButton search=(ImageButton)findViewById(R.id.searchBut);
         search.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Log.i("input string onclick ", "2");
                 EditText value=(EditText)findViewById(R.id.takeTitle);
                 String title = value.getText().toString();
                 //pass user input for a search class
                 Intent intent =new Intent(MainActivity.this,BookSearchView.class);
                 intent.putExtra("BookTitle",title);
                 Log.i("intent on Main with " + title, "3");
                 startActivity(intent);
             }
         });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
