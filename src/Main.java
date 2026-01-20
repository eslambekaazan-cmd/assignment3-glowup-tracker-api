/*
import model.*;
import service.ActivityService;

public class Main {
    public static void main(String[] args) {

        ActivityService activityService = new ActivityService();

        Routine routine =
                new Routine(1, "Glow Up Morning", new RoutineType(1, "Morning"));

        System.out.println("=== DUPLICATE DEMO START ===");

        String activityName = "Duplicate Test Activity";

        try {

            activityService.create(
                    new WellnessActivity(0, activityName, routine, 5, "low")
            );
            System.out.println("First activity created.");

            activityService.create(
                    new WellnessActivity(0, activityName, routine, 10, "medium")
            );

            System.out.println("ERROR: Duplicate was created (should not happen).");

        } catch (Exception e) {
            System.out.println("EXPECTED ERROR: " + e.getMessage());
        }

        System.out.println("=== DUPLICATE DEMO END ===");
    }
}

/*
import model.*;
import service.ActivityService;

public class Main {
    public static void main(String[] args) {

        ActivityService activityService = new ActivityService();

        Routine routine1 = new Routine(1, "Glow Up Morning", new RoutineType(1, "Morning"));

        System.out.println("=== CREATE ===");
        String name = "Simple Activity " + System.currentTimeMillis();

        SelfCareActivityBase created = activityService.create(
                new WellnessActivity(0, name, routine1, 5, "low")
        );
        System.out.println("Created: id=" + created.getId() + ", name=" + created.getName());

        System.out.println("\n=== READ ===");
        SelfCareActivityBase found = activityService.getById(created.getId());
        found.printInfo();

        System.out.println("\n=== UPDATE ===");
        found.setName(found.getName() + " (UPDATED)");
        SelfCareActivityBase updated = activityService.update(found.getId(), found);
        updated.printInfo();

        System.out.println("\n=== DELETE ===");
        activityService.delete(updated.getId());
        System.out.println("Deleted id=" + updated.getId());

        System.out.println("\n=== DONE ===");
    }
}
 */
