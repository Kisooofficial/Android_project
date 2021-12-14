package org.techtown.final_exam_project;

public class ListItem {
    //리스트뷰에 쓰일 아이템들을 모아두는 class
    //데이터베이스 컬럼에 해당하는 요소들 모두 선언
    private String name, type, distance, average;
    public String getName(){
        return name;
    } // 이름 가져오기

    public void setName(String name){
        this.name = name;
    } //이름 저장

    public String getType(){
        return type;
    }//러닝 타입 가져오기

    public void setType(String type){
        this.type = type;
    } // 러닝 타입 저장

    public String getDistance(){
        return distance;
    } //거리 가져오기

    public void setDistance(String distance){
        this.distance = distance;
    } // 거리 저장

    public String getAverage(){
        return average;
    } // 평균 가져오기

    public void setAverage(String average){
        this.average = average;
    } //평균 저장

    //생성자
    ListItem(String name, String type, String distance, String average){
        this.name = name;
        this.type = type;
        this.distance = distance;
        this.average = average;
    }
}
