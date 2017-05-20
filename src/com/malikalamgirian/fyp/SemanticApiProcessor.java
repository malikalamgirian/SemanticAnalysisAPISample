/*
 * This calls the semanticApi classes
 * 1. creates Directory
 * 2. calls the subsequent APIs
 * 3. creates XML files, for the sentences
 */
package com.malikalamgirian.fyp;

import java.io.*;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;

/**
 *
 * @author Wasif
 */
public class SemanticApiProcessor {
    /*
     * Declarations
     */

    private String Input_File_URL;

    public SemanticApiProcessor(String Input_File_URL) {

        this.Input_File_URL = Input_File_URL;
    }

    /*
     * processForOpenAmplify : Does complete procedure of
     * 1. creating Directory
     * 2. creating XML files in proper format with responses
     * for OpenAmplifyApI
     */
    public boolean processForOpenAmplify() throws Exception {
        /*
         * Declarations
         */
        OpenAmplifyJavaCaller openAmplifyCaller;
        boolean status;
        NodeList pair, string1, string2;
        Document inputFileDoc;
        Document outputFileDoc;

        /* This stores the name of directory, with complete path */
        String directoryPathName,
                semApiPrefix, /* for the directory name, part 1 */
                fileName, /* for the directory name, part 2 */
                outputXmlFilePathName, /* outputfile path and name */
                pairQuality, /* whether pair is true or false */
                comparisonStringId = "";    /* string with which it is to be compared */

        try {
            /*
             * Create directory name, and path, based on the file selected
             */
            semApiPrefix = "OpenAmplifyResponse";
            fileName = Input_File_URL.substring(Input_File_URL.lastIndexOf("\\") + 1, Input_File_URL.lastIndexOf("."));
            directoryPathName = createDirectoryPathName(semApiPrefix, Input_File_URL);

            /*
             * Create directory using createDirectory()
             */
            status = createDirectory(directoryPathName);

            if (status == false) {
                throw new Exception("createDirectory has gotten some problem.");
            }

            /*
             * Here we parse the input file, and for every pair,
             * extract each string and tag it
             */
            String stringIdNumber = "", /* for output file Id attribute*/
                    stringTextContent = "", /* for output file String's text content*/
                    responseFromSemanticApiAnalysis; /* for RDF or XML response received */

            /* for processing String responseFromSemanticApiAnalysis as Document */
            Document semApiResponseContent;
            /* for documentElement of file inputted */
            Element inputFileDocumentElement;

            /* Semantic Api caller object creation */
            openAmplifyCaller = new OpenAmplifyJavaCaller();

            /* Parse inputted file and get document Element */
            inputFileDoc = XMLProcessor.getXMLDocumentForXMLFile(Input_File_URL);
            inputFileDocumentElement = inputFileDoc.getDocumentElement();

            /* Make NodeList selections of all, Pair, String1 and String2 tags*/
            pair = inputFileDocumentElement.getElementsByTagName("Pair");
            string1 = inputFileDocumentElement.getElementsByTagName("String1");
            string2 = inputFileDocumentElement.getElementsByTagName("String2");

            /*
             * For each Pair
             * 1. get PairQuailty attribute's value
             *
             * And for every string in Pair
             * 1. get StringId attribute
             * 2. get string for String tag's textContent
             * 3. get comparisonStringId
             */
            for (int i = 0; i < pair.getLength(); i++) {
                /*
                 * Get pair quality
                 */
                pairQuality = pair.item(i).getAttributes().getNamedItem("Quality").getNodeValue();

                /*
                 * For both strings of the pair
                 * create separate files
                 */
                for (int j = 0; j < 2; j++) {
                    /*
                     * There are two strings in a pair, so we use condition
                     */

                    if (j == 0) {
                        /*
                         * Extract string and stringId
                         */
                        stringTextContent = string1.item(i).getTextContent();
                        System.out.println(stringTextContent);

                        stringIdNumber = string1.item(i).getAttributes().getNamedItem("Id").getNodeValue();
                        System.out.println(stringIdNumber);

                        /*
                         * Set comparisonStringId
                         */
                        comparisonStringId = string2.item(i).getAttributes().getNamedItem("Id").getNodeValue();
                        System.out.println(comparisonStringId);

                    } else if (j == 1) {
                        /*
                         * Extract string and stringId
                         */
                        stringTextContent = string2.item(i).getTextContent();
                        System.out.println(stringTextContent);

                        stringIdNumber = string2.item(i).getAttributes().getNamedItem("Id").getNodeValue();
                        System.out.println(stringIdNumber);

                        /*
                         * Set comparisonStringId
                         */
                        comparisonStringId = string1.item(i).getAttributes().getNamedItem("Id").getNodeValue();
                        System.out.println(comparisonStringId);

                    }

                    /*
                     * Process string by using the subsequent semantic_Api_Class
                     */
                    responseFromSemanticApiAnalysis = openAmplifyCaller.getResponse(stringTextContent);

                    /*
                     * Convert the response received into DOM
                     */
                    semApiResponseContent = XMLProcessor.stringToXmlDocument(responseFromSemanticApiAnalysis);

                    /*
                     * Generate outputFileDocument, with proper String inclused
                     * with PairQuality and ComparisonStringID
                     */
                    outputFileDoc = xmlDocumentForString(stringIdNumber, stringTextContent, comparisonStringId, pairQuality, semApiResponseContent);

                    /*
                     * Create output file Path and Name
                     */
                    outputXmlFilePathName = directoryPathName + "\\" + semApiPrefix + "_" + stringIdNumber + ".xml";

                    /*
                     * Transfer outputFileDoc to folder created,
                     * with proper .xml filePathName "outputXmlFilePathName"
                     */
                    XMLProcessor.transformXML(outputFileDoc, new StreamResult(new File(outputXmlFilePathName)));

                }

            }
        } catch (Exception e) {
            throw new Exception("processForOpenAmplify has gotten some problem : "
                    + e + " : " + e.getMessage());
        }

        return true;
    }

