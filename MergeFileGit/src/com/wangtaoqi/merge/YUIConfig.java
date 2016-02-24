/**
 * 
 */
package com.wangtaoqi.merge;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @author 王淘气
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class YUIConfig
{
	
	// private JavaScript javaScript = new JavaScript ( );
	private JavaScript javaScript ;
	// private Css css =new Css ( );
	private Css css ;
	public static final String COMPRESS_EXTENSION_JAVASCRIPT="js";
	public static final String COMPRESS_EXTENSION_CSS="css";
	public static class JavaScript
	{
		/**
		 * Writer out, int linebreak, boolean munge, boolean verbose,
            boolean preserveAllSemiColons, boolean disableOptimizations
		 */
		private int  linebreak ;
		private boolean munge ;
		private boolean verbose ;
		private boolean preserveAllSemiColons ;
		private boolean disableOptimizations ;
		/**
		 * @return the linebreak
		 */
		public int getLinebreak ( )
		{
			return linebreak;
		}
		/**
		 * @param linebreak the linebreak to set
		 */
		public void setLinebreak ( int linebreak )
		{
			this.linebreak = linebreak;
		}
		/**
		 * @return the munge
		 */
		public boolean isMunge ( )
		{
			return munge;
		}
		/**
		 * @param munge the munge to set
		 */
		public void setMunge ( boolean munge )
		{
			this.munge = munge;
		}
		/**
		 * @return the verbose
		 */
		public boolean isVerbose ( )
		{
			return verbose;
		}
		/**
		 * @param verbose the verbose to set
		 */
		public void setVerbose ( boolean verbose )
		{
			this.verbose = verbose;
		}
		/**
		 * @return the preserveAllSemiColons
		 */
		public boolean isPreserveAllSemiColons ( )
		{
			return preserveAllSemiColons;
		}
		/**
		 * @param preserveAllSemiColons the preserveAllSemiColons to set
		 */
		public void setPreserveAllSemiColons ( boolean preserveAllSemiColons )
		{
			this.preserveAllSemiColons = preserveAllSemiColons;
		}
		/**
		 * @return the disableOptimizations
		 */
		public boolean isDisableOptimizations ( )
		{
			return disableOptimizations;
		}
		/**
		 * @param disableOptimizations the disableOptimizations to set
		 */
		public void setDisableOptimizations ( boolean disableOptimizations )
		{
			this.disableOptimizations = disableOptimizations;
		}
	}
	public  static class Css
	{
		private int linebreakpos;

		/**
		 * @return the linebreakpos
		 */
		public int getLinebreakpos ( )
		{
			return linebreakpos;
		}

		/**
		 * @param linebreakpos the linebreakpos to set
		 */
		public void setLinebreakpos ( int linebreakpos )
		{
			this.linebreakpos = linebreakpos;
		}
		
	}
	/**
	 * @return the javaScript
	 */
	public JavaScript getJavaScript ( )
	{
		return javaScript;
	}
	/**
	 * @param javaScript the javaScript to set
	 */
	public void setJavaScript ( JavaScript javaScript )
	{
		this.javaScript = javaScript;
	}
	/**
	 * @return the css
	 */
	public Css getCss ( )
	{
		return css;
	}
	/**
	 * @param css the css to set
	 */
	public void setCss ( Css css )
	{
		this.css = css;
	}
}
