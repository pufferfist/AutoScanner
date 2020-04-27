package nju.autoscanner.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Version {
    private String time;
    private String tag;

    public Version(String time, String tag) {
        this.time = time;
        this.tag = tag;
    }
}
