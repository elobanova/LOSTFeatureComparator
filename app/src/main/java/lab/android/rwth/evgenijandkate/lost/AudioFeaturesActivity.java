package lab.android.rwth.evgenijandkate.lost;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lab.android.rwth.evgenijandkate.lost.fragments.FeatureValueListFragment;
import lab.android.rwth.evgenijandkate.lost.json.JSONHandler;
import lab.android.rwth.evgenijandkate.lost.model.AudioFileListItem;
import lab.android.rwth.evgenijandkate.lost.xml.XMLHandler;
import lab.android.rwth.evgenijandkate.lost.xml.model.Feature;

/**
 * Created by ekaterina on 05.07.2015.
 */
public class AudioFeaturesActivity extends FragmentActivity {
    private final static String FRAGMENT_TAG = "data";
    public static final int MENU_EXPORT_AS_JSON = Menu.FIRST;
    public static final String MUSIC_FILE_TO_SEARCH_SIMILARITY_ITEM = "FILE_TO_SEARCH_SIMILARITY";
    private FeatureValueListFragment featureValueListFragment;
    private AudioFileListItem audioFileItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_features_activity);
        Bundle extras = getIntent().getExtras();
        this.audioFileItem = (AudioFileListItem) extras.getSerializable(MusicListActivity.MUSIC_FILE_ITEM);
        if (this.audioFileItem != null) {
            setTitle(this.audioFileItem.getFileName());
        }

        FragmentManager fragmentManager = getFragmentManager();
        //fetch the fragment if it was saved (e.g. during orientation change)
        this.featureValueListFragment = (FeatureValueListFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (featureValueListFragment == null) {
            // add the fragment
            featureValueListFragment = FeatureValueListFragment.newInstance(this.audioFileItem);
            fragmentManager.beginTransaction().add(R.id.fragment_container, featureValueListFragment, FRAGMENT_TAG).commit();
        }

        Button findSimilarVectorButton = (Button) findViewById(R.id.find_vector);
        findSimilarVectorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AudioFeaturesActivity.this, DisplaySimilarFilesActivity.class);
                intent.putExtra(MUSIC_FILE_TO_SEARCH_SIMILARITY_ITEM, AudioFeaturesActivity.this.audioFileItem);
                startActivity(intent);
            }
        });
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
        if (this.audioFileItem != null) {
            JSONHandler handler = new JSONHandler(this.audioFileItem);
            try {
                List<AudioFileListItem> listItems = new ArrayList<>();
                listItems.add(this.audioFileItem);
                JSONObject oneFileJSONObject = handler.generateJSON(listItems);
                Log.i("json", oneFileJSONObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
