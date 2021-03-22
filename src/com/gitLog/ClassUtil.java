package com.gitLog;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.eclipse.jgit.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ClassUtil {

    private static List<String> method_modifier = new ArrayList<>();
    private static List<String> attr_modifier = new ArrayList<>();
    private static List<String> modifier = new ArrayList<>();
    static {
        method_modifier.add("(");
        method_modifier.add("void");
        attr_modifier.add("=");
        modifier.add("public");
        modifier.add("private");
        modifier.add("protected");
    }
    private Integer getCodeType(Integer lineNumber,String[] fileCodes){
        //1,class,2.函数,3 属性,4 函数体
        Integer type = 0;
        while(type != 0){
            String codeLineStr = fileCodes[lineNumber--];
            if(StringUtils.isEmptyOrNull(codeLineStr)){
                continue;
            }
            String codeLine = codeLineStr.trim();
            if(StringUtils.isEmptyOrNull(codeLine)){
                continue;
            }
            if(codeLine.contains("class")){
                type = 1;
                break;
            }

        }
        return type;
    }
}
