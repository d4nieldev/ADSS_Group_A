package BussinessLayer.EmployeesLayer;

import java.util.LinkedList;
import java.util.List;

import DataAccessLayer.DAO.EmployeesLayer.*;
import DataAccessLayer.DTO.EmployeeLayer.DriverDTO;
import DataAccessLayer.DTO.EmployeeLayer.EmployeeDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import Misc.*;

public class EmployeeFacade {
    private LinkedList<Employee> employees;
    private LinkedList<Driver> drivers;
    private EmployeesDAO employeesDAO;
    private DriversDAO driversDAO;
    private Role roleClass;

    public EmployeeFacade() {
        employees = new LinkedList<>();
        drivers = new LinkedList<>();
        employeesDAO = new EmployeesDAO();
        driversDAO = new DriversDAO();
        roleClass = new Role();

        // // add roles instaces to the class Role in Misc
        // roleClass.addRole("HRMANAGER");
        // roleClass.addRole("TRANSPORTMANAGER");
        // roleClass.addRole("SUPPLIERMANAGER");
        // roleClass.addRole("INVENTORYMANAGER");
        // roleClass.addRole("BRANCHMANAGER");
        // roleClass.addRole("SHIFTMANAGER");
        // roleClass.addRole("CASHIER");
        // roleClass.addRole("STOREKEEPER");
        // roleClass.addRole("DRIVER");
        // roleClass.addRole("GENERRAL");
        // roleClass.addRole("CLEANER");
        // roleClass.addRole("SECURITY");

        // Adding Hr manager manualy to the system.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse("01-02-1980", formatter);
        addHRManagerForStartUpTheSystem("Rami", "Arnon", 123456789, "abc", 0, 0,
                0, 50000, 30000, localDate, "free terms of employment", null,
                roleClass.getRoleByName("HRMANAGER").getId(), 0);

        // Adding Transport manager manualy to the system.
        addTransportManagerForStartUpTheSystem("Kfir", "Rotem", 987654321, "abc", 0, 0,
                0, 0, 0, localDate, "free terms of employment", null,
                roleClass.getRoleByName("TRANSPORTMANAGER").getId(), 0);
    }

    // commit log in for employee, if exsist
    public void logIn(int id, String password) {
        if (isEmployeeExistsAndLoadEmployee(id) && !isEmployeeLoggedIn(id)) {
            Employee e = getEmployeeById(id);

            if (e.getId() == id && e.getPassword().equals(password)) {
                e.SetIsLoggedInToTrue();
                if (isEmployeeDriver(id)) {
                    driversDAO.update(((Driver) e).driverToDTO());
                } else {
                    employeesDAO.update(e.toDTO());
                }
                System.out.println("Hello " + e.getFirstName() + " " +
                        e.getLastName() + " You have logged in successfully");
            } else {
                System.out.println("Id or password are incorrect.");
            }
        } else {
            throw new Error("You must enter a valid Id and be logged out to that user before you log in back again.");
        }
    }

    // commit log out for employee, if exsist
    public void logOut(int id) {
        if (isEmployeeExistsAndLoadEmployee(id) && isEmployeeLoggedIn(id)) {
            Employee e = getEmployeeById(id);
            e.SetIsLoggedInToFalse();
            if (isEmployeeDriver(id)) {
                driversDAO.update(((Driver) e).driverToDTO());
            } else {
                employeesDAO.update(e.toDTO());
            }
            System.out.println("Bye Bye " + e.getFirstName() + " " +
                    e.getLastName() + " You have logged out successfully.");
        } else {
            throw new Error("You must enter a valid Id and be logged in to that user.");
        }
    }

