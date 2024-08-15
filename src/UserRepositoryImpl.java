import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

// Реализация UserRepositoryImpl, имплементирующего UserRepository
public class UserRepositoryImpl implements UserRepository {
    private List<User> userList;
    // Конструктор UserRepositoryImpl заполняющий userList
    public UserRepositoryImpl() {
        userList = new ArrayList<>();
    }
    // Реализован метод addUser в UserRepositoryImpl для добавления пользователя в список пользователей.
    @Override
    public void addUser(User user) {
        userList.add(user);
    }
    // Реализован метод deleteUser в UserRepositoryImpl для удаления пользователя из списка пользователей.
    @Override
    public void deleteUser(String userName) {
        Iterator<User> iterator = this.getUserList().iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getName().equals(userName)) {
                iterator.remove();
                break;
            }
        }
    }
    // Реализован метод getUserList в UserRepositoryImpl для получения списка всех пользователей.
    @Override
    public List<User> getUserList() {
        return userList;
    }
    // Реализован метод searchUserList в UserRepositoryImpl для поиска пользователей по определенному критерию.
    @Override
    public List<User> searchUserList(SearchStrategy strategy) {
        List<User> result = new ArrayList<>();
        for (User user : userList) {
            if (strategy.test(user)) {
                result.add(user);
            }
        }
        return result;
    }
    // Реализован метод sortUserList в UserRepositoryImpl для сортировки списка пользователей по определенному критерию.
    @Override
    public List<User> sortUserList(SortStrategy strategy) {
        List<User> result = new ArrayList<>(userList);
        Collections.sort(result, strategy);
        return result;
    }


}
