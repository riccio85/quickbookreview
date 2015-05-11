package app.com.example.rihanna.abookfinder;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton search=(ImageButton)findViewById(R.id.searchBut);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText value = (EditText) findViewById(R.id.takeTitle);
                String title = value.getText().toString();
                Intent intent = new Intent(MainActivity.this, BookSearchView.class);


                final String QUERY_BASE = "https://www.googleapis.com/books/v1/volumes?";
                final String QUERY_PARAM = "q";
                final String FORMAT_PARAM = "mode";
                final String PRINT_TYPE = "printType";

                Uri builtUri = Uri.parse(QUERY_BASE).buildUpon()
                        .appendQueryParameter(QUERY_PARAM,title)
                        .appendQueryParameter(FORMAT_PARAM, "json")
                        .appendQueryParameter(PRINT_TYPE, "books")
                        .build();
                final String url= builtUri.toString();
                if(checkInternetConnection()){


                intent.putExtra("BookTitle", title);
                Log.i("intent on Main with " + title, "3");
                startActivity(intent);}
                else{
                    Toast toast = Toast.makeText(getApplication(), "Can't do search now. You are offline!", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent favIntent = new Intent(getApplicationContext(),Favorites.class);
            startActivity(favIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean checkInternetConnection(){
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

}
