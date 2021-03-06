package lab.android.rwth.evgenijandkate.lost.xml;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import lab.android.rwth.evgenijandkate.lost.model.AudioFileListItem;
import lab.android.rwth.evgenijandkate.lost.xml.model.Feature;
import lab.android.rwth.evgenijandkate.lost.xml.model.FeatureVectorFile;
import lab.android.rwth.evgenijandkate.lost.xml.model.Pair;
import lab.android.rwth.evgenijandkate.lost.xml.parser.FeaturePullParser;

/**
 * Created by ekaterina on 05.07.2015.
 */
public class XMLHandler {
    private static final String POSTFIX = "_result.xmlFV.xml";
    private static final int DEFAULT_K = 10;

    public static List<Feature> extractFeatures(AudioFileListItem audioFileItem) {
        String path = Environment.getExternalStorageDirectory().toString() + "/LostXML";
        File XMLFile = new File(path + "/" + audioFileItem.getFileName() + POSTFIX);
        FeatureVectorFile featureVectorFile = new FeaturePullParser().parse(XMLFile);
        return featureVectorFile.getDataSet().getFeatures();
    }

    public static List<AudioFileListItem> getSimilarObjects(AudioFileListItem audioFileItem) {
        List<AudioFileListItem> result = new ArrayList<>();
        String path = Environment.getExternalStorageDirectory().toString() + "/LostXML";
        File resourceDirectory = new File(path);
        List<? extends Pair<AudioFileListItem, Pair<File, Double>>> sortedFirstKSimilarObjects = getSimilarObjectsForAudio(
                audioFileItem, resourceDirectory);
        for (Pair<AudioFileListItem, Pair<File, Double>> closePair : sortedFirstKSimilarObjects) {
            result.add(new AudioFileListItem(closePair.secondArg.firstArg.getName()));
        }
        return result;
    }

    private static List<? extends Pair<AudioFileListItem, Pair<File, Double>>> getSimilarObjectsForAudio(AudioFileListItem audioFileItem, File resourceDirectory) {
        List<Pair<AudioFileListItem, Pair<File, Double>>> similarAudioFiles = new ArrayList<>();
        String path = Environment.getExternalStorageDirectory().toString() + "/Music";
        File audioFile = new File(path + "/" + audioFileItem.getFileName());
        double[] featureVector = getVectorValues(audioFile);
        if (featureVector != null) {
            List<Pair<File, Double>> distances = new LinkedList<>();
            for (File anotherAudioFile : new File(path).listFiles()) {
                if (!audioFile.equals(anotherAudioFile)) {
                    double[] anotherFeatureVector = getVectorValues(anotherAudioFile);
                    if (anotherFeatureVector != null) {
                        double L2Distance = calculateL2DistanceBetweenVectors(featureVector,
                                anotherFeatureVector);

                        // remember the distances from audio to all
                        // other audio files
                        distances.add(new Pair<>(anotherAudioFile, L2Distance));
                    }
                }
            }

            // define ascending sorting for distances
            Collections.sort(distances, new Comparator<Pair<File, Double>>() {
                public int compare(Pair<File, Double> o1, Pair<File, Double> o2) {
                    return o1.secondArg.compareTo(o2.secondArg);
                }
            });

            // choose only k nearest neighbors
            for (int i = 0; i < DEFAULT_K; i++) {
                similarAudioFiles.add(new Pair<>(
                        audioFileItem, new Pair<>(distances.get(i).firstArg, distances
                        .get(i).secondArg)));
            }
        }
        return similarAudioFiles;

    }

    private static double calculateL2DistanceBetweenVectors(double[] f1, double[] f2) {
        double squaredDistance = 0;
        for (int i = 0; i < f1.length; i++) {
            squaredDistance += Math.pow(f1[i] - f2[i], 2);
        }

        return Math.sqrt(squaredDistance);
    }

    private static double[] getVectorValues(File audioFileItem) {
        List<Feature> features = extractFeatures(new AudioFileListItem(audioFileItem.getName()));
        double[] result = new double[features.size()];
        for (int i = 0; i < features.size(); i++) {
            double value = features.get(i).getValue();
            result[i] = Double.isNaN(value) ? 0.0d : value;
        }
        return result;
    }
}
