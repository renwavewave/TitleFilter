# TitleFilter
标题行
将其添加到存储库末尾的root build.gradle中：

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
步骤2.添加依赖项

	dependencies {
	        implementation 'com.github.renwavewave:TitleFilter:Tag'
	}
Current latest version: 1.0.0
