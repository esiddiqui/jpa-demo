package com.es.records.domain;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import javax.swing.text.html.CSS;
import java.io.File;
import java.io.IOException;


public class HtmlTables {

    private static String CSS_SELECTOR = "table#directory";

    private static String CHARSET = "UTF-8";

    public static DataTable read(File f) {

        try {
            Document doc = Jsoup.parse(f,CHARSET);
            Elements ele = doc.select(CSS_SELECTOR);

            if (ele.size() != 1)
                throw new RuntimeException("A table element with id=directory expected.");
            else {

                //load table header
                Elements headers = ele.select("tr th");


                Header header = new Header(headers.stream().map(e->e.text()).toArray(String[]::new));
                final DataTable table = new DataTable(header);

                //load table data
                Elements trs = ele.select("tr");
                trs.remove(0);
                trs.stream().forEach(tr->{
                    int counter =0;
                    Elements tds = tr.select("td");
                    if (tds.size()!=table.header.size()) {
                        System.err.println(String.format(
                                "SKIPPING! - Number of columns mismatch with Row definitions, expected %d, found %d",table.header.size(), tds.size()));
                    } else {
                        Row row = table.createRow(); //create new row from header definition;
                        for (Element td: tds) {
                            row.setValueAt(counter++, td.text());
                        }
                        table.insert(row);
                    }
                });
                System.out.println(String.format("%d rows read",table.getRows().size()));
                return table;
            }
        } catch (IOException ioe) {
            System.err.print(String.format("Error while loading %s",f.getName()));
            throw new RuntimeException(ioe);
        } catch (RuntimeException re) {
            throw re;
        }
    }


    public static void write(DataTable table, File f) {
        //write to disk
    }


}
