/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package c.application;

import c.application.FilterProcessingElement.Operator;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



public class ScenarioProcessor {
    public static void ParseAndExecute(String jsonFilePath) throws FileNotFoundException, ParseException {
//        if (args.length == 0) {
//            System.out.println("Please provide the JSON file path as the first command line argument.");
//            System.exit(1);
//        }

//        String jsonFilePath = args[0];
//        String jsonFilePath = "C:\\Users\\98910\\Downloads\\Test Scenario.json";
        try (FileReader reader = new FileReader(jsonFilePath)) {
            JSONParser jsonParser = new JSONParser();
            JSONObject scenario = (JSONObject) jsonParser.parse(reader);

            System.out.println("name: " + scenario.get("name"));
            JSONArray proc_els = (JSONArray) scenario.get("processing_elements");
            for(Object p : proc_els) {
                JSONObject proc_element = (JSONObject) p;
                String type = (String)proc_element.get("type");
                JSONArray inp_entrs = (JSONArray)proc_element.get("input_entries");
                List<Entry> entries = new ArrayList<Entry>();
                for(Object e : inp_entrs) {
                    JSONObject entry = (JSONObject) e;
                    if(entry.get("type").equals("local")){
                        entries.add(new LocalEntry((String) entry.get("path")));
                    } else if(entry.get("type").equals("remote")) {
                        entries.add(new RemoteEntry((String)entry.get("repositoryId"), Integer.parseInt((String)entry.get("entryId"))));
                    }
                }
                JSONArray params0 = (JSONArray)proc_element.get("parameters");
                List<JSONObject> params = new ArrayList<JSONObject>();
                for(int i =0; i<params0.size(); i++){
                    params.add((JSONObject)params0.get(i));
                }
                switch (type){
                    case "Name Filter" -> {
                        String Key = "";
                        for(JSONObject q : params){
                            if(q.get("name").equals("Key")){
                                Key = (String)q.get("value");
                            }
                        }
                        FilterProcessingElement.nameFilter(entries, Key);
                    }
                    case "Length Filter" -> {
                        String Operator1 = "";
                        long Length = 0;
                        for(JSONObject q : params){
                            if(q.get("name").equals("Length")){
                                Length = Long.parseLong((String)q.get("value"));
                            }
                            if(q.get("name").equals("Operator")){
                                Operator1 = (String)q.get("value");
                            }
                        }
                        FilterProcessingElement.lengthFilter(entries, Length, Operator.valueOf(Operator1));
                    }
                    case "Content Filter" -> {
                        String Key1 = "";
                        for(JSONObject q : params){
                            if(q.get("name").equals("Key")){
                                Key1 = (String)q.get("value");
                            }
                        }
                        FilterProcessingElement.contentFilter(entries, Key1);
                    }
                    case "Count Filter" -> {
                        String Key2 = "";
                        int min = 0;
                        for(JSONObject q : params){
                            if(q.get("name").equals("Key")){
                                Key2 = (String)q.get("value");
                            }
                            if(q.get("name").equals("Min")){
                                min = Integer.parseInt((String)q.get("value"));
                            }
                        }
                        FilterProcessingElement.countFilter(entries, Key2, min);
                    }
                    case "Split" -> {
                        int Lines = 0;
                        for(JSONObject q : params){
                            if(q.get("name").equals("Lines")){
                                Lines = Integer.parseInt((String)q.get("value"));
                            }
                        }
                        SplitProcessingElement.process(entries, Lines);
                    }
                    case "List" -> {
                        int max = 0;
                        for(JSONObject q : params){
                            if(q.get("name").equals("Max")){
                                max = Integer.parseInt((String)q.get("value"));
                            }
                        }
                        ListProcessingElement.process(entries, max);
                    }
                    case "Rename" -> {
                        String Suffix = "";
                        for(JSONObject q : params){
                            if(q.get("name").equals("Suffix")){
                                Suffix = (String)q.get("value");
                            }
                        }
                        RenameProcessingElement.process(entries, Suffix);
                    }
                    default -> PrintProcessingElement.process(entries);
                }
            }
            System.out.println(proc_els.get(1).getClass().getName());
        } catch (IOException | ParseException e) {
            System.out.println("Error parsing JSON file:");
            e.printStackTrace();
        }

    }
}