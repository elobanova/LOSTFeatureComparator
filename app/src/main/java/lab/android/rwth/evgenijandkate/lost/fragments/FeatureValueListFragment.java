package lab.android.rwth.evgenijandkate.lost.fragments;

import android.app.ListFragment;
import android.os.Bundle;

import java.io.Serializable;

import lab.android.rwth.evgenijandkate.lost.adapters.FeatureListAdapter;
import lab.android.rwth.evgenijandkate.lost.model.AudioFileListItem;
import lab.android.rwth.evgenijandkate.lost.xml.XMLHandler;
import lab.android.rwth.evgenijandkate.lost.xml.model.Feature;

/**
 * Created by ekaterina on 05.07.2015.
 */
public class FeatureValueListFragment extends ListFragment {
    public static final String AUDIO_FILE_KEY = "AUDIO";
    private AudioFileListItem audioFileItem;
    private FeatureListAdapter adapter;

    public static FeatureValueListFragment newInstance(AudioFileListItem audioFileItem) {

        Bundle args = new Bundle();
        args.putSerializable(AUDIO_FILE_KEY, (Serializable) audioFileItem);
        FeatureValueListFragment fragment = new FeatureValueListFragment();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.audioFileItem = (AudioFileListItem) getArguments().getSerializable(AUDIO_FILE_KEY);

        this.adapter = new FeatureListAdapter(getActivity().getApplicationContext());
        addFeatureItemsToAdapter();
        setListAdapter(this.adapter);
    }

    private void addFeatureItemsToAdapter() {
        if (this.adapter != null && this.audioFileItem != null) {
            for (Feature feature : XMLHandler.extractFeatures(this.audioFileItem)) {
                this.adapter.add(feature);
            }
        }
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.mensa_menu_list_fragment, container, false);
//    }
}
