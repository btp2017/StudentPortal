package comc.android.vishal.studentportal;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sulabh Kumar on 3/18/2017.
 */

public class User {
    String name;
    String batch;
    String roll;
    String passingYear;
    String degree;

    public User(String name, String batch, String roll, String passingYear, String degree) {
        this.name = name;
        this.batch = batch;
        this.roll = roll;
        this.passingYear = passingYear;
        this.degree = degree;
    }

    public User(){

    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getPassingYear() {
        return passingYear;
    }

    public void setPassingYear(String passingYear) {
        this.passingYear = passingYear;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }


    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("batch", batch);
        result.put("roll", roll);
        result.put("passingYear", passingYear);
        result.put("degree", degree);

        return result;
    }

}