    // add employee to the system.
    // only if its HR manager and the employee does not exsist already.
    public void addEmployee(int managerId, String firstName, String lastName, int id, String password, int bankNum,
            int bankBranch, int bankAccount, int salary, int InitializeBonus, LocalDate startDate,
            String tempsEmployment, String role, int branch) {
        if (isEmployeeExistsAndLoadEmployee(managerId) && isEmployeeLoggedIn(managerId) && !isEmployeeExistsAndLoadEmployee(id)) {
            checkHrManager(managerId);
            Integer roleInt = roleClass.getRoleByName(role.toUpperCase()).getId();
            Employee employee = new Employee(firstName, lastName, id, password, bankNum,
                    bankBranch, bankAccount, salary, InitializeBonus, startDate, tempsEmployment, roleInt, branch);
            employees.add(employee);
            System.out.println("The employee " + firstName + " " + lastName + " has been added successfully");
            this.employeesDAO.insert(employee.toDTO());// add to DB
        } else {
            throw new Error(
                    "You must be logged in, be an HR manager and enter a non exist employee in order to do that action.");
        }
    }

    // add driver to the system.
    // only if its HR manager and the employee does not exsist already.
    public void addDriver(int managerId, String firstName, String lastName, int id, String password, int bankNum,
            int bankBranch,
            int bankAccount, int salary, int InitializeBonus, LocalDate startDate, String tempsEmployment,
            License driverLicense) {
        if (isEmployeeExistsAndLoadEmployee(managerId) && isEmployeeLoggedIn(managerId) && !isEmployeeExistsAndLoadEmployee(id)) {
            checkHrManager(managerId);
            Driver driver = new Driver(firstName, lastName, id, password, bankNum, bankBranch, bankAccount, salary,
                    InitializeBonus, startDate, tempsEmployment, roleClass.getRoleByName("DRIVER").getId(),
                    driverLicense);
            drivers.add(driver);
            System.out.println("The driver " + firstName + " " + lastName + " has been added successfully");
            this.driversDAO.insert(driver.driverToDTO());// add to DB
        } else {
            throw new Error(
                    "You must be logged in, be an HR manager and enter a non exist employee in order to do that action.");
        }
    }

    // delete/remove employee from the system.
    public void deleteEmployee(int id) {
        employeesDAO.delete("ID", id);
        Employee employeeToRemove = getEmployeeById(id);
        employees.remove(employeeToRemove);
    }

    // delete/remove driver from the system.
    public void deleteDriver(int managerId, int id) {
        driversDAO.delete("ID", id);
        checkHrManager(managerId); // only HR manager
        Driver driverToRemove = getDriverById(id);
        drivers.remove(driverToRemove);
    }

    // print all employees in the system.
    // only if its HR manager.
    public String printAllEmployees(int managerId) {
        String strPrint = "";
        checkHrManager(managerId);
        List<EmployeeDTO> employeesDTO = employeesDAO.getAll();
        employees = new LinkedList<>();
        for (EmployeeDTO employeeDTO : employeesDTO) {
            employees.add(new Employee(employeeDTO));
        }
        for (Employee employee : employees) {
            strPrint += employee.newToString() + "\n";
        }
        return strPrint;
    }

    // print all employees in the system.
    // only if its HR manager.
    public String printAllDrivers(int managerId) {
        String strPrint = "";
        checkManager(managerId);
        List<DriverDTO> driversDTO = driversDAO.getAll();
        drivers = new LinkedList<>();
        for (DriverDTO driverDTO : driversDTO) {
            drivers.add(new Driver(driverDTO));
        }
        for (Driver driver : drivers) {
            strPrint += driver.newToString() + "\n";
        }
        return strPrint;
    }

    

    public void addRoleToEmployee(int managerId, int idEmployee, Integer role) {
        checkHrManager(managerId);
        checkEmployee(idEmployee);
        getEmployeeById(idEmployee).addRole(role);
        employeesDAO.addRole(idEmployee, role);
    }

    public void removeRoleFromEmployee(int managerId, int idEmployee, Integer role) {
        checkHrManager(managerId);
        checkEmployee(idEmployee);
        getEmployeeById(idEmployee).removeRole(role);
        employeesDAO.removeRole(idEmployee, role);
    }

    public void checkRoleInEmployee(int idEmployee, Integer role) {
        checkEmployee(idEmployee);
        getEmployeeById(idEmployee).checkRoleInEmployee(role);
    }

    // public void addBonus(int managerId, int idEmployee, int bonus){
    // checkHrManager(managerId); checkEmployee(idEmployee);
    // getEmployeeById(idEmployee).setBonus(bonus);
    // }

