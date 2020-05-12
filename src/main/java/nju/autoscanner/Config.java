package nju.autoscanner;

public class Config {
    public static final String repoPath = "E:\\projects\\maven";//必须是repo根目录
    public static final String analysePath = "E:\\projects\\maven";
    public static final String repoName = "maven";//sonarqube扫描的名字
    public static final String repoSource = "src\\backend\\commands";//源文件目录，如有多个以逗号隔开
    public static final String metricKeys = "ncloc,complexity,violations,duplicated_blocks," +
            "duplicated_lines,duplicated_lines_density,violations,code_smells,bugs,vulnerabilities," +
            "classes,comment_lines,comment_lines_density";//从sonarqube返回的结果

    public static final String sonarServer = "http://localhost:9000/";
    public static final String sonarUsername = "admin";
    public static final String sonarPassword = "admin";


    public static String getCmd(String version) {
//        String cmd = "sonar-scanner" +
//                " -Dsonar.projectName=" + repoName + "_" + version
//                + " -Dsonar.projectKey=" + version;
//                + " -Dsonar.exclusions=**/*.java,**/*.css,**/*.html"
//                + " -Dsonar.scm.disabled=true"
//                + " -Dsonar.sources=" + repoSource;
        String cmd = "mvn clean install -Drat.numUnapprovedLicenses=9999 -Dmaven.test.skip=true sonar:sonar";
        cmd += " -Dsonar.projectName=" + repoName + "_" + version
                + " -Dsonar.projectKey=" + version +
                " -Dsonar.host.url="+sonarServer;
//        String cmd = "gradle build -x test sonarqube" +
//                " -Dsonar.projectName=" + repoName + "_" + version
//                + " -Dsonar.projectKey=" + version;
        return cmd;
    }
}
