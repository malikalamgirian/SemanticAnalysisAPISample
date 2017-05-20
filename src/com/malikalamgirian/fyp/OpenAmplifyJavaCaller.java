/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.malikalamgirian.fyp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 *
 * @author Wasif
 */
public class OpenAmplifyJavaCaller {

    /*
     * Declarations
     */
     private String apiKey;
     private  String analysis;
     private  String outputFormat;
     private String inputText;

     private String URL;

     /*
      * The response to be returned to user
      */
     private String response = "";


    /*
     * Constructor
     */
    public OpenAmplifyJavaCaller() {
        /*
         * My API key.
         */
        apiKey = "eykzzbpqqu2x45dh86wvp3kpvv4yaean";

        /*
         * Standard settings for analysis.
         */
        analysis = "all";

        outputFormat = "xml";

        URL       = "http://portaltnx20.openamplify.com/AmplifyWeb_v20/AmplifyThis";

    }

    /*
     * GetResponse() returns the response gathered from OpenCalais Web Service
     */
    public String getResponse() throws Exception {
        String line;

        try{
            /*
             * Create the URL
             */
            URL url = new URL(URL);

            /*
             * Connect and send the HTTP request.
             */
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(encodeUrlArgs());
            wr.flush();

            /*
             * Get the Response.
             */
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            /**
             * Reset response to default
             */
             response = "";
             
            while ((line = rd.readLine()) != null) {
                response += line + "\n";
            }

        }catch (Exception e) {
            throw new Exception("getResponse has gotten some problem : "
                    + e + " : " + e.getMessage());
        }

        return this.response;
    }


    /*
     * GetResponse() returns the response gathered from OpenCalais Web Service
     * takes String "content" as param
     */
    public String getResponse(String inputText) throws Exception{
        String line;

        try{
            /*
             * set the content string
             */
             setInputText(inputText);

            /*
             * Create the URL
             */
            URL url = new URL(URL);

            /*
             * Connect and send the HTTP request.
             */
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(encodeUrlArgs());
            wr.flush();

            /*
             * Get the Response.
             */
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            /**
             * Reset response to default
             */
             response = "";
            
            while ((line = rd.readLine()) != null) {
                this.response += line + "\n";
            }

        }catch (Exception e) {
            throw new Exception("getResponse has gotten some problem : "
                    + e + " : " + e.getMessage());
        }

        return this.response;
    }


    private String encodeUrlArgs() throws Exception {
        String url_args;

        try{

        url_args = URLEncoder.encode("apiKey", "UTF-8") + "=" + URLEncoder.encode(apiKey, "UTF-8");
            url_args += "&" + URLEncoder.encode("analysis", "UTF-8") + "=" + URLEncoder.encode(analysis, "UTF-8");
            url_args += "&" + URLEncoder.encode("outputFormat", "UTF-8") + "=" + URLEncoder.encode(outputFormat, "UTF-8");
            url_args += "&" + URLEncoder.encode("inputText", "UTF-8") + "=" + URLEncoder.encode(inputText, "UTF-8");

        }
        catch(UnsupportedEncodingException ex){
                throw new Exception("encodeUrlArgs has gotten some problem : UnsupportedEncodingException " 
                    + ex + " : " + ex.getMessage());
        }

        return url_args;
    }


     /*
      * Accessor methods
      */

     /*
      * Getters
      */
     public String getApiKey(){
            return this.apiKey;
     }

     public String getAnalysis(){
            return this.analysis;
     }

     public String getOutputFormat(){
            return this.outputFormat;
     }

     public String getInputText(){
            return this.inputText;
     }

     public String getURL(){
            return this.URL;
     }

     /*
      * Setters
      */
     public String setApiKey(String apiKey){
            return this.apiKey = apiKey ;
     }

     public String setAnalysis(String analysis){
            return this.analysis = analysis ;
     }

     public String setOutputFormat(String outputFormat){
            return this.outputFormat = outputFormat ;
     }

     public String setInputText(String inputText){
            return this. inputText = inputText ;
     }

     public String setURL(String URL){
            return this.URL = URL ;
     }

}
