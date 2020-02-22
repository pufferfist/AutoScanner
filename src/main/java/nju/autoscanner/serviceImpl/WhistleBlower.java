package nju.autoscanner.serviceImpl;

import nju.autoscanner.util.JGit.JGit;
import nju.autoscanner.util.sonarWeb.SonarWeb;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 负责接收回调、切换版本、开始下一步扫描等核心功能
 * 由于只有一个对外提供服务的回调接口，所以没有使用数据库保存状态，
 * 所以这个类实际上违背了分层、无状态等原则，也不符合spring架构
 * 直接上spring boot是为了之后的扩展
 */

public class WhistleBlower {
    private static WhistleBlower instance=new WhistleBlower();

    private ArrayList<String> versionList;
    private int currentIndex;
    private static String path;
    private SonarWeb sonar;
    private FileWriter fileWriter;

    private static final String repoPath ="X:\\javaProjects\\sonar_example\\sonar-scanning-examples";//必须是repo根目录
    private static final String repoName="example";
    private static final String repoSource="src,copybooks";

    //读取同目录下的版本列表文件，构建要扫描的versionList
    private WhistleBlower() {
        path=System.getProperty("user.dir");
        versionList=new ArrayList<>();
        String filePath=path+"\\version list.txt";
        Scanner s= null;
        try {
            s = new Scanner(new File(filePath));
        } catch (FileNotFoundException e) {
            System.out.println("no version file");
        }
        while(s.hasNext()){
            String line=s.nextLine();
            if(!line.isEmpty()) {
                versionList.add(line);
            }
        }

        sonar=new SonarWeb();
        try {
            fileWriter=new FileWriter(new File(path+"\\result.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static WhistleBlower getInstance(){
        return instance;
    }

    public void start(){
        if(currentIndex>=versionList.size()){
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }else {
            String currentVersion = versionList.get(currentIndex);
            currentIndex++;
            switchOver(currentVersion);
            System.out.println(currentVersion+"scanning");
            scan(currentVersion);

        }

    }

    //请求数据、切换至currentIndex指向的的版本并扫描
    public void next() {
        System.out.println("get"+versionList.get(currentIndex-1)+" metrics");
        String result= null;
        try {
            result = sonar.findMetrics(versionList.get(currentIndex-1));
        } catch (IOException e) {
            e.printStackTrace();
        }
        write(result);
        start();
    }

    private void switchOver(String version){
        try {
            JGit.getInstance().checkout(repoPath,version);
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    private void scan(String version){
        String cmd="sonar-scanner" +
                " -Dsonar.projectName="+repoName+"_"+version
                +" -Dsonar.projectKey="+version+
                " -Dsonar.projectBaseDir="+repoPath+"\\sonarqube-scanner"+
                " -Dsonar.sources="+ repoSource;//此命令参数也应在配置文件中配置
        try {
            Process s=Runtime.getRuntime().exec(new String[]{"cmd.exe","/C",cmd});
            Scanner out=new Scanner(s.getInputStream());
            Scanner error=new Scanner(s.getErrorStream());
            while(out.hasNext()){
                System.out.println("output> "+out.nextLine());
            }
            while(error.hasNext()){
                System.out.println("error> "+error.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void write(String metrics){
        try {
            fileWriter.write(metrics+System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
