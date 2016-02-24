
package com.wangtaoqi.merge;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wangtaoqi.merge.utility.JSONUtility;
import com.wangtaoqi.merge.utility.MergeUntility;

/** @author 王淘气 */
public abstract class AbstractMergeBasis extends MergeConfig implements Merge
{
	protected final Logger				logger		= LoggerFactory.getLogger ( this.getClass ( ) );
	protected String currentFileName ;
	protected long						scalerFileSize;
	
	@ Override
	public abstract void mergeFile ( File targetfile );
	

	@ Override
	public abstract File createNewFile ( );

	@ Override
	public abstract boolean isCreate ( );
	
	/** 创建文件内容签名
	 * 
	 * @param file
	 * @return */
	public String createSign ( File file )
	{
		String sign = "";
		String content = IOAssist.readFile ( file.getAbsolutePath ( ) , IOAssist.CHARSET_UTF8 );
		sign = DigestUtils.md5Hex ( content );
		return sign;
	}
	
	public String getBeanProperty ( Object object , String name )
	{
		return MergeUntility .getBeanProperty ( object , name );
		
	}
	
	protected long getUnitSizeValue ( String until )
	{
		return MergeUntility.getUnitSizeValue ( until );
	}
	
	private void scalerFileSize ( )
	{
		 this.scalerFileSize =  this.getUnitSizeValue (  this.getFileSizeUntil ( )  )*this.getFileMaxSize ( ) ;
	}
	
	private  Method getThisSetMethod(  String name, Class<?>... parameterTypes)
	{
		Method set_method = null;
		try
		{
			set_method = this.getClass ( ).getMethod ( name , parameterTypes );
		} catch ( NoSuchMethodException | SecurityException e )
		{
			//忽略
		}
		return set_method;
	}
	/**
	 * 通过反射获得参数值
	 */
	protected void autoAnalyzeParamVariable()
	{
		Class config_class = this.getClass ( ) ;
		Method[] methods = config_class.getMethods ( ) ;
		String methodname = "";
		for ( Method method : methods )
		{
			try
			{
				 methodname = method.getName ( ) ;
				if ( methodname .startsWith ( "get" )  && method.getParameterTypes ( ).length ==0 )
				{
					String methodname_set = methodname.replaceFirst ( "^g","s");
					Method method_set = null;
						method_set = getThisSetMethod( methodname_set , String.class ) ;
					if( method_set != null )
					{
						Object val = method.invoke ( this , null );
						if ( val !=null &&  val.getClass ( ).isAssignableFrom ( String.class ) )
						{
							String value = ( String ) val;
							if ( value != null )
							{
								value = MergeUntility.analyzeParamVariable ( this , value );
								method_set.invoke ( this , value );
							}
							
						}
					}
				
				}
			} catch ( Exception e )
			{
				e.printStackTrace ( );
			}
		}
	}
	/** 读取配置 */
	public void readMergeCofig ( )
	{
		String path = System.getProperty ( "user.dir" );
		String configpath = path.concat ( "/merge_config.js" );
		String config_json = IOAssist.readFile ( configpath , IOAssist.CHARSET_UTF8 );
		MergeConfig mergeConfig = JSONUtility.mapperReadValue ( config_json , new TypeReference<MergeConfig> ( )
		{
		} );
		try
		{
			BeanUtilsBean.getInstance ( ).copyProperties ( this , mergeConfig );
			this.autoAnalyzeParamVariable ( );
			System.out.println ( JSONUtility.SpringMvcMapperWriteJSON ( this ) );
			scalerFileSize ( );
		} catch ( IllegalAccessException | InvocationTargetException e )
		{
			logger.error ( "解析配置文件过程中遇到错误:" , e );
			e.printStackTrace ( );
		}
		logger.info ( String.format ( "readMergeCofig Path:%s" , configpath ) );
	}
	
	/** @return the scalerFileSize */
	public long getScalerFileSize ( )
	{
		return scalerFileSize;
	}

	/**
	 * @return the currentFileName
	 */
	protected String getCurrentFileName ( )
	{
		return currentFileName;
	}

	/**
	 * @param currentFileName the currentFileName to set
	 */
	protected void setCurrentFileName ( String currentFileName )
	{
		this.currentFileName = currentFileName;
	}
}
