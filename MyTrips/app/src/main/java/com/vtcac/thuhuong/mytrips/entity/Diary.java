package com.vtcac.thuhuong.mytrips.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "diary"
        , foreignKeys = @ForeignKey(
        entity = Travel.class
        , parentColumns = "id"
        , childColumns = "travelId"
        , onDelete = ForeignKey.CASCADE
)
        , indices = {
        @Index("travelId")
}
)
public class Diary extends TravelBaseEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long travelId;
    private String imgUri;
    private String time;
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTravelId() {
        return travelId;
    }

    public void setTravelId(long travelId) {
        this.travelId = travelId;
    }

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    @Override
    public String toString() {
        return "Diary{" +
                "id=" + id +
                ", travelId=" + travelId +
                ", imgUri='" + imgUri + '\'' +
                ", startDt='" + startDt + '\'' +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", placeName='" + placeName + '\'' +
                ", placeAddr='" + placeAddr + '\'' +
                ", placeLat=" + placeLat +
                ", placeId='" + placeId + '\'' +
                ", placeLng=" + placeLng +
                '}';
    }
}
