package com.assignmenttracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import android.util.Log;

import org.w3c.dom.Text;

import java.sql.SQLException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AssignmentAdapter.ItemClicked
{
     /*
    @Class MainActivity
    @constructor(Bundle savedInstanceState)
    @return: None
    @purpose:
    1/create MainActivity class
    2/Displays list of assignments
    3/provides buttons to access AddEdit activity
     */

    //window for displaying assignments
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;

    TextView tvMonth, tvDate, tvYear;


    ArrayList<Assignment> assignments;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        /*
        @onCreate method
        @return: none
        @purpose:
        1/initializes activity and links display window with xml field
        2/displays contents of database in display window
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rvAssignmentList);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        tvMonth = findViewById(R.id.tvMonth);
        tvDate = findViewById(R.id.tvDate);
        tvYear = findViewById(R.id.tvYear);

        Date currentTime = Calendar.getInstance().getTime();
        String formattedDate = DateFormat.getDateInstance(DateFormat.FULL).format(currentTime);

        String[] splitDate = formattedDate.split(",");

        Log.d("myLog",currentTime.toString());
        Log.d("myLog",formattedDate);

        tvMonth.setText(splitDate[1]);
        tvDate.setText(splitDate[0]);
        tvYear.setText(splitDate[2]);
        Log.d("myLog",splitDate[0].trim());
        Log.d("myLog",splitDate[1].trim());
        Log.d("myLog",splitDate[2].trim());

        ArrayList<Assignment> assignments = new ArrayList<Assignment>();

        try
        {
            AssignmentsDB db = new AssignmentsDB(this);
            db.open();


            assignments = db.getData();

            db.close();

        }
        catch (SQLException e)
        {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }




        myAdapter = new AssignmentAdapter(this, assignments);

        recyclerView.setAdapter(myAdapter);

    }
        public void btnAddAssignment(View v)
        {
            /*
            @btnAddAssignment method
            @return: none
            @purpose: launches addEdit activity
             */
            startActivity(new Intent(this, AddEdit.class));
        }
        public void btnClear(View view)
        {
            View parent = (View)view.getParent();
            TextView tvAssignmentName = (TextView)parent.findViewById(R.id.tvAssignmentName);
            String Assignment = String.valueOf((tvAssignmentName.getText()));
            ArrayList<Assignment> assignments = new ArrayList<Assignment>();
            try
            {
                AssignmentsDB db = new AssignmentsDB(this);

                db.open();

                db.deleteAssignment(Assignment);
                assignments = db.getData();
                Toast.makeText(this, Assignment, Toast.LENGTH_SHORT).show();

                myAdapter = new AssignmentAdapter(this, assignments);

                recyclerView.setAdapter(myAdapter);
                db.close();

            }
            catch (SQLException e)
            {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            //assignments.remove(0);
            //myAdapter.notifyDataSetChanged();
        }
        public void btnEdit(View view) {
            View parent = (View) view.getParent();
            TextView tvAssignmentName = (TextView) parent.findViewById(R.id.tvAssignmentName);
            String Assignment = String.valueOf((tvAssignmentName.getText()));
            ArrayList<Assignment> assignments = new ArrayList<Assignment>();
            try {
                AssignmentsDB db = new AssignmentsDB(this);

                db.open();
                db.setEdit(Assignment);
                db.close();
            } catch (SQLException e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            startActivity(new Intent(this, AddEdit.class));
        }

    @Override
    public void onItemClicked(int index) {
        //Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, ""+ assignments.get(index).getName(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "" +index, Toast.LENGTH_SHORT).show();

    }
}