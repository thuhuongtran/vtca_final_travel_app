package com.vtcac.thuhuong.mytrips.entity;

import com.vtcac.thuhuong.mytrips.utils.MyString;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "expense"
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
public class Expense extends TravelBaseEntity{
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long travelId;
    private String type;
    private double amount;
    private String currency;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public String getAmountText() {
        return MyString.getMoneyText(amount);
    }
    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", travelId=" + travelId +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
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
