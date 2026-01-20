package model;

import exception.InvalidInputException;
import interfaces.Validatable;

public abstract class SelfCareActivityBase extends BaseEntity implements Validatable {
    protected Routine routine;
    protected String kind; // "BEAUTY" or "WELLNESS"

    public SelfCareActivityBase(int id, String name, Routine routine, String kind) {
        super(id, name);
        setRoutine(routine);
        setKind(kind);
    }

    public Routine getRoutine() {
        return routine;
    }

    public void setRoutine(Routine routine) {
        if (routine == null) throw new InvalidInputException("Routine must not be null.");
        this.routine = routine;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        if (kind == null || kind.trim().isEmpty()) {
            throw new InvalidInputException("Kind must not be empty.");
        }
        String k = kind.trim().toUpperCase();
        if (!k.equals("BEAUTY") && !k.equals("WELLNESS")) {
            throw new InvalidInputException("Kind must be BEAUTY or WELLNESS.");
        }
        this.kind = k;
    }
}
