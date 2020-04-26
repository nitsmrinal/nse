package nsedownload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;

public class BhavCopy {
    public static void main(String[] args) {
        BhavCopy bc = new BhavCopy();
        System.out.println("Enter type M (Monthly) or D (Daily)");
        Scanner sc = new Scanner(System.in);
        String frequency = sc.next();
        System.out.println("Enter Start Date DDMMYYYY format");
        String startDate = sc.next();
        System.out.println("Enter duration");
        int duration = sc.nextInt();
        List<String> downloadStr = bc.populatedownloadString(startDate, duration);
//		System.out.println(downloadStr);

        try {
            Properties prop = new Properties();
            InputStream is = bc.getClass().getClassLoader().getResourceAsStream("application.properties");
            prop.load(is);
            String staticUrl = prop.getProperty("staticUrl");
            String downloadPath = prop.getProperty("downloadLocation");

            for (String url : downloadStr) {
                try {

                    Path path = Paths.get(url);
                    String downloadedFileName = url.substring(url.lastIndexOf("/"));
                    String zipDownloadPathStr = downloadPath+"/zip/"+downloadedFileName;
					Path zipDownloadPath = Paths.get(zipDownloadPathStr);
                    String fileName = path.getFileName().toString();
                    System.out.println("fileName"+fileName);
                    ReadableByteChannel rbc = Channels.newChannel((new URL(staticUrl + url)).openStream());
                    FileOutputStream fos = new FileOutputStream(new File(downloadPath +  "/zip/" + fileName));
                    fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                    Zip.unzip( zipDownloadPathStr, downloadPath + "/");
                    // Load the unzipped file to DB;
                    //	DatabaseLoader.loadReportToDB(downloadPath+"/"+fileName.replace(".zip", ""));
                    String dbFileName = zipDownloadPathStr.replace("zip/","");
                    DatabaseLoader.sqlLoad(dbFileName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<String> populatedownloadString(String fromDate, int numOfDays) {

        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
        Date dt;
        String year = "";
        String month = "";
        String day = "";
        List<String> formattedDownloadString = new ArrayList<String>();

        try {
            dt = sdf.parse(fromDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(dt);

            for (int i = 0; i < numOfDays; i++) {
                cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 1);
                SimpleDateFormat df = new SimpleDateFormat("ddMMMyyyy");
                String newDate = df.format(cal.getTime()).toUpperCase();
                day = newDate.substring(0, 2);
                month = newDate.substring(2, 5);
                year = newDate.substring(5, 9);
                formattedDownloadString.add("/" + year + "/" + month + "/" + "fo" + newDate + "bhav.csv.zip");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return formattedDownloadString;
    }


    String getMonthForInt(int m) {
        String month = "invalid";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (m >= 0 && m <= 11) {
            month = months[m].toUpperCase();
        }
        return month;
    }

}
