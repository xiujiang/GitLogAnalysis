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

import java.io.*;
import java.util.*;

public class Main {


    public static void main(String[] args) throws Exception {
        //1.连接git
        GitUtil gitUtil = new GitUtil("liuxiujiang","Lxj123456","https://github.com/xiujiang/trainGitLogProj.git","trainGitLogProj","main");
        //2.根据git获取最新变更记录

        //3.解析gitlog
        //4.根据log 分析变更
        //5.输出变更信息

        ArrayList<HashMap<String, Object>> gitVersion = gitUtil.getGitVersion();
//        HashMap<String, Object> commitLogList = gitUtil.getCommitLogList("5fda70b7bca8afc296c7e1da52d07302ccb1ac41");
        System.out.println(gitVersion);
//        System.out.println(commitLogList);
        gitUtil.diffMethod("63ed0c2e611e9488752c7d04a5c22c6ac56c7a93"+"^{tree}","947bfac6a84695b95afe1ab4312723677babbd0a"+"^{tree}");


    }
}
