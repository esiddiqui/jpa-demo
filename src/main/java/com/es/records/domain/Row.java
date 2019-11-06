package com.es.records.domain;

import com.google.common.base.Preconditions;

import java.util.Arrays;
import java.util.List;

public class Row {

    protected final Header metadata;

    private int id;

    private final String[] data;

    protected Row(final Header header) {
        this.metadata = header;
        this.data = new String[header.size()];
    }

    public int getId() {
        return id;
    }


    /**
     * Sets values for the row.
     *
     * @return This row
     */
    public Row setValues(Object... values) {
        Preconditions.checkArgument(values.length==this.data.length, String.format(
                "Number of columns mismatch with Row definitions, expected %d, found %d\n",this.data.length,values.length));
        int index=0;
        for (Object str:values) {
            this.setValueAt(index++,str);
        }
        return this;
    }

    /**
     * Sets values for a specified column.
     *
     * @return This row
     */
    public Row setValueAt(int index, Object value) {
        // nulls are allowed, but not in ID column
        if (index==this.metadata.getIndexOfIdentityColumn()) {
            Preconditions.checkArgument(value != null, "Row::setValueAt Nulls are not allowed in ID columns\n");
            try {
                    this.id = Integer.parseInt(value.toString());
            } catch (Exception ex) {
                System.err.println(String.format("Row::setValueAt Invalid value %s provided for ID column",value));
                throw ex;
            }
        }
        this.data[index] = value.toString();
        return this;
    }


    /**
     * This method copies the underlying row data (String[]) from the other row to this row.
     * @param other
     *
     * @return This row
     */
    protected Row copy(Row other) {
        System.arraycopy(other.data,0,this.data,0,other.metadata.size());
        this.id = other.id;
        return this;
    }


    /**
     * This method will copy the data column by column, from other row to this row
     * by looking at both row's metadata (header). Starts at index specified by the seconds
     * parameter
     *
     * @return This row.
     */
    protected Row map(Row other, int start) {
        for (int i=start; i<this.metadata.size(); i++)   {
            String colName = this.metadata.get(i);
            int otherIndex = other.metadata.indexOf(colName);
            if (otherIndex!=-1)
                this.data[i]= other.data[other.metadata.indexOf(colName)];
            if (colName.equalsIgnoreCase(Header.ID_COLUMN_NAME))
                this.id = other.id;
        }
        return this;
    }


    /**
     * Same as @See map(Row,int), but starts at first column, i.e. index 0
     *
     * @return This row.
     */
    protected Row map(Row other) {
        return this.map(other,0);
    }


    protected String[] getData() {
        return this.data;
    }

    public List<String> getDataAsList() {
        return Arrays.asList(this.data);
    }

    @Override
    public String toString() {
        return "Row{" +
                "metadata=" + metadata +
                ", id=" + id +  "}" +
                ", data=" + Arrays.toString(data) +
                "";
    }
}
