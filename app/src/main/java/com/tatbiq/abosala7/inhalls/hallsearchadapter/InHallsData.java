package com.tatbiq.abosala7.inhalls.hallsearchadapter;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class InHallsData {

    @SerializedName("HallID")
    private String id;
    @SerializedName("HallName")
    private String hallName;
    @SerializedName("Capacity")
    private String capacity;
    @SerializedName("MenCapacity")
    private String menCapacity;
    @SerializedName("WomenCapacity")
    private String womenCapacity;
    @SerializedName("ImgPath")
    private String imagePath;
    @SerializedName("Photos")
    private String photos ;
    @SerializedName("Services")
    private String services ;


    public InHallsData(String id, String hallName, String capacity, String menCapacity,
                       String womenCapacity, String imagePath, String photos, String services) {
        this.id = id;
        this.hallName = hallName;
        this.capacity = capacity;
        this.menCapacity = menCapacity;
        this.womenCapacity = womenCapacity;
        this.imagePath = imagePath;
        this.photos = photos;
        this.services = services;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
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

    /*   public static List<InHallsData> getInHallDataArray(String response , List<InHallsData> myList) throws JSONException {

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
    */
    public String getHallName() {
        return hallName;
    }
}

