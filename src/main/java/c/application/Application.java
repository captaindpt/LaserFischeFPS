/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package c.application;



import static c.application.FilterProcessingElement.Operator.GTE;
import static c.application.FilterProcessingElement.Operator.LT;
import static c.application.FilterProcessingElement.Operator.LTE;
import static c.application.FilterProcessingElement.Operator.EQ;
import static c.application.FilterProcessingElement.Operator.GT;
import static c.application.FilterProcessingElement.Operator.NEQ;
import com.laserfiche.api.client.model.AccessKey;
import com.laserfiche.repository.api.RepositoryApiClient;
import com.laserfiche.repository.api.RepositoryApiClientImpl;
import com.laserfiche.repository.api.clients.impl.model.ODataValueContextOfIListOfEntry;
import com.laserfiche.repository.api.clients.impl.model.Entry;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author 98910
 */
public class Application {

    public static void main(String[] args) { 
       String servicePrincipalKey = "9_YVh_11HPvRIrThlsE7";
        String accessKeyBase64 = "ewoJImN1c3RvbWVySWQiOiAiMTQwMTM1OTIzOCIsCgkiY2xpZW50SWQiOiAiYzI3NWE0NTktNTg5My00M2JmLTk4NTktNzVjM2NjN2Q0NGIyIiwKCSJkb21haW4iOiAibGFzZXJmaWNoZS5jYSIsCgkiandrIjogewoJCSJrdHkiOiAiRUMiLAoJCSJjcnYiOiAiUC0yNTYiLAoJCSJ1c2UiOiAic2lnIiwKCQkia2lkIjogIjdfcW0wVE1wRl9PeGl3TF90V2Z4ZUZiYVZmRTg5d3RsVEtHNUpQb1FSU0kiLAoJCSJ4IjogIkNnVUpKN2Zzcmx0MEM0R3JGWHFIbDRhVm9NeU9vdG5Ud1JtOXBXeDExSlkiLAoJCSJ5IjogInBESlZfNzZWZ1AyU0d5Y2RmRXFKX3J5alpTZ1Z5THljZkdFaDcyV2ZmVUUiLAoJCSJkIjogIkF5UXM5eGZvLTBIS0J2bElnUTltZ09sOWo3cXBXMHN4UC1xU3kxV2V0Y1UiLAoJCSJpYXQiOiAxNjc3Mjk3NDUwCgl9Cn0=";
		String repositoryId = "r-0001d410ba56";
        AccessKey accessKey = AccessKey.createFromBase64EncodedAccessKey(accessKeyBase64);

        RepositoryApiClient client = RepositoryApiClientImpl.createFromAccessKey(
                servicePrincipalKey, accessKey);

        // Get information about the ROOT entry, i.e. entry with ID=1
        int rootEntryId = 15;
        com.laserfiche.repository.api.clients.impl.model.Entry entry = client.getEntriesClient()
                .getEntry(repositoryId, rootEntryId, null).join();

        System.out.println(String.format("Entry ID: %d, Name: %s, EntryType: %s, FullPath: %s",entry.getId(), entry.getName(), entry.getEntryType(), entry.getFullPath()));

        // Get information about the child entries of the Root entry
        ODataValueContextOfIListOfEntry result = client
                .getEntriesClient()
                .getEntryListing(repositoryId, rootEntryId, true, null, null, null, null, null, "name", null, null, null).join();
        
        //nameFilter testing **Functional**
        List<Entry> entries2 = result.getValue();
        //List<Entry> filteredNames = FilterProcessingElement.nameFilter(entries2, "900k");
        //for (Entry entry_: filteredNames){
        //    System.out.println(entry_.getName());
        //}  
        
        //lengthFilter testing
        //Cannot figure out how to get Remote Entry length
        //List<Entry> filteredNames4 = FilterProcessingElement.lengthFilter(entries2, 1, GT);
        //for (Entry entry_: filteredNames4){
        //    System.out.println("Name: " + entry_.getName() + ", Size: " + entry_);
        //};
        
        //contentFilter testing, **Functional**
        //FilterProcessingElement.contentFilter(entries2, "Tadanac, Kootenay Boundary, British Columbia, BC");
        
        
        //countFilter testing, **Functional**
        FilterProcessingElement.countFilter(entries2, "ON", 6000);
        
        //Test DownloadFile method
        //Application app = new Application();
        //System.out.println("Path: " + app.DownloadFile("r-0001d410ba56", 7));
        //client.close();
    }  
    //Method to DownloadFile from Laserfiche Repo, using repoId and entryId
    public String DownloadFile(String repoId, int entryId){
    String servicePrincipalKey = "9_YVh_11HPvRIrThlsE7";
        String accessKeyBase64 = "ewoJImN1c3RvbWVySWQiOiAiMTQwMTM1OTIzOCIsCgkiY2xpZW50SWQiOiAiYzI3NWE0NTktNTg5My00M2JmLTk4NTktNzVjM2NjN2Q0NGIyIiwKCSJkb21haW4iOiAibGFzZXJmaWNoZS5jYSIsCgkiandrIjogewoJCSJrdHkiOiAiRUMiLAoJCSJjcnYiOiAiUC0yNTYiLAoJCSJ1c2UiOiAic2lnIiwKCQkia2lkIjogIjdfcW0wVE1wRl9PeGl3TF90V2Z4ZUZiYVZmRTg5d3RsVEtHNUpQb1FSU0kiLAoJCSJ4IjogIkNnVUpKN2Zzcmx0MEM0R3JGWHFIbDRhVm9NeU9vdG5Ud1JtOXBXeDExSlkiLAoJCSJ5IjogInBESlZfNzZWZ1AyU0d5Y2RmRXFKX3J5alpTZ1Z5THljZkdFaDcyV2ZmVUUiLAoJCSJkIjogIkF5UXM5eGZvLTBIS0J2bElnUTltZ09sOWo3cXBXMHN4UC1xU3kxV2V0Y1UiLAoJCSJpYXQiOiAxNjc3Mjk3NDUwCgl9Cn0=";
        repoId = "r-0001d410ba56";
        AccessKey accessKey = AccessKey.createFromBase64EncodedAccessKey(accessKeyBase64);
    RepositoryApiClient client = RepositoryApiClientImpl.createFromAccessKey(
                servicePrincipalKey, accessKey);  
    // Download a list of entries from Repo 
      com.laserfiche.repository.api.clients.impl.model.Entry entry = client.getEntriesClient()
                .getEntry(repoId, entryId, null).join();
        final String FILE_NAME = entry.getName() + ".txt";
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
        client.getEntriesClient()
                .exportDocument(repoId, entryId, null, consumer)
                .join();
        Path path = Paths.get(FILE_NAME);
           return path.toAbsolutePath().toString();   
    }   
}
