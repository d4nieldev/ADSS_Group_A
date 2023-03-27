package BussinessLayer.EmployeesMoudle;
import java.util.Map;

import Misc.Role;
import Misc.Day;
import Misc.ShiftTime;


class Shift{
    private Day day;
    private ShiftTime time;
    private Map<Employee, Role> roles;
    private Map<Employee, Role> constraints;
    private List<int> cancellations;

    public Shift(String _day){
        if (_day == "Sunday") day = Day.Sunday;
        else if (_day == "Monday") day = Day.Monday;
        else if (_day == "Tuesday") day = Day.Tuesday;
        else if (_day == "Wednesday") day = Day.Wednesday;
        else if (_day == "Thursday") day = Day.Thursday;
        else if (_day == "Friday") day = Day.Friday;
        else if (_day == "Saturday") day = Day.Saturday;

    }
}