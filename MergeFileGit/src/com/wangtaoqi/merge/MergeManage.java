/**
 * 
 */

package com.wangtaoqi.merge;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wangtaoqi.merge.utility.JSONUtility;
import com.wangtaoqi.merge.utility.compress.CssCompressUtility;
import com.wangtaoqi.merge.utility.compress.JavaScriptCompressUtility;

/** @author 王淘气
 * 文件合并管理控制操作
 * 
 *  
 *  */
public class MergeManage
{
	protected final Logger				logger				= LoggerFactory.getLogger ( this.getClass ( ) );
	private AbstractMergeBasis			merge				= new MergeFileSize ( );
	private Map<String , List<String>>	sourcesFileMap		= new HashMap<> ( );
	private final StringBuilder			content_builder		= new StringBuilder ( );
	private String						outFileJsonPath;
	private StringBuilder				summaryBuilder		= new StringBuilder ( );
	private Map<String , Boolean>		cleanMap			= new HashMap<> ( );
	private final String				summaryDirectory	= "summary/";
	private final String				compressFileSuffix	= "_min";
	private YUIConfig					yuiConfig;
										
	public MergeManage ( )
	{
		init ( );
	}
	
	private void summary ( File file )
	{
		
		if ( this.merge.isCreate ( ) )
		{
			String summary_file = summaryDirectory.concat ( file.getName ( ) ).concat ( ".summary" );
			new File ( FilenameUtils.getFullPath ( summary_file ) ).mkdirs ( );
			this.logger.info ( "summaryFilePath:{}" , summary_file );
			if ( this.merge.isCreateFileSign ( ) )
			{
				String sign_md5 = DigestUtils.md5Hex ( this.content_builder.toString ( ) );
				this.addSummaryContent ( String.format ( "sign_md5:%s\r\n" , sign_md5 ) );
				this.logger.info ( "生成：{}文件内容签名:{}" , file.getName ( ) , sign_md5 );
			}
			IOAssist.write ( summary_file , summaryBuilder.toString ( ) , IOAssist.CHARSET_UTF8 , false );
			cleanSummary ( );
		}
	}
	
	private void init ( )
	{
		boolean isdirectory = true;
		outFileJsonPath = merge.getOutDirectory ( )+ "/"+ merge.getOutJsonFileName ( );
		outFileJsonPath =outFileJsonPath.replace ("//","/" );
		cleanMap.put ( merge.getOutDirectory ( ) , isdirectory );
		cleanMap.put ( outFileJsonPath , false );
		cleanMap.put ( summaryDirectory , true );
		
		this.yuiConfig = merge.getYuiConfig ( );
	}
	
	private boolean isSecureDeleteDirectory ( String del_directory )
	{
		boolean is_secure = false;
		try
		{
			is_secure = ! FilenameUtils.directoryContains ( merge.getSourceFileDirectory ( ) , del_directory );
			if ( ! is_secure )
			{
				
				logger.warn ( String.format ( "忽略删除%s目录操作，因为当前目录包括sourceFileDirectory目录[%s]，建议更换其他目录" , del_directory ,
						merge.getSourceFileDirectory ( ) ) );
			}
		} catch ( Exception e )
		{
			logger.error ( "isSecureDeleteDirectory:" , e );
			e.printStackTrace ( );
		}
		return is_secure;
	}
	
	public MergeManage clean ( )
	{
		File cleanfile = null;
		String path = "";
		for ( Map.Entry<String , Boolean> cleanpath : cleanMap.entrySet ( ) )
		{
			path = cleanpath.getKey ( );
			cleanfile = new File ( path );
			logger.info ( "delete:{}" , path );
			if ( this.isSecureDeleteDirectory ( path ) )
			{
				FileUtils.deleteQuietly ( cleanfile );
			}
			
			if ( cleanpath.getValue ( ) )
			{
				cleanfile.mkdirs ( );
			}
		}
		return this;
	}
	
	private void addSummaryContent ( String content )
	{
		this.summaryBuilder.append ( content ).append ( "\r\n" );
	}
	
