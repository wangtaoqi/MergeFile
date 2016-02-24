/**
 * 
 */
package testunit;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.wangtaoqi.merge.MergeConfig;
import com.wangtaoqi.merge.MergeFileSize;
import com.wangtaoqi.merge.YUIConfig;
import com.wangtaoqi.merge.YUIConfig.Css;
import com.wangtaoqi.merge.YUIConfig.JavaScript;
import com.wangtaoqi.merge.utility.JSONUtility;


/**
 * @author 王淘气
 *
 */
public class TestJunit {

	public void display(String s1,String [] array)
	{
		for(String s : array)
		{
			System.out.println(s);
		}
	}
	//@org.junit.Test
	public void test()
	{
		// d:/Workspaces/eclipse-DecisionDiagram/DecisionDiagService2.4_Local/frame/WebContent/decision/js/out/merge/includeFile.js
		//d:/Workspaces/eclipse-DecisionDiagram/DecisionDiagService2.4_Local/frame/WebContent/decision/js/api.js
		String path = "d:/Workspaces/eclipse-DecisionDiagram/DecisionDiagService2.4_Local/frame/WebContent/decision/js/out/merge/../../api.js";
		String normalize  = "d:/Workspaces/eclipse-DecisionDiagram/DecisionDiagService2.4_Local/frame/WebContent/decision/js/api.js";
		System.out.println(FilenameUtils.normalize(path).replace("\\", "/"));
		System.out.println(  normalize );
	}
	@org.junit.Test
	public void test1()
	{
		MergeFileSize  mergeFileSize = new  MergeFileSize ( ) ;
		mergeFileSize.readMergeCofig ( );
		System.out.println (  mergeFileSize.getOutFileJsonParamName ( )  );
	}
	@org.junit.Test
	public void testAnalyze()
	{
			String text = "123${jsonPath}/out/0.txt";
			text = "$${jsonPath}/out/0.txt";
			InputStream  inputStream= this.getClass ( ). getResourceAsStream ( "/variable.property" );
			Properties   properties = new Properties ( ) ;
			try
			{
				properties.load ( inputStream );
			} catch ( IOException e )
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String regex =  properties.getProperty ( "variableRegex" ) ;
			Pattern pattern =Pattern.compile (regex );
			Matcher  matcher =  pattern.matcher ( text );
			while ( matcher.find ( ) )
			{
				System.out.println ( matcher.group ( ) );
				System.out.println ( matcher.group ( 1) );
				
			}
	}
	@org.junit.Test
	public void createConfigJson()
	{
		MergeConfig config = new MergeConfig ( ) ;
		config.setFileMaxSize ( 100 );
		config.setFileSizeUntil ( "kb" );
		config.setOutDirectory ( "" );
		config.setOutFileJsonParamName ( "" );
		config.setOutFileName ( "" );
		config.setSourceFileDirectory ( "" );
		config.setSourceFileListPath ( "" );
		
		System.out.println ( JSONUtility.SpringMvcMapperWriteJSON ( config ) );
	}
	@Test
	public void basis()
	{
		String path = "D:/版本控制-代码/本地SVN/SVN本地版本库/mergeFile/123.txt.js";
		// path = "SVN本地版本库/mergeFile/";
		System.out.println ( path );
		System.out.printf ("getBaseName:%s\n",  FilenameUtils .getBaseName ( path ) );
		System.out.printf ("getExtension:%s\n",  FilenameUtils .getExtension ( path ) );
		System.out.printf ("getFullPath:%s\n",  FilenameUtils .getFullPath  ( path ) );
		System.out.printf ("ONE_KB:%s\n",  FileUtils.ONE_KB );
		System.out.printf ("ONE_MB:%s\n",  FileUtils.ONE_MB );
		
	}
	@Test
	public void staticVariable()
	{
	//	MergeFileSize fileSize = new MergeFileSize ( );
	//	System.out.println ( fileSize.getBeanProperty ( new FileUtils() , "ONE_MB" ) );
		try
		{
			Field field =  FileUtils.class.getField ( "ONE_GB" );
			System.out.println ( field.getLong ( null ) );
		} catch ( NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void createMD5()
	{
		System.out.println ( DigestUtils.md5Hex ( "1" ) );
	}
	@Test
	public void relpaceFileName()
	{
		String name = "f:/temp/jar/merge/123/f.txt";
		String filename = FilenameUtils.getName ( name );
		String extension = FilenameUtils.getExtension ( name ) ;
		if( StringUtils.isNotEmpty ( extension ))
		{
			name  =name.replaceAll ( String.format ( "([\\s\\S]+?)(%s)$" , extension ) , "$1_1$2" );
		}
		System.out.println ( name );
	}
	@Test
	public void directoryContains()
	{
		try
		{
			String p = "d:/Workspaces/eclipse-DecisionDiagram/DecisionDiagService2.4_Local/frame/WebContent/decision/";
			String c = "d:/Workspaces/eclipse-DecisionDiagram/DecisionDiagService2.4_Local/frame/WebContent/decision/js/";
			System.out.println ( FilenameUtils.directoryContains ( p,c ) );
			System.out.println ( FilenameUtils.directoryContains ( c,p ) );
		} catch ( IOException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String repeatPath(String directory , String filename )
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
	@Test
	public void repeatPath()
	{
			String directory = "d:/Workspaces/eclipse-DecisionDiagram/DecisionDiagService2.4_Local/frame/WebContent/decision/js";
			String filename = "/js/merge/mergerFile.js";
			System.out.println ( repeatPath( directory ,filename ) );;
	}
}
