/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package c.application;

import com.laserfiche.api.client.model.AccessKey;
import com.laserfiche.repository.api.RepositoryApiClient;
import com.laserfiche.repository.api.RepositoryApiClientImpl;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader; 
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author 98910
 */

  class FilterProcessingElement {
      //Operator enum used for lengthFilter
       public enum Operator{
            EQ, NEQ, GT, GTE, LT, LTE
        }
    public static List<Entry> nameFilter(List<Entry> entries, String key) {
        String servicePrincipalKey = "9_YVh_11HPvRIrThlsE7";
        String accessKeyBase64 = "ewoJImN1c3RvbWVySWQiOiAiMTQwMTM1OTIzOCIsCgkiY2xpZW50SWQiOiAiYzI3NWE0NTktNTg5My00M2JmLTk4NTktNzVjM2NjN2Q0NGIyIiwKCSJkb21haW4iOiAibGFzZXJmaWNoZS5jYSIsCgkiandrIjogewoJCSJrdHkiOiAiRUMiLAoJCSJjcnYiOiAiUC0yNTYiLAoJCSJ1c2UiOiAic2lnIiwKCQkia2lkIjogIjdfcW0wVE1wRl9PeGl3TF90V2Z4ZUZiYVZmRTg5d3RsVEtHNUpQb1FSU0kiLAoJCSJ4IjogIkNnVUpKN2Zzcmx0MEM0R3JGWHFIbDRhVm9NeU9vdG5Ud1JtOXBXeDExSlkiLAoJCSJ5IjogInBESlZfNzZWZ1AyU0d5Y2RmRXFKX3J5alpTZ1Z5THljZkdFaDcyV2ZmVUUiLAoJCSJkIjogIkF5UXM5eGZvLTBIS0J2bElnUTltZ09sOWo3cXBXMHN4UC1xU3kxV2V0Y1UiLAoJCSJpYXQiOiAxNjc3Mjk3NDUwCgl9Cn0=";
                AccessKey accessKey = AccessKey.createFromBase64EncodedAccessKey(accessKeyBase64);
  RepositoryApiClient client = RepositoryApiClientImpl.createFromAccessKey(
                servicePrincipalKey, accessKey);
        List<Entry> filteredNames = new ArrayList<>();
        for(Entry entryy: entries) {
            if(entryy instanceof RemoteEntry){
            RemoteEntry entry = (RemoteEntry)entryy;
            if(entryy.getName().contains(key)){
            filteredNames.add(entry);
           }
        } else if (entryy instanceof LocalEntry){
            LocalEntry entry = (LocalEntry)entryy;
            if(entry.getName().contains(key)){
            filteredNames.add(entry);
           }
         }
      } 
        client.close();
        return filteredNames;
    }
   
     public static List<Entry> lengthFilter(List<Entry> entries, Long len, Operator operator) {
        String servicePrincipalKey = "9_YVh_11HPvRIrThlsE7";
        String accessKeyBase64 = "ewoJImN1c3RvbWVySWQiOiAiMTQwMTM1OTIzOCIsCgkiY2xpZW50SWQiOiAiYzI3NWE0NTktNTg5My00M2JmLTk4NTktNzVjM2NjN2Q0NGIyIiwKCSJkb21haW4iOiAibGFzZXJmaWNoZS5jYSIsCgkiandrIjogewoJCSJrdHkiOiAiRUMiLAoJCSJjcnYiOiAiUC0yNTYiLAoJCSJ1c2UiOiAic2lnIiwKCQkia2lkIjogIjdfcW0wVE1wRl9PeGl3TF90V2Z4ZUZiYVZmRTg5d3RsVEtHNUpQb1FSU0kiLAoJCSJ4IjogIkNnVUpKN2Zzcmx0MEM0R3JGWHFIbDRhVm9NeU9vdG5Ud1JtOXBXeDExSlkiLAoJCSJ5IjogInBESlZfNzZWZ1AyU0d5Y2RmRXFKX3J5alpTZ1Z5THljZkdFaDcyV2ZmVUUiLAoJCSJkIjogIkF5UXM5eGZvLTBIS0J2bElnUTltZ09sOWo3cXBXMHN4UC1xU3kxV2V0Y1UiLAoJCSJpYXQiOiAxNjc3Mjk3NDUwCgl9Cn0=";
                AccessKey accessKey = AccessKey.createFromBase64EncodedAccessKey(accessKeyBase64);
  RepositoryApiClient client = RepositoryApiClientImpl.createFromAccessKey(
                servicePrincipalKey, accessKey);
          
        List<String> filteredContent = new ArrayList<>();
        for(Entry entryy: entries){
            //Download Remote Entries
            if (entryy instanceof RemoteEntry){
                RemoteEntry entry = (RemoteEntry)entryy;
        int entryIdToDownload = entry.getEntryID();
        String repoID = entry.getRepoId();
        final String FILE_NAME = entryy.getName() + ".txt";
        Consumer<InputStream> consumer = inputStream -> {
            File exportedFile = new File(FILE_NAME);
            try (FileOutputStream outputStream = new FileOutputStream(exportedFile)) {
                byte[] buffer = new byte[1024];
                while (true) {
                    int length = inputStream.read(buffer);
                    if (length == -1) {
                        break;
                    }
                    outputStream.write(buffer, 0, length);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        client.getEntriesClient().exportDocument(repoID, entryIdToDownload, null, consumer).join();
        Path path = Paths.get(FILE_NAME);
        String pathstr = path.toAbsolutePath().toString();
        File file = new File(pathstr);
        Long fileLen = file.length();
            switch (operator) {
                case EQ:
                    if(fileLen == len){
                         filteredContent.add(file.getName());
                         System.out.println(file.getName() + " has a length = " + file.length() + " bytes");
                     }
                     break;
                 case NEQ: 
                     if (fileLen != len){
                         filteredContent.add(file.getName());
                         System.out.println(file.getName() + " has a length = " + file.length() + " bytes");
                     }
                     break;
                 case GT: 
                     if(fileLen > len) {
                        filteredContent.add(file.getName());
                        System.out.println(file.getName() + " has a length = " + file.length() + " bytes");
                     }
                     break;
                 case GTE: 
                     if(fileLen >= len){
                         filteredContent.add(file.getName());
                         System.out.println(file.getName() + " has a length = " + file.length() + " bytes");
                     }
                     break;
                 case LTE: 
                     if(fileLen <= len){
                         filteredContent.add(file.getName());
                         System.out.println(file.getName() + " has a length = " + file.length() + " bytes");
                     }
                     break;
                 case LT: 
                     if(fileLen < len){
                         filteredContent.add(file.getName());
                         System.out.println(file.getName() + " has a length = " + file.length() + " bytes"); 
                     }
                     break;
                 default: 
                     break;
              }
            
            } else if(entryy instanceof LocalEntry){
                LocalEntry entry = (LocalEntry)entryy;
                String FILE_NAME = entry.getName();
                Path path = Paths.get(FILE_NAME);
        String pathstr = path.toAbsolutePath().toString();
        File file = new File(pathstr);
        Long fileLen = file.length();
            switch (operator) {
                case EQ:
                    if(fileLen == len){
                         filteredContent.add(file.getName());
                         System.out.println(file.getName() + " has a length = " + file.length() + " bytes");
                     }
                     break;
                 case NEQ: 
                     if (fileLen != len){
                         filteredContent.add(file.getName());
                         System.out.println(file.getName() + " has a length = " + file.length() + " bytes");
                     }
                     break;
                 case GT: 
                     if(fileLen > len) {
                        filteredContent.add(file.getName());
                        System.out.println(file.getName() + " has a length = " + file.length() + " bytes");
                     }
                     break;
                 case GTE: 
                     if(fileLen >= len){
                         filteredContent.add(file.getName());
                         System.out.println(file.getName() + " has a length = " + file.length() + " bytes");
                     }
                     break;
                 case LTE: 
                     if(fileLen <= len){
                         filteredContent.add(file.getName());
                         System.out.println(file.getName() + " has a length = " + file.length() + " bytes");
                     }
                     break;
                 case LT: 
                     if(fileLen < len){
                         filteredContent.add(file.getName());
                         System.out.println(file.getName() +  " has a length = " + file.length() + " bytes"); 
                     }
                     break;
                 default: 
                     break;
              }
            }
        } 
        client.close();
        return null;
    }
    public static List<Entry> contentFilter(List<Entry> entries, String key) {
        String servicePrincipalKey = "9_YVh_11HPvRIrThlsE7";
        String accessKeyBase64 = "ewoJImN1c3RvbWVySWQiOiAiMTQwMTM1OTIzOCIsCgkiY2xpZW50SWQiOiAiYzI3NWE0NTktNTg5My00M2JmLTk4NTktNzVjM2NjN2Q0NGIyIiwKCSJkb21haW4iOiAibGFzZXJmaWNoZS5jYSIsCgkiandrIjogewoJCSJrdHkiOiAiRUMiLAoJCSJjcnYiOiAiUC0yNTYiLAoJCSJ1c2UiOiAic2lnIiwKCQkia2lkIjogIjdfcW0wVE1wRl9PeGl3TF90V2Z4ZUZiYVZmRTg5d3RsVEtHNUpQb1FSU0kiLAoJCSJ4IjogIkNnVUpKN2Zzcmx0MEM0R3JGWHFIbDRhVm9NeU9vdG5Ud1JtOXBXeDExSlkiLAoJCSJ5IjogInBESlZfNzZWZ1AyU0d5Y2RmRXFKX3J5alpTZ1Z5THljZkdFaDcyV2ZmVUUiLAoJCSJkIjogIkF5UXM5eGZvLTBIS0J2bElnUTltZ09sOWo3cXBXMHN4UC1xU3kxV2V0Y1UiLAoJCSJpYXQiOiAxNjc3Mjk3NDUwCgl9Cn0=";
		
                AccessKey accessKey = AccessKey.createFromBase64EncodedAccessKey(accessKeyBase64);
                RepositoryApiClient client = RepositoryApiClientImpl.createFromAccessKey(servicePrincipalKey, accessKey);
        // Download Remote Entries from Repo 
        List<String> filteredContent = new ArrayList<>();
        for(Entry entryy: entries){
        if (entryy instanceof RemoteEntry){
            RemoteEntry entry = (RemoteEntry)entryy;
        int entryIdToDownload = entry.getEntryID();
        String RepoId = entry.getRepoId(); 
        final String FILE_NAME = entryy.getName() + ".txt";
        Consumer<InputStream> consumer = inputStream -> {
            File exportedFile = new File(FILE_NAME);
            try (FileOutputStream outputStream = new FileOutputStream(exportedFile)) {
                byte[] buffer = new byte[1024];
                while (true) {
                    int length = inputStream.read(buffer);
                    if (length == -1) {
                        break;
                    }
                    outputStream.write(buffer, 0, length);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        client.getEntriesClient().exportDocument(RepoId, entryIdToDownload, null, consumer).join();
        Path path = Paths.get(FILE_NAME);
        String pathstr = path.toAbsolutePath().toString();
        File file = new File(pathstr);
            if(pathstr.contains(".txt")){
                if(containsKey(file, key)){
                   filteredContent.add(file.getName());
                   System.out.println(file.getName());
                }
            }
        } else if (entryy instanceof LocalEntry){
            LocalEntry entry = (LocalEntry)entryy;
            String FILE_NAME = entry.getName();
            Path path = Paths.get(FILE_NAME);
        String pathstr = path.toAbsolutePath().toString();
        File file = new File(pathstr);
            if(pathstr.contains(".txt")){
                if(containsKey(file, key)){
                   filteredContent.add(file.getName());
                   System.out.println(file.getName());
                }
            }
         }
       }
        client.close();
        return null; 
    }   
    public static List<Entry> countFilter(List<Entry> entries, String key, int min) {
        String servicePrincipalKey = "9_YVh_11HPvRIrThlsE7";
        String accessKeyBase64 = "ewoJImN1c3RvbWVySWQiOiAiMTQwMTM1OTIzOCIsCgkiY2xpZW50SWQiOiAiYzI3NWE0NTktNTg5My00M2JmLTk4NTktNzVjM2NjN2Q0NGIyIiwKCSJkb21haW4iOiAibGFzZXJmaWNoZS5jYSIsCgkiandrIjogewoJCSJrdHkiOiAiRUMiLAoJCSJjcnYiOiAiUC0yNTYiLAoJCSJ1c2UiOiAic2lnIiwKCQkia2lkIjogIjdfcW0wVE1wRl9PeGl3TF90V2Z4ZUZiYVZmRTg5d3RsVEtHNUpQb1FSU0kiLAoJCSJ4IjogIkNnVUpKN2Zzcmx0MEM0R3JGWHFIbDRhVm9NeU9vdG5Ud1JtOXBXeDExSlkiLAoJCSJ5IjogInBESlZfNzZWZ1AyU0d5Y2RmRXFKX3J5alpTZ1Z5THljZkdFaDcyV2ZmVUUiLAoJCSJkIjogIkF5UXM5eGZvLTBIS0J2bElnUTltZ09sOWo3cXBXMHN4UC1xU3kxV2V0Y1UiLAoJCSJpYXQiOiAxNjc3Mjk3NDUwCgl9Cn0=";
		
                AccessKey accessKey = AccessKey.createFromBase64EncodedAccessKey(accessKeyBase64);
  RepositoryApiClient client = RepositoryApiClientImpl.createFromAccessKey(
                servicePrincipalKey, accessKey);
        // Download a list of entries from Repo 
        List<String> filteredContent = new ArrayList<>();
        for(Entry entryy: entries){
           if(entryy instanceof RemoteEntry){
            RemoteEntry entry = (RemoteEntry)entryy;
        int entryIdToDownload = entry.getEntryID() ;
        String RepoId = entry.getRepoId();
        final String FILE_NAME = entryy.getName() + ".txt";
        Consumer<InputStream> consumer = inputStream -> {
            File exportedFile = new File(FILE_NAME);
            try (FileOutputStream outputStream = new FileOutputStream(exportedFile)) {
                byte[] buffer = new byte[1024];
                while (true) {
                    int length = inputStream.read(buffer);
                    if (length == -1) {
                        break;
                    }
                    outputStream.write(buffer, 0, length);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        client.getEntriesClient().exportDocument(RepoId, entryIdToDownload, null, consumer).join();
        Path path = Paths.get(FILE_NAME);
        String pathstr = path.toAbsolutePath().toString();
        File file = new File(pathstr);
            if(pathstr.contains(".txt")){
                if(ModifiedcontainsKey(file, key, min)){
                   filteredContent.add(file.getName());
                   System.out.println(file.getName());
                    }
                }
            } else if (entryy instanceof LocalEntry){
                LocalEntry entry = (LocalEntry)entryy;
                String FILE_NAME = entry.getName();
                Path path = Paths.get(FILE_NAME);
        String pathstr = path.toAbsolutePath().toString();
        File file = new File(pathstr);
            if(pathstr.contains(".txt")){
                if(ModifiedcontainsKey(file, key, min)){
                   filteredContent.add(file.getName());
                   System.out.println(file.getName());
                    }
                }
        } 
     }
        client.close();
        return null;
    }  
    //To check if each line of each file contains key for content filter
            public static boolean containsKey(File FILE_NAME, String key){
                try(BufferedReader reader = new BufferedReader( new FileReader(FILE_NAME))){
                    String line;
                    while((line = reader.readLine()) != null){
                        if (line.contains(key)){
                            return true;
                        }
                    }
                    return false;
                } catch(IOException e){
                    return false;
          }
    }
  //ModifiedcontainsKey is similar to containsKey with small changes for countFilter
       public static boolean ModifiedcontainsKey(File FILE_NAME, String key, int min){
                try(BufferedReader reader = new BufferedReader( new FileReader(FILE_NAME))){
                    String line;
                    int count = 0;
                    while((line = reader.readLine()) != null){
                        if (line.contains(key)){
                            count++;
                        }
                        if (count>=min){
                            return true;
                            }
                        }
                    return false;
                } catch(IOException e){
                    return false;
          }

    }
}


