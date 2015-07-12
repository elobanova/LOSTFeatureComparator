package lab.android.rwth.evgenijandkate.lost.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import lab.android.rwth.evgenijandkate.lost.model.AudioFileListItem;
import lab.android.rwth.evgenijandkate.lost.xml.XMLHandler;
import lab.android.rwth.evgenijandkate.lost.xml.model.Feature;

/**
 * Created by ekaterina on 12.07.2015.
 */
public class JSONHandler {
    private static final String HEADER_PROPERTY = "header";
    private static final String DATA_PROPERTY = "data";
    private static final String VALUES_PROPERTY = "values";
    private static final String RELATION_PROPERTY = "relation";
    private static final String ATTRIBUTES_PROPERTY = "attributes";
    private static final String NAME_PROPERTY = "name";
    private static final String TYPE_PROPERTY = "type";
    private static final String CLASS_PROPERTY = "class";
    private static final String WEIGHT_PROPERTY = "weight";
    private static final String AUDIO_RELATION_NAME = "audio";
    private static final String SPARSE_ATTRIBUTE_NAME = "sparse";
    public static final String NUMERIC_VALUE_ATTRIBUTE = "numeric";
    public static final boolean IS_ATTRIBUTE_CLASS = false;
    public static final double WEIGHT_VALUE = 1.0;
    private static final String POSTFIX = "_AS_JSON.js";

    private final List<Feature> features;

    public JSONHandler(AudioFileListItem audioFileItem) {
        this.features = XMLHandler.extractFeatures(audioFileItem);
    }

    private JSONObject prepareHeader() throws JSONException {
        JSONObject header = new JSONObject();
        header.put(RELATION_PROPERTY, AUDIO_RELATION_NAME);
        JSONArray featureNamesJSONArray = new JSONArray();
        JSONObject fileNameAttribute = new JSONObject();
        fileNameAttribute.put(NAME_PROPERTY, "filename");
        fileNameAttribute.put(TYPE_PROPERTY, "string");
        fileNameAttribute.put(CLASS_PROPERTY, IS_ATTRIBUTE_CLASS);
        fileNameAttribute.put(WEIGHT_PROPERTY, WEIGHT_VALUE);
        featureNamesJSONArray.put(fileNameAttribute);
        for (Feature feature : this.features) {
            JSONObject JSONFeatureAttribute = new JSONObject();
            JSONFeatureAttribute.put(NAME_PROPERTY, feature.getName());
            JSONFeatureAttribute.put(TYPE_PROPERTY, NUMERIC_VALUE_ATTRIBUTE);
            JSONFeatureAttribute.put(CLASS_PROPERTY, IS_ATTRIBUTE_CLASS);
            JSONFeatureAttribute.put(WEIGHT_PROPERTY, WEIGHT_VALUE);
            featureNamesJSONArray.put(JSONFeatureAttribute);
        }

        header.put(ATTRIBUTES_PROPERTY, featureNamesJSONArray);
        return header;
    }

    private JSONArray prepareData(List<AudioFileListItem> audioFilesItems) throws JSONException {
        JSONArray data = new JSONArray();
        for (AudioFileListItem fileListItem : audioFilesItems) {
            data.put(getDataObjectForOneAudioFile(fileListItem));
        }
        return data;
    }

    private JSONObject getDataObjectForOneAudioFile(AudioFileListItem audioFileListItem) throws JSONException {
        JSONObject JSONFeatureData = new JSONObject();
        JSONFeatureData.put(SPARSE_ATTRIBUTE_NAME, false);
        JSONFeatureData.put(WEIGHT_PROPERTY, WEIGHT_VALUE);
        JSONArray valuesJSONArray = new JSONArray();
        valuesJSONArray.put(audioFileListItem.getFileName());
        for (Feature feature : XMLHandler.extractFeatures(audioFileListItem)) {
            valuesJSONArray.put(feature.getValueWithoutNaN());
        }
        JSONFeatureData.put(VALUES_PROPERTY, valuesJSONArray);
        return JSONFeatureData;
    }

    public JSONObject generateJSON(List<AudioFileListItem> audioFilesItems) throws JSONException {
        JSONObject finalJSON = new JSONObject();
        finalJSON.put(HEADER_PROPERTY, prepareHeader());
        finalJSON.put(DATA_PROPERTY, prepareData(audioFilesItems));
        return finalJSON;
    }
}