	private void cleanSummary ( )
	{
		int length = this.summaryBuilder.length ( );
		if ( length > 0 )
		{
			this.summaryBuilder.delete ( 0 , length );
		}
	}
	
	public MergeManage analyzeSourcesFile ( )
	{
		String sourcefilelistpath = merge.getSourceFileListPath ( );
		String content = IOAssist.readFile ( sourcefilelistpath , IOAssist.CHARSET_UTF8 );
		
		if ( StringUtils.isNotEmpty ( content ) )
		{
			List<String> sourcesFileList = JSONUtility.mapperReadValue ( content , new TypeReference<List<String>> ( ){} );
			if ( sourcesFileList != null )
			{
				sourcesFileMap.put ( this.merge.getOutFileName ( ) , sourcesFileList );
			} else
			{
				sourcesFileMap = JSONUtility.mapperReadValue ( content ,
						new TypeReference<Map<String , List<String>>> ( )
						{
						} );
			}
		} else
		{
			logger.warn ( "sourceFileListPath:{}内容为空" , sourcefilelistpath );
		}
		return this;
	}
	
	private void cleanContent ( )
	{
		if ( content_builder.length ( ) > 0 )
		{
			content_builder.delete ( 0 , content_builder.length ( ) );
		}
	}
	
	private String fileAddCompressSuffix ( String path )
	{
		String filename = FilenameUtils.getBaseName ( path );
		String extension = FilenameUtils.getExtension ( path );
		extension = StringUtils.isNotEmpty ( extension ) ? ".".concat ( extension ) : "";
		String replace_new = String.format ( "$1%s%s" , compressFileSuffix , extension );
		// String min_name = filename.replaceAll ( "([\\s\\S]+)" ,
		// "$1_min".concat ( extension ) ) ;
		String min_name = filename.replaceAll ( "([\\s\\S]+)" , replace_new );
		String path_min = path.replaceAll ( FilenameUtils.getName ( path ).concat ( "$" ) , min_name );
		return path_min;
	}
	
	private void addContent ( String start_comments , String content , String content_attach , String end_comments )
	{
		// 开始备注
		if ( this.merge.isComments ( ) && StringUtils.isNotEmpty ( start_comments ) )
		{
			content_builder.append ( "/* mergeFileStart " ).append ( start_comments ).append ( " */ \r\n" );
		}
		content_attach = StringUtils.isNotEmpty ( content_attach ) ? content_attach : "";
		content_builder.append ( content ).append ( content_attach );
		// 结束备注
		if ( this.merge.isComments ( ) && StringUtils.isNotEmpty ( end_comments ) )
		{
			content_builder.append ( "\r\n /* mergeFileEnd " ).append ( end_comments ).append ( "*/ \r\n\r\n" );
		}
	}
	
	private File compress ( File file )
	{
		return this.compress ( file , null );
	}
	
	// 进行文件压缩
	private File compress ( File file , String extension )
	{
		File compress_file = file;
		final String file_absolutepath = file.getAbsolutePath ( );
		boolean iscompress = false;
		final String filepath_compress = this.fileAddCompressSuffix ( file_absolutepath );
		extension = StringUtils.isEmpty ( extension ) ? FilenameUtils.getExtension ( file_absolutepath ) : extension;
		switch ( extension )
		{
			case YUIConfig.COMPRESS_EXTENSION_CSS : {
				iscompress = CssCompressUtility.compress ( file_absolutepath , filepath_compress , yuiConfig );
				break;
			}
			case YUIConfig.COMPRESS_EXTENSION_JAVASCRIPT : {
				iscompress = JavaScriptCompressUtility.compress ( file_absolutepath , filepath_compress , yuiConfig );
				break;
			}
		}
		
		// 压缩成功，替换文件对象
		if ( iscompress )
		{
			compress_file = new File ( filepath_compress );
		}
		return compress_file;
	}
	
