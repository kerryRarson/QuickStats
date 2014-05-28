package com.montana.quickstats.app;


import android.content.Context;
import android.graphics.AvoidXfermode;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by klarsen on 5/27/2014.
 */
public class Player implements Parcelable {
    private String _name;
    private String _team;
    private String _Position;
    private String _wgt;
    private String _hgt;
    private String _Speed;

    public String getPlayerName() {
        return _name;
    }

    public void setPlayerName(String name) {
        _name = name;
    }

    public String getTeam() {
        return _team;
    }

    public void setTeam(String team) {
        _team = team;
    }

    public String getPosition() {
        return _Position;
    }

    public void setPosition(String pos) {
        _Position = pos;
    }

    public String getWeight() {
        return _wgt;
    }

    public void setWeight(String w) {
        _wgt = w;
    }

    public String getHeight() {
        return _hgt;
    }

    public void setHeight(String h) {
        _hgt = h;
    }

    public String getSpeed() {
        return _Speed;
    }

    public void setSpeed(String spd) {
        _Speed = spd;
    }
    public Player(String name, String team, String pos, String hgt, String wgt, String spd){
        _name = name;
        _team = team;
        _Position = pos;
        _hgt = hgt;
        _wgt = wgt;
        _Speed = spd;
    }
    public Player(){}

    //Serialize/Deserialize
    public String Serialize(){
        return _name.trim() + "~" + _Position + "~" + _team + "~" + _hgt + "~" + _wgt + "~" + _Speed;
    }
    public void Deserialize(String strings) {
        String[] values = strings.split("~");
        _name = values[0];
        _Position = values[1];
        _team = values[2];
        _hgt = values[3];
        _wgt = values[4];
        _Speed = values[5];
    }
    //Parceable stuff
    public Player(Parcel in) {
        String[] data = new String[6];

        in.readStringArray(data);
        this._name = data[0];
        this._Position = data[1];
        this._team = data[2];
        this._hgt = data[3];
        this._wgt = data[4];
        this._Speed = data[5];
    }

    public int describeContents(){
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this._name,
                this._team,
                this._Position,
                this._hgt,
                this._wgt,
                this._Speed});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        public Player[] newArray(int size) {
            return new Player[size];
        }
    };
}