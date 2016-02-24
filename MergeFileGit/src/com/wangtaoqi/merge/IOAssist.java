package com.wangtaoqi.merge;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;
/**
 * @author 王淘气
 */
public class IOAssist implements Serializable {
	 public static final int BUFSIZE = 2048 * 8;
	/**
	 * 实现这个接口可以对内容(每行)实现自定义处理
	 * @author 王淘气
	 *
	 */
	public static interface Dispose
	{
		/**
		 * 对每行内容继续自定义处理
		 * @param linecontent 行内容
		 * @return 返回处理后的行内容
		 */
		public String dispose( String linecontent ) ;
	}

	public final static String CHARSET_UTF8 = "utf-8" ;
	public final static String CHARSET_GBK = "gbk" ;
	/**
	 * 创建一个InputStreamReader对象 默认编码是utf-8
	 * @param filepath 文件路径
	 * @return 成功返回一个InputStreamReader对象否则返回一个null
	 */
	public static InputStreamReader createInputStreamReader(String filepath)
	{
		return createInputStreamReader( filepath,CHARSET_UTF8 )  ;
	}
	/**
	 * 创建一个InputStreamReader对象
	 * @param filepath 文件路径
	 * @param chatrset 编码
	 * @return 成功返回一个InputStreamReader对象否则返回一个null
	 */
	public static InputStreamReader createInputStreamReader( String filepath,String chatrset )
	{
		FileInputStream filterInputStream=null ;
		InputStreamReader inputStreamReader=null ;
		try {
			filterInputStream = new FileInputStream( new File( filepath ) ) ;
			inputStreamReader = new InputStreamReader( filterInputStream,chatrset ) ;
		} catch (FileNotFoundException e) {
			e.printStackTrace() ;
		}
		catch(Exception exception)
		{
			exception.printStackTrace() ;
		}

		return  inputStreamReader ;
	}
	public static void  closeFile( Closeable closeable )
	{
		if( closeable != null )
		{
			try
			{
				closeable.close();
			}
			catch(IOException exception)
			{
				exception.printStackTrace() ;
			}
			catch(Exception exception)
			{
				exception.printStackTrace() ;
			}
		}
	}

	/**
	 * 读取文件内容
	 * @param filepath 文件完成路径
	 * @return 返回文件内容 如果读取失败返回null
	 */
	public static String readFile( InputStreamReader inputStream, Dispose dispose )
	{
		String content = null;
		BufferedReader bufferedReader = null ;
		try
		{
			bufferedReader = new BufferedReader( inputStream  )  ;
			StringBuilder builder = new StringBuilder() ;
			String linecontent = "" ;
			while( (linecontent = bufferedReader.readLine() ) != null  )
			{
				// 进行处理
				if( dispose != null )
				{
					linecontent = dispose.dispose( linecontent ) ;
				}
				builder.append( linecontent ).append( "\r\n" ) ;
			}
			content = builder.toString() ;
			content = content.trim() ;
		}
		catch(IOException ioException )
		{
			ioException.printStackTrace() ;
		}
		finally
		{
			IOAssist.closeFile( bufferedReader ) ;
		}
		return content ;
	}
	/**
	 * 读取文件内容
	 * @param is 文件流对象
	 * @param charset 文件编码
	 * @return 成功返回所有内容，否则返回null
	 */
	public static String readFile( InputStream is,String charset )
	{
		String content = readFile( is,charset,null ) ;
		return content ;
	}

	/**
	 * 读取文件内容
	 * @param is 文件流对象
	 * @param charset 读取文件的编码
	 * @param dispose 一个Dispose实现对象，用来处理行内容
	 * @return 成功返回所有内容，否则返回null
	 */
	public static String readFile( InputStream is,String charset,Dispose dispose )
	{
		String content = null ;
		InputStreamReader inputStream = null ;
		try
		{
			inputStream = new InputStreamReader(is,charset);
			content = readFile( inputStream ,dispose ) ;
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		finally
		{

		}
		return content ;
	}

	public static String readFile( String filepath,String charset )
	{
		String content = readFile( filepath,charset,null ) ;
		return content ;
	}

	/**
	 * 读取文件内容
	 * @param filepath 文件完成路径
	 * @param charset 文件编码
	 * @param dispose dispose 接口实例
	 * @return 返回文件内容 如果读取失败返回null
	 */
	public static String readFile( String filepath,String charset,Dispose dispose )
	{
		String content = null;
		InputStreamReader inputStream = null ;
		try
		{
			inputStream =  new InputStreamReader( new FileInputStream( filepath ),charset );
			content = readFile( inputStream, dispose ) ;
		}
		catch(IOException ioException )
		{
			ioException.printStackTrace() ;
		}
		catch(Exception exception)
		{
			exception.printStackTrace() ;
		}
		return content ;
	}
	public static boolean write( String path,String content,String charset ,boolean iscreate,boolean isappend )
	{
		if( iscreate )
		{
			File file = new File(path);
			String name = file.getName();
			String regex = String.format("%s$", name);
			String dir=path.replaceFirst(regex, "");
			file=null;
			file =new File(dir);
			if( !file.exists())
			{
				boolean iscreated = file.mkdirs();
				System.out.println(iscreated);
			}
		}
		return write(path, content, charset,isappend);
	}
	/**
	 * 将内容写入指定文件
	 * @param path  保存内容的文件路径
	 * @param content 需要保存的内容
	 */
	public static boolean write( String path,String content,String charset,boolean isappend )
	{
		boolean issucceed  = false ;
		BufferedWriter bufferedWriter = null ;
		try {

				bufferedWriter = new BufferedWriter(new OutputStreamWriter( new FileOutputStream( path,isappend ),charset ) ) ;
				bufferedWriter.write( content, 0, content.length() ) ;
				bufferedWriter.flush() ;
				issucceed = true ;
		}
		catch (IOException e) {
			e.printStackTrace() ;
		}
		catch (Exception e) {
			e.printStackTrace() ;
		}
		finally
		{
			IOAssist.closeFile( bufferedWriter ) ;
		}
		return issucceed ;
	}
	/**
	 * 文件合并
	 * @param savefile 保存的文件目录
	 * @param mergelist 需要合并的文件列表
	 * @return
	 */
	public static  boolean fileMerge(String savefile,List<String>mergelist )
	{
		boolean ismerge = true ;
		FileChannel outChannel = null;
		try
		{
			 outChannel = new FileOutputStream(savefile).getChannel();
			 for(String f : mergelist){
				  FileChannel fc = new FileInputStream(f).getChannel();
	                ByteBuffer bb = ByteBuffer.allocate(BUFSIZE);
	                while(fc.read(bb) != -1){
	                	  bb.flip();
	                      outChannel.write(bb);
	                      bb.clear();
	                }
	                closeFile(fc);
			 }

		}
		catch(Exception exception)
		{
			ismerge=false;
			exception.printStackTrace();
		}
		finally
		{
			closeFile(outChannel);
		}
		return ismerge ;

	}

}
