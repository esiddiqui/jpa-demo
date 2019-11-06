package com.es.records.domain;


import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.File;
import java.io.FileReader;

import java.io.FileWriter;
import java.util.Optional;

public class CsvTables {


    public static DataTable read(File f) {
        try {
            CSVReader reader = new CSVReader(new FileReader(f));

            //Read Header
            Header header = new Header(reader.readNext());
            final DataTable table = new DataTable(header);

            //Read Rows
            String[] dataRow;
            while ((dataRow = reader.readNext()) != null) {
                Row row = table.createRow().setValues((Object[])dataRow);
                table.insert(row);
            }
            System.out.println(String.format("%d rows read",(table).getRows().size()));
            return table;

        } catch (Exception e) {
            System.err.println(e.getMessage());
            Optional.empty();
        }
        return null;
    }


    public static void write(DataTable table, File f) {

        //delete file if already exits
        if (f.exists())
            f.delete();

        try {
            CSVWriter writer = new CSVWriter(new FileWriter(f));
            //write header
            String[] header = table.header.getColumnNames();
            writer.writeNext(header);
            //write data
            for (int i=0; i<table.rows.size(); i++) {
                writer.writeNext(table.getRow(i).getData());
            }
            writer.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }
}


