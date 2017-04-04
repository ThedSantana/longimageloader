package com.sheiud.redbible.longimageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by bible on 2017. 3. 28..
 */

public class LongImageLoader {
    private static final LongImageLoader LONG_IMAGE_LOADER = new LongImageLoader();
    private HashMap<Integer, WeakReference<ImageAdapterAddingAdapter>> hash_adapter = new HashMap<>();
    private String style = "img {display: inline; height: auto; width: 100%%;}iframe {width: 100vw; height: 56vw;}";
    private Integer deviceWidthPixels = null;
    private Picasso picasso;
    private Target target;

    public static LongImageLoader getInstance() {
        return LONG_IMAGE_LOADER;
    }

    /**
     * Get html form Style String
     *
     * @return current Style String
     */
    public String getStyle() {
        return style;
    }

    /**
     * Set manually style using html form
     *
     * @param style string, html form style
     *              {@link LongImageLoader#style}, example
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * set img to webview
     *
     * @param webView target WebView to draw image
     * @param imgUrl  image Url to draw
     */
    public void setWebViewByImageUrl(WebView webView, String imgUrl) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);    //For video
        settings.setUseWideViewPort(true);      //full Screen
        settings.setBuiltInZoomControls(true);  // optional, zoomable
        webView.setInitialScale(100);

        String formString = String.format("<![CDATA[<html><head>" +
                "<style type=\"text/css\">%s</style>" +
                "</head><body>" +
                "<img src=%s>" +
                "</body></html>]]>", style, imgUrl);
        webView.loadDataWithBaseURL(null, formString
                , "text/html", "UTF-8", null);
    }

    /**
     * set html to webview
     *
     * @param webView target WebView to draw image
     * @param html    html to setting
     */
    public void setWebViewByHtml(WebView webView, String html) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);    //For video
        settings.setUseWideViewPort(true);      //full Screen
        settings.setBuiltInZoomControls(true);  // optional, zoomable
        webView.setInitialScale(100);

        String formString = String.format("<![CDATA[<html><head>" +
                "<style type=\"text/css\">%s</style></head>" +
                "<body>%s</body>" +
                "</html>]]>", style, html);
        webView.loadDataWithBaseURL(null, formString
                , "text/html", "UTF-8", null);
    }

    /**
     * Long Images in ListView
     * @param context context
     * @param list target list, list to draw LongImage
     * @param url LongImage Url
     */
    public void setListView(Context context, ListView list, String url) {
        if(deviceWidthPixels == null) initDisplayMatricx(context);
        this.picasso = Picasso.with(context);
        list.setDivider(null);
        getImage(context, url, list, null);
    }

    /**
     * Long Images in ListView Adding Coustom(Your) Adapter
     * @param context context
     * @param list target list, list to draw LongImage
     * @param url LongImage Url
     * @param adapter
     */
    public void setListView(Context context, ListView list, String url, BaseAdapter adapter) {
        if(deviceWidthPixels == null) initDisplayMatricx(context);
        this.picasso = Picasso.with(context);
        list.setDivider(null);
        getImage(context, url, list, adapter);
    }

    public void setListView(Context context, ListView list, String url, BaseAdapter adapter, Picasso picasso) {
        if(deviceWidthPixels == null) initDisplayMatricx(context);
        this.picasso = picasso;
        list.setDivider(null);
        getImage(context, url, list, adapter);
    }

    /**
     * get Phone Screen Pixel Size
     * @param context context
     */
    private void initDisplayMatricx(Context context){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        deviceWidthPixels = displayMetrics.widthPixels;
    }

    public void notifyDataSetChanged(BaseAdapter adapter){
        if(adapter != null && hash_adapter.get(adapter.hashCode()) != null && hash_adapter.get(adapter.hashCode()).get() != null)
            hash_adapter.get(adapter.hashCode()).get().notifyDataSetChanged();
    }

    /**
     *
     * @param context context
     * @param url Image Url
     * @param list target List
     * @param adapter Coustom Adapter
     */
    private void getImage(final Context context, String url, final ListView list, final BaseAdapter adapter) {
        final ArrayList<Bitmap> arr_bitmap = new ArrayList<>();

        target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                int split_h = 2048;
                int cur_y = 0;

                while (cur_y < height) {
                    if (cur_y + split_h < height) arr_bitmap.add(Bitmap.createBitmap(bitmap, 0, cur_y, width, split_h));
                    else arr_bitmap.add(Bitmap.createBitmap(bitmap, 0, cur_y, width, height - cur_y));
                    cur_y += split_h;
                }

                if(adapter == null) list.setAdapter(new ImageAdapter(context, arr_bitmap));
                else {
                    ImageAdapterAddingAdapter myadapter = new ImageAdapterAddingAdapter(context, arr_bitmap, adapter);
                    hash_adapter.put(adapter.hashCode(), new WeakReference(myadapter));
                    list.setAdapter(myadapter);
                }
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        };

        picasso.load(url)
                .into(target);
    }

    class ImageAdapter extends BaseAdapter {
        private final ArrayList<Bitmap> items;
        protected final LayoutInflater inflater;
        protected final Context context;
        protected final int IS_MY_IMAGE_VIEW = 1231;

        public ImageAdapter(Context context, ArrayList<Bitmap> items) {
            this.context = context;
            this.items = items;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View vi, ViewGroup parent) {
            if(vi == null){
                vi = new ImageView(context);
                vi.setTag(IS_MY_IMAGE_VIEW);
            }

            drawBitmap((Bitmap)getItem(position), (ImageView)vi);

            return vi;
        }

        protected void drawBitmap(Bitmap bitmap, ImageView img){
            img.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, deviceWidthPixels * bitmap.getHeight() / bitmap.getWidth()));
            img.setImageBitmap(bitmap);
        }
    }

    class ImageAdapterAddingAdapter extends ImageAdapter {
        private final BaseAdapter adapter;

        public ImageAdapterAddingAdapter(Context context, ArrayList<Bitmap> items, BaseAdapter adapter) {
            super(context, items);
            this.adapter = adapter;
        }

        @Override
        public int getCount() {
            return (super.getCount() + adapter.getCount());
        }

        @Override
        public Object getItem(int position) {
            return position < super.getCount() ? super.getItem(position) : adapter.getItem(position);
        }

        @Override
        public long getItemId(int position) {
            return position < super.getCount() ? 0 : 1;
        }

        private boolean isMyImageViewHolder(View vi){
            //Filtering is My View, set Null, to Recreate in coustom adapter
            if(vi != null
                    && vi instanceof ImageView
                    && vi.getTag() != null
                    && vi.getTag() instanceof Integer
                    && (int)vi.getTag() == IS_MY_IMAGE_VIEW) return true;
            else return false;
        }

        @Override
        public View getView(int position, View vi, ViewGroup parent) {
            if(getItemId(position) == 0) {
                if (!isMyImageViewHolder(vi)) {
                    vi = new ImageView(context);
                    vi.setTag(IS_MY_IMAGE_VIEW);
                }
                drawBitmap((Bitmap)getItem(position), (ImageView)vi);
            } else {
                if(isMyImageViewHolder(vi)) vi = null;
                vi = adapter.getView(position - super.getCount(), vi, parent);
            }

            return vi;
        }
    }
}
