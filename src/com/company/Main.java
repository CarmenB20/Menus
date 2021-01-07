package com.company;

import java.util.LinkedList;
import java.util.List;

import static util.Keyboard.nextInt;
import static util.Keyboard.nextString;

class AccessDenied extends IllegalArgumentException{
    public AccessDenied(String msg){
        super(msg);
    }
}
class ExistentEmailException extends RuntimeException{
    public ExistentEmailException(String msg){
        super(msg);
    }
}
class WeakPasswordException extends RuntimeException{
    public WeakPasswordException(String msg){
        super(msg);
    }
}
class EmailNotFoundException extends RuntimeException{
    public EmailNotFoundException(String msg){
        super(msg);
    }
}
class ExceptionNameNotFound extends RuntimeException{
    public ExceptionNameNotFound(String msg){
        super(msg);
    }
}
class NoActiveEmployeesException extends RuntimeException{
    public NoActiveEmployeesException(String msg){
        super(msg);
    }
}
class NotExistsException extends RuntimeException{
    public NotExistsException(String msg){
        super(msg);
    }
}
class User{
    private String password, email;
    public User(String email, String password){
        this.email = email;
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
class Employee {
    private String name, email, department;
    private int age;
    private double salary;

    public Employee(String name, String email, String department, int age, double salary) {
        this.name = name;
        this.email = email;
        this.department = department;
        this.age = age;
        this.salary = salary;
    }

    public String toString() {
        return "\n " + "Employee : " + this.name + "\n\t" + "Department: " + this.department + "\n\t" + "age: " + this.age
                + "\n\t " + "salary " + this.salary;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDepartment() {
        return department;
    }

    public int getAge() {
        return age;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}

class DataBase{
    private List<User>users;
    private List<Employee> employees;


    public DataBase(){
        this.users = new LinkedList<>();
        this.employees = new LinkedList<>();

    }

    public List<Employee> getEmployees() {
        return this.employees;
    }

    public List<User> getUsers() {
        return this.users;
    }

    public void addUsers(User u){
        this.users.add(u);
    }
    public void addEMployee(Employee e){
        this.employees.add(e);
    }

}


class EmployeeService {
    private DataBase dataBase;

    public EmployeeService(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public DataBase getDataBase() {
        return this.dataBase;
    }


    public List<Employee> displayEmployees() {
        if (this.dataBase.getEmployees().size() == 0) {
            throw new NotExistsException("No employees found!");
        }
        for (Employee e : dataBase.getEmployees()) {
            return dataBase.getEmployees();
        }
        return null;
    }

    public Employee searchByName(String name) throws ExceptionNameNotFound {
        Employee e = getEmployeeByName(name);
        if (e == null) {
            throw new ExceptionNameNotFound("Name not found!");
        }
        return e;
    }

    public Employee searchByEmail(String email) {
        Employee e = getEmployeeByEmail(email);
        if (e == null) {
            throw new EmailNotFoundException("Email not found!Please try again!");
        }
        return e;


    }

    public Employee searchByDepartment(String department) {
        Employee e = getEmployeeByDepartment(department);
        if (e == null) {
            throw new NoActiveEmployeesException("No employees in this department!");
        }
        return e;
    }

    public List<Employee> getByAge() {
        for (int i = 0; i < dataBase.getEmployees().size(); i++) {
            for (int j = i + 1; j < dataBase.getEmployees().size(); j++) {
                if (dataBase.getEmployees().get(j).getAge() > dataBase.getEmployees().get(i).getAge()) {
                    Employee a = dataBase.getEmployees().get(i);
                    dataBase.getEmployees().set(i, dataBase.getEmployees().get(i + 1));
                    dataBase.getEmployees().set(i + 1, a);
                }
            }
        }
        for (Employee a : this.dataBase.getEmployees()) {
            return this.dataBase.getEmployees();
        }
        return null;
    }


    public Employee moveEmployeeToDepartment(String name, String department) {
        if ( getEmployeeByName(name) == null) {
            throw new NotExistsException("The employee does not exists!");
        }
        getEmployeeByName(name).setDepartment(department);
        return getEmployeeByName(name);

    }


    public void deleteEmployeeByEmail(String email) {
        Employee e = getEmployeeByEmail(email);
        if (e == null) {
            throw new EmailNotFoundException("Email not found!Please try again!");
        }
        this.dataBase.getEmployees().remove(e);
    }

    public void applyRaise(int amount) {
        for (Employee e : this.dataBase.getEmployees()) {
            if (e.getSalary() > 0) {
                e.setSalary(e.getSalary() +amount);
            }
        }
    }

    private Employee getEmployeeByName(String name){
        for(Employee e : this.dataBase.getEmployees()){
            if(e.getName().equals(name)){
                return e;
            }
        }
        return null;
    }
    private Employee getEmployeeByEmail(String email){
        for(Employee e : this.dataBase.getEmployees()){
            if(e.getEmail().equals(email)){
                return e;
            }
        }
        return null;
    }
    private Employee getEmployeeByDepartment(String department){
        for(Employee e : this.dataBase.getEmployees()){
            if(e.getDepartment().equals(department)){
                return e;
            }
        }
        return null;
    }
}
class UserService{
    private DataBase dataBase;

    public UserService(DataBase dataBase){
        this.dataBase = dataBase;
    }


    public boolean login (String email, String password){
        List<User>users = dataBase.getUsers();
        for(User user : dataBase.getUsers()){
            if(!email.equals(user.getEmail())  && !password.equals(user.getPassword())){
                throw  new AccessDenied("Email or password incorrect!");
            }
        }
        return true;
    }


    public void register(String email, String password)throws ExistentEmailException , WeakPasswordException{
        User u = getUSerByEmail(email);
        if(u != null){
            throw new ExistentEmailException("Email already assigned!");
        }
        if(!isPasswordSecure(password)){
            throw new WeakPasswordException("Password should contain at least 8 digits, digits and chars combined!");
        }
        User user = new User(email, password);
        this.dataBase.addUsers(user);
    }

    public void deleteUser(String email)throws EmailNotFoundException{
        User u = getUSerByEmail(email);
        if(u == null){
            throw new EmailNotFoundException("User with email: " + email + "not found!");
        }
        this.dataBase.getUsers().remove(u);
    }


    private User getUSerByEmail(String email){
        for(User u : this.dataBase.getUsers()){
            if(u.getEmail().equals(email)){
                return u;
            }
        }
        return null;
    }

    private boolean isPasswordSecure(String password) {
        if (password.length() < 8) {
            return false;
        }
        int countDigits = 0;
        int countChars = 0;

        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                countDigits++;
            }
            if (Character.isLetter(password.charAt(i))) {
                countChars++;
            }
        }
        if (countChars == 0 || countDigits == 0) {
            return false;
        }
        return true;
    }

    private User getPassword(String password){
        for(User u : this.dataBase.getUsers()){
            if(u.getPassword().equals(password)){
                return u;
            }
        }
        return null;
    }
}


class Menu {
    private UserService userService;
    private EmployeeService employeeService;

    public Menu(UserService userService, EmployeeService employeeService){
        this.userService = userService;
        this.employeeService = employeeService;
    }


    public void loginMenu() {
        int option = 0;
        while (option != 4) {
            System.out.println("1.Login!");
            System.out.println("2.Register!");
            System.out.println("3.Delete user!");
            System.out.println("4.Logout!");
            option = nextInt();
            switch (option) {
                case 1: {
                    System.out.println("Please type email and password: ");
                    String email = nextString();
                    System.out.println("Please type password: ");
                    String password = nextString();

                    if(userService.login(email, password)==true){
                        this.employeeServiceMenu();
                    }
                }
                case 2:{
                    System.out.println("Register here");
                    System.out.print("Please type in your email: "   );
                    String email = nextString();
                    System.out.print("Please type in your password: ");
                    String pass = nextString();

                    userService.register(email,pass);
                    System.out.println("Your account has been created!");

                    break;
                }
                case 3:{
                    System.out.println("Type email:");
                    String email = nextString();
                    try{
                        userService.deleteUser(email);
                    }catch (EmailNotFoundException e){
                        System.out.println(e.getMessage());
                    }
                    System.out.println("User with :"  + email + " email, removed!" );
                    break;
                }
                case 4:{
                    System.out.println("You ve been logged out! HAve a nice day!");
                    break;
                }
            }
        }
    }
    public void employeeServiceMenu() {
        int option = 0;
        while (option != 9) {
            System.out.println("1.Display all employees!");
            System.out.println("2.Search employee by name!");
            System.out.println("3.Search employee by email!");
            System.out.println("4.Search employee by department!");
            System.out.println("5.Display employees by age!");
            System.out.println("6.Move employee from a department to another!");
            System.out.println("7.Delete employee by email!");
            System.out.println("8.Apply salary raise!");
            System.out.println("9.Logout!");
            option = nextInt();
            switch (option) {
                case 1: {
                    System.out.println("Employee's List: ");
                    try{
                        System.out.println(employeeService.displayEmployees());
                    }catch (IllegalArgumentException e){
                        System.out.println(e.getMessage());
                    }
                    break;

                }
                case 2:{
                    System.out.print("Please type the name: ");
                    String name = nextString();
                    System.out.println( employeeService.searchByName(name));
                }
                break;

                case 3:{
                    System.out.println("Please type email: ");
                    String email = nextString();
                    System.out.println( employeeService.searchByEmail(email));
                }
                break;

                case 4:{
                    System.out.println("Please type department: ");
                    String department = nextString();
                    System.out.println(employeeService.searchByDepartment(department));
                }
                break;

                case 5:{
                    System.out.println(employeeService.getByAge());
                }
                break;
                case 6:{
                    System.out.println("Employees name: ");
                    String name = nextString();
                    System.out.println("Department desired: ");
                    String department = nextString();
                    System.out.println(employeeService.moveEmployeeToDepartment(name, department));


                }
                break;
                case 7:{
                    System.out.println("Please type email: ");
                    String email = nextString();

                    employeeService.deleteEmployeeByEmail(email);
                    System.out.println("Employee with email : " + email + "removed from database!");
                }
                break;

                case 8:{
                    System.out.println("Apply raise: ");
                    int amount = nextInt();
                    employeeService.applyRaise(amount);

                }
                break;
                case 9:{
                    System.out.println("You've been logged out! Have a nice day!");
                }
            }
        }
    }
}

public class Main {

    public static void main(String[] args) {

        Employee e1 = new Employee("ana","ana@gmail.com","sales",32,2000.0);
        Employee e2 = new Employee("john","john@gmail.com","hr",38,3650.0);
        Employee e3 = new Employee("marie","marie@gmail.com","hr",28,3650.0);



        User u1 = new User("ana@gmail.com","asdfghj");
        User u2 = new User("john@gmail.com","asdfghj123");


        DataBase dataBase = new DataBase();
        dataBase.addUsers(u1);
        dataBase.addEMployee(e1);
        dataBase.addEMployee(e2);
        dataBase.addEMployee(e3);

        EmployeeService employeeService = new EmployeeService(dataBase);
        UserService userService = new UserService(dataBase);


        userService.login("ana@gmail.com", "asdfghj");


        Menu menu = new Menu(userService,employeeService);
        menu.loginMenu();





    }
}
