package nsedownload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Zip {

	public static void unzip(String pathToZip, String pathToUnzip) {
		byte[] buffer = new byte[1024];
		ZipInputStream zis;
		try {
			zis = new ZipInputStream(new FileInputStream(pathToZip));
			ZipEntry zipEntry = zis.getNextEntry();
			while (zipEntry != null && zipEntry.getName().endsWith(".csv")) {
				String fileName = zipEntry.getName();
				File newFile = new File(pathToUnzip + fileName);

				FileOutputStream fos = new FileOutputStream(newFile);
				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fos.close();
				zipEntry = zis.getNextEntry();
			}
			zis.closeEntry();
			zis.close();
			System.out.println("file__"+pathToZip);
			File f = new File(pathToZip);
			Boolean checkDeletion = f.delete();
			System.out.println(checkDeletion);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}