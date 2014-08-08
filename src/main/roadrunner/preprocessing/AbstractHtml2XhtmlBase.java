package roadrunner.preprocessing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The abstract class for converting html page to xhtml page
 *
 * @author hcnlinh
 * @version $Revision:  $
 */
public abstract class AbstractHtml2XhtmlBase implements Html2Xhtml {
	
	
	/** encoding (gb2312,utf8,shift_jis) */
	protected String encoding;
	
	/** inputEncoding */
	protected String inputEncoding;
	
	/** filter */
	protected boolean filter;
	
	/** postfixes */
	protected List<String> postfixes;
	
	
	/**
	 * is has filter?
	 *
	 * @param hasFilter
	 */
	public void setHasFilter(boolean hasFilter)
	{
		filter = hasFilter;
	}


	
	/**
	 * Constructor
	 *
	 */
	public AbstractHtml2XhtmlBase() {
		
		this.encoding = "utf8";
		this.inputEncoding = "utf8";
		this.filter= true;
		postfixes = new ArrayList<String>();
		String [] fixArr = {".html",".htm",".xhtml"};
		postfixes.addAll(Arrays.asList(fixArr));
		
	}

	/**
	 * @see research.vn.dataextract.roadrunner.preprocess.Html2Xhtml#setOutputEncoding(java.lang.String)
	 */
	public Html2Xhtml setOutputEncoding(String encoding) {
		this.encoding = encoding;
		return this;
	}
	
	/**
	 * @see research.vn.dataextract.roadrunner.preprocess.Html2Xhtml#setInputEncoding(java.lang.String)
	 */
	public Html2Xhtml setInputEncoding(String encoding)
	{
		this.inputEncoding = encoding;
		return this;
	}
	
	
	/**
	 * check if name is valid not not
	 * @param name
	 * @return isValidName
	 */
	public boolean isValidName(String name) {
		
		if(this.filter)
		{
			for(String postfix: postfixes)
			{
				if(name.toLowerCase().trim().endsWith(postfix))
				{
					return true;
				}
			}
			return false;
		}
		return true;
	}
	
	/**
	 * set file filter
	 * @param li_postfix
	 */
	public void setFileFilter(List<String> li_postfix)
	{
		postfixes.clear();
		for(String fix: li_postfix)
		{
			postfixes.add(fix.trim().toLowerCase());
		}
	}
	
	/**
	 * add file filter
	 * @param postfixesParam
	 */
	public void addFileFilter(List<String> postfixesParam)
	{
		for(String fix: postfixesParam)
		{
			this.postfixes.add(fix.trim().toLowerCase());
		}

	}
	
	/**
	 * create xhtml name
	 * @param name
	 * @return xhtml name
	 */
	protected String createXhtmlName(String name)
	{
		return name+"-to-.xhtml";
	}
	
		


}
