package com.es.records;

import com.es.records.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class RecordMerger {

    public static final String DEBUG = "debug";

    public static final String FILENAME_COMBINED = "combined.csv";

    public static final String DATA_DIR = "data/";


    /**
     * Entry point of this test.
     *
     * @param args command line arguments: first.html and second.csv.
     * @throws Exception bad things had happened.
     */
    public static void main(/*final*/ String[] args) throws Exception {

        args = new String[] {
                //"third.csv",
                //"first.html",
//                "union.csv2",
//                "union2.csv2",
//                "union3.csv2",
//                "union4.csv2",
//                "programmatic1.csv",
//                "programmatic2.csv"
                "first-ext.htmls",
                "second-ext.csv"
         };

        if (args.length == 0) {
            System.err.println("Usage: java RecordMerger file1 [ file2 [...] ]");
            System.exit(1);
        }



//        final Table t1 = new DataTable("ID","A","B","C");
//        final Table t2 = new DataTable("ID","d","e","f");
//        IntStream.rangeClosed(0,9999).forEach(v-> {
//            t1.insert(v,"a"+v, "b"+v, "c"+v);
//            t2.insert(9999-v,"d"+v, "e"+v, "f"+v);
//        });
//
//
//        Tables.save(t1,"data/massive1.csv");
//        Tables.save(t2,"data/massive2.csv");
//
//
//        System.exit(0);



        // your code starts here.
        try {

            if (!System.getProperties().containsKey(DEBUG))
                System.out.println("TableMerger: Run with -Ddebug to print table & rows\n");

            List<Optional<? extends Table>> tables =
                    Arrays.stream(args).map(file -> DATA_DIR + file).map(Tables::newTable).collect(Collectors.toList());

            if (tables.size() == 0) {
                System.err.println("No table read");
            }

            //skip over possibly empty tables...
            int index = 0;
            while (!tables.get(index).isPresent()) {
                index++;
                if (index==tables.size())
                    throw new Exception("No supported tables found.");
            }

            Table combined = tables.get(index).get();
            if (System.getProperties().containsKey(DEBUG))
                System.out.println(combined);

            for (int i = index; i < args.length; i++) {
                if (tables.get(i).isPresent()) {
                    combined = combined.merge(tables.get(i).get());
                    if (System.getProperties().containsKey(DEBUG))
                        System.out.println(tables.get(i).get());
                }
            }

            if (System.getProperties().containsKey(DEBUG))
                System.out.println(combined);

            Tables.save(combined, DATA_DIR + FILENAME_COMBINED);
        } catch (Exception e) {
            System.out.print("Error while merging" + e.getMessage());
        }
    }

}


