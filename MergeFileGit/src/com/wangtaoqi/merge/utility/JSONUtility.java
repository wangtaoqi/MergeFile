/**
 * 
 */

package com.wangtaoqi.merge.utility;

import java.io.IOException;
import java.io.OutputStream;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

/** @author 王淘气 */
public class JSONUtility
{
	public static String SpringMvcMapperWriteJSON(Object body)
	{
		ObjectMapper mapper = new ObjectMapper();
		String json = "{}";
		try {
			json = mapper.writeValueAsString(body);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json ;
	}
	public static void SpringMvcMapperWriteJSON(Object body,OutputStream out)
	{
		ObjectMapper mapper = new ObjectMapper();
		//String json = "{}";
		try {	
					mapper.writeValue(out, body);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//	return json ;
	}
	public static <T> T mapperReadValue ( String body , TypeReference<T> valueTypeRef )
	{
		ObjectMapper mapper = new ObjectMapper ( );
		mapper.configure ( DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY , true );
		T t = null;
		try
		{
			t = mapper.readValue ( body , valueTypeRef );
		} catch ( JsonParseException e )
		{
			
		} catch ( JsonMappingException e )
		{
			
		} catch ( IOException e )
		{
			
		}
		return t;
	}
}
