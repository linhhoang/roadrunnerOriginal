package roadrunner.preprocessing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;

import roadrunner.application.ExtractFromHtml;

/**
 * 
 * The class for convert html to xhtml using Jtidy library
 * 
 * @author hli
 *
 */
public class Html2XhtmlByJtidy extends AbstractHtml2XhtmlBase {
	
    static private Logger log = Logger.getLogger(Html2XhtmlByJtidy.class.getName());
	/**
	 * Constructor
	 *
	 */
	public Html2XhtmlByJtidy()
	{
		
	}

	
	/**
	 * @see roadrunner.preprocessing.Html2Xhtml#convert(java.lang.String, java.lang.String)
	 */
	public String convert(String inputUrl, String outputPath) {
		
		// http://www.iana.org/assignments/character-sets/character-sets.xhtml
		OutputStream fileOut;
		try {
			if(!isValidName(inputUrl))
			{
				return null;
			}
			
			Tidy tidy = new Tidy();
//	        tidy.setShowWarnings(false);
//	        tidy.setXmlTags(false);
//	        tidy.setInputEncoding(this.inputEncoding);
//	        tidy.setOutputEncoding(this.inputEncoding);
//	        tidy.setXHTML(true); 
//	        tidy.setMakeClean(true);
	        
	        fileOut = new FileOutputStream(outputPath);
	        File file = new File(inputUrl);
	        
	        if (!file.exists() || !file.canRead())
	        {
	            log.warning(String.format("File '%s' is not exist.", inputUrl));
	            return null;
	        }
            InputStream inputStream = new FileInputStream(file);

            Document xmlDoc = tidy.parseDOM(inputStream, fileOut);
            fileOut.close();
			return outputPath;
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        catch (IOException e)
        {
            log.warning("Error occurred: " + e.getMessage());
        }
		return null;
		
		
	}

	
	/**
	 * @see roadrunner.preprocessing.Html2Xhtml#convertAll(java.lang.String, java.lang.String)
	 */
	public List<String> convertAll(String inputDir, String outputDir) {
		
		if(! new File(outputDir).isDirectory())
		{
			System.out.println("output dir not exist");
			System.exit(-1);
		}
		
		File f = new File(inputDir);
		List<String> xhtmls = new ArrayList<String>();
		if(f.isDirectory())
		{
//			String [] fileArr  =f.list();
			String outDir = new File(outputDir).getAbsolutePath();
			File [] files = f.listFiles();
			for(File tmpFile: files)
			{
				if(tmpFile.isFile() && isValidName(tmpFile.getAbsolutePath()))
				{
					String out = convert(tmpFile.getPath(),
							outDir+"\\"+createXhtmlName(tmpFile.getName()) );
					if(out!=null)
					{
						xhtmls.add(out);
					}
				}
			}
		}
		else
		{
			System.out.println("error:  your input is not a dirctory!!");
			System.exit(-1);
		}
		
		return xhtmls;
		
		
	}


	/**
	 * The main method
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		
		Html2XhtmlByJtidy obj = new Html2XhtmlByJtidy();
		obj.setOutputEncoding("utf-8"); // setting output encoding of xhtml pages
		obj.setInputEncoding("utf-8"); // setting input encoding of html pages
		Scanner sc = new Scanner(System.in);
        System.out.println("Input site name:");
        String siteName = sc.nextLine();
        obj.convertAll(String.format("examples/%s/html/", siteName),
                       String.format("examples/%s/xhtml/", siteName));
        
        System.out.println("-------------- Convert succesful. --------------");

	}
	


}
