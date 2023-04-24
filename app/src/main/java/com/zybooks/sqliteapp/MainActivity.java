package com.zybooks.sqliteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    // declare views included in the .xml layout file
    private EditText stName, stSurname, stMarks, stId;
    private TextView tvDisplayData;

    //declare reference variable for the 'DatabaseHelper'
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize the views included in the layout file
        initialize();
    }

    // initialize the views included in the layout file
    private void initialize() {
        tvDisplayData = findViewById(R.id.tv_display_data);
        stName = findViewById(R.id.st_name);
        stSurname = findViewById(R.id.st_surname);
        stMarks = findViewById(R.id.st_marks);
        stId = findViewById(R.id.st_id);


        // create 'DatabaseHelper' class object
        dbHelper = new DatabaseHelper(this);
    }

    // method to be called when user clicks 'add data' button
    // method to save edittext fields data to data base with 'DatabaseHelper' class methods
    public void addData(View view) {
        // get text and convert to string
        String name = stName.getText().toString();
        String surname = stSurname.getText().toString();
        String marks = stMarks.getText().toString();
        String id = stId.getText().toString();

        if (!(name.isEmpty() || surname.isEmpty() || marks.isEmpty() || id.isEmpty())) {

            // check if the user entered data in all fields
            // insert data to the table
            ContentValues values = new ContentValues();
            //'DatabaseHelper.COL_0' variable references to the 'COL_0' variable in 'DatabaseHelper' class
            values.put(DatabaseHelper.COL_0, name);
            values.put(DatabaseHelper.COL_1, surname);
            values.put(DatabaseHelper.COL_2, marks);
            values.put(DatabaseHelper.COL_3, id);

            //call 'insertData()' method of 'DataBaseHelper' class
            dbHelper.insertData(values);
            Toast.makeText(this, "You entry was added successfully.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Please provide input for all fields. ", Toast.LENGTH_SHORT).show();
        }
    }

    // method to show database tables when 'view all' button is clicked.
    public void viewAll(View view) {

        Cursor c = dbHelper.getAlldata();
        if(c.moveToFirst()) {
            tvDisplayData.setText("Added records: \n");
            //iterate the rows of table
            do {
                String name = c.getString(0);
                String surname = c.getString(1);
                String marks = c.getString(2);
                String id = c.getString(3);

                //append values to be displayed
                tvDisplayData.append("\nName: " + name);
                tvDisplayData.append("\nSurname: " + surname);
                tvDisplayData.append("\nMarks: " + marks);
                tvDisplayData.append("\nId: " + id);
                tvDisplayData.append("\n\n");

            } while (c.moveToNext());
        }
        // if there are no rows in the table
        else{
            Toast.makeText(this, "The database is empty!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void update(View view) {
        // get text for EditTexts and save into variables
        String name = stName.getText().toString();
        String surname = stSurname.getText().toString();
        String marks = stMarks.getText().toString();
        String id = stId.getText().toString();

        if (!(name.isEmpty() || surname.isEmpty() || marks.isEmpty() || id.isEmpty())) {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COL_0, name);
            values.put(DatabaseHelper.COL_1, surname);
            values.put(DatabaseHelper.COL_2, marks);

            //call 'updateData()' method of 'DataBaseHelper' class
            dbHelper.updateData(id, values);

        }else{
            Toast.makeText(this, "Please provide all fields data", Toast.LENGTH_SHORT).show();
        }
    }

    public void delete(View view) {
        //get entered text from Id EditText fields and save into variable.
        String id = stId.getText().toString();

        //call 'deleteData()' method of 'DataBaseHelper' class
        dbHelper.deleteData(id);
    }
}