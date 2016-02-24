/**
 * 
 */

package testunit;

import org.junit.Test;

import com.wangtaoqi.merge.MergeManage;

/** @author 王淘气 */
public class MergeManageUnit
{
	private MergeManage mergeManage = new MergeManage ( );
	
	@ Test
	public void manage ( )
	{
		mergeManage.clean ( ).analyzeSourcesFile ( ).mergeFile ( ).mergeFileToJson ( );
	}
}
