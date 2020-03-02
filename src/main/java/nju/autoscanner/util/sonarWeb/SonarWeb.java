package nju.autoscanner.util.sonarWeb;

import nju.autoscanner.Config;
import nju.autoscanner.util.Http;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class SonarWeb {
    private String sonarServerBase;
    private Http http;

    public SonarWeb() {
        this.sonarServerBase = Config.sonarServer;
        this.http = new Http();
        boolean login=false;
        try {
            login=this.login(Config.sonarUsername,Config.sonarPassword);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (login){
            System.out.println("login success");
        }
    }

    public boolean login(String username, String password) throws IOException {
        String url=sonarServerBase+"api/authentication/login?login="+username+"&password="+password;
        CloseableHttpResponse response=http.post(url);
        return response.getStatusLine().getStatusCode()==200;
    }

    public String findMetrics(String componentKey) throws IOException {
        String url=sonarServerBase+"api/measures/component_tree?component="+componentKey+"&metricKeys="+Config.metricKeys;
        CloseableHttpResponse response=http.get(url);
        return EntityUtils.toString(response.getEntity());
    }

//    public void setWebHook(){
//        String url=sonarServerBase+"api/webhooks/create"
//    }
}
