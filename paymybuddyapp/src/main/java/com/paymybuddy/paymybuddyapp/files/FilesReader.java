package com.paymybuddy.paymybuddyapp.files;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class FilesReader {
	
	public static String getDBParameter(String parameter) {
		
		try{
			Path path = FileSystems.getDefault().getPath("src/main/resources", "DBConfig.txt");
			BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
			
            try{
            	String line = reader.readLine();
            	
				// Loop on all file
				while (line != null) {

					if (line.contains(parameter)) {
						
						return line.substring(parameter.length() + 1);
					}
					line = reader.readLine();
				}
            }finally{
                reader.close();
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
		return null;
	}
	
}
