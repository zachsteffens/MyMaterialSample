package com.example.Weyerhaeuser.mymaterialsample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    // Declare the UI components
    private ListView lvItems;

    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Product[] items = {
                new Product(2, "Butter", 15.99),
                new Product(3, "Yogurt", 14.90),
                new Product(4, "Toothpaste", 7.99),
                new Product(5, "Ice Cream", 10.00),
        };

        // Initialize the UI components
        lvItems = (ListView) findViewById(R.id.lvItems);
        // For this moment, you have ListView where you can display a list.
        // But how can we put this data set to the list?
        // This is where you need an Adapter

        // context - The current context.
        // resource - The resource ID for a layout file containing a layout
        // to use when instantiating views.
        // From the third parameter, you plugged the data set to adapter
        ArrayAdapter<Product> adapter = new ArrayAdapter<Product>(this,android.R.layout.simple_list_item_1,items);

        // By using setAdapter method, you plugged the ListView with adapter
        lvItems.setAdapter(adapter);

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = ((TextView)view).getText().toString();
                Toast.makeText(getBaseContext(),item,Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}



