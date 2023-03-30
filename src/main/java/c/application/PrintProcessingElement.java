/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package c.application;

import java.util.List;

/**
 *
 * @author 98910
 */
class PrintProcessingElement {
    public List<Entry> process(List<Entry> input) {
        input.forEach(entry -> {
            if(entry instanceof LocalEntry){
                System.out.println("Name: " + entry.getName());
                System.out.println("Length: " + entry.getLength());
                System.out.println("Path" + entry.toString());
            } else if(entry instanceof RemoteEntry) {
                System.out.println("Name: " + entry.getName());
                System.out.println("Length: " + entry.getLength());
                System.out.println("entryID: " + entry.getEntryID());
            }
        });

        return input;
    }
}