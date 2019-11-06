package com.es.records.domain;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Rows {

     private List<Row> rowList = new ArrayList<>();

     //todo: add protection and check if it's the same metadata
     protected void add(Row row) {
         this.rowList.add(row);
     }

     public Row getRow(int index) {
         Preconditions.checkArgument(index<this.rowList.size(),"Invalid index requested");
         return this.rowList.get(index);
     }

     public void sort() {
         this.rowList.sort(Comparator.comparingInt(r->r.getId()));
         //this.rowList.sort((r1,r2)->r1.getId()-r2.getId());
     }


     public int size() {
         return this.rowList.size();
     }

    @Override
    public String toString() {
        return "\nRows{" +
                "rowList=" + rowList  +
                "}\n";
    }
}
