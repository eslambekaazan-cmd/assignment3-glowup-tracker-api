package service;

import exception.DuplicateResourceException;
import exception.InvalidInputException;
import exception.ResourceNotFoundException;
import model.RoutineType;
import repository.RoutineTypeRepository;

import java.util.List;

public class RoutineTypeService {

    private final RoutineTypeRepository repository = new RoutineTypeRepository();

    public RoutineType create(RoutineType type) {
        if (type == null) {
            throw new InvalidInputException("RoutineType cannot be null.");
        }


        if (type.getName() == null || type.getName().trim().isEmpty()) {
            throw new InvalidInputException("RoutineType name must not be empty.");
        }


        if (repository.getByName(type.getName()) != null) {
            throw new DuplicateResourceException(
                    "RoutineType with name '" + type.getName() + "' already exists."
            );
        }

        return repository.create(type);
    }


    public List<RoutineType> getAll() {
        return repository.getAll();
    }


    public RoutineType getById(int id) {
        RoutineType type = repository.getById(id);
        if (type == null) {
            throw new ResourceNotFoundException(
                    "RoutineType with id " + id + " not found."
            );
        }
        return type;
    }


    public RoutineType update(int id, RoutineType newData) {
        if (newData == null || newData.getName() == null || newData.getName().trim().isEmpty()) {
            throw new InvalidInputException("RoutineType name must not be empty.");
        }


        getById(id);


        RoutineType existing = repository.getByName(newData.getName());
        if (existing != null && existing.getId() != id) {
            throw new DuplicateResourceException(
                    "RoutineType with name '" + newData.getName() + "' already exists."
            );
        }

        repository.update(id, newData);
        return getById(id);
    }


    public void delete(int id) {
        // check exists
        getById(id);

        repository.delete(id);
    }
}
