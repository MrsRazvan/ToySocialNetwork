package socialnetwork.repository.file;

import socialnetwork.domain.User;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.repoExceptions.FileError;

import java.util.List;

import static socialnetwork.Utils.constants.ValidatorConstants.*;

public class UserFileRepository extends AbstractFileRepository<Long, User> {

    public UserFileRepository(String fileName, Validator<User> validator) {
        super(fileName, validator);
    }

    @Override
    public User extractEntity(List<String> attributes, int index_corrupted_file) {
        if(attributes.size() != USER_NUMBER_OF_ATTRIBUTES)
            throw new FileError(String.format("Corrupted file at line %d",index_corrupted_file));
        User user = new User(attributes.get(1),attributes.get(2));
        try {
            user.setId(Long.parseLong(attributes.get(0)));
        }
        catch (NumberFormatException ex){
            throw new FileError(String.format("Corrupted file at line %d",index_corrupted_file));
        }
        return user;
    }

    @Override
    protected String createEntityAsString(User entity) {
        return entity.getId() + ";" + entity.getFirstName() + ";" + entity.getLastName();
    }
}

