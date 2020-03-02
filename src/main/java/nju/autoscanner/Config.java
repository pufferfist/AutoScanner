package nju.autoscanner;

public class Config {
    public static final String repoPath ="X:\\javaProjects\\sonar_example\\sonar-scanning-examples";//必须是repo根目录
    public static final String repoName="example";//sonarqube扫描的名字
    public static final String repoSource="src,copybooks";//源文件目录，如有多个以逗号隔开
    public static final String metricKeys="ncloc,complexity,violations";//从sonarqube返回的结果

    public static final String sonarServer="http://localhost:9000/";
    public static final String sonarUsername="admin";
    public static final String sonarPassword="admin";


    public static String getCmd(String version) {
        String cmd = "sonar-scanner" +
                " -Dsonar.projectName=" + repoName + "_" + version
                + " -Dsonar.projectKey=" + version +
                " -Dsonar.projectBaseDir=" + repoPath + "\\sonarqube-scanner" +
                " -Dsonar.sources=" + repoSource;
        return cmd;
    }
}
