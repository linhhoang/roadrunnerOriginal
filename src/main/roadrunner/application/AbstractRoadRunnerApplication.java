package roadrunner.application;

/**
 * The abstract class of RoadRunnerApplication
 * 
 */
public abstract class AbstractRoadRunnerApplication implements RoadRunnerApp
{

    /**
     * input encoding
     */
    protected String inputEncoding;

    /**
     * configuration path
     */
    protected String confPath;


    /**
     * Constructor
     */
    public AbstractRoadRunnerApplication()
    {

        inputEncoding = "utf8";
        confPath = "./etc/wrapperconf.xml";

    }


    /**
     * setter for input file encoding
     * 
     * @param encode - file encoding
     */
    public void setInputFileEncoding(String encode)
    {
        inputEncoding = encode;
    }


    /**
     * setter for config path
     * 
     * @param path - config path
     */
    public void setConfigPath(String path)
    {
        confPath = path;
    }


    /**
     * create xhtml name from html name
     * 
     * @param name
     * @return name - xhtml name
     */
    protected String createXhtmlName(String name)
    {
        return name + "-to-.xhtml";
    }

}