    /*
     * processForOpenCalais : Does complete procedure of
     * 1. creating Directory
     * 2. creating XML files in proper format with response
     * for OpenCalaisAPI
     */
    public boolean processForOpenCalais() throws Exception {
        /*
         * Declarations
         */
        OpenCalaisJavaCaller openCalaisCaller;
        boolean status;
        NodeList pair, string1, string2;
        Document inputFileDoc;
        Document outputFileDoc;

        /* This stores the name of directory, with complete path */
        String directoryPathName,
                semApiPrefix, /* for the directory name, part 1 */
                fileName, /* for the directory name, part 2 */
                outputXmlFilePathName, /* outputfile path and name */
                pairQuality, /* whether pair is true or false */
                comparisonStringId = "";    /* string with which it is to be compared */

        try {
            /*
             * Create directory name, and path, based on the file selected
             */
            semApiPrefix = "OpenCalaisResponse";
            fileName = Input_File_URL.substring(Input_File_URL.lastIndexOf("\\") + 1, Input_File_URL.lastIndexOf("."));
            directoryPathName = createDirectoryPathName(semApiPrefix, Input_File_URL);

            /*
             * Create directory using createDirectory()
             */
            status = createDirectory(directoryPathName);

            if (status == false) {
                throw new Exception("createDirectory has gotten some problem.");
            }

            /*
             * Here we parse the input file, and for every pair,
             * extract each string and tag it
             */
            String stringIdNumber = "", /* for output file Id attribute          */
                    stringTextContent = "", /* for output file String's text content */
                    responseFromSemanticApiAnalysis; /* for RDF or XML response received      */

            /* for processing String responseFromSemanticApiAnalysis as Document */
            Document semApiResponseContent;
            /* for documentElement of file inputted */
            Element inputFileDocumentElement;

            /* Semantic Api caller object creation */
            openCalaisCaller = new OpenCalaisJavaCaller();

            /* Parse inputted file and get document Element */
            inputFileDoc = XMLProcessor.getXMLDocumentForXMLFile(Input_File_URL);
            inputFileDocumentElement = inputFileDoc.getDocumentElement();

            /* Make NodeList selections of all, Pair, String1 and String2 tags*/
            pair = inputFileDocumentElement.getElementsByTagName("Pair");
            string1 = inputFileDocumentElement.getElementsByTagName("String1");
            string2 = inputFileDocumentElement.getElementsByTagName("String2");

            /*
             * For each Pair
             * 1. get PairQuailty attribute's value
             *
             * And for every string in Pair
             * 1. get StringId attribute
             * 2. get string for String tag's textContent
             * 3. get comparisonStringId
             */
            for (int i = 0; i < pair.getLength(); i++) {
                /*
                 * Get pair quality
                 */
                pairQuality = pair.item(i).getAttributes().getNamedItem("Quality").getNodeValue();

                /*
                 * For both strings of the pair
                 * create separate files
                 */
                for (int j = 0; j < 2; j++) {
                    /*
                     * There are two strings in a pair, so we use condition
                     */

                    if (j == 0) {
                        /*
                         * Extract string and stringId
                         */
                        stringTextContent = string1.item(i).getTextContent();
                        System.out.println(stringTextContent);

                        stringIdNumber = string1.item(i).getAttributes().getNamedItem("Id").getNodeValue();
                        System.out.println(stringIdNumber);

                        /*
                         * Set comparisonStringId
                         */
                        comparisonStringId = string2.item(i).getAttributes().getNamedItem("Id").getNodeValue();
                        System.out.println(comparisonStringId);

                    } else if (j == 1) {
                        /*
                         * Extract string and stringId
                         */
                        stringTextContent = string2.item(i).getTextContent();
                        System.out.println(stringTextContent);

                        stringIdNumber = string2.item(i).getAttributes().getNamedItem("Id").getNodeValue();
                        System.out.println(stringIdNumber);

                        /*
                         * Set comparisonStringId
                         */
                        comparisonStringId = string1.item(i).getAttributes().getNamedItem("Id").getNodeValue();
                        System.out.println(comparisonStringId);
                    }

                    /*
                     * Process string by using the subsequent semantic_Api_Class
                     */
                    responseFromSemanticApiAnalysis = openCalaisCaller.getResponse(stringTextContent);

                    /*
                     * Convert the response received into DOM
                     */
                    semApiResponseContent = XMLProcessor.stringToXmlDocument(responseFromSemanticApiAnalysis);

                    /*
                     * Generate outputFileDocument, with proper String inclused
                     * with PairQuality and ComparisonStringID
                     */
                    outputFileDoc = xmlDocumentForString(stringIdNumber, stringTextContent, comparisonStringId, pairQuality, semApiResponseContent);

                    /*
                     * Create output file Path and Name
                     */
                    outputXmlFilePathName = directoryPathName + "\\" + semApiPrefix + "_" + stringIdNumber + ".xml";

                    /*
                     * Transfer outputFileDoc to folder created,
                     * with proper .xml filePathName "outputXmlFilePathName"
                     */
                    XMLProcessor.transformXML(outputFileDoc, new StreamResult(new File(outputXmlFilePathName)));

                }

            }
        } catch (Exception e) {
            throw new Exception("processForOpenCalais has gotten some problem : "
                    + e + " : " + e.getMessage());
        }

        return true;
    }

