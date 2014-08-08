package roadrunner.preprocessing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class LabelBase implements Label {

	public LabelBase() {
		postfixes = new ArrayList<String>();
		postfixes.addAll(Arrays.asList(".htm",".html",".xhtml"));
		
	}
	
	protected String inputEncoding = "utf8";
	
	protected String outputEncoding = "utf8";
	
	protected List<String> postfixes;// = {".htm",".html",".xhtml"};
	
	protected boolean filter = true;
	
	
	/**
	 * setting input encoding
	 * @param encode
	 */
	public void setInputEncoding(String encode)
	{
		this.inputEncoding = encode;
	}

	
	/**
	 * setting input encoding
	 * @param encode
	 */
	public void setOutputEncoding(String encode)
	{
	    this.outputEncoding = encode;
	}
	
	/**
	 * 设置是否进行文件名过滤，默认进行文件名过滤，".htm",".html",".xhtml"能够通过过滤
	 * @param f
	 */
	public void setHaveFilter(boolean f)
	{
		this.filter = f;
	}
	
	/**
	 * 设置通过过滤的文件的后缀名
	 * @param postfixList
	 */
	public void setFileFilter(List<String> postfixList)
	{
		this.postfixes.clear();
		for(String fix:postfixList)
		{
			this.postfixes.add(fix.toLowerCase().trim());
		}
	}
	
	/**
	 * 添加能够通过过滤的文件的后缀名
	 * @param postfixList
	 */
	public void addFileFilter(List<String> postfixList)
	{
		for(String fix:postfixList)
		{
			this.postfixes.add(fix.toLowerCase().trim());
		}
	}
	
	/**
	 * 判断文件名是否合理，即是否能通过过滤
	 * @param name
	 * @return
	 */
	protected boolean validFileName(String name)
	{
		if(filter)
		{
			for(String fix: postfixes)
			{
				if(name.trim().toLowerCase().endsWith(fix))
				{
					return true;
				}
			}
			return false;
		}
		else
		{
			return true;
		}
		
	}
	
	


}
