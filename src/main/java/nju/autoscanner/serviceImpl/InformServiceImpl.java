package nju.autoscanner.serviceImpl;

import nju.autoscanner.service.InformService;
import org.springframework.stereotype.Service;

@Service
public class InformServiceImpl implements InformService {
    @Override
    public void callBack() {
        WhistleBlower.getInstance().next();
    }
}
