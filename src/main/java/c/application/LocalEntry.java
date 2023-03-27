/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package c.application;


import java.io.*;
import java.nio.file.*;

/**
 *
 * @author 98910
 */
class LocalEntry implements Entry {
    private Path path;

    public LocalEntry(String path) {
        this.path = Paths.get(path);
    }

    public String getName() {
        return path.getFileName().toString();
    }

    public long getLength() {
        try {
            return Files.size(path);
        } catch (IOException e) {
            return 0;
        }
    }

    public boolean isDirectory() {
        return Files.isDirectory(path);
    }
    
    public String getPath() {
        return path.toString();
    }
}
