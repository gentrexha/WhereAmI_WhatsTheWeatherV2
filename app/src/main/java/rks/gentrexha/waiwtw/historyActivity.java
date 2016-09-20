package rks.gentrexha.waiwtw;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

// References:
// http://www.androidauthority.com/use-sqlite-store-data-app-599743/
// http://hmkcode.com/android-custom-listview-items-row/
// None of these said that the layout file for the cursorAdapter had to be a file in its own...

public class historyActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        DBHelper objDB = new DBHelper(this);
        final Cursor objCursor = objDB.getAllLocations();

        String [] columns = new String[] {
                DBHelper.LOCATION_COLUMN_PLACE,
                DBHelper.LOCATION_COLUMN_WEATHER,
                DBHelper.LOCATION_COLUMN_TIME
        };

        int [] widgets = new int[] {
                R.id.place,
                R.id.weather,
                R.id.time
        };

        ListView listView = (ListView) findViewById(R.id.list);
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this,R.layout.location_info,objCursor,columns,widgets,0);
        listView.setAdapter(cursorAdapter);
    }
}
