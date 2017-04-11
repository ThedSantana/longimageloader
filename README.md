# Long Image Loader
Use WebView or ListView Style

# Download
in app gradle

repositories {
    maven {
        url  "http://dl.bintray.com/sheiud123/maven"
    }
}

compile 'com.sheiud.redbible:longimageloader:0.0.9-beta'

this is beta version, But it's works.

# Usage
WebView :
LongImageLoader.getInstance().setWebViewByHtml(web_view, html);

- draw to webview. it was good to memory

ListView :
LongImageLoader.getInstance().setListView(context, listView, imgUrl);
LongImageLoader.getInstance().setListView(context, listView, imgUrl, adapterAttachToListview);

- draw to listview. it was bad to memory but smooth to drawing.
