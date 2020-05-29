package nju.autoscanner.serviceImpl;

import nju.autoscanner.service.InformService;

@org.springframework.stereotype.Service
public class InformServiceImpl implements InformService {
    @Override
    public void callBack() {
        Service.getInstance().next();
    }
}
