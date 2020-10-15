package webscraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import java.io.FileWriter;

public class WebScraper {
	
	private final String[] URL = {
		"https://s.cafef.vn/Lich-su-giao-dich-VNINDEX-1.chn",
		"https://s.cafef.vn/Lich-su-giao-dich-VN30INDEX-1.chn",
		"https://s.cafef.vn/Lich-su-giao-dich-VN100-INDEX-1.chn",
	};
	
	public WebScraper() {
		//...
	}
	
	public void scrapeToCSVFile() {
		// Delimiter used in CSV file
		final String FILE_INPUT_NAMES[] = {
			"input/VN-INDEX.csv",
			"input/VN30-INDEX.csv",
			"input/VN100-INDEX.csv",
		};
		
		final String COMMA_DELIMITER = ",";
		final String NEW_LINE_SEPARATOR = "\n";

		FileWriter fileWriter = null;

		for (int k = 0; k < FILE_INPUT_NAMES.length; k++) {
			try {
				fileWriter = new FileWriter(FILE_INPUT_NAMES[k]);

				try {
					Document document = Jsoup.connect(URL[k]).get();
					Element table = document.getElementById("GirdTable2");
					Elements rows = table.getElementsByTag("tr");

					for (int i = 2; i < rows.size(); i++) {
						Elements tds = rows.get(i).getElementsByTag("td");
						for (int j = 0; j < tds.size() - 3; j++) {
							if (j == 3) continue;
							String text = tds.get(j).text();
							text = text.replaceAll(",", "");
							fileWriter.append(text);
							fileWriter.append(COMMA_DELIMITER);
						}
						fileWriter.append(NEW_LINE_SEPARATOR);
					}
			    } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (Exception e) {
				System.out.println("Error in CsvFileWriter !!!");
				e.printStackTrace();
			} finally {
				try {
					fileWriter.flush();
					fileWriter.close();
				} catch (IOException e) {
					System.out.println("Error while flushing/closing fileWriter !!!");
					e.printStackTrace();
				}
			}
		}
	}
	
}
//file.setReadOnly();
