package com.assignmenttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Button;
import android.widget.DatePicker;
import android.app.DatePickerDialog;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddEdit extends AppCompatActivity {
    /*
    @Class AddEdit
    @constructor(Bundle savedInstanceState)
    @return: None
    @purpose:
    1/create AddEdit class
    2/create fields for entering information
    3/add data to database when submit is clicked
     */

    //text entry variables
    EditText etName;
    EditText etType;
    EditText etDueDate;
    EditText etClass;
    EditText etNotes;
    DatePickerDialog picker;
    Button btnSelectDate;
    int year_x,month_x,day_x;
    String sDayOfMonth;
    String sMonthOfYear;
    String sYear;
    String showDate = "";


    //links text entry variables to boxes in the xml layout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* @onCreate method
            @purpose: initializes screen and links editText boxes to xml fields
            @return: none
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        etName = findViewById(R.id.etName);
        etType = findViewById(R.id.etType);
        etDueDate = findViewById(R.id.etDueDate);
        etClass = findViewById(R.id.etClass);
        etNotes = findViewById(R.id.etNotes);
        Button btnSelectDate = findViewById(R.id.btnSelectDate);
        Spinner mySpinner = (Spinner) findViewById(R.id.mySpinner);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(AddEdit.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Priority));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        Spinner mySpinner2 = (Spinner) findViewById(R.id.mySpinner2);

        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(AddEdit.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Assignments));
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner2.setAdapter(myAdapter2);


        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                day_x = cldr.get(Calendar.DAY_OF_MONTH);
                month_x = cldr.get(Calendar.MONTH);
                year_x = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(AddEdit.this,
                        new DatePickerDialog.OnDateSetListener(){
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){

                                if (dayOfMonth<10){
                                    sDayOfMonth = "0" + dayOfMonth;
                                } else {
                                    sDayOfMonth = Integer.toString(dayOfMonth);
                                }
                                if (monthOfYear<10){
                                    sMonthOfYear = "0" + (monthOfYear + 1);
                                } else {
                                    sMonthOfYear = Integer.toString(monthOfYear+1);
                                }
                                sYear = Integer.toString(year);
                                showDate = sMonthOfYear + "/" + sDayOfMonth + "/" + year;
                                etDueDate.setText(showDate);
                            }
                        },year_x,month_x,day_x);
                picker.show();
            }
        });


        //checks database for assignment being edited
        ArrayList<Assignment> assignments = new ArrayList<Assignment>();
        try
        {
            AssignmentsDB db = new AssignmentsDB(this);
            db.open();


            assignments = db.getData();

            for( int i =0; i < assignments.size(); i++)
            {

                if(assignments.get(i).getEdit().equals("true"))
                {
                    etName.setText( assignments.get(i).getName());
                    etType.setText( assignments.get(i).getType());
                    etDueDate.setText (assignments.get(i).getDueDate());
                    etClass.setText(assignments.get(i).getAssignClass());
                    etNotes.setText(assignments.get(i).getNotes());
                    db.deleteAssignment(assignments.get(i).getName());
                }
            }




            db.close();

        }
        catch (SQLException e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }



    }

    //
    public void btnSubmit(View v)
    {
        /*
        @btnSubmit method
        @purpose: adds data from entry fields to database when Submit button is clicked
        @return: none
         */

        if (etName.getText().toString().equals("") != true && etDueDate.getText().toString().equals("") != true) {


            String name = etName.getText().toString().trim();
            String type = etType.getText().toString().trim();
            String duedate = etDueDate.getText().toString().trim();
            String myClass = etClass.getText().toString().trim();
            String notes = etNotes.getText().toString().trim();

            try {
                AssignmentsDB db = new AssignmentsDB(this);
                db.open();
                db.createAssignment(name, type, duedate, myClass, notes + "\n", "false");
                db.close();
                Toast.makeText(AddEdit.this, "Successfully Added!", Toast.LENGTH_SHORT).show();
                etName.setText("");
                etType.setText("");
                etDueDate.setText("");
                etClass.setText("");
                etNotes.setText("");
            } catch (SQLException e) {
                Toast.makeText(AddEdit.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            startActivity(new Intent(this, MainActivity.class));
        }
        else {
            startActivity(new Intent(this, MainActivity.class));
        }
        }


}