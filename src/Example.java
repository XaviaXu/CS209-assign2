
import org.apache.commons.csv.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Example {
    public static void main(String[] args) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final String[] HEADERS = {"ID", "Name", "Gender", "Department", "GPA", "Credit Earned", "Birthday"};

        // Demo: How to read a csv file
        Reader in = new FileReader("example.csv");
        CSVParser parser = CSVFormat.RFC4180.withHeader(HEADERS)
                .withFirstRecordAsHeader().withQuoteMode(QuoteMode.ALL).parse(in);
        List<CSVRecord> records = parser.getRecords();
        for (CSVRecord record : records) {
            System.out.println(record.get("ID"));
            System.out.println(record.get("Name"));
            System.out.println(sdf.parse(record.get("Birthday")));
            System.out.println(Integer.parseInt(record.get("Credit Earned")));
        }

        // Demo: How to write a csv file
        FileWriter fw = new FileWriter("test.csv");
        CSVPrinter printer = new CSVPrinter(fw, CSVFormat.RFC4180.withHeader(HEADERS).withQuoteMode(QuoteMode.ALL));
        printer.printRecord("11111111", "student0", "MALE", "unknown", "0.0", "0", sdf.format(new Date()));
        fw.close();

    }
}
