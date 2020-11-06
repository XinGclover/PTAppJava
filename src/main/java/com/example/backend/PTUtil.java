package com.example.backend;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class PTUtil {
    public List<String> callBashCommand(String command){
        List<String> result= new ArrayList<>();
        try {
//            Process proc= rt.exec("pytornado -v --run /Users/xingao/pytornado/settings/template.json");
            Process proc= Runtime.getRuntime().exec(command);
            BufferedReader reader= new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            while ((line=reader.readLine())!=null){
               result.add(line);
            }
            int exitVal=proc.waitFor();
            if(exitVal==0){
                System.out.println("Success!"+"\n"+result);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
       return result;
    }


}
