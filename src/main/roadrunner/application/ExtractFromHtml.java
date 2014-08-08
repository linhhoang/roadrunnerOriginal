package roadrunner.application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import roadrunner.Shell;
import roadrunner.Wrapper;
import roadrunner.preprocessing.Html2Xhtml;
import roadrunner.preprocessing.Html2XhtmlByNeko;
import roadrunner.util.FileUtils;


/**
 * Extract from html
 *
 * @author hcnlinh
 * @version $Revision:  $
 */
public class ExtractFromHtml extends AbstractRoadRunnerApplication
{
    static private Logger log = Logger.getLogger(ExtractFromHtml.class.getName());
    Html2Xhtml converter;


    /**
     * Constructor
     *
     */
    public ExtractFromHtml()
    {
        // TODO Auto-generated constructor stub

        converter = new Html2XhtmlByNeko();
        converter.setInputEncoding(this.inputEncoding);
        converter.setOutputEncoding(this.inputEncoding);

    }


    /**
     * @see roadrunner.application.AbstractRoadRunnerApplication#setInputFileEncoding(java.lang.String)
     */
    @Override
    public void setInputFileEncoding(String encode)
    {
        this.inputEncoding = encode;
        converter.setOutputEncoding(encode);
        converter.setInputEncoding(encode);
    }


    /**
     * set html to xhtml tool
     * 
     * @param convert
     */
    public void setHtmlToXhtmlTool(Html2Xhtml convert)
    {
        this.converter = convert;
    }


    /**
     * generate wrapper
     */
    public void generateWrapper(String fileDir, String wrapperName)
    {
        File f = new File(fileDir);

        String newFileDir = f.getAbsolutePath() + "\\extract\\";
        new File(newFileDir).mkdir();
        if (f.isDirectory())
        {

//            List<String> fileList = converter.convertAll(fileDir, newFileDir);

            String conf = "-O" + this.confPath;
            String out = "-N" + wrapperName;

            List<String> argList = new ArrayList<String>();
            argList.add(conf);
            argList.add(out);

            File [] files = f.listFiles();
            for(File tmpFile: files)
            {
                if(tmpFile.isFile())
                {
                    argList.add(tmpFile.getAbsolutePath());
                }
            }
            
            String[] argv = new String[argList.size()];
            argList.toArray(argv);

            try
            {
                Shell.disguiseShell(argv, this.inputEncoding);
            }
            catch (Exception e)
            {
                log.warning("Error occured: " + e.getMessage());
            }

        }
        else
        {
            System.out.println("error:  your input dir is not correct!");
            System.exit(-1);
        }

    }


    /**
     * @see roadrunner.application.RoadRunnerApp#extract(java.lang.String, java.lang.String, java.lang.String)
     */
    public void extract(String wrapperPath, String filePath, String resultDir)
    {
        List<String> argList = new ArrayList<String>();
        argList.add(wrapperPath);
        argList.add(new File(resultDir).getAbsolutePath() + "\\");
        argList.add(this.inputEncoding);
        File htmlFile = new File(filePath);
        new File(htmlFile.getParent() + "\\extract\\").mkdir();
        String xhtmlPath = converter.convert(htmlFile.toURI().toString(), htmlFile.getParent()
                                                                          + "\\extract\\"
                                                                          + htmlFile.getName()
                                                                          + "-to-.xhtml");
        argList.add(new File(xhtmlPath).toURI().toString());

        String args[] = new String[argList.size()];
        argList.toArray(args);
        try
        {
            Wrapper.extract(args);
        }
        catch (IOException e)
        {
            log.warning("Error occurred: " + e.getMessage());
        }
        catch (Exception e)
        {
            log.warning("Error occurred: " + e.getMessage());
        }

    }


    /**
     * @see roadrunner.application.RoadRunnerApp#extractAll(java.lang.String, java.lang.String, java.lang.String)
     */
    public void extractAll(String wrapperPath, String inputDir, String resultDir)
    {
        List<String> argList = new ArrayList<String>();
        argList.add(wrapperPath);
        argList.add(new File(resultDir).getAbsolutePath() + "\\");
        argList.add(this.inputEncoding);
        File f = new File(inputDir);
        
        File[] listFiles = f.listFiles();
        for (File htmlFile : listFiles)
        {
            if (htmlFile.isFile())
            {
                argList.add(htmlFile.toURI().toString());
            }
        }

        String args[] = new String[argList.size()];
        argList.toArray(args);
        try
        {
            Wrapper.extract(args);
        }
        catch (IOException e)
        {
            log.warning("Error occurred: " + e.getMessage());
        }
        catch (Exception e)
        {
            log.warning("Error occurred: " + e.getMessage());
        }
    }


    /**
     * main method
     *
     * @param args
     */
    public static void main(String[] args)
    {
        ExtractFromHtml parser = new ExtractFromHtml();
        parser.setInputFileEncoding("utf8");
        String siteName = "iconic";
        FileUtils.getInstance().cleanUp("output/logs/");
        FileUtils.getInstance().createFolder(String.format("output/%s/result/", siteName));
        parser.generateWrapper("examples/" + siteName + "/html", siteName);
        parser.extractAll(String.format("output/%1$s/%1$s00.xml", siteName),
                          String.format("output/%s/html", siteName),
                          String.format("output/%s/result", siteName));
        System.out.println("============= END ===============");
    }

}
