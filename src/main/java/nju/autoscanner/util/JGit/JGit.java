package nju.autoscanner.util.JGit;

import nju.autoscanner.Config;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class JGit {
    private static JGit instance=new JGit();

    private JGit(){

    }

    public static JGit getInstance(){
        return instance;
    }

    public void checkout(String repoPath,String version) throws GitAPIException {
        Git git = null;
        try {
            git = new Git(new FileRepository(repoPath+"\\.git"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        git.checkout().setName(version).call();
    }

    public void clean(){
        String cmd="git clean -xfd";
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
}
