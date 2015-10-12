package g7.bluesky.listviewsearch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import g7.bluesky.listviewsearch.R;
import g7.bluesky.listviewsearch.dto.MyAppInfo;

/**
 * Created by tuanpt on 9/10/2015.
 */
public class AppListAdapter extends ArrayAdapter<MyAppInfo> implements Filterable {

    private Context context;
    private Filter appListFilter;
    private List<MyAppInfo> appList;
    private List<MyAppInfo> originalAppList;

    public AppListAdapter(Context context, List<MyAppInfo> appList) {
        super(context, R.layout.item_app, appList);
        this.appList = appList;
        this.originalAppList = appList;
        this.context = context;
    }

    @Override
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return appList == null ? 0 : appList.size();
    }

    @Override
    public MyAppInfo getItem(int position) {
        return appList == null ? null : appList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        MyAppInfo app = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_app, parent, false);
            viewHolder.iconView = (ImageView) convertView.findViewById(R.id.item_app_icon);
            viewHolder.appName = (TextView) convertView.findViewById(R.id.item_app_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.iconView.setImageDrawable(app.getIcon());
        viewHolder.appName.setText(app.getName());
        // Return the completed view to render on screen
        return convertView;
    }

    public void resetData() {
        this.appList = this.originalAppList;
    }

    @Override
    public Filter getFilter() {
        return appListFilter == null ? new AppListFilter() : appListFilter;
    }

    // View lookup cache
    private static class ViewHolder {
        ImageView iconView;
        TextView appName;
    }

    private class AppListFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();

            if (constraint == null || constraint.length() == 0) {
                filterResults.values = originalAppList;
                filterResults.count = originalAppList.size();
            } else {
                List<MyAppInfo> searchList = new ArrayList<>();

                for (MyAppInfo app : originalAppList) {
                    if (app.getName().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        searchList.add(app);
                    }
                }

                filterResults.values = searchList;
                filterResults.count = searchList.size();
            }

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count == 0) {
                notifyDataSetInvalidated();
            } else {
                appList = (List<MyAppInfo>) results.values;
                notifyDataSetChanged();
            }
        }
    }
}
