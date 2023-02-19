package com.assignmenttracker;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;

public class AssignmentsDB
{
    /*
    @Class AssignmentsDB
    @Constructor: AssignmentsDB(Context context)
    @return: none
    @purpose:
    1/create a database for saving assignment data locally on device
    2/create methods for adding, changing, and removing assignments from DB
    3/create method for retrieving data from DB
     */

    //class variables
    public static final String KEY_ROWID = "_id";
    public static final String KEY_NAME = "assignment_name";
    public static final String KEY_TYPE = "assignment_type";
    public static final String KEY_DUEDATE = "assignment_duedate";
    public static final String KEY_CLASS = "assignment_class";
    public static final String KEY_NOTES = "assignment_notes";
    public static final String KEY_EDIT = "assignment_edit";


    private final String DATABASE_NAME = "AssignmentsDB";
    private final String DATABASE_TABLE = "AssignmentsTable";
    private final int DATABASE_VERSION = 1;

    private DBHelper AssignmentsDBHelper;
    private final Context myContext;
    private SQLiteDatabase myDatabase;

    public AssignmentsDB (Context context)
    {
        myContext = context;
    }

    private class DBHelper extends SQLiteOpenHelper
    {
        /*
        @Class AssignmentsDB
        @Constructor: DBHelper (Context context)
        @return: none
        @purpose:
        1/create a method for creating a database for use by assigments db
        2/create a methods for updating a database for use by assigments db

        */

        public DBHelper (Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase)
        {
        /*
        @onCreate method
        @return: none
        @purpose: creates a SQLite database by passing the following sql code as an argument to sqLiteDatabase.execSQL():
        CREATE TABLE AssignmentsTable (_id INTEGER PRIMARY KEY AUTOINCREMENT,
                assignment_name TEXT NOT NULL, assignment_type TEXT NOT NULL,
                assignment_duedate TEXT NOT NULL, asssignment_notes TEXT NOT NULL,
                assignment_class TEXT NOT NULL);
         */
            String sqlcode = "CREATE TABLE " + DATABASE_TABLE + " (" +
                    KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_NAME + " TEXT NOT NULL, " +
                    KEY_TYPE + " TEXT NOT NULL, " +
                    KEY_DUEDATE + " TEXT NOT NULL, " +
                    KEY_CLASS + " TEXT NOT NULL, " +
                    KEY_NOTES + " TEXT NOT NULL, " +
                    KEY_EDIT + " TEXT NOT NULL);";
            sqLiteDatabase.execSQL(sqlcode);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)
        {
            /*
            @onUpgrade method
            @return: none
            @purpose: updates the database and changes the version number
             */
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(sqLiteDatabase);
        }
    }

    public AssignmentsDB open() throws SQLException
    {
        /*
        @open method
        @return AssignmentsDB
        @purpose: opens a new database if one does not already exist
         */
        AssignmentsDBHelper = new DBHelper(myContext);
        myDatabase = AssignmentsDBHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        /*
        @close method
        @return: none
        @purpose: closes the database
         */
        AssignmentsDBHelper.close();
    }
    public long createAssignment(String name, String type, String duedate, String myClass, String notes, String edit)
    {
        /*
        @createAssignment method
        @return long
        @purpose: creates a new assignment in database
         */
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME,name);
        cv.put(KEY_TYPE, type);
        cv.put(KEY_DUEDATE, duedate);
        cv.put(KEY_CLASS, myClass);
        cv.put(KEY_NOTES, notes);
        cv.put(KEY_EDIT, edit);
        return myDatabase.insert(DATABASE_TABLE, null, cv);
    }
    /*
    public ArrayList<Assignment> getArrayList()
    {

    }

     */
    public ArrayList<Assignment> getData()
    {
        /*
        @getData method
        @return: String
        @purpose: gets all entries from the database
         */
        ArrayList<Assignment> assignments = new ArrayList<Assignment>();

        String [] columns = new String [] {KEY_ROWID,KEY_NAME,KEY_TYPE,KEY_DUEDATE,KEY_CLASS,KEY_NOTES,KEY_EDIT};

        Cursor cursor = myDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
        String result  = "";

        int RowIndex = cursor.getColumnIndex(KEY_ROWID);
        int NameIndex = cursor.getColumnIndex(KEY_NAME);
        int TypeIndex = cursor.getColumnIndex(KEY_TYPE);
        int DuedateIndex = cursor.getColumnIndex(KEY_DUEDATE);
        int ClassIndex = cursor.getColumnIndex(KEY_CLASS);
        int NotesIndex = cursor.getColumnIndex(KEY_NOTES);
        int EditIndex = cursor.getColumnIndex(KEY_EDIT);

        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
        {

       //     AL.add(new Assignment(cusror.getString(NameIndex), cursor.getString(TypeIndex), ))
            assignments.add(new Assignment(cursor.getString(NameIndex), cursor.getString(TypeIndex), cursor.getString(DuedateIndex), cursor.getString(ClassIndex), cursor.getString(NotesIndex),cursor.getString(EditIndex)));
        }

        cursor.close();

        return assignments;

    }

    public long deleteAssignment(String name)
    {
        return myDatabase.delete(DATABASE_TABLE,KEY_NAME + "=?", new String[]{name});
    }
    public long setEdit(String name)
    {
        ContentValues cv = new ContentValues();
        cv.put(KEY_EDIT,"true");
        return myDatabase.update(DATABASE_TABLE, cv, KEY_NAME + "=?", new String[]{name});


    }


    /*
    public long updateEntry(String rowIndex, String name, String type, String date, String myClass, String notes)
    {
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME,name);
        cv.put(KEY_TYPE,type);
        cv.put(KEY_DUEDATE, date);
        cv.put(KEY_CLASS, myClass);
        cv.put(KEY_NOTES, notes);

        return myDatabase.update(DATABASE_TABLE, cv, KEY_ROWID + "=?", new String[]{rowIndex});
    }
*/
}
