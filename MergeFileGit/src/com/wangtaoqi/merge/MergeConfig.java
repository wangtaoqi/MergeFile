/**
 * 
 */

package com.wangtaoqi.merge;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/** @author 王淘气 */
@ JsonIgnoreProperties ( ignoreUnknown = true )
public class MergeConfig
{
	private String	sourceFileDirectory; // 资源文件根目录
	private String	sourceFileListPath; // 资源文件列表完成路径
	private String	outDirectory;//合并文件的输出目录
	private String	outFileName;// 输出文件名
	private String	outJsonFileName;// 输出合并文件路径，以json形式输出
	private String	outFileJsonParamName;//输出文件JSON变量名
	private long	fileMaxSize;// 合并文件的大小
	private String fileSizeUntil ;// 文件大小单位 kb mb 
	private String contentAttachText ; // 附加内容
	private boolean isComments = true ;//是否添加注解
	private boolean isCreateFileSign=true ;//是否生成文件签名
	private YUIConfig yuiConfig = new YUIConfig();
	/**
	 * @return the sourceFileDirectory
	 */
	public String getSourceFileDirectory ( )
	{
		return sourceFileDirectory;
	}
	/**
	 * @param sourceFileDirectory the sourceFileDirectory to set
	 */
	public void setSourceFileDirectory ( String sourceFileDirectory )
	{
		this.sourceFileDirectory = sourceFileDirectory;
	}
	/**
	 * @return the sourceFileListPath
	 */
	public String getSourceFileListPath ( )
	{
		return sourceFileListPath;
	}
	/**
	 * @param sourceFileListPath the sourceFileListPath to set
	 */
	public void setSourceFileListPath ( String sourceFileListPath )
	{
		this.sourceFileListPath = sourceFileListPath;
	}
	/**
	 * @return the outDirectory
	 */
	public String getOutDirectory ( )
	{
		return outDirectory;
	}
	/**
	 * @param outDirectory the outDirectory to set
	 */
	public void setOutDirectory ( String outDirectory )
	{
		this.outDirectory = outDirectory;
	}

	/**
	 * @return the outFileName
	 */
	public String getOutFileName ( )
	{
		return outFileName;
	}
	/**
	 * @param outFileName the outFileName to set
	 */
	public void setOutFileName ( String outFileName )
	{
		this.outFileName = outFileName;
	}
	/**
	 * @return the outFileJsonFullPath
	 */
	
	/**
	 * @return the fileMaxSize
	 */
	public long getFileMaxSize ( )
	{
		return fileMaxSize;
	}
	/**
	 * @param fileMaxSize the fileMaxSize to set
	 */
	public void setFileMaxSize ( long fileMaxSize )
	{
		this.fileMaxSize = fileMaxSize;
	}
	/**
	 * @return the fileSizeUntil
	 */
	public String getFileSizeUntil ( )
	{
		return fileSizeUntil;
	}
	/**
	 * @param fileSizeUntil the fileSizeUntil to set
	 */
	public void setFileSizeUntil ( String fileSizeUntil )
	{
		this.fileSizeUntil = fileSizeUntil;
	}
	/**
	 * @return the contentAttachText
	 */
	public String getContentAttachText ( )
	{
		return contentAttachText;
	}
	/**
	 * @param contentAttachText the contentAttachText to set
	 */
	public void setContentAttachText ( String contentAttachText )
	{
		this.contentAttachText = contentAttachText;
	}
	/**
	 * @return the isComments
	 */
	public boolean isComments ( )
	{
		return isComments;
	}
	/**
	 * @param isComments the isComments to set
	 */
	public void setComments ( boolean isComments )
	{
		this.isComments = isComments;
	}
	/**
	 * @return the isCreateFileSign
	 */
	public boolean isCreateFileSign ( )
	{
		return isCreateFileSign;
	}
	/**
	 * @param isCreateFileSign the isCreateFileSign to set
	 */
	public void setCreateFileSign ( boolean isCreateFileSign )
	{
		this.isCreateFileSign = isCreateFileSign;
	}
	/**
	 * @return the outFileJsonParamName
	 */
	public String getOutFileJsonParamName ( )
	{
		return outFileJsonParamName;
	}
	/**
	 * @param outFileJsonParamName the outFileJsonParamName to set
	 */
	public void setOutFileJsonParamName ( String outFileJsonParamName )
	{
		this.outFileJsonParamName = outFileJsonParamName;
	}
	
	/**
	 * @return the outJsonFileName
	 */
	public String getOutJsonFileName ( )
	{
		return outJsonFileName;
	}
	/**
	 * @param outJsonFileName the outJsonFileName to set
	 */
	public void setOutJsonFileName ( String outJsonFileName )
	{
		this.outJsonFileName = outJsonFileName;
	}
	/**
	 * @return the yuiConfig
	 */
	public YUIConfig getYuiConfig ( )
	{
		return yuiConfig;
	}
	/**
	 * @param yuiConfig the yuiConfig to set
	 */
	public void setYuiConfig ( YUIConfig yuiConfig )
	{
		this.yuiConfig = yuiConfig;
	}

	
}
