package com.es.records.domain;


import com.google.common.base.Preconditions;

public class DataTable implements Table {

    //table metadata
    protected final Header header;

    //table data
    protected final Rows rows;


    public DataTable(String... columns) {
        this(new Header(columns));
    }

    public DataTable(Header header) {
        this.header = header;
        this.rows = new Rows();
    }


    //builder for Rows

    /**
     * Creates an empty row in this table, respecting the header metadata
     * this method will not add the new row to the table. @see DataTable.insert(Row).
     * @return the reference to the new row.
     */
    protected Row createRow() {
        Row row = new Row(this.header);
        return row;
    }


    /**
     * Inserts the row to the table.
     * @param r
     */
    protected void insert(Row r) {
        Preconditions.checkArgument(this.header.equals(r.metadata), "Rows does not have the same metadata");
        this.rows.add(r);
    }

    /**
     * Returns the row at the specified index.
     * @param index
     * @return
     */
    protected Row getRow(int index) {
        return this.rows.getRow(index);
    }


    /**
     * Returns all rows
     * @return
     */
    protected Rows getRows() {
        return this.rows;
    }


    /**
     * Insert a new row with the given values
     * @param values
     */
    @Override
    public void insert(Object... values) {
        Row row = new Row(this.header);
        if (row.setValues(values)!=null)
            this.rows.add(row);
    }


    @Override
    public int size() {
        return this.rows.size();
    }

    @Override
    public void sort() {
        this.rows.sort();
    }


    /**
     * Merges two tables & return the new resulting table
     */
    @Override
    public Table merge(final Table other) {
        DataTable left = this;
        DataTable right = ((DataTable)other);
        left.sort();
        right.sort();

        //merge headers
        Header header = Header.of(left.header,right.header);

        //merge rows
        DataTable merged = new DataTable(header);
        int lPtr = 0, rPtr = 0;
        int lSize = left.header.size();
        while (lPtr<left.rows.size() && rPtr<right.rows.size()) {
            Row newRow = merged.createRow();
            if (left.getRow(lPtr).getId() < right.getRow(rPtr).getId()) {
                newRow.copy(left.getRow(lPtr));
                lPtr++;
            } else if (left.getRow(lPtr).getId() > right.getRow(rPtr).getId()) {
                newRow.map(right.getRow(rPtr));
                rPtr++;
            } else {
                newRow.copy(left.getRow(lPtr));
                newRow.map(right.getRow(rPtr),lSize);
                rPtr++;
                lPtr++;
            }
            merged.insert(newRow);
        }
        //remaining rows
        while (lPtr<left.rows.size()) {
            Row newRow = merged.createRow();
            newRow.copy(left.getRow(lPtr));
            merged.insert(newRow);
            lPtr++;
        }
        while (rPtr<right.rows.size()) {
            Row newRow = merged.createRow();
            newRow.map(right.getRow(rPtr));
            merged.insert(newRow);
            rPtr++;
        }
        return merged;
    }



    @Override
    public String toString() {

        Rows r = this.rows;
        int size= r.size();
        StringBuilder builder = new StringBuilder();
        for (int i=0; i<size; i++) {
            builder.append(r.getRow(i).toString()+"\n");
        }
        return "\nTable{" +
                //"file='" + file + '\'' +
                " header='" + header + '\'' +
                " count(rows)='" + rows.size() + '\'' +
                //", status=" + status + "}" +
                " \n------\n" + builder.toString() + "------\n";
    }

}
