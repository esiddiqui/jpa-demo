package com.es.records.domain;

public interface Table {

    void sort();

    Table merge(final Table right);

    void insert(Object... values);

    int size();

}
