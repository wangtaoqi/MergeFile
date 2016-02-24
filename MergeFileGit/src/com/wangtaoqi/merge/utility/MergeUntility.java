/**
 * 
 */

package com.wangtaoqi.merge.utility;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wangtaoqi.merge.AbstractMergeBasis;
import com.wangtaoqi.merge.IOAssist;
import com.wangtaoqi.merge.MergeFileSize;

/** @author 王淘气 */
public class MergeUntility
{
	private static final Logger		logger		= LoggerFactory.getLogger ( MergeUntility.class );
	public final static Properties	properties	= new Properties ( );
	private static String			extractVariable_Regex;
	
	static
	{
		InputStream inputStream = AbstractMergeBasis.class.getResourceAsStream ( "/variable.property" );
		try
		{
			properties.load ( inputStream );
			extractVariable_Regex = properties.getProperty ( "variableRegex" );
		} catch ( IOException e )
		{
			
			e.printStackTrace ( );
		}
	}
	/**
	 * 目录与文件目录进行合并
	 * 去除目录结尾和开头相连的目录
	 * @param directory
	 * @param filename
	 * @return
	 */
	public static  String concatRepeatPath(String directory , String filename )
	{
		//String directory = this.getOutDirectory ( ) ;
		directory  = directory.replaceFirst ( "/$","" );
		filename  = filename.replaceFirst ( "^/","" );
		String filename_first_dirname = filename.replaceFirst ( "^(\\w+)(?>/[\\s\\S]+)" , "$1" );
		String directory_last_dirname = directory.replaceFirst ( "(?:[\\s\\S]+/)(\\w+)$" , "$1" );
		if( directory_last_dirname .endsWith ( filename_first_dirname ))
		{
			directory = directory.replaceFirst ( String.format ( "%s$" , directory_last_dirname ) , "" );
		}
		
		return directory.concat ( "/"+filename ).replace ( "//","/" );
				
	}
	/** 解析参数中的变量
	 * 
	 * <pre>
	 *  尝试将<b>变量名:${variable}</b>替换成<b>变量值</b>,参数的其他部分保持不变
	 * </pre>
	 * 
	 * @param param_value
	 *            参数值
	 * @return 返回解析后的参数值 */
	public static String analyzeParamVariable ( Object target , String param_value )
	{
		Pattern pattern = Pattern.compile ( extractVariable_Regex );
		Matcher matcher = pattern.matcher ( param_value );
		String full = "";
		String varname = "";
		String varvalue = "";
		while ( matcher.find ( ) )
		{
			full = matcher.group ( );
			varname = matcher.group ( 1 );
			varvalue = getBeanProperty ( target , varname );
			if ( varvalue != null )
			{
				varvalue = analyzeParamVariable (target , varvalue  );
				param_value = param_value.replace ( full , varvalue );
			}
		}
		if( param_value != null )
		{
			param_value = param_value.replace ( "//","/");
		}
		return param_value;
		
	}
	
	public static String getBeanProperty ( Object object , String name )
	{
		String value = null;
		BeanUtilsBean beanUtilsBean = BeanUtilsBean.getInstance ( );
		try
		{
			if ( beanUtilsBean.getPropertyUtils ( ).isReadable ( object , name ) )
			{
				value = beanUtilsBean.getProperty ( object , name );
			} else
			{
				logger.warn ( "变量数名:[{}]不正确" , name );
				
			}
		} catch ( IllegalAccessException | InvocationTargetException | NoSuchMethodException e )
		{
			e.printStackTrace ( );
		}
		return value;
	}
	
	/** 创建文件内容签名
	 * 
	 * @param file
	 * @return */
	public static String createSign ( File file )
	{
		String sign = "";
		String content = IOAssist.readFile ( file.getAbsolutePath ( ) , IOAssist.CHARSET_UTF8 );
		sign = DigestUtils.md5Hex ( content );
		return sign;
	}
	
	public static long getUnitSizeValue ( String until )
	{
		long unitvalue = - 1;
		try
		{
			String fieldname = "One_".concat ( until ).toUpperCase ( ).trim ( );
			Field field = FileUtils.class.getField ( fieldname );
			if ( field != null )
			{
				unitvalue = field.getLong ( null );
			}
		} catch ( NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e )
		{
			logger.error ( "getSizeUnitValue异常 " , e );
			e.printStackTrace ( );
		}
		return unitvalue;
	}
	
	private static void setBeanVariable( Object bean ,Field field ){
		
		field.setAccessible ( true );
		if ( field.getType ( ).isAssignableFrom ( String.class ) )
		{
			try
			{
				String value = ( String ) field.get ( bean );
				if ( value != null )
				{
					value = MergeUntility.analyzeParamVariable ( bean , value );
					field.set ( bean , value );
				}
			} catch ( IllegalArgumentException | IllegalAccessException e )
			{
				logger.error ("setBeanVariable异常",e );
				e.printStackTrace ( );
			}
		}
	}
	public static void analyzeBeanVariable ( Object bean )
	{
		Field[] fields = bean.getClass ( ).getDeclaredFields ( );
		for ( Field field : fields )
		{
			System.out.println ( field.getName ( ) );
			setBeanVariable( bean , field );
		}
	}
}
