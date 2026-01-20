package model;

import exception.InvalidInputException;

public class BeautyCareActivity extends SelfCareActivityBase {
    private String product;
    private int stepCount;

    public BeautyCareActivity(int id, String name, Routine routine, String product, int stepCount) {
        super(id, name, routine, "BEAUTY");
        setProduct(product);
        setStepCount(stepCount);
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        if (product == null || product.trim().isEmpty()) {
            throw new InvalidInputException("Product must not be empty.");
        }
        this.product = product.trim();
    }

    public int getStepCount() {
        return stepCount;
    }

    public void setStepCount(int stepCount) {
        if (stepCount <= 0) throw new InvalidInputException("Step count must be > 0.");
        this.stepCount = stepCount;
    }

    @Override
    public String getCategoryType() {
        return "BeautyCare";
    }

    @Override
    public String getSummary() {
        return "[BEAUTY] " + name +
                " | product=" + product +
                " | steps=" + stepCount +
                " | routine=" + routine.getName();
    }

    @Override
    public void validate() {
        if (name == null || name.trim().isEmpty()) throw new InvalidInputException("Name must not be empty.");
        if (product == null || product.trim().isEmpty()) throw new InvalidInputException("Product must not be empty.");
        if (stepCount <= 0) throw new InvalidInputException("Step count must be > 0.");
    }
}
