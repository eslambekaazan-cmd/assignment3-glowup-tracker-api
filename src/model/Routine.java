package model;

import exception.InvalidInputException;

public class Routine {
    private int id;
    private String name;
    private RoutineType type;

    public Routine(int id, String name, RoutineType type) {
        this.id = id;
        setName(name);
        setType(type);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0) throw new InvalidInputException("Routine id cannot be negative.");
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Routine name must not be empty.");
        }
        this.name = name.trim();
    }

    public RoutineType getType() {
        return type;
    }

    public void setType(RoutineType type) {
        if (type == null) throw new InvalidInputException("RoutineType must not be null.");
        this.type = type;
    }

    @Override
    public String toString() {
        return "Routine{id=" + id + ", name='" + name + "', type=" + type.getName() + "}";
    }
}
