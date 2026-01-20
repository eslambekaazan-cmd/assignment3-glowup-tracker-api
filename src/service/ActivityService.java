package service;

import exception.DuplicateResourceException;
import exception.InvalidInputException;
import exception.ResourceNotFoundException;
import model.SelfCareActivityBase;
import repository.ActivityRepository;

import java.util.List;

public class ActivityService {

    private final ActivityRepository activityRepository = new ActivityRepository();
    private final RoutineService routineService = new RoutineService();

    public SelfCareActivityBase create(SelfCareActivityBase activity) {
        if (activity == null) throw new InvalidInputException("Activity cannot be null.");

        activity.validate();

        routineService.getById(activity.getRoutine().getId());

        if (activityRepository.getByNameAndRoutine(activity.getName(), activity.getRoutine().getId()) != null) {
            throw new DuplicateResourceException(
                    "Activity '" + activity.getName() + "' already exists in this routine."
            );
        }

        return activityRepository.create(activity);
    }

    public List<SelfCareActivityBase> getAll() {
        return activityRepository.getAll();
    }

    public SelfCareActivityBase getById(int id) {
        SelfCareActivityBase a = activityRepository.getById(id);
        if (a == null) throw new ResourceNotFoundException("Activity with id " + id + " not found.");
        return a;
    }

    public SelfCareActivityBase update(int id, SelfCareActivityBase newData) {
        if (newData == null) throw new InvalidInputException("Activity cannot be null.");

        getById(id);

        newData.validate();

        routineService.getById(newData.getRoutine().getId());


        SelfCareActivityBase existing = activityRepository.getByNameAndRoutine(newData.getName(), newData.getRoutine().getId());
        if (existing != null && existing.getId() != id) {
            throw new DuplicateResourceException("Activity '" + newData.getName() + "' already exists in this routine.");
        }

        activityRepository.update(id, newData);
        return getById(id);
    }

    public void delete(int id) {
      
        getById(id);
        activityRepository.delete(id);
    }
}
