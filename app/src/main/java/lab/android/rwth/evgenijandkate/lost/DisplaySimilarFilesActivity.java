package lab.android.rwth.evgenijandkate.lost;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;

import lab.android.rwth.evgenijandkate.lost.adapters.MusicFilesListAdapter;
import lab.android.rwth.evgenijandkate.lost.model.AudioFileListItem;
import lab.android.rwth.evgenijandkate.lost.xml.XMLHandler;

/**
 * Created by ekaterina on 05.07.2015.
 */
public class DisplaySimilarFilesActivity extends ListActivity {
    public static final String MUSIC_FILE_ITEM = "item";
    private MusicFilesListAdapter adapter;
    private AudioFileListItem audioFileItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        this.audioFileItem = (AudioFileListItem) extras.getSerializable(AudioFeaturesActivity.MUSIC_FILE_TO_SEARCH_SIMILARITY_ITEM);

        this.adapter = new MusicFilesListAdapter(getApplicationContext());
        addMusicItemsToAdapter();
        setListAdapter(this.adapter);

        ListView lv = getListView();

        // Enable filtering when the user types in the virtual keyboard
        lv.setTextFilterEnabled(true);
    }

    private void addMusicItemsToAdapter() {
        if (this.adapter != null && this.audioFileItem != null) {
            for (AudioFileListItem listItem : XMLHandler.getSimilarObjects(audioFileItem)) {
                this.adapter.add(listItem);
            }
        }
    }
}
