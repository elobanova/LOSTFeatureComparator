package lab.android.rwth.evgenijandkate.lost.xml.parser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import lab.android.rwth.evgenijandkate.lost.xml.model.DataSet;
import lab.android.rwth.evgenijandkate.lost.xml.model.Feature;
import lab.android.rwth.evgenijandkate.lost.xml.model.FeatureVectorFile;

/**
 * Created by ekaterina on 04.07.2015.
 */
public class FeaturePullParser {
    private String text;
    private Feature feature;
    private List<Feature> features = new ArrayList<>();
    private DataSet dataSet;
    private FeatureVectorFile featureVectorFile;
    public FeatureVectorFile parse(File xmlFile) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            InputStream in = new FileInputStream(xmlFile);
            xpp.setInput(in, "UTF-8");
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = xpp.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("feature")) {
                            feature = new Feature();
                        } else if (tagname.equalsIgnoreCase("data_set")) {
                            dataSet = new DataSet();
                        } else if (tagname.equalsIgnoreCase("feature_vector_file")) {
                            featureVectorFile = new FeatureVectorFile();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("feature")) {
                            features.add(feature);
                        } else if (tagname.equalsIgnoreCase("name")) {
                            feature.setName(text);
                        } else if (tagname.equalsIgnoreCase("v")) {
                            feature.setValue(Double.parseDouble(text));
                        } else if (tagname.equalsIgnoreCase("comments")) {
                            featureVectorFile.setComments(text);
                        } else if (tagname.equalsIgnoreCase("data_set")) {
                            dataSet.setFeatures(features);
                            featureVectorFile.setDataSet(dataSet);
                        } else if (tagname.equalsIgnoreCase("data_set_id")) {
                            dataSet.setDataSetId(text);
                        }
                        break;
                    case XmlPullParser.TEXT:
                        text = xpp.getText();
                        break;
                    default:
                        break;
                }
                eventType = xpp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return featureVectorFile;
    }
}
