package lab.android.rwth.evgenijandkate.lost;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import lab.android.rwth.evgenijandkate.lost.fragments.FeatureValueListFragment;
import lab.android.rwth.evgenijandkate.lost.model.AudioFileListItem;
import lab.android.rwth.evgenijandkate.lost.xml.XMLHandler;

/**
 * Created by ekaterina on 05.07.2015.
 */
public class AudioFeaturesActivity extends FragmentActivity {
    private final static String FRAGMENT_TAG = "data";
    public static final String MUSIC_FILE_TO_SEARCH_SIMILARITY_ITEM = "FILE_TO_SEARCH_SIMILARITY";
    private FeatureValueListFragment featureValueListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_features_activity);
        Bundle extras = getIntent().getExtras();
        final AudioFileListItem audioFileItem = (AudioFileListItem) extras.getSerializable(MusicListActivity.MUSIC_FILE_ITEM);
        if (audioFileItem != null) {
            setTitle(audioFileItem.getFileName());
        }

        FragmentManager fragmentManager = getFragmentManager();
        //fetch the fragment if it was saved (e.g. during orientation change)
        this.featureValueListFragment = (FeatureValueListFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (featureValueListFragment == null) {
            // add the fragment
            featureValueListFragment = FeatureValueListFragment.newInstance(audioFileItem);
            fragmentManager.beginTransaction().add(R.id.fragment_container, featureValueListFragment, FRAGMENT_TAG).commit();
        }

        Button findSimilarVectorButton = (Button) findViewById(R.id.find_vector);
        findSimilarVectorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AudioFeaturesActivity.this, DisplaySimilarFilesActivity.class);
                intent.putExtra(MUSIC_FILE_TO_SEARCH_SIMILARITY_ITEM, audioFileItem);
                startActivity(intent);
            }
        });
    }
}
