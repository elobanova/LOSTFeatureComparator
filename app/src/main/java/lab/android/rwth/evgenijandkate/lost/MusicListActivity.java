package lab.android.rwth.evgenijandkate.lost;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lab.android.rwth.evgenijandkate.lost.adapters.MusicFilesListAdapter;
import lab.android.rwth.evgenijandkate.lost.json.JSONHandler;
import lab.android.rwth.evgenijandkate.lost.model.AudioFileListItem;


public class MusicListActivity extends ListActivity {
    public static final String MUSIC_FILE_ITEM = "item";
    private static final int MENU_EXPORT_AS_JSON = Menu.FIRST;
    private MusicFilesListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.adapter = new MusicFilesListAdapter(getApplicationContext());
        addMusicItemsToAdapter();
        setListAdapter(this.adapter);

        ListView lv = getListView();

        // Enable filtering when the user types in the virtual keyboard
        lv.setTextFilterEnabled(true);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Object item = adapter.getItem(position);
                if (item instanceof AudioFileListItem) {
                    AudioFileListItem musicListItem = (AudioFileListItem) item;
                    Intent intent = new Intent(MusicListActivity.this, AudioFeaturesActivity.class);
                    intent.putExtra(MUSIC_FILE_ITEM, musicListItem);
                    startActivity(intent);
                }
            }
        });
    }

    private void addMusicItemsToAdapter() {
        if (this.adapter != null) {
            this.adapter.addAll(getAudioFileItems());
        }
    }

    public static List<AudioFileListItem> getAudioFileItems() {
        List<AudioFileListItem> result = new ArrayList<>();
        String path = Environment.getExternalStorageDirectory().toString() + "/Music";
        File musicDirectory = new File(path);
        if (musicDirectory.isDirectory()) {
            for (File musicFile : musicDirectory.listFiles()) {
                result.add(new AudioFileListItem(musicFile.getName()));
            }
        }

        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(Menu.NONE, MENU_EXPORT_AS_JSON, Menu.NONE, R.string.export_as_json_menu_label);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_EXPORT_AS_JSON:
                performExport();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void performExport() {
        List<AudioFileListItem> audioFilesItems = getAudioFileItems();
        if (audioFilesItems != null) {
            JSONHandler handler = new JSONHandler(audioFilesItems.get(0));
            try {
                JSONObject allFilesJSON = handler.generateJSON(audioFilesItems);
                Log.i("json", allFilesJSON.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
