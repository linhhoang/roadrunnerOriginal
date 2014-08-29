/*
 * PropertyUtils.java
 *
 */
package roadrunner.util;

import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * The utility class to get properties from resource
 *
 * @author hcnlinh
 * @version $Revision:  $
 */
public final class PropertyUtils
{

    /** The Constant LOG. */
    private static final Logger log = Logger.getLogger(PropertyUtils.class);
    
    private static PropertyUtils instance = null;
    private Properties mappingProperties = null;
    
    
    /**
     * get instance
     *
     * @return PropertyUtils instance
     */
    public static PropertyUtils getInstance()
    {
        if (instance == null)
        {
            instance = new PropertyUtils();
        }
        
        return instance;
    }
    
    /**
     * Constructor
     */
    private PropertyUtils()
    {
        setProperties("roadrunner/configuration.properties");
    }
    
    /**
     * set Properties
     *
     * @param resource
     */
    public void setProperties(String resource)
    {
        try
        {
            mappingProperties = PropertiesLoaderUtils.loadAllProperties(resource);
        }
        catch (IOException e)
        {
            log.error("Error occurred: " + e.getMessage(), e);
        }
    }
    
    /**
     * get value
     *
     * @param configKey
     * @return String
     */
    public String getValue(String configKey)
    {
        String propertyValue = mappingProperties.getProperty(configKey);
        
        if (StringUtils.isNotEmpty(propertyValue))
        {
            Pattern pattern = Pattern.compile("(\\$\\{)(.*)(\\})");
            Matcher matcher = pattern.matcher(propertyValue);
            
            if (matcher.find())
            {
                String group = matcher.group();
                String matchGroup = matcher.group(2);
                if (StringUtils.isNotEmpty(matchGroup))
                {
                    propertyValue = propertyValue.replace(group, getValue(matchGroup));
                }
            }
        }
        else
        {
        	log.error("Key '" + configKey + "' is not defined.");
        }
        return propertyValue;
    }

    /**
     * get integer value of config key
     *
     * @param configKey
     * @return IntValue
     */
    public Integer getIntValue(String configKey)
    {
        String value = getValue(configKey).trim();
        
        if (StringUtils.isEmpty(value))
        {
            return null; 
        }
        Integer intValue = null;
        
        try
        {
            intValue = Integer.valueOf(value);
        }
        catch (NumberFormatException e)
        {
            log.error("Error occurred: " + e.getMessage(), e);
        }
        
        return intValue;
    }
}


/*
 * Changes:
 * $Log: $
 */