    /*
     * This method returns DOM for the string
     * with proper pair quality, and comparisonStringId attributes
     */
    private Document xmlDocumentForString(
            String stringIdNumber, String stringTextContent,
            String comparisonStringId, String pairQuality,
            Document semApiResponseContent)
            throws Exception {

        try {

            Element root = semApiResponseContent.getDocumentElement();

            /*
             * Insert the string information as first child of documentElement
             */
            Element string = semApiResponseContent.createElement("String");

            string.setAttribute("Id", stringIdNumber);
            string.setAttribute("ComparisonStringId", comparisonStringId);
            string.setAttribute("PairQuality", pairQuality);
            string.setTextContent(stringTextContent);
            root.insertBefore(string, root.getFirstChild());

            semApiResponseContent.normalizeDocument();

        } catch (Exception e) {
            throw new Exception("xmlDocumentForString has gotten some problem : "
                    + e + " : " + e.getMessage());
        }

        return semApiResponseContent;
    }


    /*
     * This method creates directory name
     * using Prefix, and the file_URL provided
     */
    private String createDirectoryPathName(String directoryPrefix, String Input_File_URL) throws Exception {
        String directoryPathName;

        try {
            directoryPathName = Input_File_URL.substring(0, Input_File_URL.lastIndexOf("\\") + 1);

            directoryPathName += directoryPrefix + "_" + Input_File_URL.substring(Input_File_URL.lastIndexOf("\\") + 1, Input_File_URL.lastIndexOf("."));
        } catch (Exception e) {
            throw new Exception("createDirectoryPathName has gotten some problem : "
                    + e + " : " + e.getMessage());
        }

        return directoryPathName;
    }

    /*
     * createDirectory : is for creating directory for storing the XML files
     * of subsequent sentences
     */
    private boolean createDirectory(String directoryPathName) throws Exception {
        boolean status;
        
        try {
            File directory = new File(directoryPathName);

            /*
             * if directory already exists delete that
             */
            if (directory.exists()) {
                status = deleteDirectory(directory);
                if (status == false) {
                    throw new Exception("Could not delete existing directory.\n");
                }
            }

            status = directory.mkdir();

        } catch (Exception e) {
            throw new Exception("createDirectory has gotten some problem : "
                    + e + " : " + e.getMessage());
        }
        return status;
    }

    /* Deletes all files and subdirectories under dir.
     * Returns true if all deletions were successful.
     * If a deletion fails, the method stops attempting to delete and returns false.
     */     
    public static boolean deleteDirectory(File directoryToDelete) {
        
        if (directoryToDelete.isDirectory()) {
            String[] children = directoryToDelete.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDirectory(new File(directoryToDelete, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        /* The directory is now empty so delete it. */
        return directoryToDelete.delete();
    }
}