	public MergeManage mergeFile ( )
	{
		String sourceFilePath = "";
		String content = "";
		File save_merge_file = null;
		String file_unit_size = "";
		for ( Map.Entry<String , List<String>> entry : sourcesFileMap.entrySet ( ) )
		{
			for ( String source : entry.getValue ( ) )
			{
				merge.setOutFileName ( entry.getKey ( ) );
				
				sourceFilePath = merge.getSourceFileDirectory ( );
				sourceFilePath = sourceFilePath.concat ( source );
				content = IOAssist.readFile ( sourceFilePath , IOAssist.CHARSET_UTF8 );
				/*
				 * 如果新建文件
				 * 将上次的文件内容尝试进行压缩处理
				 */
				if ( this.merge.isCreate ( ) && save_merge_file != null )
				{
					File file_compress = this.compress ( save_merge_file );
					String save_merge_filename = save_merge_file.getName ( );
					String file_compress_filename = file_compress.getName ( );
					if ( ! save_merge_filename.equals ( file_compress_filename ) )
					{
						this.compressMergeFiles ( save_merge_file );
						
					}
				}
				save_merge_file = this.merge.createNewFile ( );
				this.addContent ( sourceFilePath , content , this.merge.getContentAttachText ( ) , sourceFilePath );
				// 保存文件
				IOAssist.write ( save_merge_file.getAbsolutePath ( ) , content_builder.toString ( ) ,
						IOAssist.CHARSET_UTF8 , true );
						
				// 获得文件大小(带单位）
				file_unit_size = FileUtils.byteCountToDisplaySize ( save_merge_file.length ( ) );
				String log = String.format ( "writeFile:%s,dispalySize:%s" , save_merge_file.getAbsolutePath ( ) ,
						file_unit_size );
				logger.info ( log );
				
				this.addSummaryContent ( sourceFilePath );
				this.summary ( save_merge_file );
				cleanContent ( );
			}
			this.compressMergeFiles ( save_merge_file );			
		}
		return this;
	}
	
	private void compressMergeFiles ( File save_merge_file )
	{
		if ( save_merge_file != null )
		{
			List<String> mergefilelist = this.merge.getMergeFileList ( );
			File file_compress = this.compress ( save_merge_file );
			String save_merge_filename = save_merge_file.getName ( );
			String file_compress_filename = file_compress.getName ( );
			if ( ! save_merge_filename.equals ( file_compress_filename ) )
			{
				int last_index = mergefilelist.size ( ) - 1;
				// TODO : 从完整路径中获得文件的相对目录路径
				String current_merge_filename = mergefilelist.get ( last_index );
				current_merge_filename = current_merge_filename.replaceAll ( save_merge_filename.concat ( "$" ) ,
						file_compress_filename );
				mergefilelist.set ( last_index , current_merge_filename );
			}
		}
	}
	
	public MergeManage mergeFileToJson ( )
	{
		// 尝试将合并的文件内容进行压缩处理
		// compressMergeFiles ( );
		
		String json = JSONUtility.SpringMvcMapperWriteJSON ( this.merge.getMergeFileList ( ) );
		String json_param = this.merge.getOutFileJsonParamName ( );
		
		if ( StringUtils.isNotEmpty ( json_param ) )
		{
			// 生成json文件内容
			json = String.format ( "%s=%s" , json_param , json );
			
		}
		IOAssist.write ( outFileJsonPath , json , IOAssist.CHARSET_UTF8 , false );
		return this;
	}
	
	public void writeSourceFileTotal ( )
	{
		int source_file_total = 0;
		
		for ( Map.Entry<String , List<String>> entry : this.sourcesFileMap.entrySet ( ) )
		{
			source_file_total += entry.getValue ( ).size ( );
		}
		logger.info ( "组名：{}, 资源文件总数:{}" , sourcesFileMap.keySet ( ) , source_file_total );
	}
	
	public static void main ( String[] args )
	{
		new MergeManage ( ).clean ( ).analyzeSourcesFile ( ).mergeFile ( ).mergeFileToJson ( ).writeSourceFileTotal ( );
	}
}
