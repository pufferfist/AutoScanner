package nju.autoscanner.serviceImpl;

import nju.autoscanner.Config;
import nju.autoscanner.entity.Version;
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

    private ArrayList<Version> versionList;
    private int currentIndex;
    private static String path;
    private SonarWeb sonar;
    private FileWriter fileWriter;

    private static final String repoPath =Config.repoPath;

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
                String[] infos=line.split(",");
                versionList.add(new Version(infos[0],infos[1]));
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
            String currentVersion = versionList.get(currentIndex).getTag();
            currentIndex++;
            switchOver(currentVersion);
            System.out.println(currentVersion+" is scanning");
            scan(currentVersion);

        }

    }

    //请求数据、切换至currentIndex指向的的版本并扫描
    public void next() {
        System.out.println("got "+versionList.get(currentIndex-1).getTag()+" metrics");
        String result= null;
        try {
            result = sonar.findMetrics(versionList.get(currentIndex-1).getTag())+System.lineSeparator();
        } catch (IOException e) {
            e.printStackTrace();
        }
        write(versionList.get(currentIndex-1).getTime(),result);
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
        String cmd= Config.getCmd(version);
        try {
            Process s=Runtime.getRuntime().exec(new String[]{"cmd.exe","/C",cmd},null,new File(Config.analysePath));
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

    private void write(String time,String metrics){
        try {
            fileWriter.write(time+" "+metrics+System.lineSeparator());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
