package com.example.medregister.models;
import android.os.Parcel;
import android.os.Parcelable;

public class Pill {

    private String pill_name;
    private String pill_instruction;
    private String pill_number_in_package;
    private String pill_daily_usage;
    private String pill_id;
    private String creator_id;

    public Pill(String pill_name, String pill_instruction, String pill_number_in_package, String pill_daily_usage, String pill_id,String creator_id) {
        this.pill_name = pill_name;
        this.pill_instruction = pill_instruction;
        this.pill_number_in_package = pill_number_in_package;
        this.pill_daily_usage = pill_daily_usage;
        this.pill_id = pill_id;
        this.creator_id = creator_id;
    }

    public Pill() {

    }

    protected Pill(Parcel in) {
        pill_name = in.readString();
        pill_instruction = in.readString();
        pill_number_in_package = in.readString();
        pill_daily_usage = in.readString();
        pill_id = in.readString();
        creator_id = in.readString();
    }

    public static final Creator<Pill> CREATOR = new Creator<Pill>() {
        @Override
        public Pill createFromParcel(Parcel in) {
            return new Pill(in);
        }

        @Override
        public Pill[] newArray(int size) {
            return new Pill[size];
        }
    };

    public String getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(String creator_id) {
        this.creator_id = creator_id;
    }

    public String getPill_id() {
        return pill_id;
    }

    public void setPill_id(String pill_id) {
        this.pill_id = pill_id;
    }

    public String getPill_name() {
        return pill_name;
    }

    public void setPill_name(String pill_name) {
        this.pill_name = pill_name;
    }

    public String getPill_instruction() {
        return pill_instruction;
    }

    public void setPill_instruction(String pill_instruction) {
        this.pill_instruction = pill_instruction;
    }

    public String getPill_number_in_package() {
        return pill_number_in_package;
    }

    public void setPill_number_in_package(String pill_number_in_package) {
        this.pill_number_in_package = pill_number_in_package;
    }

    public String getPill_daily_usage() {
        return pill_daily_usage;
    }

    public void setPill_daily_usage(String pill_daily_usage) {
        this.pill_daily_usage = pill_daily_usage;
    }

    @Override
    public String toString() {
        return "Pill{" +
                "pill_name='" + pill_name + '\'' +
                ", pill_instruction='" + pill_instruction + '\'' +
                ", pill_number_in_package='" + pill_number_in_package + '\'' +
                ", pill_daily_usage='" + pill_daily_usage + '\'' +
                ", pill_id='" + pill_id + '\'' +
                ", creator_id='" + creator_id + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pill_name);
        dest.writeString(pill_instruction);
        dest.writeString(pill_number_in_package);
        dest.writeString(pill_daily_usage);
        dest.writeString(pill_id);
        dest.writeString(creator_id);
    }
}
