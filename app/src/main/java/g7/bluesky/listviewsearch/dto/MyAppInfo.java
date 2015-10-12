package g7.bluesky.listviewsearch.dto;

import android.graphics.drawable.BitmapDrawable;

/**
 * Created by tuanpt on 9/10/2015.
 */
public class MyAppInfo {

    private String name;
    private String packageName;
    private BitmapDrawable icon;

    public MyAppInfo() {
    }

    public MyAppInfo(String name, BitmapDrawable icon) {
        this.name = name;
        this.icon = icon;
    }

    public MyAppInfo(String packageName, String name, BitmapDrawable icon) {
        this.packageName = packageName;
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public BitmapDrawable getIcon() {
        return icon;
    }

    public void setIcon(BitmapDrawable icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "MyAppInfo{" +
                "name='" + name + '\'' +
                ", packageName='" + packageName + '\'' +
                '}';
    }
}
