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
import com.wangtaoqi.merge.YUIConfig.Css;
import com.yahoo.platform.yui.compressor.CssCompressor;

/** @author 王淘气
 * Css压缩工具类
 *  */
public class CssCompressUtility
{
	private final static Logger logger = LoggerFactory.getLogger ( CssCompressUtility.class );
	
	public static boolean compress ( String file_path , String save_path , YUIConfig yuiConfig )
	{
		boolean is_compressed = false;
		String content = IOAssist.readFile ( file_path , IOAssist.CHARSET_UTF8 );
		StringReader reader = new StringReader ( content );
		BufferedReader in = new BufferedReader ( reader );
		Css css_config = yuiConfig.getCss ( );
		if ( css_config != null )
		{			
			try
			{
				CssCompressor compressor = new CssCompressor ( in );
				is_compressed = true;
				FileOutputStream writer = new FileOutputStream ( save_path );
				OutputStreamWriter outputStreamWriter = new OutputStreamWriter ( writer );
				compressor.compress ( outputStreamWriter , css_config.getLinebreakpos ( ) );
			} catch ( Exception e )
			{
				logger.error ( String.format ( "压缩css文件:%s异常" , file_path ) , e ) ;
				e.printStackTrace ( );
			}
		}
		else 
		{
			logger.warn ( "没有获得CSS压缩配置信息，文件:[{}]忽略压缩操作",file_path ) ;
		}
		return is_compressed;
	}
	
}
