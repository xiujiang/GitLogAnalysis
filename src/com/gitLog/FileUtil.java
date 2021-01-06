package com.gitLog;

import java.io.*;
import java.util.Map;

public class FileUtil {


    public Map<String,String> getFile() throws IOException {
        File file = new File("/Users/joel/IdeaProjects2/gitLog/trainGitLogProj/src/main/java/com/blog/controller/ArticleController.java");
        System.out.println(file.exists());
        String s = null;
        FileReader fileReader = new FileReader(file);
        BufferedReader bfr = new BufferedReader(fileReader);
        int a = 1;
        while((s = bfr.readLine()) != null){
            System.out.println(a++ +"    "+s);
        }
        return null;
    }
}
