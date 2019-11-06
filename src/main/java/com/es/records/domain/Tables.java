package com.es.records.domain;

import com.google.common.base.Preconditions;
import org.apache.commons.io.FilenameUtils;

import javax.swing.text.html.Option;
import java.io.File;
import java.util.Optional;


public class Tables {

    enum FileTypes {

        HTML("html"), CSV("csv");

        FileTypes(String extension) {
            this.extension = extension;
        };

        private String extension;

        public String getExtension() {
            return this.extension;
        }
    }


    public static Optional<? extends Table> newTable(String filename) {

        Preconditions.checkNotNull(filename,"Filename is null");
        Preconditions.checkArgument(filename.trim().length()>0,"Filename can't be blank");
        File file = new File( filename);
        Preconditions.checkState(file.exists(),"File %s does not exist",filename);
        return Tables.newTable(file);
    }



    public static Optional<? extends Table> newTable(File file) {
        System.out.println(String.format("Reading table from %s",file.getAbsoluteFile()));
        if (FilenameUtils.isExtension(file.getName(), Tables.FileTypes.HTML.getExtension())) {
            DataTable t = HtmlTables.read(file);
            return Optional.of(t);
        } else if (FilenameUtils.isExtension(file.getName(), Tables.FileTypes.CSV.getExtension()))
            return Optional.of(CsvTables.read(file));
        else {
            System.out.println(String.format("%s: File format not supported at this time ",file.getName()));
            return Optional.empty();
        }
    }


    public static void save(Table table, String filename) {

        Preconditions.checkNotNull(table, "Table cannot be null");
        Preconditions.checkNotNull(filename, "Filename cannot be null");

        File file = new File( filename);
        if (!file.exists()) {
            System.out.println(String.format("File %s exists; will be overwritten",filename));
        }
        save(table,file);
        System.out.println(String.format("%d rows written",table.size()));
    }

    public static void save(Table table, File file) {

        Preconditions.checkNotNull(table, "Table cannot be null");
        Preconditions.checkNotNull(file, "File object cannot be null");
        Preconditions.checkArgument(table instanceof  DataTable, "Only DataTable type supported with Tables.save()");

        System.out.println(String.format("Saving table to %s",file.getAbsoluteFile()));
        if (FilenameUtils.isExtension(file.getName(), Tables.FileTypes.HTML.getExtension()))
             HtmlTables.write((DataTable)table, file);
        else if (FilenameUtils.isExtension(file.getName(), Tables.FileTypes.CSV.getExtension()))
             CsvTables.write((DataTable)table, file);

    }

}
