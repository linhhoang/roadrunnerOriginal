package roadrunner.application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import roadrunner.Shell;
import roadrunner.Wrapper;


/**
 * The class of extractor using xhtml
 * 
 * @author hcnlinh
 * @version $Revision: $
 */
public class XhtmlExtractor extends AbstractRoadRunnerApplication
{

    static private Logger log = Logger.getLogger(ExtractFromHtml.class.getName());


    /**
     * Constructor
     */
    public XhtmlExtractor()
    {

    }


    /**
     * @see roadrunner.application.RoadRunnerApp#generateWrapper(java.lang.String,
     *      java.lang.String)
     */
    public void generateWrapper(String fileDir, String outDir)
    {

        File f = new File(fileDir);
        if (f.isDirectory())
        {
            String[] xhtmlList = f.list();
            String conf = "-O" + this.confPath;
            String out = "-N" + outDir;

            List<String> argList = new ArrayList<String>();
            argList.add(conf);
            argList.add(out);

            for (String xhtml : xhtmlList)
            {
                System.out.println(xhtml);
                argList.add(fileDir + xhtml);
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
     * @see roadrunner.application.RoadRunnerApp#extract(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    public void extract(String wrapperPath, String filePath, String resultDir)
    {
        List<String> argList = new ArrayList<String>();
        argList.add(wrapperPath);
        argList.add(new File(resultDir).getAbsolutePath() + "\\");
        argList.add(this.inputEncoding);
        File f = new File(filePath);

        argList.add(f.toURI().toString());

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
     * @see roadrunner.application.RoadRunnerApp#extractAll(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    public void extractAll(String wrapperPath, String inputDir, String resultDir)
    {

        List<String> argList = new ArrayList<String>();
        argList.add(wrapperPath);
        argList.add(new File(resultDir).getAbsolutePath() + "\\");
        argList.add(this.inputEncoding);
        File f = new File(inputDir);
        File[] fileList = f.listFiles();
        for (File tmpFile : fileList)
        {
            if (tmpFile.isFile())
            {
                argList.add(tmpFile.toURI().toString());
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
     * @param args
     */
    public static void main(String[] args)
    {

        XhtmlExtractor parser = new XhtmlExtractor();
        parser.setInputFileEncoding("utf8");
        Scanner sc = new Scanner(System.in);
        String contChar = "";
        do
        {
            System.out.println("Input site name:");
            String siteName = sc.nextLine();

            if (siteName == null || siteName.length() == 0)
            {
                siteName = "careerlink";
                System.out.println("Sitename default:'" + siteName + "'");
            }

            System.out.println("Data extract:");
            System.out.println("0: Generate wrapper");
            System.out.println("1: Extract all data");
            System.out.println("Please input a number:");
            int value = sc.nextInt();

            switch (value)
            {
                case 0:
                    parser.generateWrapper("input/" + siteName + "/xhtml/", siteName);
                    break;
                case 1:
                    parser.extractAll(String.format("D:/workspaces/ofwi/roadrunner/output/%s/%s00.xml",
                                                    siteName,
                                                    siteName),
                                      String.format("input/%s/xhtml", siteName),
                                      String.format("output/%s/result", siteName));
                    break;
                default:
            }
            System.out.println("press any to continue, or 'E' to exit");
            contChar = sc.nextLine();
            sc.nextLine();
        }
        while (!"e".equalsIgnoreCase(contChar));

        System.out.println("============  END   ==============");

    }

}
