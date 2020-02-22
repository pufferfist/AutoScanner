package nju.autoscanner.util.JGit;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;

import java.io.IOException;

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
}
