package com.example.Weyerhaeuser.mymaterialsample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

    // Declare the UI components


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_main);

        //new GetDataAsync().execute("");


        //moved the async task to a frgament.  to execute the fragment, add the fragment to the fragment manager like so and call commit.
         getFragmentManager().beginTransaction().add(new GetDataAsyncCallFragment(),"GetDataAsyncFragment").commit();



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

    /*private String writeXml()
    {
        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        try{
            serializer.setOutput(writer);
            serializer.startDocument("UTF-8",true);
            serializer.startTag("","messages");
            serializer.attribute("","number", String.valueOf(4));

            serializer.startTag("","message");
            serializer.attribute("","date","7/11/2014");
            serializer.startTag("", "title");
            serializer.text("this is the title");
            serializer.endTag("", "title");
            serializer.startTag("", "url");
            serializer.text("this is some other text");
            serializer.endTag("", "url");
            serializer.startTag("", "body");
            serializer.text("here is the body");
            serializer.endTag("", "body");
            serializer.endTag("", "message");


            serializer.endTag("", "messages");
            serializer.endDocument();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }*/


}







