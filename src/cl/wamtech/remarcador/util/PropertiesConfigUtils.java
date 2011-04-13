package cl.wamtech.remarcador.util;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 
 * @author vutreras
 *
 */
public class PropertiesConfigUtils extends PropertyPlaceholderConfigurer{

	/**
	 * 
	 */
	public PropertiesConfigUtils() {
		super();
	}
  
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	public Properties getProperties() throws IOException {
		return super.mergeProperties();
	}
	
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	public Map getPropertiesMap() throws IOException {
		return super.mergeProperties();
	}
}