package lab.android.rwth.evgenijandkate.lost.xml.model;

import java.io.Serializable;

/**
 * Created by ekaterina on 04.07.2015.
 */
public class Feature implements Serializable {
    private String name;
    private double value;

//    public Feature(String name, double value) {
//        this.name = name;
//        this.value = value;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
