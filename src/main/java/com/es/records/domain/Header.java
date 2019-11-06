package com.es.records.domain;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.Arrays;
import java.util.LinkedHashSet;

public class Header {

    public static final String ID_COLUMN_NAME =  "ID";

    private LinkedHashSet<String> columnSet = new LinkedHashSet<>();

    private int indexOfIdentityColumn = -1;

    private String[] columns;

    private BiMap<String,Integer> columnIndexMap = HashBiMap.create();

    public int getIndexOfIdentityColumn() {
        return indexOfIdentityColumn;
    }

    public String get(int i) {
        return this.columns[i];
    }

    public int indexOf(String columnName) {
        if (this.columnIndexMap.containsKey(columnName))
         return this.columnIndexMap.get(columnName);
        else
            return -1;
    }

    public int size() {
        return this.columnIndexMap.keySet().size();
    }


    /**
     * Creates a new header with colulmn names
     * @param columns
     */
    public Header(String... columns) {
        Preconditions.checkNotNull (columns,"Header::new Null parameters provided");
        Preconditions.checkArgument(columns.length > 1,"Header::new At least 1 column required");
        this.columns = new String[columns.length];
        Arrays.stream(columns).forEach(c->this.add(c.toString()));
        Preconditions.checkState(this.indexOfIdentityColumn>=0,"Header::new No ID column found\n");
    }

    private void add(String column) {
        column = Preconditions.checkNotNull(column,"Header::add Column name cannot be null");
        Preconditions.checkArgument(column.trim().length() != 0, "Header::add Column name cannot be blank\n");
        column = column.trim();
        //store index for ID column
        if (column.equalsIgnoreCase(ID_COLUMN_NAME))
            this.indexOfIdentityColumn = this.size();
        this.columns[this.size()] = column;
        this.columnIndexMap.put(column,this.size());
        this.columnSet.add(column);
    }


    /**
     * Static builder to merge two header definitions
     */
    public static Header of(Header left, Header right) {
        LinkedHashSet<String> set = (LinkedHashSet<String>)left.columnSet.clone();
        set.addAll(right.columnSet);
        String[] columns = new String[set.size()];
        columns = set.toArray(columns);
        return new Header(columns);
    }


    /**
     * Return a String[] of column names
     * @return
     */
    public String[] getColumnNames() {
        return this.columns;
    }

    @Override
    public String toString() {
        return "Header{" +
                "columnSet=" + columnSet +
                ", indexOfIdentityColumn=" + indexOfIdentityColumn +
                '}';
    }

}
