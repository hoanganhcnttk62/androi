package com.nguyennguyenhoanganh.c2d4;

public class Student {
    private String id;
    private String name;
    private String className;

    public Student(String id, String name, String className) {
        this.id = id;
        this.name = name;
        this.className = className;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getClassName() {
        return className;
    }
}
