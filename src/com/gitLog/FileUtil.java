package com.gitLog;

import org.eclipse.jgit.util.StringUtils;

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

    public Integer checkStr(String str){
        if(StringUtils.isEmptyOrNull(str) || StringUtils.isEmptyOrNull(str.trim())){
            return -1;
        }
        if(checkIsClass(str)){
            return 1;
        }
        if(checkIsMethod(str)){
            return 2;
        }
        if(checkAttr(str)){
            return 3;
        }
        if(checkMethodBody(str)){
            return 4;
        }
        return -1;
    }

    private static boolean checkMethodBody(String str){
        if(StringUtils.isEmptyOrNull(str) || StringUtils.isEmptyOrNull(str.trim())){
            return false;
        }
        // 方法签名的正则
        String classSignaturePattern = "(public|protected|private)?(\\s+)?(static)?(\\s+)?(final)?(\\s+)?" +
                "(\\w)+(\\s+)?" + "(\\w)+(\\s+)?"+"(=)?(\\s+)?"+"(\\w)+(\\s+)?"+";";
        return str.matches(classSignaturePattern);
    }

    private static boolean checkAttr(String str){
        if(StringUtils.isEmptyOrNull(str) || StringUtils.isEmptyOrNull(str.trim())){
            return false;
        }
        // 方法签名的正则
        String classSignaturePattern = "(public|protected|private)?(\\s+)?(static)?(\\s+)?(final)?(\\s+)?" +
                "(\\w)+(\\s+)?" + "(\\w)+(\\s+)?"+"(=)?(\\s+)?"+"(\\w)+(\\s+)?"+";";
        return str.matches(classSignaturePattern);
    }

    private static boolean checkIsClass(String str){
        if(StringUtils.isEmptyOrNull(str) || StringUtils.isEmptyOrNull(str.trim())){
            return false;
        }

        // 方法签名的正则
        String classSignaturePattern = "(public|protected|private)?(\\s+)?(static)?(\\s+)?(final)?(\\s+)?" +
                "class";
        return str.matches(classSignaturePattern);
    }
    private static boolean checkIsMethod(String str){

        if(StringUtils.isEmptyOrNull(str) || StringUtils.isEmptyOrNull(str.trim())){
            return false;
        }
        str = str.trim();
        // 名字，模板，类型的正则
        String namePattern = "[_a-zA-Z]+\\w*";
        String templatePattern = "(<[_a-zA-Z]+\\w*(, [_a-zA-Z]+\\w*)*>)?";
        String typePattern = namePattern + templatePattern;

        // 参数的正则
        String argPatternZero = "";       // 无参数
        String argPatternDynamic = "..."; // 动态参数
        String argPattern = typePattern + " " + namePattern;                     // 一个参数
        String argPatternOne = argPattern + "(, ...)?";                          // 一个参数 ＋ 动态参数
        String argPatternMulti = argPattern + "(, " + argPattern + ")*(, ...)?"; // 多个参数 ＋ 动态参数
        String argsPattern = "(" + argPatternZero + "|" + argPatternDynamic + "|" + argPatternOne + "|" + argPatternMulti + ")";

        // 方法签名的正则
        String methodSignaturePattern = "(public|protected|private)(\\s+)?(static)?(\\s+)?(final)?(\\s+)?" +
                typePattern + " " + namePattern + "\\(" + argsPattern + "\\)"+"(\\{)?"+"(})?";

//        // 每个部分的正则测试
//        System.out.println("List<int, Object>".matches(typePattern));
//        System.out.println("".matches(argPatternZero));
//        System.out.println("...".matches(argPatternDynamic));
//        System.out.println(argPatternOne);
//        System.out.println("int age".matches(argPatternOne));
//        System.out.println(argPatternMulti);
//        System.out.println("int age, List<Integer> values".matches(argPatternMulti));
//        System.out.println("int age, List<Integer> values, ...".matches(argsPattern));

        // 测试是否一个方法的签名
        // 暂时规则，单词间只用一个空格分隔，因为实现情况要考虑回车，多个空格，tab键等，把空格在正则中换成\\s+
        // 例如要在','前后加\\s*，函数签名中的'(', ')'前后加\\s*
        // 这里为了不使正则变得太难懂，直接只用了一个空格.
        return str.matches(methodSignaturePattern);
    }


}
