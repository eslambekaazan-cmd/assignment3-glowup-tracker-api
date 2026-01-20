package service;

import exception.DuplicateResourceException;
import exception.InvalidInputException;
import exception.ResourceNotFoundException;
import model.Routine;
import model.RoutineType;
import repository.RoutineRepository;

import java.util.List;

public class RoutineService {

    private final RoutineRepository routineRepository = new RoutineRepository();
    private final RoutineTypeService routineTypeService = new RoutineTypeService();

    public Routine create(Routine routine) {
        if (routine == null) throw new InvalidInputException("Routine cannot be null.");
        if (routine.getName() == null || routine.getName().trim().isEmpty()) {
            throw new InvalidInputException("Routine name must not be empty.");
        }
        if (routine.getType() == null) throw new InvalidInputException("RoutineType must not be null.");

        RoutineType realType = routineTypeService.getById(routine.getType().getId());
        routine.setType(realType);

        if (routineRepository.getByNameAndType(routine.getName(), realType.getId()) != null) {
            throw new DuplicateResourceException(
                    "Routine '" + routine.getName() + "' already exists for type '" + realType.getName() + "'."
            );
        }

        return routineRepository.create(routine);
    }

    public List<Routine> getAll() {
        return routineRepository.getAll();
    }

    public Routine getById(int id) {
        Routine r = routineRepository.getById(id);
        if (r == null) throw new ResourceNotFoundException("Routine with id " + id + " not found.");
        return r;
    }

    public Routine update(int id, Routine newData) {
        if (newData == null) throw new InvalidInputException("Routine cannot be null.");
        if (newData.getName() == null || newData.getName().trim().isEmpty()) {
            throw new InvalidInputException("Routine name must not be empty.");
        }
        if (newData.getType() == null) throw new InvalidInputException("RoutineType must not be null.");

        getById(id);

        RoutineType realType = routineTypeService.getById(newData.getType().getId());
        newData.setType(realType);

        Routine existing = routineRepository.getByNameAndType(newData.getName(), realType.getId());
        if (existing != null && existing.getId() != id) {
            throw new DuplicateResourceException(
                    "Routine '" + newData.getName() + "' already exists for type '" + realType.getName() + "'."
            );
        }

        routineRepository.update(id, newData);
        return getById(id);
    }

    public void delete(int id) {
        getById(id);
        routineRepository.delete(id);
    }
}
