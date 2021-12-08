package org.techtown.final_exam_project;

public class ListItem {
    private String name, type, distance, average;
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getDistance(){
        return distance;
    }

    public void setDistance(String distance){
        this.distance = distance;
    }

    public String getAverage(){
        return average;
    }

    public void setAverage(String average){
        this.average = average;
    }

    ListItem(String name, String type, String distance, String average){
        this.name = name;
        this.type = type;
        this.distance = distance;
        this.average = average;
    }
}
