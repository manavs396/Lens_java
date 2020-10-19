package com.example.a2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.a2.Model.Lens;
import com.example.a2.Model.LensManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class detail_lens extends AppCompatActivity {
    private LensManager lensManager = LensManager.getInstance();
    private static int lens_index = -1;
    private static final String Extra_Message = "Extra - message";

    protected static Intent makeLaunchIntent(Context c, String message){

        Intent intent = new Intent(c,detail_lens.class);
        intent.putExtra(Extra_Message, message);
        return intent;
    }
    protected static Intent makeIntent(Context context, int lensIndex) {
        lens_index  = lensIndex;
        return new Intent(context, detail_lens.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail_lens, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_item_save) {
            EditText edittext = ( EditText) findViewById(R.id.editText);

            String temp = edittext.getText().toString();

            EditText edittext2 = ( EditText) findViewById(R.id.editText2);
            String temp2 = edittext2.getText().toString();
            int temp2d = Integer.valueOf(temp2);

            EditText edittext3 = ( EditText) findViewById(R.id.editText3);
            String temp3 = edittext3.getText().toString();
            double temp3d = Double.valueOf(temp3);

            if(temp3d>=1.4 && temp.length()>0 && temp2d >0 ) {
                Lens make_lens1 = new Lens(temp, temp3d, temp2d);
                lensManager.add(make_lens1);
                String x= temp+","+temp3d+","+temp2d+",";

                SharedPreferences abc = getApplicationContext().getSharedPreferences("lens", 0);
                SharedPreferences.Editor xyz = abc.edit();
                xyz.putString("lens1", abc.getString("lens1","") + x);
                xyz.commit();
                finish();
            }
            else{
                Toast.makeText(detail_lens.this,"Check your Lens name or Focal lenght or Aperture !(Make > 0, Focal > 0, Aperture >= 1.4)", Toast.LENGTH_LONG).show();
            }
        }

        else if (id == R.id.menu_item_cancel) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_lens);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Handle
        Intent i = getIntent();
        String message = i.getStringExtra(Extra_Message);
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();

    }

// this function is used for optional features in the toolbar for dof calculator page in the app
    private void actionOn_editLens() {
        setTitle("Edit lens");

        EditText lensMake = (EditText) findViewById(R.id.editText);
        EditText lensFocalLength = (EditText) findViewById(R.id.editText2);
        EditText lensAperture = (EditText) findViewById(R.id.editText3);

        lensMake.setText(lensManager.get_retrieve1(lens_index).getMake_lens());
        lensFocalLength.setText(lensManager.get_retrieve1(lens_index).getLens_focal() + "");
        lensAperture.setText(lensManager.get_retrieve1(lens_index).getMax_aperture() + "");
    }

}

