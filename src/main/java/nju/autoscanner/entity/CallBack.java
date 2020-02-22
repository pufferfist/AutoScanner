package nju.autoscanner.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class CallBack {
    private String serverUrl;
    private String taskId;
    private String status;
    private Project project;
}
