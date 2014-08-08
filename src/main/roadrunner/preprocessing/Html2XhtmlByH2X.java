package roadrunner.preprocessing;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * convert html page to xhtml page using external application (Html2Xhtml.exe)
 * 
 * @author hcnlinh
 * @version $Revision: $
 */
public class Html2XhtmlByH2X extends AbstractHtml2XhtmlBase
{

    /**
     * Constructor
     */
    public Html2XhtmlByH2X()
    {
    }


    /**
     * @see research.vn.dataextract.roadrunner.preprocess.Html2Xhtml#convert(java.lang.String,
     *      java.lang.String)
     */
    public String convert(String inputPath, String outputPath)
    {
        // TODO Auto-generated method stub

        if (!new File(inputPath).isFile())
        {
            System.out.println("error:  your input path is not a file!");
            System.exit(-1);
        }
        if (!isValidName(new File(inputPath).getName()))
        {
            return null;
        }

        String cmd[] = {"./exec/html2xhtml.exe", inputPath, "-o", outputPath, "--ics",
                        this.inputEncoding, "--ocs", this.encoding};
        try
        {
            Runtime.getRuntime().exec(cmd);
            return outputPath;

        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    }


    /**
     * @see research.vn.dataextract.roadrunner.preprocess.Html2Xhtml#convertAll(java.lang.String,
     *      java.lang.String)
     */
    public List<String> convertAll(String inputDir, String outputDir)
    {
        if (!new File(outputDir).isDirectory())
        {
            System.out.println("error:  your output dir not exist");
            System.exit(-1);
        }

        File f = new File(inputDir);
        List<String> outList = new ArrayList<String>();
        if (f.isDirectory())
        {
            String outDir = new File(outputDir).getAbsolutePath();
            File[] fileArr = f.listFiles();
            for (File tmpFile : fileArr)
            {
                if (tmpFile.isFile() && isValidName(tmpFile.getAbsolutePath()))
                {
                    String out = convert(tmpFile.getAbsolutePath(),
                                         outDir + "\\" + createXhtmlName(tmpFile.getName()));
                    if (out != null)
                    {
                        outList.add(out);
                    }
                }
            }
        }
        else
        {
            System.out.println("error: your input is not a directory");
        }

        return outList;
    }


    /**
     * @param args
     */
    public static void main(String[] args)
    {
        Html2XhtmlByH2X obj = new Html2XhtmlByH2X();
        obj.setInputEncoding("utf8");
        obj.convertAll("examples/vnwork/html/", "examples/vnwork/xhtml/");
        System.out.println("Convert success.");
    }

}
