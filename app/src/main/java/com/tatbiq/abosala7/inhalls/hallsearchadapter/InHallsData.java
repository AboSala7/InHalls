package com.tatbiq.abosala7.inhalls.hallsearchadapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class InHallsData {
    private String hallName;
    private String created;
    private String capacity;
    private String menCapacity;
    private String womenCapacity;
    private String imagePath;


    public InHallsData(String hallName, String created, String capacity, String menCapacity, String womenCapacity, String imagePath) {
        this.hallName = hallName;
        this.created = created;
        this.capacity = capacity;
        this.menCapacity = menCapacity;
        this.womenCapacity = womenCapacity;
        this.imagePath = imagePath;
    }

    public static List<InHallsData> getInHallDataArray(String response , List<InHallsData> myList) throws JSONException {

        HashMap<String,String> map = new HashMap<>();
        map.put("key",response);
        JSONObject jsonObject = new JSONObject(map.toString());
        JSONArray jsonArray = jsonObject.getJSONArray("key");

        for(int x = 0 ; x <= jsonArray.length() ; x++){
            InHallsData inHallData = new InHallsData((jsonArray.getJSONObject(x).getString("hallname")),
                    (jsonArray.getJSONObject(x).getString("Created")),
                    (jsonArray.getJSONObject(x).getString("Capacity")),
                    (jsonArray.getJSONObject(x).getString("MenCapacity")),
                    (jsonArray.getJSONObject(x).getString("WomenCapacity")),
                    (jsonArray.getJSONObject(x).getString("ImgPath")));

            myList.add(inHallData);

        }
        return myList;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getMenCapacity() {
        return menCapacity;
    }

    public void setMenCapacity(String menCapacity) {
        this.menCapacity = menCapacity;
    }

    public String getWomenCapacity() {
        return womenCapacity;
    }

    public void setWomenCapacity(String womenCapacity) {
        this.womenCapacity = womenCapacity;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}

