package nju.autoscanner;

import nju.autoscanner.serviceImpl.WhistleBlower;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AutoscannerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoscannerApplication.class, args);
        System.out.println("start");
        WhistleBlower.getInstance().start();
    }

}
