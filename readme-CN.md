# 富文本TextView

Android 富文本解析器，可以处理TextView中的图片加载、常见的网页tags。

使用 [Volley Library](https://android.googlesource.com/platform/frameworks/volley) 实现图片加载。

##截图
![](https://github.com/moonChenHaohui/blog/blob/gh-pages/image/richtext/reflresh.gif)

## 如何使用




Step 1. 在 build.gradle 中 repositories加入：

	repositories {
			maven { url "https://jitpack.io" }
	}

Step 2. 加入依赖：

	dependencies {
	        compile 'com.github.moonChenHaohui:android_richtext:2.0'
	}
	
**使用 Maven**

    <repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
	

	<dependency>
	    <groupId>com.github.moonChenHaohui</groupId>
	    <artifactId>android_richtext</artifactId>
	    <version>2.0</version>
	</dependency>


Step 3. 添加 com.github.moon.RichText 到XML中

	<ScrollView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:scrollbars="none">
        <com.github.moon.RichText
            android:id="@+id/rich_view"
            android:layout_width="match_parent"
            android:layout_height="match_parent"/>
    </ScrollView>
* 最好的方式是外部包裹一个ScrollView。
    
Step 4. 使用

必须实现：

		//necessary
		textViewClickListener(new OnTextViewClickListener() {
            @Override
            public void imageClicked(ArrayList<String> arrayList, int i) {
                //do click
            }

            @Override
            public void textLinkClicked(String url) {
                //do click

            }
        })
        //necessary
		.text("<html><img src='1.png'/></html>");
		
可选项：
	
        //optional
        .imageLoadAdapter(new MoonHtmlRemoteImageGetter.Adapter() {
            @Override
            public Drawable getDefaultDrawable() {
                return ContextCompat.getDrawable(ArticleActivity.this,R.drawable.image_bg);
            }            

            @Override
            public Drawable getErrorDrawable() {
                return ContextCompat.getDrawable(ArticleActivity.this,R.drawable.image_bg);
            }
        })
        //optional
        .fullImage(true) //图片占满全屏  
        
	
## Other
如果你喜欢这个项目，请给我一个star.
	