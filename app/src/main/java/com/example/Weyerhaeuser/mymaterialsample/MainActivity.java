package com.example.Weyerhaeuser.mymaterialsample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;


public class MainActivity extends Activity {

    // Declare the UI components
    private ListView lvItems;

    private ArrayAdapter arrayAdapter;

    private Context ctx;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet request = new HttpGet("http://10.0.2.2:55702/upload" + "/GetInventory");
        request.setHeader("Accept", "application/xml");
        request.setHeader("Content-type", "application/xml");
        String result = "";
        try {
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity);
            System.out.println(result);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        XPathFactory factory = XPathFactory.newInstance();
        XPath xPath = factory.newXPath();

        NodeList productGroups = null;
        try {
            productGroups = (NodeList) xPath.evaluate("/root/productGroup",new InputSource(new ByteArrayInputStream(result.getBytes())), XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        for(int i = 0; i<productGroups.getLength(); i++){
            System.out.println(productGroups.item(i).getFirstChild().getTextContent());
        }

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
        ArrayAdapter<Product> adapter = new ArrayAdapter<Product>(this, android.R.layout.simple_list_item_1, items);

        // By using setAdapter method, you plugged the ListView with adapter
        lvItems.setAdapter(adapter);

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(MainActivity.this, Details.class);
                String itemClicked = ((TextView) view).getText().toString();
                myIntent.putExtra("name", itemClicked);
                MainActivity.this.startActivity(myIntent);

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

    private String writeXml()
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
    }
}





