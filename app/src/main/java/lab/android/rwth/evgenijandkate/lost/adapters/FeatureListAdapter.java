package lab.android.rwth.evgenijandkate.lost.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import lab.android.rwth.evgenijandkate.lost.R;
import lab.android.rwth.evgenijandkate.lost.model.AudioFileListItem;
import lab.android.rwth.evgenijandkate.lost.xml.model.Feature;

/**
 * Created by ekaterina on 05.07.2015.
 */
public class FeatureListAdapter extends AbstractListAdapter<Feature> {
    public FeatureListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        TextView text;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            view = inflater.inflate(R.layout.feature_list_item, parent, false);
        } else {
            view = convertView;
        }

        if (view instanceof LinearLayout) {
            Feature item = (Feature) getItem(position);
            TextView featureName = (TextView)view.findViewById(R.id.feature_name);
            featureName.setText(item.getName());
            TextView featureValue = (TextView)view.findViewById(R.id.feature_value);
            featureValue.setText(String.valueOf(item.getValue()));
        }
        return view;
    }
}
