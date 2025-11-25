package Service.Impl;

import Model.Dto.Users;
import Model.Entity.UsersEntity;
import Repository.UserRepository;
import Repository.Impl.UserRepositoryImpl;
import Service.UserService;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public void addUser(Users users) throws SQLException {
        UsersEntity usersEntity = new UsersEntity(
                users.getId(),
                users.getName(),
                users.getTelNo(),
                users.getEmail(),
                users.getRole(),
                users.getUsername(),
                users.getPassword(),
                users.getSalary(),
                users.getRegDate()
        );
        userRepository.save(usersEntity);
    }

    @Override
    public void updateUser(Users users) throws SQLException {
        UsersEntity usersEntity = new UsersEntity(
                users.getId(),
                users.getName(),
                users.getTelNo(),
                users.getEmail(),
                users.getRole(),
                users.getUsername(),
                users.getPassword(),
                users.getSalary(),
                users.getRegDate()
        );
        userRepository.update(usersEntity);
    }

    @Override
    public void deleteUser(String id) throws SQLException {
        userRepository.delete(id);
    }

    @Override
    public Users getUserById(String id) throws SQLException {
        UsersEntity entity = userRepository.findById(id);
        return convertToDto(entity);
    }

    @Override
    public Users getUserByUsername(String username) throws SQLException {
        UsersEntity entity = userRepository.findByUsername(username);
        return convertToDto(entity);
    }

    @Override
    public List<Users> getAllUsers() throws SQLException {
        List<UsersEntity> entities = userRepository.findAll();
        return entities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Users> searchUsers(String keyword) throws SQLException {
        List<UsersEntity> entities = userRepository.search(keyword);
        return entities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public String generateNextUserId() throws SQLException {
        String lastId = userRepository.getLastUserId();

        if (lastId == null) {
            // If no users exist, start with U001
            return "U001";
        }

        try {
            // Extract the numeric part and increment
            String prefix = "U";
            String numericPart = lastId.substring(1); // Remove the "U" prefix
            int number = Integer.parseInt(numericPart);
            number++; // Increment the number

            // Format back to 3-digit string with leading zeros
            return String.format("%s%03d", prefix, number);
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            // If there's any issue with the ID format, start from U001
            return "U001";
        }
    }

    @Override
    public boolean validateUser(String username, String password) throws SQLException {
        UsersEntity entity = userRepository.findByUsername(username);
        return entity != null && entity.getPassword().equals(password);
    }

    private Users convertToDto(UsersEntity entity) {
        if (entity == null) return null;
        return new Users(
                entity.getId(),
                entity.getName(),
                entity.getTelNo(),
                entity.getEmail(),
                entity.getRole(),
                entity.getUsername(),
                entity.getPassword(),
                entity.getSalary(),
                entity.getRegDate()
        );
    }
}