<pre>
说明：
	mergeFile是一个简单的文件合并小工具，使用Java，这个工具起初是为了项目中使用，后来感觉效果还不错，想把这个小工具分享出来
	希望可以帮助大家提高一下工作效率，下面说下这个小家伙有哪些功能以及使用：
		*根据预设文件大小，对合并目标文件自动拆分。
		*根据配置，将合并目标文件按照顺序保存到指定的js文件中,可以指定变量名
		*支持为给每个文件内容块添加开始和结束标示（sourceFile完成路径）,用来区分
		*支持配置文件内部变量
		*支持指定资源文件父目录
		*支持资源文件列表文件配置
		*支持指定输出文件名
		*支持两种文件类型合并：列表类型、分组类型
			列表类型：
				根据预设的文件大小自动分隔文件，并基于文件名自动追加索引。例如： mergeFile.js,mergeFile_1.js,mergeFile_2.js ....
			分组类型：
				文件名称为组名，不对文件大小进行分隔，fileMaxSize参数设置为-1即可
			  资源文件配置数据类型为map（k-v) key:为文件存储的相对路径；value:是资源文件列表信息
			  例如：
			  	{
					"js/merge/core.js" : ["js/source1.js", "js/source2.js", ...]
				}
		*可以为 outJsonFileName 的josn指定一个变量
		*css、js文件支持压缩处理，压缩使用的是YUICommpress
		*支持附加信息功能，在每个文件内容最后可以根据需要添加
		*支持合并文件签名
		*日志工具，summary目录下，日志文件名和合并文件名保持一致。日志文件不会自动删除
		*文件合并前，会先删除当前目标文件内容(其他的不会删除)，历史文件不会删除。		
	如何执行：
		*在IDE中执行：com.wangtaoqi.merge.MergeManage类中的main函数
		*命令行：运行startMergefile.bat
	代码管理：
		*使用Maven 参考命令：mvn dependency:copy-dependencies  package
	配置文件说明:
		*merge_config.js配置文件：
		{
			"sourceFileDirectory" : "D:/frame/WebContent/decision/", 
			"sourceFileListPath" : "${sourceFileDirectory}/js/path_map.json",
			"outDirectory" : "${sourceFileDirectory}/js/",
			"outFileName" : "js/merge/mergerFile.js",
			"outJsonFileName" : "mergeFileJsonPathList.js",
			"fileMaxSize" : -1,
			"fileSizeUntil" : "MB",
			"contentAttachText" : ";\n",
			"createFileSign" : true,
			"comments" : true,	
			"outFileJsonParamName" : "loadFiles",
			"$yuiConfig" : {
				"javaScript" : {
					"linebreak" : 0,
					"munge" : false,
					"verbose" : false,
					"preserveAllSemiColons" : false,
					"disableOptimizations" : false
				},
				"css" : {
					"linebreakpos" : 0
				}
			}
		}

	*参数
		--sourceFileDirectory：
			资源文件根目录，文件的读写路径都是基sourceFileDirectory之上进行的。
		--sourceFileListPath：
			文件列表配置文件路径。
		--outDirectory：   
			如果资源文件配置使用list结构存储，当前变量将作为输出文件名，否则以配置文件为主。
		--outJsonFileName：
			将合并后的文件列表以jsonList形式存储到这个文件中。
		--fileMaxSize：
			分隔文件大小，如果在写入文件时发现目标文件大小已经超过上限，将新建一个文件，否则继续写入。 -1:忽略文件大小。
		--fileSizeUntil：
			文件分隔单位：MB、KB 这个参数必填，否则不能保存成功。
		--contentAttachText：
			文件附加内容，每个文件末尾处需要追加的额外信息。
		--createFileSign：
			是否创建合并文件签名
		--comments：
			是否在合并文件中记录每个文件的原始路径信息。
		--outFileJsonParamName：
			指定outJsonFileName文件中的json变量名
		--yuiConfig:
			yui配置文件，如果不想使用只需在变量名前添加一个$符号即可。
		
</pre>
