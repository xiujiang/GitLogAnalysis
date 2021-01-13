package com.gitLog;

import java.io.*;
import java.util.Map;

public class FileUtil {
    public Map<String,String> getFile(String fileName) throws IOException {
        File file = new File("/Users/joel/IdeaProjects2/gitLog/trainGitLogProj/"+fileName);
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
