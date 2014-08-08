/*
 * FileUtils.java
 *
 * Copyright by Orell F�ssli Wirtschaftsinformationen AG
 * Z�rich
 * All rights reserved.
 */
package roadrunner.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * 
 *
 * @author hcnlinh
 * @version $Revision:  $
 */
public class FileUtils
{
    private static final Logger log = Logger.getLogger(FileUtils.class);
    private static FileUtils instance;
    
    /**
     * get instance
     *
     * @return FileUtils
     */
    public static FileUtils getInstance()
    {
        if (instance == null)
        {
            instance = new FileUtils();
            log.debug("Initialize FileUtils");
        }
        
        return instance;
    }
    
    /**
     * write text file
     *
     * @param content
     * @param filePath
     * @param extension 
     */
    public void writeTextFile(String content, String filePath, String extension) {
        try {
            String fileName = "";
            if (StringUtils.isNotEmpty(extension)) {
                fileName = filePath + "." + extension;
            }
            File file = new File(fileName);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
                log.info(String.format("File '%s' was created.", file.getAbsolutePath()));
            }

            OutputStream ostream = new FileOutputStream(file.getAbsoluteFile());
            Writer fw = new OutputStreamWriter(ostream, "utf8");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * check if file or folder is exist
     *
     * @param path
     * @return is exist
     */
    public boolean checkExist(String path)
    {
        if (StringUtils.isEmpty(path))
        {
            log.warn("file path is null or empty");
            return false;
        }
        File file = new File(path);
        return file.exists();
    }
    
    /**
     * create folder
     *
     * @param folderPath
     * @return is create successful
     */
    public boolean createFolder(String folderPath) {
        boolean result = false;
        File outputFolder = new File(folderPath);
        if (!outputFolder.exists()) {
            System.out.println("creating directory: " + outputFolder.getPath());

            try {
                outputFolder.mkdirs();
                result = true;
            } catch (SecurityException se) {
                log.error("Error is occured: " + se.getMessage());
            }
            if (result) {
                System.out.println("DIR created");
            }
        }

        return result;
    }
    
    
    /**
     * Method description
     *
     * @param crawlStoreFolder
     */
    public void cleanUp(String crawlStoreFolder)
    {
        File folder = new File(crawlStoreFolder);
        
        if (folder.exists())
        {
            try
            {
                org.apache.commons.io.FileUtils.cleanDirectory(folder);
                log.info("Clean up folder '" + folder + "' successful.");
            }
            catch (IOException e)
            {
                log.warn("Error occurred: " + e.getMessage(), e);
            }
        }
    }
    
    
    /**
     * write text file
     *
     * @param content
     * @param filePath
     */
    public void writeTextFile(String content, String filePath)
    {
        writeTextFile(content, filePath, null);
    }
}


/*
 * Changes:
 * $Log: $
 */