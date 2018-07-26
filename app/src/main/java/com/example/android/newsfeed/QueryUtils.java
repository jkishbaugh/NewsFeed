package com.example.android.newsfeed;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;

public class QueryUtils {
    //tag for error reporting
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils(){

    }

    public static List<Article> extractArticles(String requestUrl){
        //create the url for the request
        URL url = createUrl(requestUrl);
        //string to hold the json response
        String jsonResponse ="";

        try{
            jsonResponse = makeHttpRequest(url);
        }catch(IOException e){
            Log.e(LOG_TAG,"problem getting jsonResponse",e);
        }

        List<Article> articles = pullArticleData(jsonResponse);
        return articles;
    }

    private static List<Article> pullArticleData(String jsonResponse) {
        if(TextUtils.isEmpty(jsonResponse)){
            return null;
        }
        List<Article> articles = new ArrayList<>();

        try{
            JSONObject baseJson = new JSONObject(jsonResponse);
            JSONArray results =  baseJson.getJSONArray("result");

            //loop through all objects return and extract objects
            for(int i = 0;i< results.length();i++){
                JSONObject currentArticle = results.getJSONObject(i);
                String url = currentArticle.getString("webUrL");
                JSONObject fields = currentArticle.getJSONObject("fields");
                String headline = fields.getString("headline");
                String summary = fields.getString("trailText");
                String byline = fields.getString("byline");
                String date = fields.getString("firstPublicationDate");
                String image = fields.getString("thumbnail");

                Article article = new Article(headline,url, summary, image,byline, date);
                articles.add(article);

            }
        }catch (JSONException e){
            Log.e(LOG_TAG, "problem parsing json",e);
        }
        return articles;
    }

    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse= "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(150000);
            urlConnection.connect();

            if(urlConnection.getResponseCode()== 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else{
                Log.e(LOG_TAG,"Error response code"+ urlConnection.getResponseCode());
            }
        }catch(IOException e){
            Log.e(LOG_TAG, "Problem retrieve json response", e);
        }finally{
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            if(inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while(line!=null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static URL createUrl(String requestUrl) {
        URL url = null;
        try{
            url = new URL(requestUrl);

        }catch(MalformedURLException e){
            Log.e(LOG_TAG,"error creating url",e);
        }
        return url;
    }
}
