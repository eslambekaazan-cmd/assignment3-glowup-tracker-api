package model;

import exception.InvalidInputException;
import interfaces.Trackable;

public class WellnessActivity extends SelfCareActivityBase implements Trackable {
    private int minutes;
    private String intensity;

    public WellnessActivity(int id, String name, Routine routine, int minutes, String intensity) {
        super(id, name, routine, "WELLNESS");
        setMinutes(minutes);
        setIntensity(intensity);
    }

    @Override
    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        if (minutes <= 0) throw new InvalidInputException("Minutes must be > 0.");
        this.minutes = minutes;
    }

    public String getIntensity() {
        return intensity;
    }

    public void setIntensity(String intensity) {
        if (intensity == null || intensity.trim().isEmpty()) {
            throw new InvalidInputException("Intensity must not be empty.");
        }
        this.intensity = intensity.trim();
    }

    @Override
    public String getCategoryType() {
        return "Wellness";
    }

    @Override
    public String getSummary() {
        return "[WELLNESS] " + name +
                " | minutes=" + minutes +
                " | intensity=" + intensity +
                " | routine=" + routine.getName();
    }

    @Override
    public void validate() {
        if (name == null || name.trim().isEmpty()) throw new InvalidInputException("Name must not be empty.");
        if (minutes <= 0) throw new InvalidInputException("Minutes must be > 0.");
        if (intensity == null || intensity.trim().isEmpty()) throw new InvalidInputException("Intensity must not be empty.");
    }
}
