package roadrunner.preprocessing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.xerces.xni.parser.XMLDocumentFilter;
import org.cyberneko.html.filters.Purifier;
import org.cyberneko.html.filters.Writer;
import org.cyberneko.html.parsers.DOMParser;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

/**
 * 
 * The class for convert html to xhtml using Neko library
 * 
 * @author Admin
 *
 */
public class Html2XhtmlByNeko extends AbstractHtml2XhtmlBase {
	
	/**
	 * Constructor
	 *
	 */
	public Html2XhtmlByNeko()
	{
		
	}

	
	/**
	 * @see research.vn.dataextract.roadrunner.preprocess.Html2Xhtml#convert(java.lang.String, java.lang.String)
	 */
	public String convert(String inputUrl, String outputPath) {
		
		// http://www.iana.org/assignments/character-sets/character-sets.xhtml
		//  编码设置的词要用上面地址里的标准
		OutputStream fileOut;
		try {
			if(!isValidName(inputUrl))
			{
				return null;
			}
			
			fileOut = new FileOutputStream(outputPath);
			XMLDocumentFilter writer = new Writer(fileOut,this.encoding);
			Purifier purifier = new Purifier();
			XMLDocumentFilter [] filters = {writer,purifier};
			
			DOMParser parser = new DOMParser();
			parser.setFeature("http://xml.org/sax/features/namespaces", false);
	        parser.setFeature("http://cyberneko.org/html/features/balance-tags", true);
			parser.setProperty("http://cyberneko.org/html/properties/default-encoding", this.inputEncoding);
			parser.setProperty("http://cyberneko.org/html/properties/filters", 
					filters);
			parser.parse(inputUrl);
			
			return outputPath;
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SAXNotRecognizedException e) {
			e.printStackTrace();
		} catch (SAXNotSupportedException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
		
	}

	
	/**
	 * @see research.vn.dataextract.roadrunner.preprocess.Html2Xhtml#convertAll(java.lang.String, java.lang.String)
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
					String out = convert(tmpFile.toURI().toString(),
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
		
		Html2XhtmlByNeko obj = new Html2XhtmlByNeko();
		obj.setOutputEncoding("utf8"); // setting output encoding of xhtml pages
		obj.setInputEncoding("utf8"); // setting input encoding of html pages
		Scanner sc = new Scanner(System.in);
        System.out.println("Input site name:");
        String siteName = sc.nextLine();
        obj.convertAll(String.format("examples/%s/html/", siteName),
                       String.format("examples/%s/xhtml/", siteName));
        
        System.out.println("-------------- Convert succesful. --------------");

	}
	


}
