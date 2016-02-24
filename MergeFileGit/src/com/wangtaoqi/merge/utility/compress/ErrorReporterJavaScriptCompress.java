/**
 * 
 */
package com.wangtaoqi.merge.utility.compress;

import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 王淘气
 * 压缩错误报告类
 */
public class ErrorReporterJavaScriptCompress implements ErrorReporter
{
	private final Logger logger = LoggerFactory.getLogger ( this.getClass ( ) );
	@ Override
	public void warning ( String arg0 , String arg1 , int arg2 , String arg3 , int arg4 )
	{
		logger.warn ( String.format ( "warning:%s,%s,%s,%s,%s" , arg0 , arg1 , arg2 , arg3 , arg4 ) );
		
	}
	
	@ Override
	public EvaluatorException runtimeError ( String arg0 , String arg1 , int arg2 , String arg3 , int arg4 )
	{
		EvaluatorException evaluatorException = new EvaluatorException (
				String.format ( "error:%s,%s,%s,%s,%s" , arg0 , arg1 , arg2 , arg3 , arg4 ) );
		evaluatorException.setStackTrace ( Thread.currentThread ( ).getStackTrace ( ) );
		return evaluatorException;
	}
	
	@ Override
	public void error ( String arg0 , String arg1 , int arg2 , String arg3 , int arg4 )
	{
		logger.error ( String.format ( "error:%s,%s,%s,%s,%s" , arg0 , arg1 , arg2 , arg3 , arg4 ) );
		
	}
	
}
