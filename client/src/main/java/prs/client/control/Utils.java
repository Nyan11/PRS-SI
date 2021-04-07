package prs.client.control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;



public class Utils {
	
	public static void decodeToImage(String base64, File file) throws IOException {
		byte[] imageBytes;
		imageBytes = Base64.getDecoder().decode(base64);
		extracted(file).write(imageBytes);
	}

	private static FileOutputStream extracted(File file) throws FileNotFoundException {
		return new FileOutputStream(file);
	}
}