    public void addRoleToSystem(int managerHR, String role) {
        checkHrManager(managerHR);
        roleClass.addRole(role.toUpperCase());
    }

    public void AddConstraintDriver(int driverId, LocalDate date) {
        checkEmployee(driverId);
        checkLoggedIn(driverId);
        getDriverById(driverId).AddConstraintDriver(date);
        driversDAO.addAvailableShiftDates(driverId, date);
    }

    public void RemoveConstraintDriver(int driverId, LocalDate date) {
        checkEmployee(driverId);
        checkLoggedIn(driverId);
        getDriverById(driverId).RemoveConstraintDriver(date);
        driversDAO.removeAvailableShiftDates(driverId, date);
    }

    public String printDayDriversPast(LocalDate date) {
        String strDrivers = "";
        List<Driver> driversForFunction = getDayDriversPast(date);
        for (Driver driver : driversForFunction) {
            if (driver.getWorkedDates().contains(date)) {
                strDrivers += driver.toString();
            }
        }
        return strDrivers;
    }

    public String printDayDriversFuture(LocalDate date) {
        String strDrivers = "";
        List<Driver> driversForFunction = getDayDriversFuture(date);
        for (Driver driver : driversForFunction) {
            if (driver.getAvailableShiftDates().contains(date)) {
                strDrivers += driver.toString();
            }
        }
        return strDrivers;
    }

    public LinkedList<Driver> getDayDriversPast(LocalDate date) {
        LinkedList<Driver> returnDrivers = new LinkedList<>();
        for (Driver driver : drivers) {
            if (driver.getWorkedDates().contains(date)) {
                drivers.remove(driver);
            }
        }
        List<DriverDTO> driversDTO = driversDAO.getDriversByDate(date);
        for (DriverDTO driverDTO : driversDTO) {
            Driver d = new Driver(driverDTO);
            drivers.add(d);
            returnDrivers.add(d);
        }
        return returnDrivers;
    }

    public LinkedList<Driver> getDayDriversFuture(LocalDate date) {
        LinkedList<Driver> returnDrivers = new LinkedList<>();
        for (Driver driver : drivers) {
            if (driver.getAvailableShiftDates().contains(date)) {
                drivers.remove(driver);
            }
        }
        List<DriverDTO> driversDTO = driversDAO.getDriversByDate(date);
        for (DriverDTO driverDTO : driversDTO) {
            Driver d = new Driver(driverDTO);
            drivers.add(d);
            returnDrivers.add(d);
        }
        return returnDrivers;
    }

    // -------------------------------------Getters And
    // Setters--------------------------------------------------------

    public LinkedList<Driver> getAllDrivers() {
        List<DriverDTO> driversDTO = driversDAO.getAll();
        drivers = new LinkedList<>();
        for (DriverDTO driverDTO : driversDTO) {
            drivers.add(new Driver(driverDTO));
        }
        return drivers;
    }

    public void changeFirstName(int managerId, int idEmployee, String firstName) {
        checkHrManager(managerId);
        checkEmployee(idEmployee);
        Employee employee = getEmployeeById(idEmployee);
        employee.setFirstName(firstName);
        if (isEmployeeDriver(idEmployee)) {
            driversDAO.update(((Driver) employee).driverToDTO());
        } else {
            employeesDAO.update(employee.toDTO());
        }
    }

    public void changeLastName(int managerId, int idEmployee, String lastName) {
        checkHrManager(managerId);
        checkEmployee(idEmployee);
        Employee employee = getEmployeeById(idEmployee);
        employee.setLastName(lastName);
        if (isEmployeeDriver(idEmployee)) {
            driversDAO.update(((Driver) employee).driverToDTO());
        } else {
            employeesDAO.update(employee.toDTO());
        }
    }

    public void changePassword(int managerId, int idEmployee, String password) {
        checkHrManager(managerId);
        checkEmployee(idEmployee);
        Employee employee = getEmployeeById(idEmployee);
        employee.setPassword(password);
        if (isEmployeeDriver(idEmployee)) {
            driversDAO.update(((Driver) employee).driverToDTO());
        } else {
            employeesDAO.update(employee.toDTO());
        }
    }

