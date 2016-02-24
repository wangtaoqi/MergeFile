/**
 * 
 */

package testunit;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;

import org.junit.Test;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hylanda.resources.IOAssist;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

/** @author 王淘气 */
public class YUICompressJUnit
{
	final Logger logger = LoggerFactory.getLogger ( this.getClass ( ) );
	
	@ Test
	public void jsCompress ( )
	{
		
		ErrorReporter reporter = new ErrorReporter ( )
		{
			
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
		};
		String base = "f:/temp/file";
		base = "d:/Workspaces/eclipse-DecisionDiagram/DecisionDiagService2.4_Local/frame/WebContent/scripts";
		
		// String path = base + "/mergerFile_0.js";
		// String save = base + "/mergerFile_0_min.js";
		
		String path = base + "/jquery-1.8.3.js";
		String save = base + "/jquery-1.8.3_compress.js";
		String content = IOAssist.readFile ( path , IOAssist.CHARSET_UTF8 );
		StringReader reader = new StringReader ( content );
		BufferedReader in = new BufferedReader ( reader );
		try
		{
			JavaScriptCompressor compressor = new JavaScriptCompressor ( in , reporter );
			FileOutputStream writer = new FileOutputStream ( save );
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter ( writer );
			
			compressor.compress ( outputStreamWriter , 1 , true , true , true , false );
		} catch ( EvaluatorException | IOException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace ( );
		}
	}
}
