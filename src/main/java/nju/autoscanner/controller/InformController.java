package nju.autoscanner.controller;

import com.google.gson.Gson;
import nju.autoscanner.entity.CallBack;
import nju.autoscanner.service.InformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InformController {
    @Autowired
    InformService informService;

    @PostMapping("callback")
    public void callBack(@RequestBody String result) {
//        Gson gsn=new Gson();
//        System.out.println(gsn.fromJson(result, CallBack.class));
        informService.callBack();
    }
}
