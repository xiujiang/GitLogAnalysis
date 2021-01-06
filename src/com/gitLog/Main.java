package com.gitLog;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.RenameDetector;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.TreeWalk;

import java.io.File;
import java.util.*;

public class Main {


    public static void main(String[] args) throws Exception {
        System.out.println(new File("/Users/joel/IdeaProjects2/gitLog" + File.separator + "MyBlog/").isDirectory());
        File file = new File("/Users/joel/IdeaProjects2/gitLog" + File.separator + "MyBlog/.git");
        System.out.println( file.exists());
        GitUtil gitUtil = new GitUtil("liuxiujiang","Lxj@qq.com.1234","http://git.dangdang.com/fission/freetry-api.git","freetry-api","master");
        ArrayList<HashMap<String, Object>> gitVersion = gitUtil.getGitVersion();
        HashMap<String, Object> commitLogList = gitUtil.getCommitLogList("5fda70b7bca8afc296c7e1da52d07302ccb1ac41");
        System.out.println(gitVersion);
        System.out.println(commitLogList);

        gitUtil.diffMethod("7aaf43c73e729b360699fcdeb65d5447bf526c02"+"^{tree}","60600c9cf682a47bedc611c4f31310f131c1daa3"+"^{tree}");
    }
}