    public void changeBankNum(int managerId, int idEmployee, int bankNum) {
        checkHrManager(managerId);
        checkEmployee(idEmployee);
        Employee employee = getEmployeeById(idEmployee);
        employee.setBankNum(bankNum);
        if (isEmployeeDriver(idEmployee)) {
            driversDAO.update(((Driver) employee).driverToDTO());
        } else {
            employeesDAO.update(employee.toDTO());
        }
    }

    public void changeBankBranch(int managerId, int idEmployee, int bankBranch) {
        checkHrManager(managerId);
        checkEmployee(idEmployee);
        Employee employee = getEmployeeById(idEmployee);
        employee.setBankBranch(bankBranch);
        if (isEmployeeDriver(idEmployee)) {
            driversDAO.update(((Driver) employee).driverToDTO());
        } else {
            employeesDAO.update(employee.toDTO());
        }
    }

    public void changeBankAccount(int managerId, int idEmployee, int bankAccount) {
        checkHrManager(managerId);
        checkEmployee(idEmployee);
        Employee employee = getEmployeeById(idEmployee);
        employee.setBankAccount(bankAccount);
        if (isEmployeeDriver(idEmployee)) {
            driversDAO.update(((Driver) employee).driverToDTO());
        } else {
            employeesDAO.update(employee.toDTO());
        }
    }

    public void changeSalary(int managerId, int idEmployee, int salary) {
        checkHrManager(managerId);
        checkEmployee(idEmployee);
        Employee employee = getEmployeeById(idEmployee);
        employee.setSalary(salary);
        if (isEmployeeDriver(idEmployee)) {
            driversDAO.update(((Driver) employee).driverToDTO());
        } else {
            employeesDAO.update(employee.toDTO());
        }
    }

    public void changeStartDate(int managerId, int idEmployee, LocalDate stastDate) {
        checkHrManager(managerId);
        checkEmployee(idEmployee);
        Employee employee = getEmployeeById(idEmployee);
        employee.setStartDate(stastDate);
        if (isEmployeeDriver(idEmployee)) {
            driversDAO.update(((Driver) employee).driverToDTO());
        } else {
            employeesDAO.update(employee.toDTO());
        }
    }

    public void changeDriverLicence(int managerId, int idEmployee, License licene) {
        checkHrManager(managerId);
        checkEmployee(idEmployee);
        Driver employee = getDriverById(idEmployee);
        employee.setDriverLicense(licene);
        driversDAO.update(((Driver) employee).driverToDTO());
    }

    public String getManagerType(int id) {
        Employee manager = getEmployeeById(id);
        return roleClass.getRoleById(manager.getRoles().getFirst()).getName();
    }

    public Role getRoleClassInstance() {
        return roleClass;
    }

    public void removeAllRolesForEmployeeFromDB(int employeeId, LinkedList<Integer> roles) {
        employeesDAO.removeAllRolesForEmployee(employeeId, roles);
    }

    // -------------------------------------------------------Help
    // Functions------------------------------------------------------------

    // called only if the employee exist, else will return error.
    public Employee getEmployeeById(int id) {
        if (isEmployeeExistsAndLoadEmployee(id)) {
            for (Employee employee : employees) {
                if (employee.getId() == id)
                    return employee;
            }

            for (Driver driver : drivers) {
                if (driver.getId() == id)
                    return (Driver) driver;
            }
        }   
        throw new Error("The id " + id + " is not in the system. Please try again");
    }

    // called only if the employee exist, else will return error.
    public Driver getDriverById(int id) {
        if (isEmployeeExistsAndLoadEmployee(id)) {      
            for (Driver driver : drivers) {
                if (driver.getId() == id)
                    return (Driver) driver;
            }
        }
        throw new Error("The id " + id + "is not in the system. Please try again");
    }

