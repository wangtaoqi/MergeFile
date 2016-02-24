/**
 * 
 */

package testunit;

import java.lang.reflect.Method;

import org.junit.Test;

import com.wangtaoqi.merge.MergeFileSize;
import com.wangtaoqi.merge.utility.MergeUntility;

/** @author 王淘气 */
public class BeanUnit
{
	private  Method getThisSetMethod (  Class  this_class , String name, Class<?>... parameterTypes)
	{
		Method set_method = null;
		try
		{
			set_method =this_class .getMethod ( name , parameterTypes );
		} catch ( NoSuchMethodException | SecurityException e )
		{
			
		}
		return set_method;
	}
	@ Test
	public void updatePropertiy ( )
	{
		MergeFileSize config = new MergeFileSize ( );
		config.setFileMaxSize ( 100 );
		config.setFileSizeUntil ( "kb" );
		config.setOutDirectory ( "pppppp" );
		config.setOutJsonFileName ( "${outDirectory}/111" );
		config.setOutFileName ( "" );
		config.setSourceFileDirectory ( "" );
		config.setSourceFileListPath ( "" );
		System.out.println ( config.getOutJsonFileName ( ) );
		
		/*	*/
		// Field[] fields= config.getClass ( ).getDeclaredFields ( );
		// Method[] methods = config.getClass ( ).getDeclaredMethods ( );
		Class config_class = config.getClass ( ) ;
		Method[] methods = config_class.getMethods ( ) ;
		
		for ( Method method : methods )
		{
			System.out.println ( method.getName ( ) );
			// method.setAccessible ( true );
			try
			{
				String methodname = method.getName ( ) ;
				if ( methodname .startsWith ( "get" )  && method.getParameterTypes ( ).length ==0 )
				{
					String set_methodname = methodname.replaceFirst ( "^g","s");
					Method set_method = null;
				
					//	set_method = 	config_class.getMethod ( set_methodname , String.class );
						set_method = getThisSetMethod( config_class , set_methodname , String.class );
								
				
					if( set_method != null )
					{
						Object val = method.invoke ( config , null );
						if ( val !=null &&  val.getClass ( ).isAssignableFrom ( String.class ) )
						{
							String value = ( String ) val;
							if ( value != null )
							{
								value = MergeUntility.analyzeParamVariable ( config , value );
								set_method.invoke ( config , value );
							}
							
						}
					}
				
				}
			} catch ( Exception e )
			{
				// TODO Auto-generated catch block
				e.printStackTrace ( );
			}
		}
		System.out.println ( config.getOutJsonFileName ( ) );
		
	}
	
	@ Test
	public void getDeclaringClass ( )
	{
		MergeFileSize config = new MergeFileSize ( );
		config.setFileMaxSize ( 100 );
		config.setFileSizeUntil ( "kb" );
		config.setOutDirectory ( "pppppp" );
		config.setOutJsonFileName ( "${outDirectory}/111" );
		config.setOutFileName ( "" );
		config.setSourceFileDirectory ( "" );
		config.setSourceFileListPath ( "" );
		
	}
}
