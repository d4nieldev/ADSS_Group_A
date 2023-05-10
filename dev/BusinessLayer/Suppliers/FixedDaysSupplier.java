package BusinessLayer.Suppliers;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import BusinessLayer.enums.Day;

class FixedDaysSupplier extends Supplier {
    private List<Day> days;

    // Copy constructor
    public FixedDaysSupplier(int id, String name, String phone, String bankAcc, List<String> fields,
            String paymentCondition,
            TreeMap<Integer, Double> amountToDiscount, List<Contact> contacts, List<Integer> days) {
        super(id, name, phone, bankAcc, fields, paymentCondition, amountToDiscount, contacts);
        this.days = makeDaysList(days);
    }

    // Constructor without contacts, reservation history and fields
    public FixedDaysSupplier(int id, String name, String phone, String bankAcc, String paymentCondition,
            TreeMap<Integer, Double> amountToDiscount, List<Integer> days) {
        super(id, name, phone, bankAcc, paymentCondition, amountToDiscount);
        this.days = makeDaysList(days);
    }

    // Constructor without reservation history and contacts
    public FixedDaysSupplier(int id, String name, String phone, String bankAcc, List<String> fields,
            String paymentCondition,
            TreeMap<Integer, Double> amountToDiscount, List<Integer> days) {
        super(id, name, phone, bankAcc, fields, paymentCondition, amountToDiscount);
        this.days = makeDaysList(days);

    }

    // Creates an enum Day list from a list of integers which represents weekdays
    private List<Day> makeDaysList(List<Integer> days) {
        List<Day> daysList = new LinkedList<Day>();
        for (Integer day : days) {
            daysList.add(getDay(day));
        }
        return daysList;
    }

    // Converts an integer to a Day enum
    private Day getDay(Integer dayNum) {
        switch (dayNum) {
            case 1:
                return Day.SUNDAY;
            case 2:
                return Day.MONDAY;
            case 3:
                return Day.TUESDAY;
            case 4:
                return Day.WEDNESDAY;
            case 5:
                return Day.THURSDAY;
            case 6:
                return Day.FRIDAY;
            case 7:
                return Day.SATURDAY;
            default:
                return null;

        }
    }

    @Override
    public String toString() {
        return super.toString() + "\nSupplier Type: Fixed Days Supplier\nDays: " + this.days;
    }
}
