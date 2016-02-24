/**
 * 
 */
package com.wangtaoqi.merge;

import java.io.File;
import java.util.List;

/**
 * @author 王淘气
 *
 */
public interface Merge {
	/**
	 * 文件合并
	 * @param targetfile
	 */
 public void  mergeFile(File targetfile);
 /**
  * 创建一个新文件
  * 根据 {@link #isCreate()} 
  * @return
  */
 public File createNewFile();
 /**
  * 是否需要创建一个文件
  * @return 返回true 创建 否则返回false`
  */
 public boolean isCreate() ;
 /**
  * 获得合并文件列表
  * @return
  */
 public List<String> getMergeFileList ( );


}
