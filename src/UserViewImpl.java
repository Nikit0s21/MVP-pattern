import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.List;
public class UserViewImpl implements UserView, ActionListener, ChangeListener, AdjustmentListener {
    private JFrame frame;
    private JPanel panel;
    private JList<User> listArea;
    private JButton addButton;
    private JButton deleteButton;
    private JButton sortButton;
    private JButton searchButton;
    private JButton showListButton;
    private UserPresenter presenter;
    private List<User> currentList;

    public UserViewImpl() {
        frame = new JFrame("User Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);

        panel = new JPanel(new BorderLayout());

        // Добавление списка пользователей в центр панели, внутри панели прокрутки
        listArea = new JList<User>();
        JScrollPane scrollPane = new JScrollPane(listArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Добавление панели кнопок в нижнюю часть панели
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Добавление кнопок на панель кнопок
        addButton = new JButton("Add User");
        addButton.addActionListener(this);
        buttonPanel.add(addButton);

        deleteButton = new JButton("Delete User");
        deleteButton.addActionListener(this);
        buttonPanel.add(deleteButton);

        sortButton = new JButton("Sort");
        sortButton.addActionListener(this);
        buttonPanel.add(sortButton);

        searchButton = new JButton("Search Users");
        searchButton.addActionListener(this);
        buttonPanel.add(searchButton);

        showListButton = new JButton("Show User List");
        showListButton.addActionListener(this);
        buttonPanel.add(showListButton);

        panel.add(new JScrollPane(listArea), BorderLayout.CENTER);

        frame.setContentPane(panel);
        frame.setVisible(true);

        presenter = new UserPresenter(this, new UserRepositoryImpl());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Добавление пользователя
        if (e.getSource() == addButton) {
            String name = JOptionPane.showInputDialog("Enter name:");
            String ageStr = JOptionPane.showInputDialog("Enter age:");
            String email = JOptionPane.showInputDialog("Enter email:");

            try {
                int age = Integer.parseInt(ageStr);
                presenter.addUser(name, age, email);
            } catch (NumberFormatException ex) {
                showErrorMessage("Invalid age: " + ageStr);
            }
            // Удаление пользователя
        } else if (e.getSource() == deleteButton) {
            if (currentList.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No users to delete.");
                return;
            }

            String[] userNames = currentList.stream().map(User::getName).toArray(String[]::new);
            String selectedUser = (String) JOptionPane.showInputDialog(frame, "Select user to delete:", "Delete", JOptionPane.PLAIN_MESSAGE, null, userNames, null);
            if (selectedUser != null) {
                // Удаление выбранного пользователя из списка
                presenter.deleteUser(selectedUser);
            }
            // Сортировка списка пользователей
        } else if (e.getSource() == sortButton) {
            // Показ дилога с выбором типа сортировки
            String[] sortTypes = {"Name", "Age", "Email"};
            int sortTypeIndex = JOptionPane.showOptionDialog(frame, "Select sort type:", "Sort", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, sortTypes, sortTypes[0]);

            // Показ дилога с выбором типа сортировки по возрастанию или убыванию
            String[] sortDirections = {"Ascending", "Descending"};
            int sortDirectionIndex = JOptionPane.showOptionDialog(frame, "Select sort direction:", "Sort", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, sortDirections, sortDirections[0]);

            // Сортировка по выбранным фильтрам
            SortStrategy sortStrategy = null;
            switch (sortTypeIndex) {
                case 0:
                    sortStrategy = (sortDirectionIndex == 0) ? new NameAscendingSort() : new NameDescendingSort();
                    break;
                case 1:
                    sortStrategy = (sortDirectionIndex == 0) ? new AgeAscendingSort() : new AgeDescendingSort();
                    break;
                case 2:
                    sortStrategy = (sortDirectionIndex == 0) ? new EmailAscendingSort() : new EmailDescendingSort();
                    break;
                default:
                    break;
            }
            if (sortStrategy != null) {
                presenter.sortUserList(sortStrategy);
            }
            // Поиск пользователей
        } else if (e.getSource() == searchButton) {
            String searchStr = JOptionPane.showInputDialog("Enter search string:");
            List<User> userList = presenter.searchUserList(user -> user.getName().contains(searchStr) ||
                    user.getEmail().contains(searchStr));
            showUserList(userList);
            // Показать список пользователей
        } else if (e.getSource() == showListButton) {
            presenter.showUserList();
        }
    }
    // Рализация метода showUserList для вывода нынешнего спика пользователей
    @Override
    public void showUserList(List<User> userList) {
        currentList = userList;
        DefaultListModel<User> model = new DefaultListModel<>();
        for (User user : userList) {
            model.addElement(user);
        }
        listArea.setModel(model);
    }
    // Рализация метода showErrorMessage для вывода сообщения об ошибке
    @Override
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    // Рализация метода showSuccessMessage для вывода сообщения об успешной выполненой операции
    @Override
    public void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(frame, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    // Добавлен класс NameAscendingSort для сортировки пользователей по имени в порядке возрастания
    private static class NameAscendingSort implements SortStrategy {
        @Override
        public int compare(User u1, User u2) {
            return u1.getName().compareTo(u2.getName());
        }
    }
    // Добавлен класс NameDescendingSort для сортировки пользователей по имени в порядке убывания
    private static class NameDescendingSort implements SortStrategy {
        @Override
        public int compare(User u1, User u2) {
            return u2.getName().compareTo(u1.getName());
        }
    }
    // Добавлен класс AgeAscendingSort для сортировки пользователей по возрасту в порядке возрастания
    private static class AgeAscendingSort implements SortStrategy {
        @Override
        public int compare(User u1, User u2) {
            return Integer.compare(u1.getAge(), u2.getAge());
        }
    }
    // Добавлен класс AgeDescendingSort для сортировки пользователей по возрасту в порядке убывания
    private static class AgeDescendingSort implements SortStrategy {
        @Override
        public int compare(User u1, User u2) {
            return Integer.compare(u2.getAge(), u1.getAge());
        }
    }
    // Добавлен класс EmailAscendingSort для сортировки пользователей по электронной почте в порядке возрастания
    private static class EmailAscendingSort implements SortStrategy {
        @Override
        public int compare(User u1, User u2) {
            return u1.getEmail().compareTo(u2.getEmail());
        }
    }
    // Добавлен класс EmailDescendingSort для сортировки пользователей по электронной почте в порядке убывания
    private static class EmailDescendingSort implements SortStrategy {
        @Override
        public int compare(User u1, User u2) {
            return u2.getEmail().compareTo(u1.getEmail());
        }
    }
    // Реализован метод stateChanged для отслеживания изменений состояния
    @Override
    public void stateChanged(ChangeEvent e) {
    }
    // Реализован метод adjustmentValueChanged для отслеживания изменений значения скролла.
    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {

    }
}
