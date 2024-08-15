import java.util.List;
// Добавлен интерфейс UserRepository
public interface UserRepository {
    // Добавлен метод addUser для добавления пользователя
    void addUser(User user);
    // Добавлен метод deleteUser для удаления пользователя по имени
    void deleteUser(String userName);
    // Добавлен метод getUserList для получения списка пользователей
    List<User> getUserList();
    // Добавлен метод searchUserList для поиска пользователей с использованием переданным типом поиска
    List<User> searchUserList(SearchStrategy strategy);
    // Добавлен метод sortUserList для сортировки пользователей с использованием переданной типом сортировки
    List<User> sortUserList(SortStrategy strategy);
}
