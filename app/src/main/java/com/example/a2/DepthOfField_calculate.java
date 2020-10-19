package com.example.a2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.a2.Model.DepthOfField;
import com.example.a2.Model.Lens;
import com.example.a2.Model.LensManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import static java.lang.String.format;

public class DepthOfField_calculate extends AppCompatActivity {
    static int  lens_index;
    LensManager lensManager = LensManager.getInstance();

    @SuppressLint("SetTextI18n")

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calculate_dof, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_item_cancel) {
            finish();
        } // branch statement used for dof calculator tool bar
          else if (id == R.id.menu_item_editLens) {
            Intent editLens = detail_lens.makeLaunchIntent(DepthOfField_calculate.this,"Edit Lens");
            startActivity(editLens);

        } else if (id == R.id.menu_item_deleteLens) {

            SharedPreferences x = getApplicationContext().getSharedPreferences("lens", 0);
            String q = x.getString("lens1", "");
            String[] tokens = q.split(",");
            ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(tokens));
            arrayList.remove(lens_index*3);
            arrayList.remove(lens_index*3);
            arrayList.remove(lens_index*3);
            String p= "";
            for(int i= 0; i < arrayList.size(); i++) {
                p+= arrayList.get(i)+",";
            }
            SharedPreferences.Editor o= x.edit();
            o.putString("lens1", p);
            o.commit();
            lensManager.getarray().remove(lens_index);

            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depth_of_field_calculate);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        EditText textView9 = (EditText) findViewById(R.id.editText8);
        textView9.setText("0.029");

        //gettinglens();

        TextView textView = (TextView) findViewById(R.id.lens_info);
        textView.setText(" " + lensManager.get_retrieve(lens_index));

        Button btn = (Button) findViewById(R.id.cal_dof);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // For COF
                EditText editText = (EditText) findViewById(R.id.editText8);
                String temp1 = editText.getText().toString();
                double COF = Double.valueOf(temp1);

                // FOR Subject Distance
                EditText editText1 = (EditText) findViewById(R.id.editText9);
                String temp2 = editText1.getText().toString();
                double Subject_distance = Double.valueOf(temp2);

                // For Aperature
                EditText editText2 = (EditText) findViewById(R.id.editText10);
                String temp3 = editText2.getText().toString();
                double select_aperture =  Double.valueOf(temp3);

                // this branch statement is used for optional feature i.e. error checking
                if( select_aperture <  lensManager.get_retrieve1(lens_index).getMax_aperture() || select_aperture < 1.4){

                    TextView textView1 = (TextView) findViewById(R.id.editText4);
                    textView1.setText("Invalid Aperture");

                    TextView textView2 = (TextView) findViewById(R.id.editText5);
                    textView2.setText("Invalid Aperture");

                    TextView textView3 = (TextView) findViewById(R.id.editText6);
                    textView3.setText("Invalid Aperture");

                    TextView textView4 = (TextView) findViewById(R.id.editText7);
                    textView4.setText("Invalid Aperture");
                }
                else if( Subject_distance <= 0){

                    TextView textView1 = (TextView) findViewById((R.id.editText4));
                    textView1.setText("Invalid Subject distance");

                    TextView textView2 = (TextView) findViewById((R.id.editText5));
                    textView2.setText("Invalid Subject distance");

                    TextView textView3 = (TextView) findViewById((R.id.editText6));
                    textView3.setText("Invalid Subject distance");

                    TextView textView4 = (TextView) findViewById((R.id.editText7));
                    textView4.setText("Invalid Subject distance");
                }

                else if(COF == 0){
                    TextView textView1 = (TextView) findViewById(R.id.editText4);
                    textView1.setText("NaN");

                    TextView textView2 = (TextView) findViewById(R.id.editText5);
                    textView2.setText("NaN");

                    TextView textView3 = (TextView) findViewById(R.id.editText6);
                    textView3.setText("NaN");

                    TextView textView4 = (TextView) findViewById(R.id.editText7);
                    textView4.setText("NaN");
                }

                else{
                    DepthOfField dof = new DepthOfField(lensManager.get_retrieve1(lens_index), Subject_distance, select_aperture);
                    TextView textView5 = (TextView) findViewById(R.id.editText4);

                    textView5.setText(" " + String.valueOf(format("%.2f", dof.Nearfocal()/1000)) + "m" );

                    TextView textView6 = (TextView) findViewById(R.id.editText5);
                    textView6.setText(" " + String.valueOf(format("%.2f", dof.Farfocal()/1000)) + "m" );

                    TextView textView7 = (TextView) findViewById(R.id.editText6);
                    textView7.setText(" " + String.valueOf(format("%.2f", dof.depthoffield()/1000)) + "m" );

                    TextView textView8 = (TextView) findViewById(R.id.editText7);
                    textView8.setText(" " + String.valueOf(format("%.2f", dof.Hyperfocal()/1000)) + "m" );

                }
            }
        });

    }

    public static Intent makeIntent(Context context, int lensIndex){
        Intent intent = new Intent(context, DepthOfField_calculate.class);
        lens_index = lensIndex;
        return intent;

    }
}
