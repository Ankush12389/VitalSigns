package com.ankush.vitalsigns;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


public class LoggerActivity extends ActionBarActivity {

    public String safeGet( final TextView tv ) {
            return tv.getText().toString();
    }

    public void resetValues(
            TextView textVstartOfYear,
            TextView textVexercise,
            TextView textVattack,
            TextView textVblueInhaler,
            TextView textVbrownInhaler,
            TextView textVoutside,
            TextView textVwakeUp,
            TextView textVmeal,
            TextView textVfeelMeal,
            DatabaseHandler db
    ) {
        Time nowTime = new Time();
        nowTime.setToNow();
        Time timeYearStart = new Time();
        timeYearStart.set(1, 0, nowTime.year);
        long longDiffMillis = nowTime.toMillis(true) - timeYearStart.toMillis(true);
        int startOfYearTime = (int)( longDiffMillis/60000);
        textVstartOfYear.setText("" + "" + startOfYearTime);
        SignsValues svLast = db.getLastRow();
        if( svLast.m_startOfYear !=0 ) {
            int timeDiffFromLastEntry = startOfYearTime - svLast.m_startOfYear;
            SignsValues svShifted = svLast.addTime(timeDiffFromLastEntry);

            textVexercise.setText("" + svShifted.m_exercise);
            textVattack.setText("" + svShifted.m_attack);
            textVblueInhaler.setText("" + svShifted.m_blueInhaler);
            textVbrownInhaler.setText("" + svShifted.m_brownInhaler);
            textVoutside.setText("" + svShifted.m_outside);
            textVwakeUp.setText("" + svShifted.m_wakeUp);
            textVmeal.setText("" + svShifted.m_meal);
            textVfeelMeal.setText("" + svShifted.m_feelMeal);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logger);

        final TextView textVstartOfYear = ((TextView) findViewById(R.id.startOfYear));
        final TextView textVexercise = ((TextView) findViewById(R.id.exercise));
        final TextView textVattack = ((TextView) findViewById(R.id.attack));
        final TextView textVblueInhaler = ((TextView) findViewById(R.id.blueInhaler));
        final TextView textVbrownInhaler = ((TextView) findViewById(R.id.brownInhaler));
        final TextView textVoutside = ((TextView) findViewById(R.id.outside));
        final TextView textVwakeUp = ((TextView) findViewById(R.id.wakeUp));
        final TextView textVmeal = ((TextView) findViewById(R.id.meal));
        final TextView textVfeelMeal = ((TextView) findViewById(R.id.feelMeal));
        final TextView textVrr = ((TextView) findViewById(R.id.RRText));
        final TextView textVhr = ((TextView) findViewById(R.id.HRText));

        final DatabaseHandler db = new DatabaseHandler(this);

        final Button saveButton = (Button) findViewById(R.id.button);
        saveButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int startOfYear = Integer.parseInt(safeGet(textVstartOfYear));
                        int exercise = Integer.parseInt(safeGet(textVexercise));
                        int attack = Integer.parseInt(safeGet(textVattack));
                        int blueInhaler = Integer.parseInt(safeGet(textVblueInhaler));
                        int brownInhaler = Integer.parseInt(safeGet(textVbrownInhaler));
                        int outside = Integer.parseInt(safeGet(textVoutside));
                        int wakeUp = Integer.parseInt(safeGet(textVwakeUp));
                        int meal = Integer.parseInt(safeGet(textVmeal));
                        int feelMeal = Integer.parseInt(safeGet(textVfeelMeal));
                        double rr = Double.parseDouble(safeGet(textVrr));
                        int hr = Integer.parseInt(safeGet(textVhr));

                        SignsValues sv =
                                new SignsValues(
                                        startOfYear,
                                        exercise,
                                        attack,
                                        blueInhaler,
                                        brownInhaler,
                                        outside,
                                        wakeUp,
                                        meal,
                                        feelMeal,
                                        rr,
                                        hr
                                );

                        db.addRow(sv);
                        int totalRecords = db.countRows();
                        Toast toast = Toast.makeText(getApplicationContext(), "Record saved! Total records: "+totalRecords, Toast.LENGTH_LONG);
                        toast.show();

                    }
                }
        );
        resetValues(
                textVstartOfYear,
                textVexercise,
                textVattack,
                textVblueInhaler,
                textVbrownInhaler,
                textVoutside,
                textVwakeUp,
                textVmeal,
                textVfeelMeal,
                db
        );

        Button resetButton = (Button) findViewById(R.id.button2);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetValues(
                        textVstartOfYear,
                        textVexercise,
                        textVattack,
                        textVblueInhaler,
                        textVbrownInhaler,
                        textVoutside,
                        textVwakeUp,
                        textVmeal,
                        textVfeelMeal,
                        db
                );
            }
        });

        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoggerActivity.this,RatesActivity.class);
                startActivityForResult(intent, 2);// Activity is started with requestCode 2

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            int hr=data.getIntExtra("hr",-1);
            double rr=data.getDoubleExtra("rr", -1.0);

            final TextView textVrr = ((TextView) findViewById(R.id.RRText));
            final TextView textVhr = ((TextView) findViewById(R.id.HRText));

            textVhr.setText(""+hr);
            textVrr.setText(""+((int)(rr*100))/100.0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_logger, menu);
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
