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
    
Step 4. 实现点击事件： OnTextViewClickListener   

* 你应该实现这个方法，去代理文字、图片的点击 : <code>OnTextViewClickListener</code>



		richText.setOnTextViewClickListener(new OnTextViewClickListener() {
            @Override
            public void imageClicked(ArrayList<String> arrayList, int i) {
                toast("click image,url:" + arrayList.get(i));

            }

            @Override
            public void textLinkClicked(String s) {
                toast("click link ,url:" + s);
            }
        });
        

Step 5. 实现图片加载的适配 （不是必须的）
* 实现 loadAdapter 你可以替换在图片加载中的图片以及在加载失败后的图片。
 
        richText.setImageLoadAdapter(new HtmlRemoteImageGetter.Adapter() {
            @Override
            public Drawable getDefaultDrawable() {
                return getDrawable(R.mipmap.load);
            }

            @Override
            public Drawable getErrorDrawable() {
                return getDrawable(R.mipmap.ic_launcher);
            }
        });
* 如果你不关心这部分，你可以忽略它。
        
Step 6. setText**  
* 请使用richText.setRichText来设置文本，默认会使用HTML.fromHtml来处理。请不要使用setText方法，否则不会生效。

		richText.setRichText(info);
	
## Other
如果你喜欢这个项目，请给我一个star.
	