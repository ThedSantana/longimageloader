# Long Image Loader
Use WebView or ListView Style

# Download

<dependency>
  <groupId>com.sheiud.redbible</groupId>
  <artifactId>longimageloader</artifactId>
  <version>0.0.9-beta</version>
  <type>pom</type>
</dependency>

or

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
