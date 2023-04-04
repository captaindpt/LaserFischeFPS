/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package c.application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 98910
 */
class SplitProcessingElement implements ProcessingElement {

    static public List<Entry> process(List<Entry> entries, int lines) throws IOException {
        List<Entry> result = new ArrayList<Entry>();
        ArrayList<String> paths = new ArrayList<>();
        for (Entry e : entries) {
            if (e instanceof RemoteEntry) {
                RemoteEntry e1 = (RemoteEntry) e;
                paths.add(RemoteEntry.DownloadFile(e1.getRepoId(), e1.getEntryID()));
            } else {
                LocalEntry e1 = (LocalEntry) e;
                paths.add(e1.getPath());
            }
        }
        ArrayList<File> pathObjects = new ArrayList<>();
        for (String p : paths) {
            pathObjects.add(new File(p));
        }
        
        for (File entry : pathObjects) {
            if(entry.isDirectory())
                continue;
            BufferedReader reader = new BufferedReader(new FileReader(entry));
            String line;
            int lineNumber = 0;
            int partNumber = 1;
            File outputFile = null;
            BufferedWriter writer = null;
            while ((line = reader.readLine()) != null) {
                if (lineNumber % lines == 0) {
                    // Start a new output file
                    if (writer != null) {
                        writer.close();
                    }
                    String name = entry.getName();
                    String fileName = name.substring(0, name.lastIndexOf('.')) + ".part" + partNumber + ".txt";
                    outputFile = new File(entry.getParentFile(), fileName);
                    writer = new BufferedWriter(new FileWriter(outputFile));
                    LocalEntry out1 = new LocalEntry(outputFile.getAbsolutePath());
                    System.out.println("creating... " + outputFile.getAbsolutePath());
                    result.add(out1);
                    partNumber++;
                }
                writer.write(line);
                writer.newLine();
                lineNumber++;
            }
            reader.close();
            if (writer != null) {
                writer.close();
            }
        }
        return result;
    }
    
//    public static void main(String[] args) throws IOException {
//        process(LocalEntry.GenerateListOfEntry("C:\\Users\\98910\\Documents\\NetBeansProjects\\Application\\dir3"), 3);
//    }
}


