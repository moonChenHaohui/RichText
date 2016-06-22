# Rich TextView

A rich TextView for Android,it can hold the pictures loading and some normal tags in TextView.

Using [Volley Library](https://android.googlesource.com/platform/frameworks/volley) in picture loading part;


##[中文ReadME](/readme-CN.md)



##ScreenShot
![](https://github.com/moonChenHaohui/blog/blob/gh-pages/image/richtext/reflresh.gif)

## How to use




Step 1. Add it in your root build.gradle at the end of repositories

	repositories {
			maven { url "https://jitpack.io" }
	}

Step 2. Add the dependency

	dependencies {
	        compile 'com.github.moonChenHaohui:android_richtext:2.3'
	}
	
**if you use Maven**

    <repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
	

	<dependency>
	    <groupId>com.github.moonChenHaohui</groupId>
	    <artifactId>android_richtext</artifactId>
	    <version>2.3</version>
	</dependency>


Step 3. Add the com.github.moon.RichText to your layout XML file.

	<ScrollView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:scrollbars="none">
        <com.github.moon.RichText
            android:id="@+id/rich_view"
            android:layout_width="match_parent"
            android:layout_height="match_parent"/>
    </ScrollView>
* the best way is add a ScrollView around RichText
    
Step 4. realize some setting

the necessary

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
		
the optional	
	
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
        .fullImage(true) //is all images adapter screen.   
        
	
## Other
if you like this library，please give me a star.
	