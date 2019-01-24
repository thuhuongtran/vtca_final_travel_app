package com.vtcac.thuhuong.mytrips.entity;


import java.util.Objects;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "plan"
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
public class Plan extends TravelBaseEntity{
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long travelId;
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
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, travelId);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Plan)) return false;
        if (!super.equals(o)) return false;
        Plan that = (Plan) o;
        return id == that.id &&
                travelId == that.travelId ;
    }
    @Override
    public String toString() {
        return "Plan{" +
                "id=" + id +
                ", travelId=" + travelId +
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
