package BusinessLayer.Suppliers;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

class FixedDaysSupplier extends Supplier {
    private List<DayOfWeek> days;

    // Copy constructor
    public FixedDaysSupplier(int id, String name, String phone, String bankAcc, List<String> fields,
            String paymentCondition,
            TreeMap<Integer, Discount> amountToDiscount, List<Contact> contacts, List<Integer> days) {
        super(id, name, phone, bankAcc, fields, paymentCondition, amountToDiscount, contacts);
        this.days = makeDaysList(days);
    }

    // Creates an enum Day list from a list of integers which represents weekdays
    private List<DayOfWeek> makeDaysList(List<Integer> days) {
        List<DayOfWeek> daysList = new LinkedList<>();
        for (Integer day : days) {
            daysList.add(getDay(day));
        }
        return daysList;
    }

    // Converts an integer to a Day enum
    private DayOfWeek getDay(Integer dayNum) {
        switch (dayNum) {
            case 1:
                return DayOfWeek.SUNDAY;
            case 2:
                return DayOfWeek.MONDAY;
            case 3:
                return DayOfWeek.TUESDAY;
            case 4:
                return DayOfWeek.WEDNESDAY;
            case 5:
                return DayOfWeek.THURSDAY;
            case 6:
                return DayOfWeek.FRIDAY;
            case 7:
                return DayOfWeek.SATURDAY;
            default:
                return null;

        }
    }

    @Override
    public String toString() {
        return super.toString() + "\nSupplier Type: Fixed Days Supplier\nDays: " + this.days;
    }

    @Override
    public LocalDate getClosestDeliveryDate() {
        LocalDate closestDay = LocalDate.now().plusDays(1);

        while (!days.contains(closestDay.getDayOfWeek()))
            closestDay = closestDay.plusDays(1);

        return closestDay;
    }

}
