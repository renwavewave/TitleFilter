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
	        implementation 'com.github.renwavewave:TitleFilter:1.0.0'
	}
	
自定义 一行标题类似 TabLayout，当不占满屏幕的时候会自动平分，超过屏幕后按照指定间距可以左右滑动。

