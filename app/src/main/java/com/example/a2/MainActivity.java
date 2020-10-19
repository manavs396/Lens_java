package com.example.a2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.ColorSpace;
import android.os.Bundle;

import com.example.a2.Model.Lens;
import com.example.a2.Model.LensManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;



// Array of Option -> arrayAdaptor -> listview
// list view :(Views: da
public class MainActivity extends AppCompatActivity {

    private LensManager lensManager;

    // Refresh the list after save
    @Override
    protected void onResume() {
        super.onResume();
        populateListView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lensManager =  LensManager.getInstance();
        SharedPreferences x= getApplicationContext().getSharedPreferences("lens",0);
        SharedPreferences.Editor y= x.edit();
        boolean q= x.getBoolean("first", false);
        if(!q) {
            SharedPreferences.Editor xyz = x.edit();
            Lens q1= new Lens("Canon", 1.8, 50);
            Lens q2= new Lens("Tamron", 2.8, 90);
            Lens q3= new Lens("Sigma", 2.8, 200);
            Lens q4= new Lens("Nikon", 4.0, 200);
            lensManager.add(q1);
            lensManager.add(q2);
            lensManager.add(q3);
            lensManager.add(q4);
            y.putString("lens1","Canon,1.8,50,Tamron,2.8,90,Sigma,2.8,200,Nikon,4.0,200,");
            y.putBoolean("first", true);
            y.commit();
        } else {
            String w= x.getString("lens1", "");
            String[] tokens= w.split(",");
            String[] u= new String[tokens.length/3];
            for(int i= 0; i < tokens.length; i+=3) {
                Lens z= new Lens(tokens[i], Double.valueOf(tokens[i+1]), Integer.valueOf(tokens[i+2]));
                lensManager.add(z);
            }
        }

        populateListView();
        registerClickCalback();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Creating Floating action button and abstracting its function
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent i = detail_lens.makeLaunchIntent(MainActivity.this, "Hello World");
            startActivity(i);
        });

    }

    private void populateListView() {
            // create the list of items
            SharedPreferences x = getApplicationContext().getSharedPreferences("lens", 0);
            String q = x.getString("lens1", "");
            String[] tokens = q.split(",");
            String[] u = new String[tokens.length / 3];
            for (int i = 0; i < tokens.length; i += 3) {
                Lens z = new Lens(tokens[i], Double.valueOf(tokens[i + 1]), Integer.valueOf(tokens[i + 2]));
                u[i / 3] = z.toString();
            }

            // Build adaptor convert into elements and views
            ArrayAdapter<String> adaptor = new ArrayAdapter<String>(this, R.layout.da_item, u);
            // configure the list view
            ListView list = (ListView) findViewById(R.id.listViewMain);
            list.setAdapter(adaptor);
    }

    private void registerClickCalback() {
        ListView list = (ListView) findViewById(R.id.listViewMain);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent intent = DepthOfField_calculate.makeIntent(MainActivity.this, position);
               startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Intent add_activity = new Intent(MainActivity.this, detail_lens.class);
            startActivity(add_activity);
        }
        return super.onOptionsItemSelected(item);
    }
}
