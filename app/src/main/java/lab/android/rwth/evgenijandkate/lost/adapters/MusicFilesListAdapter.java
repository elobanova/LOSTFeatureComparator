package lab.android.rwth.evgenijandkate.lost.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import lab.android.rwth.evgenijandkate.lost.R;
import lab.android.rwth.evgenijandkate.lost.model.AudioFileListItem;

/**
 * Created by ekaterina on 05.07.2015.
 */
public class MusicFilesListAdapter extends AbstractListAdapter<AudioFileListItem> {

    public MusicFilesListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        TextView text;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            view = inflater.inflate(R.layout.music_file_list_item, parent, false);
        } else {
            view = convertView;
        }

        if (view instanceof TextView) {
            text = (TextView) view;
            AudioFileListItem item = (AudioFileListItem) getItem(position);
            text.setText(item.getFileName());
        }
        return view;
    }
}