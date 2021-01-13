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
import org.eclipse.jgit.util.StringUtils;

import java.io.*;
import java.util.*;

public class Main {


    public static void main(String[] args) throws Exception {
        //1.连接git
//        GitUtil gitUtil = new GitUtil("liuxiujiang","liuxiujiang123","https://github.com/xiujiang/trainGitLogProj.git","trainGitLogProj","main");
//        //2.根据git获取最新变更记录
////        List<DiffEntry> lastCommitDiff = gitUtil.getLastCommitDiff();
//        //3.解析gitlog
//
//        //4.根据log 分析变更
//        //5.输出变更信息
//
//        ArrayList<HashMap<String, Object>> gitVersion = gitUtil.getGitVersion();
////        HashMap<String, Object> commitLogList = gitUtil.getCommitLogList("5fda70b7bca8afc296c7e1da52d07302ccb1ac41");
//        System.out.println(gitVersion);
////        System.out.println(commitLogList);
//        gitUtil.diffMethod("7408be7f120e1d02f0d984437c4356ce18bfbcb2"+"^{tree}","51beee4c49a96fed8871e18953383ddf0c038a14"+"^{tree}");
//
        String s = "    ";
        System.out.println(StringUtils.isEmptyOrNull(s.trim()));
        System.out.println("Get method line number with javassist\n");
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get("com.quaternion.demo.Widget");
        CtMethod methodX = cc.getDeclaredMethod("x");
        int xlineNumber = methodX.getMethodInfo().getLineNumber(0);
        System.out.println("method x is on line " + xlineNumber + "\n");
    }


}
