/**
 * 
 */

package com.wangtaoqi.merge.utility.compress;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hylanda.resources.IOAssist;
import com.wangtaoqi.merge.YUIConfig;
import com.wangtaoqi.merge.YUIConfig.JavaScript;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

/** @author 王淘气 
 *   压缩Js工具类
 * */
public class JavaScriptCompressUtility
{
	private final static Logger logger = LoggerFactory.getLogger ( JavaScriptCompressUtility.class );
	
	/** @param file_path
	 * @param save_path
	 * @param yuiConfig
	 * @return */
	public static boolean compress ( String file_path , String save_path , YUIConfig yuiConfig )
	{
		boolean is_compressed = false;
		String content = IOAssist.readFile ( file_path , IOAssist.CHARSET_UTF8 );
		StringReader reader = new StringReader ( content );
		BufferedReader in = new BufferedReader ( reader );
		JavaScript javaScriptConfig = yuiConfig.getJavaScript ( );
		if ( javaScriptConfig != null )
		{			
			try
			{
				JavaScriptCompressor compressor = new JavaScriptCompressor ( in ,
						new ErrorReporterJavaScriptCompress ( ) );
				FileOutputStream writer = new FileOutputStream ( save_path );
				OutputStreamWriter outputStreamWriter = new OutputStreamWriter ( writer );
				compressor.compress ( outputStreamWriter , javaScriptConfig.getLinebreak ( ) ,
						javaScriptConfig.isMunge ( ) , javaScriptConfig.isVerbose ( ) ,
						javaScriptConfig.isPreserveAllSemiColons ( ) , javaScriptConfig.isDisableOptimizations ( ) );
				is_compressed = true;
			} catch ( Exception e )
			{
				logger.error ( String.format ( "压缩Js文件:%s异常" , file_path ) , e );
				e.printStackTrace ( );
			}
		}
		else 
		{
			logger.warn ( "没有获得JS压缩配置信息，文件:[{}]忽略压缩操作",file_path ) ;
		}
		return is_compressed;
	}
}
