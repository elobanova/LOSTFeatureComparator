package lab.android.rwth.evgenijandkate.lost.xml.model;

/**
 * Created by ekaterina on 04.07.2015.
 */
public class FeatureVectorFile {
    private String comments;
    private DataSet dataSet;

//    public FeatureVectorFile(String comments, DataSet dataSet) {
//        this.comments = comments;
//        this.dataSet = dataSet;
//    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public DataSet getDataSet() {
        return dataSet;
    }

    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }
}
