package com.example.Weyerhaeuser.mymaterialsample;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 * Created by steffenz on 8/20/2014.
 */
public class GetDataAsyncCallFragment extends ListFragment{

    private WeakReference<GetDataAsync> asyncTaskWeakRef;
    private ListView lvItems;


    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        setRetainInstance(true);
        startNewAsyncTask();
    }

    private void startNewAsyncTask() {
        GetDataAsync asyncTask = new GetDataAsync(getActivity());
        this.asyncTaskWeakRef = new WeakReference<GetDataAsync>(asyncTask);

        asyncTask.execute("");

    }


    private class GetDataAsync extends AsyncTask<String, Integer, String> {

        Activity mContext;

        public GetDataAsync(Activity context){
            mContext = context;
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            ///this doesnt work yet in Android L
            //setProgressBarIndeterminateVisibility(true);
            //display progress bar
        }

        @Override
        protected String doInBackground(String... params){
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

            return result;
        }

        @Override
        protected void onProgressUpdate(Integer... values){
            super.onProgressUpdate(values);
            //update the progress bar
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            //setProgressBarIndeterminateVisibility(false);
            //dismiss the progress bar

            XPathFactory factory = XPathFactory.newInstance();
            XPath xPath = factory.newXPath();

            NodeList productGroups = null;
            try {
                productGroups = (NodeList) xPath.evaluate("/root/productGroup",new InputSource(new ByteArrayInputStream(result.getBytes())), XPathConstants.NODESET);
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }
            ArrayList<String> Ouritems = new ArrayList<String>();

            for(int i = 0; i<productGroups.getLength(); i++){
                System.out.println(productGroups.item(i).getFirstChild().getTextContent());
                Ouritems.add((productGroups.item(i).getFirstChild().getTextContent()));
            }

            String[] newArray = Ouritems.toArray(new String[Ouritems.size()]);


            // Initialize the UI components
            lvItems = (ListView) mContext.findViewById(R.id.lvItems);
            // For this moment, you have ListView where you can display a list.
            // But how can we put this data set to the list?
            // This is where you need an Adapter

            // context - The current context.
            // resource - The resource ID for a layout file containing a layout
            // to use when instantiating views.
            // From the third parameter, you plugged the data set to adapter
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext , android.R.layout.simple_list_item_1, newArray);

            // By using setAdapter method, you plugged the ListView with adapter
            lvItems.setAdapter(adapter);

            lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent myIntent = new Intent(mContext, Details.class);
                    String itemClicked = ((TextView) view).getText().toString();
                    myIntent.putExtra("name", itemClicked);
                    mContext.startActivity(myIntent);

                }
            });



        }
    }
}
