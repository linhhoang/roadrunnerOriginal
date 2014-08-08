package roadrunner.application;

/**
 * The factory class for creating extractor using xhtml or html 
 *
 * @author hcnlinh
 * @version $Revision:  $
 */
public class ExtractorFactory {

	/**
	 * Constructor
	 *
	 */
	public ExtractorFactory() {
	    // nop
	}
	
	/**
	 * create extractor using xhtml
	 * @return RoadRunnerApp - xhtml extractor
	 */
	public RoadRunnerApp createExtractorUsingXhtml()
	{
		RoadRunnerApp extracter = new XhtmlExtractor();
		return extracter;
	}
	
	/**
	 * create extractor using html
	 * @return RoadRunnerApp - html extractor
	 */
	public RoadRunnerApp createExtracterUsingHtml()
	{
		RoadRunnerApp extracter = new ExtractFromHtml();
		
		return extracter;
	}
	
	

}
