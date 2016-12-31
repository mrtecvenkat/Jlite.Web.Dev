package org.jlite.core.util;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.StandardCopyOption;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;

public class AutoFileUtil {
	
	public void doCopyFile(String origin, String destination) throws IOException {
        Path FROM = Paths.get(origin);
        Path TO = Paths.get(destination);
        CopyOption[] options = new CopyOption[]{
          StandardCopyOption.REPLACE_EXISTING,
          StandardCopyOption.COPY_ATTRIBUTES
        }; 
        Files.copy(FROM, TO, options);
    }

}