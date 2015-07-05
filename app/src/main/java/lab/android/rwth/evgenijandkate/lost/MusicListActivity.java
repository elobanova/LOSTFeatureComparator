package lab.android.rwth.evgenijandkate.lost;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Environment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;

import lab.android.rwth.evgenijandkate.lost.adapters.MusicFilesListAdapter;
import lab.android.rwth.evgenijandkate.lost.model.AudioFileListItem;


public class MusicListActivity extends ListActivity {
    public static final String MUSIC_FILE_ITEM = "item";
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
            String path = Environment.getExternalStorageDirectory().toString() + "/Music";
            File musicDirectory = new File(path);
            if (musicDirectory.isDirectory()) {
                for (File musicFile : musicDirectory.listFiles()) {
                    this.adapter.add(new AudioFileListItem(musicFile.getName()));
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_music_list, menu);
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
}
