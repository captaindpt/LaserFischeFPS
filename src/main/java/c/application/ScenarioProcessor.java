/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package c.application;

import com.nimbusds.jose.shaded.json.parser.JSONParser;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import kong.unirest.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ScenarioProcessor {
    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        
        try {     
            Object obj = parser.parse(new FileReader(args[0]));
            JSONArray jsonArray = (JSONArray) obj;
            
            JSONObject name = (JSONObject)jsonArray.get(0);
            
        }
    }
}