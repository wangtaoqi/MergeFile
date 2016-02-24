/**
 * 
 */

package com.wangtaoqi.merge;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

import com.wangtaoqi.merge.utility.MergeUntility;

/** @author 王淘气 */
public class MergeFileSize extends AbstractMergeBasis
{
	private File			saveFile;
	private long			mergeAdditionTotal =0;
	private int				nextFileIndex;
	protected List<String>	mergeFileList	= new ArrayList<> ( );
	
	public MergeFileSize()
	{
		this.readMergeCofig ( ); 
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hylanda.utility.merge.AbstractMergeBasis#mergeFile(java.io.File)
	 */
	@ Override
	public void mergeFile ( File targetfile )
	{
		// TODO Auto-generated method stub		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hylanda.utility.merge.AbstractMergeBasis#createNewFile()
	 */

	@ Override
	public File createNewFile ( )
	{
		if ( this.isCreate ( ) )
		{
			// 存储文件名发生改变后，初始化文件下标值
			if( this.isSaveFileNameChanged ( ) )
			{
				nextFileIndex = 0 ;
			} 
			
			String filename  = this.getOutFileName ( ) ;
			
			// 指定当前文件
			this.currentFileName=filename ;
			String extension = FilenameUtils.getExtension ( filename ) ;
			
			if( StringUtils.isNotEmpty ( extension ) && nextFileIndex >0)
			{
				filename  = filename.replaceAll ( String.format ( "([\\s\\S]+?)(\\.%s)$" , extension ) , String.format ( "$1_%s$2" , nextFileIndex ) ) ;				
			}
			nextFileIndex ++;
			
			String pathFull  = MergeUntility.concatRepeatPath( this.getOutDirectory ( ),filename ) ;
			String directory = FilenameUtils.getFullPath ( pathFull );
			File directory_file = new File( directory ) ;
			// 路径不存在，新建
			if( !directory_file.exists ( ) )
			{
				directory_file.mkdirs ( ) ;
			}
			
			this.logger.info ( "createNewFile:{}" , pathFull );
			this.saveFile = new File ( pathFull );
			//删除历史文件
			this.saveFile .delete ( );
			this.mergeFileList.add (  filename   );
			
		}
		
		return saveFile ;
	}
	//存储文件名是否发生改变
	private boolean isSaveFileNameChanged()
	{
		return !this.getOutFileName ( ) .equals ( this.currentFileName  )  ;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hylanda.utility.merge.AbstractMergeBasis#isCreate()
	 */
	@ Override
	public boolean isCreate ( )
	{
		if( saveFile != null )
		{
			mergeAdditionTotal = this.saveFile.length ( ) ;
		}
		
		// 是否达到文件大小上限
		boolean isLimit = mergeAdditionTotal ==0 ||  ( this.scalerFileSize>0 &&  this.mergeAdditionTotal > this.scalerFileSize ) ; 
		// 存储文件名是否发生改变
		return  isLimit || isSaveFileNameChanged();
	}

	/**
	 * @return the relativeFileList
	 */
	@Override
	public List<String> getMergeFileList ( )
	{
		return mergeFileList;
	}
	
}