    // return true if the employee exsist already in the system and load it if its in the data base
    public boolean isEmployeeExistsAndLoadEmployee(int id) {
        for (Employee employee : employees) {
            if (employee.getId() == id)
                return true;
        }
        for (Driver driver : drivers) {
            if (driver.getId() == id)
                return true;
        }
        EmployeeDTO emp = employeesDAO.getEmployeeById(id);
        if (emp != null) {
            employees.add(new Employee(emp));
            return true;
        }
        DriverDTO driver = driversDAO.getDriverById(id);
        if (driver != null) {
            drivers.add(new Driver(driver));
            return true;
        }

        return false;
    }

    // return true if the employee is a driver
    public boolean isEmployeeDriver(int id) {
        isEmployeeExistsAndLoadEmployee(id);
        for (Driver driver : drivers) {
            if (driver.getId() == id)
                return true;
        }
        return false;
    }

    // return true if the employee logged in to the system
    public boolean isEmployeeLoggedIn(int id) {
        Employee employee = getEmployeeById(id);
        return employee.getIsLoggedIn();
    }

    // return true if the employee logged in to the system
    public boolean isDriverLoggedIn(int id) {
        Driver driver = getDriverById(id);
        return driver.getIsLoggedIn();
    }

    // throw error if the employee is not logged in to the system
    public void checkLoggedIn(int id) {
        Employee employee = getEmployeeById(id);
        if (!employee.getIsLoggedIn()) {
            throw new Error("You must be logged in to the system in order to do that action.");
        }
    }

    // return true if the employee is HR manager
    private boolean isEmployeeHRManager(int id) {
        Employee employee = getEmployeeById(id);
        List<Integer> managerRoles = employee.getRoles();
        return managerRoles.contains(roleClass.getRoleByName("HRMANAGER").getId());
    }

    // return true if the employee is transport manager
    private boolean isEmployeeTranpostManager(int id) {
        Employee employee = getEmployeeById(id);
        List<Integer> managerRoles = employee.getRoles();
        return managerRoles.contains(roleClass.getRoleByName("TRANSPORTMANAGER").getId());
    }

    // check if the employee is a HRmanager and is sign in to the system
    // throw an error if something went wrong
    public void checkHrManager(int managerId) {
        if (!isEmployeeExistsAndLoadEmployee(managerId) || !isEmployeeHRManager(managerId) || !isEmployeeLoggedIn(managerId)) {
            throw new Error("You must be logged in, and be an HR manager in order to do that action.");
        }
    }

    // check if the employee is a manager and is sign in to the system
    // throw an error if something went wrong
    public void checkManager(int managerId) {
        if (!isEmployeeExistsAndLoadEmployee(managerId) || !isEmployeeLoggedIn(managerId)
                || !(isEmployeeHRManager(managerId) || isEmployeeTranpostManager(managerId))) {
            throw new Error("You must be logged in, and be an HR or Transport manager in order to do that action.");
        }
    }

    // check for exsisting employee with current id
    // throw an error if something went wrong
    public void checkEmployee(int idEmployee) {
        if (!isEmployeeExistsAndLoadEmployee(idEmployee)) {
            throw new Error("The id " + idEmployee + " is not in the system. Please try again");
        }
    }

    // help function that create HR manager to start up the system
    private void addHRManagerForStartUpTheSystem(String firstName, String lastName, int id, String password,
            int bankNum,
            int bankBranch, int bankAccount, int salary, int bonus, LocalDate startDate, String tempsEmployment,
            License driverLicense, Integer role, int branch) {
        Employee e = new Employee(firstName, lastName, id, password, bankNum,
                bankBranch, bankAccount, salary, bonus, startDate, tempsEmployment, role, branch);
        employees.add(e);
        this.employeesDAO.insert(e.toDTO());
    }

    // help function that create HR manager to start up the system
    private void addTransportManagerForStartUpTheSystem(String firstName, String lastName, int id, String password,
            int bankNum,
            int bankBranch, int bankAccount, int salary, int bonus, LocalDate startDate, String tempsEmployment,
            License driverLicense, Integer role, int branch) {
        Employee e = new Employee(firstName, lastName, id, password, bankNum,
                bankBranch, bankAccount, salary, bonus, startDate, tempsEmployment, role, branch);
        employees.add(e);
        this.employeesDAO.insert(e.toDTO());
    }
}
