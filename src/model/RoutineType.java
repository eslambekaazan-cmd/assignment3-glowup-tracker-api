package model;

import exception.InvalidInputException;

public class RoutineType {
    private int id;
    private String name; // Morning, Night, etc.

    public RoutineType(int id, String name) {
        this.id = id;
        setName(name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0) throw new InvalidInputException("RoutineType id cannot be negative.");
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("RoutineType name must not be empty.");
        }
        this.name = name.trim();
    }

    @Override
    public String toString() {
        return "RoutineType{id=" + id + ", name='" + name + "'}";
    }
}
