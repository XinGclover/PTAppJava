package com.example.backend.utils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


@Component
public class PTUtil {

    public List<String> callBashCommand(String command){
        List<String> result= new ArrayList<>();
        try {
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


    public File findFile(String folderPath){

            File dir = new File(folderPath);

            FilenameFilter filter = new MyFileFilter();

            File[] files = dir.listFiles(filter);
            List<File> conditionFiles=new ArrayList<>();
            for(File f : files){
                if(folderPath.contains("aircraft")){
                    if(f.getName().contains("template_aircraft")){
                        conditionFiles.add(f);
                    }

                }else if(folderPath.contains("state")){
                    if(f.getName().contains("template")){
                        conditionFiles.add(f);
                    }
                }

        }
            if(conditionFiles.size()>0){
                return conditionFiles.get(0);
            }
            else
                return null;

    }

    public Object parseJson(File file){
        JSONParser jsonParser = new JSONParser();
       Object obj = new Object();
        try (FileReader reader = new FileReader(file))
        {
            //Read JSON file
            obj = jsonParser.parse(reader);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return obj;
    }



  /* public void saveSettingsFile(State state, String statePath) throws IOException {
        Gson gson= new GsonBuilder().serializeNulls()
                .setPrettyPrinting()
                .create();
        FileWriter fileWriter= new FileWriter(statePath);
        gson.toJson(state,fileWriter);
        fileWriter.flush();
        fileWriter.close();

   }*/


}
