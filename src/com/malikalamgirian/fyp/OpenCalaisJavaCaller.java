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
public class OpenCalaisJavaCaller {

    /*
     * Declarations
     */
     private String licenseID;
     private  String content;
     private  String paramsXML;

     private String URL;

     /*
      * The response to be returned to user
      */
     private String response;
    

    /*
     * Constructor
     */
    public OpenCalaisJavaCaller() {

        /*
         * My API key.
         */

        licenseID = "h2ptnfdct42x99r23j84sr4y";

        /*
         * Standard settings for paramsXML.
         */
        paramsXML = "<c:params xmlns:c=\"http://s.opencalais.com/1/pred/\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">" +
                                "<c:processingDirectives c:contentType=\"TEXT/RAW\" c:enableMetadataType=\"GenericRelations,SocialTags\" c:docRDFaccessible=\"true\" c:calculateRelevanceScore=\"true\" c:outputFormat=\"XML/RDF\">" +
                                "</c:processingDirectives>" +
                                "<c:userDirectives c:allowDistribution=\"true\" c:allowSearch=\"true\" c:externalID=\"fyp\" c:submitter=\"malikalamgirian\">" +
                                "</c:userDirectives>" +
                                "<c:externalMetadata>" +
                                "</c:externalMetadata>" +
                           "</c:params>";

        URL       = "http://api.opencalais.com/enlighten/rest/";
        
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
            throw new Exception("GetResponse has gotten some problem : "
                    + e + " : " + e.getMessage());
        }

        return this.response;
    }

    /*
     * GetResponse() returns the response gathered from OpenCalais Web Service
     * takes String "content" as param
     */
    public String getResponse(String content) throws Exception {
        String line;

        try{
            /*
             * set the content string
             */
             setContent(content);

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
            throw new Exception("GetResponse has gotten some problem : "
                    + e + " : " + e.getMessage());
        }

        return this.response;
    }


    private String encodeUrlArgs() throws UnsupportedEncodingException {
        String url_args;
        
        url_args= URLEncoder.encode("licenseID", "UTF-8") + "=" + URLEncoder.encode(licenseID, "UTF-8");
            url_args += "&" + URLEncoder.encode("content", "UTF-8") + "=" + URLEncoder.encode(content, "UTF-8");
            url_args += "&" + URLEncoder.encode("paramsXML", "UTF-8") + "=" + URLEncoder.encode(paramsXML, "UTF-8");
            
        return url_args;
    }
    

     /*
      * Accessor methods 
      */

    /*
      * Getters
      */
     public String getLicenseID(){
            return this.licenseID;
     }
    
     public String getContent(){
            return this.content;
     }

     public String getParamsXML(){
            return this.paramsXML;
     }

     public String getURL(){
            return this.URL;
     }

     /*
      * Setters
      */
     public String setLicenseID(String licenseID){
            return this.licenseID = licenseID ;
     }

     public String setContent(String content){
            return this.content = content ;
     }

     public String setParamsXML(String paramsXML){
            return this.paramsXML = paramsXML ;
     }

     public String setURL(String URL){
            return this.URL = URL ;
     }

}
