/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package c.application;


import java.io.FileNotFoundException;
import org.json.simple.parser.ParseException;



/**
 *
 * @author 98910
 */
public class Application {


    public static void main(String[] args) throws FileNotFoundException, ParseException {
        System.out.println("Hello World!");

        //enter the path to the json scenario below:
        ScenarioProcessor.ParseAndExecute("C:\\Users\\98910\\Downloads\\Test Scenario.json");

    }

}
