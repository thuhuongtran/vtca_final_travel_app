package com.vtcac.thuhuong.mytrips.entity;

import java.util.Objects;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.PrimaryKey;

@Entity(tableName = "travel")
public class Travel extends TravelBaseEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String endDt;
    private String imgUri;

    public Travel() {
    }
    public Travel(String title) {
        this.title = title;
    }
    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id, endDt);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Travel)) return false;
        if (!super.equals(o)) return false;
        Travel travel = (Travel) o;
        return id == travel.id &&
                Objects.equals(endDt, travel.endDt);
    }

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

    @Override
    public String toString() {
        return "Travel{" +
                "id=" + id +
                ", endDt='" + endDt + '\'' +
                ", imgUri='" + imgUri + '\'' +
                ", startDt='" + startDt + '\'' +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", placeName='" + placeName + '\'' +
                ", placeAddr='" + placeAddr + '\'' +
                ", placeLat=" + placeLat +
                ", placeLng=" + placeLng +
                '}';
    }
}
