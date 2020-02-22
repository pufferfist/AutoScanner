package nju.autoscanner.util.sonarWeb;

import nju.autoscanner.util.Http;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class SonarWeb {
    private String sonarServerBase;
    private Http http;

    public SonarWeb() {
        this.sonarServerBase = "http://localhost:9000/";
        this.http = new Http();
        boolean login=false;
        try {
            login=this.login("admin","admin");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (login){
            System.out.println("login success");
        }
    }

    public boolean login(String admin, String password) throws IOException {
        String url=sonarServerBase+"api/authentication/login?login=admin&password=admin";
        CloseableHttpResponse response=http.post(url);
        return response.getStatusLine().getStatusCode()==200;
    }

    public String findMetrics(String componentKey) throws IOException {
        String url=sonarServerBase+"api/measures/component_tree?component="+componentKey+"&metricKeys=ncloc,complexity,violations";
        CloseableHttpResponse response=http.get(url);
        return EntityUtils.toString(response.getEntity());
    }

//    public void setWebHook(){
//        String url=sonarServerBase+"api/webhooks/create"
//    }
}
