package model;

import exception.InvalidInputException;

public abstract class BaseEntity {
    protected int id;
    protected String name;

    public BaseEntity(int id, String name) {
        this.id = id;
        setName(name);
    }

    public abstract String getCategoryType();
    public abstract String getSummary();

    public void printInfo() {
        System.out.println(getSummary());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0) throw new InvalidInputException("ID cannot be negative.");
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Name must not be empty.");
        }
        this.name = name.trim();
    }
}
