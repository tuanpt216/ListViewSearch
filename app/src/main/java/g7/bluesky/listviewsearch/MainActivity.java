package g7.bluesky.listviewsearch;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import g7.bluesky.listviewsearch.adapter.AppListAdapter;
import g7.bluesky.listviewsearch.dto.MyAppInfo;

public class MainActivity extends AppCompatActivity {

    AppListAdapter appListAdapter;
    // Search EditText
    EditText inputSearch;
    // List view
    private ListView listView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Initialize UI
        super.onCreate(savedInstanceState);

        // Load list apps
        final PackageManager packageManager = getPackageManager();

        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(PackageManager.GET_META_DATA);
        List<MyAppInfo> myAppInfoList = new ArrayList<>();

        for (PackageInfo packageInfo : packageInfos) {
            if (packageManager.getLaunchIntentForPackage(packageInfo.packageName) != null) {
                String packageName = packageInfo.packageName;
                String name = packageInfo.applicationInfo.loadLabel(packageManager).toString();
                BitmapDrawable iconBitmap = (BitmapDrawable) packageInfo.applicationInfo.loadIcon(packageManager);

                MyAppInfo appInfo = new MyAppInfo(packageName, name, iconBitmap);
                myAppInfoList.add(appInfo);
            }
        }

        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list_view);
        inputSearch = (EditText) findViewById(R.id.inputSearch);

        // Adding items to listview
        appListAdapter = new AppListAdapter(getBaseContext(), myAppInfoList);
        listView.setAdapter(appListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Launch app
                MyAppInfo selectedApp = (MyAppInfo) parent.getItemAtPosition(position);
                Intent intent = packageManager.getLaunchIntentForPackage(selectedApp.getPackageName());
                if (intent != null) {
                    startActivity(intent);
                }
            }
        });

        listView.setLongClickable(true);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Show app details
                MyAppInfo selectedApp = (MyAppInfo) parent.getItemAtPosition(position);
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", selectedApp.getPackageName(), null));
                startActivity(intent);
                return true;
            }
        });

        /**
         * Enabling Search Filter
         * */
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                MainActivity.this.appListAdapter.getFilter().filter(arg0.toString());
            }
        });
    }
}
