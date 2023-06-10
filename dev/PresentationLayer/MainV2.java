package PresentationLayer;

import ServiceLayer.EmployeesLayer.serviceFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;

import Misc.License;
import Misc.ShiftTime;

class MainV2 {

    private static serviceFactory serviceFactory = new serviceFactory();
    private static HRSystem hrSystem;
    private static MemberSystem memberSystem;
    private static TransportSystem transportSystem;
    private static DriverSystem driverSystem;

    public static void main(String[] args) {

        hrSystem = new HRSystem(serviceFactory);
        memberSystem = new MemberSystem(serviceFactory);
        transportSystem = new TransportSystem(serviceFactory);
        driverSystem = new DriverSystem(serviceFactory);

        // //////////////// My Beautifual Test Area/////////////////////
        hrSystem.employeeService.logIn(123456789, "HRmanager");
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
         LocalDate localDate = LocalDate.parse("10-05-2024", formatter);

// instert drivers -------------------------------------------------------------------------------------------------------------------
        //  hrSystem.branchService.addNewDriver(123456789, "driver", "twelve", 112, "driver12",
        //   12, 12, 121212, 35, 1000, localDate, "driver temps",
        //    License.B);
        //  hrSystem.branchService.addNewDriver(123456789, "driver", "seven", 107, "driver7",
        //   7, 7, 7777, 35, 1000, localDate, "driver temps",
        //    License.C);
        //  hrSystem.branchService.addNewDriver(123456789, "driver", "eigth", 108, "driver8",
        //   8, 8, 8888, 35, 1000, localDate, "driver temps",
        //    License.C);
        //  hrSystem.branchService.addNewDriver(123456789, "driver", "nine", 109, "driver9",
        //   9, 9, 9999, 35, 1000, localDate, "driver temps",
        //    License.B);


// instert new employees -------------------------------------------------------------------------------------------------------------------
        //    hrSystem.branchService.addNewEmployee(123456789, "emp13", "name13", 213, "emp13",
        //     13, 13, 131313, 35, 1000, localDate, "employee temps",
        //      "BRANCHMANAGER", 1);
        //      hrSystem.branchService.addNewEmployee(123456789, "emp14", "name14", 214, "emp14",
        //       14, 14, 14141414, 35, 1000, localDate, "employee temps",
        //        "BRANCHMANAGER", 2);
        //      hrSystem.branchService.addNewEmployee(123456789, "emp15", "name15", 215, "emp15",
        //       15, 15, 15151515, 35, 1000, localDate, "employee temps",
        //        "BRANCHMANAGER", 3);
        //      hrSystem.branchService.addNewEmployee(123456789, "emp16", "name16", 216, "emp16",
        //       16, 16, 16161616, 35, 1000, localDate, "employee temps",
        //        "BRANCHMANAGER", 4);
        //      hrSystem.branchService.addNewEmployee(123456789, "emp17", "name17", 217, "emp17",
        //       17, 17, 17171717, 35, 1000, localDate, "employee temps",
        //        "BRANCHMANAGER", 5);
        //      hrSystem.branchService.addNewEmployee(123456789, "emp18", "name18", 218, "emp18",
        //       18, 18, 18181818, 35, 1000, localDate, "employee temps",
        //        "SHIFTMANAGER", 1);
        //      hrSystem.branchService.addNewEmployee(123456789, "emp19", "name19", 219, "emp19",
        //       19, 19, 19191919, 35, 1000, localDate, "employee temps",
        //        "SHIFTMANAGER", 3); 
        //  hrSystem.branchService.addNewEmployee(123456789, "emp20", "name20", 220, "emp20",
        //  20, 20, 202020, 35, 1000, localDate, "employee temps",
        //   "SHIFTMANAGER", 3);
        // hrSystem.branchService.addNewEmployee(123456789, "emp22", "name22", 222, "emp22",
        //  22, 22, 2222222, 35, 1000, localDate, "employee temps",
        //   "SHIFTMANAGER", 4);
        // hrSystem.branchService.addNewEmployee(123456789, "emp23", "name23", 223, "emp23",
        //  23, 23, 232323, 35, 1000, localDate, "employee temps",
        //   "SHIFTMANAGER", 5);
        // hrSystem.branchService.addNewEmployee(123456789, "emp24", "name24", 224, "emp24",
        //  24, 24, 242424, 35, 1000, localDate, "employee temps",
        //   "SHIFTMANAGER", 5);

        // hrSystem.branchService.addNewEmployee(123456789, "emp25", "name25", 225, "emp25",
        //  25, 25, 252525, 35, 1000, localDate, "employee temps",
        //   "STOREKEEPER", 1);
        //  hrSystem.branchService.addNewEmployee(123456789, "emp26", "name26", 226, "emp26",
        //  26, 26, 262626, 35, 1000, localDate, "employee temps",
        //   "STOREKEEPER", 1);
        // hrSystem.branchService.addNewEmployee(123456789, "emp27", "name27", 227, "emp27",
        //  27, 27, 272727, 35, 1000, localDate, "employee temps",
        //   "STOREKEEPER", 1);
        // hrSystem.branchService.addNewEmployee(123456789, "emp28", "name28", 228, "emp28",
        //  28, 28, 282828, 35, 1000, localDate, "employee temps",
        //   "STOREKEEPER", 2);
        //   hrSystem.branchService.addNewEmployee(123456789, "emp29", "name29", 229, "emp29",
        //    29, 29, 292929, 35, 1000, localDate, "employee temps",
        //     "STOREKEEPER", 3);
        //   hrSystem.branchService.addNewEmployee(123456789, "emp30", "name30", 230, "emp30",
        //    30, 30, 303030, 35, 1000, localDate, "employee temps",
        //     "STOREKEEPER", 3);
        //   hrSystem.branchService.addNewEmployee(123456789, "emp31", "name31", 231, "emp31",
        //    31, 31, 313131, 35, 1000, localDate, "employee temps",
        //     "STOREKEEPER", 3);
        // hrSystem.branchService.addNewEmployee(123456789, "emp32", "name32", 232, "emp32",
        //  32, 32, 323232, 35, 1000, localDate, "employee temps",
        //   "STOREKEEPER", 4);
        // hrSystem.branchService.addNewEmployee(123456789, "emp33", "name33", 233, "emp33",
        //  33, 33, 333333, 35, 1000, localDate, "employee temps",
        //   "STOREKEEPER", 4);
        // hrSystem.branchService.addNewEmployee(123456789, "emp34", "name34", 234, "emp34",
        //  34, 34, 343434, 35, 1000, localDate, "employee temps",
        //   "STOREKEEPER", 4);
        // hrSystem.branchService.addNewEmployee(123456789, "emp35", "name35", 235, "emp35",
        //  35, 35, 353535, 35, 1000, localDate, "employee temps",
        //   "STOREKEEPER", 5);
        // hrSystem.branchService.addNewEmployee(123456789, "emp36", "name36", 236, "emp36",
        //  36, 36, 363636, 35, 1000, localDate, "employee temps",
        //   "STOREKEEPER", 5);
        // hrSystem.branchService.addNewEmployee(123456789, "emp37", "name37", 237, "emp37",
        //  37, 37, 373737, 35, 1000, localDate, "employee temps",
        //   "STOREKEEPER", 5);

        //   hrSystem.branchService.addNewEmployee(123456789, "emp38", "name38", 238, "emp38",
        //       38, 38, 383838, 35, 1000, localDate, "employee temps",
        //        "SHIFTMANAGER", 1);
        //      hrSystem.branchService.addNewEmployee(123456789, "emp39", "name39", 239, "emp39",
        //       39, 39, 393939, 35, 1000, localDate, "employee temps",
        //        "SHIFTMANAGER", 3); 
        //  hrSystem.branchService.addNewEmployee(123456789, "emp40", "name40", 240, "emp40",
        //  40, 40, 404040, 35, 1000, localDate, "employee temps",
        //   "SHIFTMANAGER", 3);
        // hrSystem.branchService.addNewEmployee(123456789, "emp41", "name41", 241, "emp41",
        //  41, 41, 414141, 35, 1000, localDate, "employee temps",
        //   "SHIFTMANAGER", 4);
        // hrSystem.branchService.addNewEmployee(123456789, "emp42", "name42", 242, "emp42",
        //  42, 42, 424242, 35, 1000, localDate, "employee temps",
        //   "SHIFTMANAGER", 5);

        // hrSystem.branchService.addNewEmployee(123456789, "emp43", "name43", 243, "emp43",
        //  43, 43, 434343, 35, 1000, localDate, "employee temps",
        //   "STOREKEEPER", 1);
        // hrSystem.branchService.addNewEmployee(123456789, "emp44", "name44", 244, "emp44",
        //  44, 44, 4444444, 35, 1000, localDate, "employee temps",
        //   "STOREKEEPER", 1);
        // hrSystem.branchService.addNewEmployee(123456789, "emp45", "name45", 245, "emp45",
        //  45, 45, 454545, 35, 1000, localDate, "employee temps",
        //   "STOREKEEPER", 2);
        //   hrSystem.branchService.addNewEmployee(123456789, "emp46", "name46", 246, "emp46",
        //    46, 46, 464646, 35, 1000, localDate, "employee temps",
        //     "STOREKEEPER", 3);
        //   hrSystem.branchService.addNewEmployee(123456789, "emp47", "name47", 247, "emp47",
        //    47, 47, 474747, 35, 1000, localDate, "employee temps",
        //     "STOREKEEPER", 3);
        //   hrSystem.branchService.addNewEmployee(123456789, "emp48", "name48", 248, "emp48",
        //    48, 48, 484848, 35, 1000, localDate, "employee temps",
        //     "STOREKEEPER", 3);
        // hrSystem.branchService.addNewEmployee(123456789, "emp49", "name49", 249, "emp49",
        //  49, 49, 494949, 35, 1000, localDate, "employee temps",
        //   "STOREKEEPER", 4);
        // hrSystem.branchService.addNewEmployee(123456789, "emp50", "name50", 250, "emp50",
        //  50, 50, 505050, 35, 1000, localDate, "employee temps",
        //   "STOREKEEPER", 4);
        // hrSystem.branchService.addNewEmployee(123456789, "emp51", "name51", 251, "emp51",
        //  51, 51, 515151, 35, 1000, localDate, "employee temps",
        //   "STOREKEEPER", 4);
        // hrSystem.branchService.addNewEmployee(123456789, "emp52", "name52", 252, "emp52",
        //  52, 52, 525252, 35, 1000, localDate, "employee temps",
        //   "STOREKEEPER", 5);
        // hrSystem.branchService.addNewEmployee(123456789, "emp53", "name53", 253, "emp53",
        //  53, 53, 535353, 35, 1000, localDate, "employee temps",
        //   "STOREKEEPER", 5);
        // hrSystem.branchService.addNewEmployee(123456789, "emp54", "name54", 254, "emp54",
        //  54, 54, 545454, 35, 1000, localDate, "employee temps",
        //   "STOREKEEPER", 5);
          
        // hrSystem.branchService.addNewEmployee(123456789, "emp55", "name55", 255, "emp55",
        // 55, 55, 555555, 35, 1000, localDate, "employee temps",
        //  "CASHIER", 1);
    //     hrSystem.branchService.addNewEmployee(123456789, "emp56", "name56", 256, "emp56",
    //     56, 56, 565656, 35, 1000, localDate, "employee temps",
    //      "CASHIER", 1);
    //    hrSystem.branchService.addNewEmployee(123456789, "emp57", "name57", 257, "emp57",
    //     57, 57, 575757, 35, 1000, localDate, "employee temps",
    //      "CASHIER", 1);
    //    hrSystem.branchService.addNewEmployee(123456789, "emp58", "name58", 258, "emp58",
    //     58, 58, 585858, 35, 1000, localDate, "employee temps",
    //      "CASHIER", 2);
    //      hrSystem.branchService.addNewEmployee(123456789, "emp59", "name59", 259, "emp59",
    //       59, 59, 595959, 35, 1000, localDate, "employee temps",
    //        "CASHIER", 3);
    //      hrSystem.branchService.addNewEmployee(123456789, "emp60", "name60", 260, "emp60",
    //       60, 60, 606060, 35, 1000, localDate, "employee temps",
    //        "CASHIER", 3);
    //      hrSystem.branchService.addNewEmployee(123456789, "emp61", "name61", 261, "emp61",
    //       61, 61, 616161, 35, 1000, localDate, "employee temps",
    //        "CASHIER", 3);
    //    hrSystem.branchService.addNewEmployee(123456789, "emp62", "name62", 262, "emp62",
    //     62, 62, 626262, 35, 1000, localDate, "employee temps",
    //      "CASHIER", 4);
    //    hrSystem.branchService.addNewEmployee(123456789, "emp63", "name63", 263, "emp63",
    //     63, 63, 636363, 35, 1000, localDate, "employee temps",
    //      "CASHIER", 4);
    //    hrSystem.branchService.addNewEmployee(123456789, "emp64", "name64", 264, "emp64",
    //     64, 64, 646464, 35, 1000, localDate, "employee temps",
    //      "CASHIER", 4);
    //    hrSystem.branchService.addNewEmployee(123456789, "emp65", "name65", 265, "emp65",
    //     65, 65, 656565, 35, 1000, localDate, "employee temps",
    //      "CASHIER", 5);
    //    hrSystem.branchService.addNewEmployee(123456789, "emp66", "name66", 266, "emp66",
    //     66, 66, 6666666, 35, 1000, localDate, "employee temps",
    //      "CASHIER", 5);
    //    hrSystem.branchService.addNewEmployee(123456789, "emp67", "name67", 267, "emp67",
    //     67, 67, 676767, 35, 1000, localDate, "employee temps",
    //      "CASHIER", 5);
         
    //     hrSystem.branchService.addNewEmployee(123456789, "emp68", "name68", 268, "emp68",
    //     68, 68, 686868, 35, 1000, localDate, "employee temps",
    //      "GENERRAL", 1);
    //     hrSystem.branchService.addNewEmployee(123456789, "emp69", "name69", 269, "emp69",
    //     69, 69, 696969, 35, 1000, localDate, "employee temps",
    //      "CLEANER", 1);
    //    hrSystem.branchService.addNewEmployee(123456789, "emp70", "name70", 270, "emp70",
    //     70, 70, 707070, 35, 1000, localDate, "employee temps",
    //      "SECURITY", 1);
    //    hrSystem.branchService.addNewEmployee(123456789, "emp71", "name71", 271, "emp71",
    //     71, 71, 717171, 35, 1000, localDate, "employee temps",
    //      "GENERRAL", 2);
    //      hrSystem.branchService.addNewEmployee(123456789, "emp72", "name72", 272, "emp72",
    //       72, 72, 727272, 35, 1000, localDate, "employee temps",
    //        "CLEANER", 3);
    //      hrSystem.branchService.addNewEmployee(123456789, "emp73", "name73", 273, "emp73",
    //       73, 73, 737373, 35, 1000, localDate, "employee temps",
    //        "GENERRAL", 3);
    //      hrSystem.branchService.addNewEmployee(123456789, "emp74", "name74", 274, "emp74",
    //       74, 74, 747474, 35, 1000, localDate, "employee temps",
    //        "CLEANER", 3);
    //    hrSystem.branchService.addNewEmployee(123456789, "emp75", "name75", 275, "emp75",
    //     75, 75, 757575, 35, 1000, localDate, "employee temps",
    //      "GENERRAL", 4);
    //    hrSystem.branchService.addNewEmployee(123456789, "emp76", "name76", 276, "emp76",
    //     76, 76, 767676, 35, 1000, localDate, "employee temps",
    //      "CLEANER", 4);
    //    hrSystem.branchService.addNewEmployee(123456789, "emp77", "name77", 277, "emp77",
    //     77, 77, 777777, 35, 1000, localDate, "employee temps",
    //      "SECURITY", 4);
    //    hrSystem.branchService.addNewEmployee(123456789, "emp78", "name78", 278, "emp78",
    //     78, 78, 787878, 35, 1000, localDate, "employee temps",
    //      "GENERRAL", 5);
    //    hrSystem.branchService.addNewEmployee(123456789, "emp79", "name79", 279, "emp79",
    //     79, 79, 797979, 35, 1000, localDate, "employee temps",
    //      "CLEANER", 5);
    //    hrSystem.branchService.addNewEmployee(123456789, "emp80", "name80", 280, "emp80",
    //     80, 80, 808080, 35, 1000, localDate, "employee temps",
    //      "SECURITY", 5);

// add constraints for drivers -------------------------------------------------------------------------------------------------------------------
           hrSystem.employeeService.AddConstraintDriver(101, LocalDate.parse("10-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(102, LocalDate.parse("10-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(103, LocalDate.parse("10-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(104, LocalDate.parse("10-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(105, LocalDate.parse("10-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(106, LocalDate.parse("10-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(107, LocalDate.parse("10-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(108, LocalDate.parse("10-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(109, LocalDate.parse("10-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(110, LocalDate.parse("10-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(111, LocalDate.parse("10-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(112, LocalDate.parse("10-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(101, LocalDate.parse("08-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(102, LocalDate.parse("08-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(103, LocalDate.parse("08-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(104, LocalDate.parse("08-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(105, LocalDate.parse("08-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(106, LocalDate.parse("08-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(107, LocalDate.parse("08-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(108, LocalDate.parse("08-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(109, LocalDate.parse("08-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(110, LocalDate.parse("08-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(111, LocalDate.parse("08-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(112, LocalDate.parse("08-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(101, LocalDate.parse("07-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(102, LocalDate.parse("07-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(103, LocalDate.parse("07-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(104, LocalDate.parse("07-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(105, LocalDate.parse("07-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(106, LocalDate.parse("07-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(107, LocalDate.parse("07-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(108, LocalDate.parse("07-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(109, LocalDate.parse("07-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(110, LocalDate.parse("07-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(111, LocalDate.parse("07-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(112, LocalDate.parse("07-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(101, LocalDate.parse("06-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(102, LocalDate.parse("06-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(103, LocalDate.parse("06-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(104, LocalDate.parse("06-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(105, LocalDate.parse("06-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(106, LocalDate.parse("06-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(107, LocalDate.parse("06-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(108, LocalDate.parse("06-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(109, LocalDate.parse("06-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(110, LocalDate.parse("06-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(111, LocalDate.parse("06-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(112, LocalDate.parse("06-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(101, LocalDate.parse("04-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(102, LocalDate.parse("04-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(103, LocalDate.parse("04-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(104, LocalDate.parse("04-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(105, LocalDate.parse("04-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(106, LocalDate.parse("04-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(107, LocalDate.parse("04-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(108, LocalDate.parse("04-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(109, LocalDate.parse("04-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(110, LocalDate.parse("04-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(111, LocalDate.parse("04-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(112, LocalDate.parse("04-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(101, LocalDate.parse("05-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(102, LocalDate.parse("05-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(103, LocalDate.parse("05-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(104, LocalDate.parse("05-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(105, LocalDate.parse("05-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(106, LocalDate.parse("05-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(107, LocalDate.parse("05-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(108, LocalDate.parse("05-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(109, LocalDate.parse("05-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(110, LocalDate.parse("05-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(111, LocalDate.parse("05-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(112, LocalDate.parse("05-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(101, LocalDate.parse("09-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(102, LocalDate.parse("09-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(103, LocalDate.parse("09-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(104, LocalDate.parse("09-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(105, LocalDate.parse("09-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(106, LocalDate.parse("09-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(107, LocalDate.parse("09-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(108, LocalDate.parse("09-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(109, LocalDate.parse("09-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(110, LocalDate.parse("09-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(111, LocalDate.parse("09-06-2024", formatter));
           hrSystem.employeeService.AddConstraintDriver(112, LocalDate.parse("09-06-2024", formatter));

// add new shifts to branch 1 -------------------------------------------------------------------------------------------------------------------
           HashMap<Integer, Integer> hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 1);
           hash.put(10, 1);
           hash.put(11, 1);
           hrSystem.branchService.addShift(123456789, 1, LocalDate.parse("04-06-2024", formatter),
            9, 16, ShiftTime.MORNING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 1);
           hash.put(10, 1);
           hash.put(11, 1);
           hrSystem.branchService.addShift(123456789, 1, LocalDate.parse("04-06-2024", formatter),
            14, 21, ShiftTime.EVENING, hash);
        
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 1);
           hash.put(10, 1);
           hash.put(11, 1);
           hrSystem.branchService.addShift(123456789, 1, LocalDate.parse("05-06-2024", formatter),
            9, 16, ShiftTime.MORNING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 1);
           hash.put(10, 1);
           hash.put(11, 1);
           hrSystem.branchService.addShift(123456789, 1, LocalDate.parse("05-06-2024", formatter),
            14, 21, ShiftTime.EVENING, hash);
        
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 1);
           hash.put(10, 1);
           hash.put(11, 1);
           hrSystem.branchService.addShift(123456789, 1, LocalDate.parse("06-06-2024", formatter),
            9, 16, ShiftTime.MORNING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 1);
           hash.put(10, 1);
           hash.put(11, 1);
           hrSystem.branchService.addShift(123456789, 1, LocalDate.parse("06-06-2024", formatter),
            14, 21, ShiftTime.EVENING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 1);
           hash.put(10, 1);
           hash.put(11, 1);
           hrSystem.branchService.addShift(123456789, 1, LocalDate.parse("07-06-2024", formatter),
            9, 16, ShiftTime.MORNING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 1);
           hash.put(10, 1);
           hash.put(11, 1);
           hrSystem.branchService.addShift(123456789, 1, LocalDate.parse("07-06-2024", formatter),
            14, 21, ShiftTime.EVENING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 1);
           hash.put(10, 1);
           hash.put(11, 1);
           hrSystem.branchService.addShift(123456789, 1, LocalDate.parse("08-06-2024", formatter),
            9, 16, ShiftTime.MORNING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 1);
           hash.put(10, 1);
           hash.put(11, 1);
           hrSystem.branchService.addShift(123456789, 1, LocalDate.parse("08-06-2024", formatter),
            14, 21, ShiftTime.EVENING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 1);
           hash.put(10, 1);
           hash.put(11, 1);
           hrSystem.branchService.addShift(123456789, 1, LocalDate.parse("09-06-2024", formatter),
            9, 16, ShiftTime.MORNING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 1);
           hash.put(10, 1);
           hash.put(11, 1);
           hrSystem.branchService.addShift(123456789, 1, LocalDate.parse("09-06-2024", formatter),
            14, 21, ShiftTime.EVENING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 1);
           hash.put(10, 1);
           hash.put(11, 1);
           hrSystem.branchService.addShift(123456789, 1, LocalDate.parse("10-06-2024", formatter),
            9, 16, ShiftTime.MORNING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 1);
           hash.put(10, 1);
           hash.put(11, 1);
           hrSystem.branchService.addShift(123456789, 1, LocalDate.parse("10-06-2024", formatter),
            14, 21, ShiftTime.EVENING, hash);
            
// add new shifts to branch 2 ------------------------------------------------------------------------------------------------------------------
        hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 2);
           hash.put(6, 2);
           hash.put(7, 1);
           hash.put(9, 1);
           hash.put(10, 0);
           hash.put(11, 0);
           hrSystem.branchService.addShift(123456789, 2, LocalDate.parse("04-06-2024", formatter),
            9, 16, ShiftTime.MORNING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 2);
           hash.put(6, 2);
           hash.put(7, 1);
           hash.put(9, 1);
           hash.put(10, 0);
           hash.put(11, 0);
           hrSystem.branchService.addShift(123456789, 2, LocalDate.parse("04-06-2024", formatter),
            14, 21, ShiftTime.EVENING, hash);
        
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 2);
           hash.put(6, 2);
           hash.put(7, 1);
           hash.put(9, 1);
           hash.put(10, 0);
           hash.put(11, 0);
           hrSystem.branchService.addShift(123456789, 2, LocalDate.parse("05-06-2024", formatter),
            9, 16, ShiftTime.MORNING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 2);
           hash.put(6, 2);
           hash.put(7, 1);
           hash.put(9, 1);
           hash.put(10, 0);
           hash.put(11, 0);
           hrSystem.branchService.addShift(123456789, 2, LocalDate.parse("05-06-2024", formatter),
            14, 21, ShiftTime.EVENING, hash);
        
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 2);
           hash.put(6, 2);
           hash.put(7, 1);
           hash.put(9, 1);
           hash.put(10, 0);
           hash.put(11, 0);
           hrSystem.branchService.addShift(123456789, 2, LocalDate.parse("06-06-2024", formatter),
            9, 16, ShiftTime.MORNING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 2);
           hash.put(6, 2);
           hash.put(7, 1);
           hash.put(9, 1);
           hash.put(10, 0);
           hash.put(11, 0);
           hrSystem.branchService.addShift(123456789, 2, LocalDate.parse("06-06-2024", formatter),
            14, 21, ShiftTime.EVENING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 1);
           hash.put(10, 0);
           hash.put(11, 0);
           hrSystem.branchService.addShift(123456789, 2, LocalDate.parse("07-06-2024", formatter),
            9, 16, ShiftTime.MORNING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 1);
           hash.put(10, 0);
           hash.put(11, 0);
           hrSystem.branchService.addShift(123456789, 2, LocalDate.parse("07-06-2024", formatter),
            14, 21, ShiftTime.EVENING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 1);
           hash.put(10, 0);
           hash.put(11, 0);
           hrSystem.branchService.addShift(123456789, 2, LocalDate.parse("08-06-2024", formatter),
            9, 15, ShiftTime.MORNING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 1);
           hash.put(10, 0);
           hash.put(11, 0);
           hrSystem.branchService.addShift(123456789, 2, LocalDate.parse("08-06-2024", formatter),
            14, 20, ShiftTime.EVENING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 1);
           hash.put(10, 1);
           hash.put(11, 1);
           hrSystem.branchService.addShift(123456789, 1, LocalDate.parse("09-06-2024", formatter),
            9, 152, ShiftTime.MORNING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 1);
           hash.put(10, 0);
           hash.put(11, 0);
           hrSystem.branchService.addShift(123456789, 2, LocalDate.parse("09-06-2024", formatter),
            14, 30, ShiftTime.EVENING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 1);
           hash.put(10, 0);
           hash.put(11, 0);
           hrSystem.branchService.addShift(123456789, 2, LocalDate.parse("10-06-2024", formatter),
            9, 15, ShiftTime.MORNING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 1);
           hash.put(10, 0);
           hash.put(11, 0);
           hrSystem.branchService.addShift(123456789, 2, LocalDate.parse("10-06-2024", formatter),
            14, 20, ShiftTime.EVENING, hash);

// add new shifts to branch 3 ------------------------------------------------------------------------------------------------------------------
        hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 2);
           hash.put(9, 0);
           hash.put(10, 1);
           hash.put(11, 1);
           hrSystem.branchService.addShift(123456789, 3, LocalDate.parse("04-06-2024", formatter),
            10, 16, ShiftTime.MORNING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 2);
           hash.put(9, 0);
           hash.put(10, 1);
           hash.put(11, 1);
           hrSystem.branchService.addShift(123456789, 3, LocalDate.parse("04-06-2024", formatter),
            16, 21, ShiftTime.EVENING, hash);
        
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 2);
           hash.put(9, 0);
           hash.put(10, 1);
           hash.put(11, 1);
           hrSystem.branchService.addShift(123456789, 3, LocalDate.parse("05-06-2024", formatter),
            10, 16, ShiftTime.MORNING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 2);
           hash.put(9, 0);
           hash.put(10, 1);
           hash.put(11, 1);
           hrSystem.branchService.addShift(123456789, 3, LocalDate.parse("05-06-2024", formatter),
            16, 21, ShiftTime.EVENING, hash);
        
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 2);
           hash.put(9, 0);
           hash.put(10, 1);
           hash.put(11, 1);
           hrSystem.branchService.addShift(123456789, 3, LocalDate.parse("06-06-2024", formatter),
            10, 16, ShiftTime.MORNING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 2);
           hash.put(9, 0);
           hash.put(10, 1);
           hash.put(11, 1);
           hrSystem.branchService.addShift(123456789, 3, LocalDate.parse("06-06-2024", formatter),
            16, 21, ShiftTime.EVENING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 2);
           hash.put(9, 0);
           hash.put(10, 1);
           hash.put(11, 1);
           hrSystem.branchService.addShift(123456789, 3, LocalDate.parse("07-06-2024", formatter),
            10, 16, ShiftTime.MORNING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 2);
           hash.put(9, 0);
           hash.put(10, 1);
           hash.put(11, 1);
           hrSystem.branchService.addShift(123456789, 3, LocalDate.parse("07-06-2024", formatter),
            16, 21, ShiftTime.EVENING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 2);
           hash.put(9, 0);
           hash.put(10, 1);
           hash.put(11, 1);
           hrSystem.branchService.addShift(123456789, 3, LocalDate.parse("08-06-2024", formatter),
            10, 16, ShiftTime.MORNING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 2);
           hash.put(9, 0);
           hash.put(10, 1);
           hash.put(11, 1);
           hrSystem.branchService.addShift(123456789, 3, LocalDate.parse("08-06-2024", formatter),
            16, 21, ShiftTime.EVENING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 2);
           hash.put(9, 0);
           hash.put(10, 1);
           hash.put(11, 1);
           hrSystem.branchService.addShift(123456789, 3, LocalDate.parse("09-06-2024", formatter),
            10, 16, ShiftTime.MORNING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 2);
           hash.put(9, 0);
           hash.put(10, 1);
           hash.put(11, 1);
           hrSystem.branchService.addShift(123456789, 3, LocalDate.parse("09-06-2024", formatter),
            16, 21, ShiftTime.EVENING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 2);
           hash.put(9, 0);
           hash.put(10, 1);
           hash.put(11, 1);
           hrSystem.branchService.addShift(123456789, 3, LocalDate.parse("10-06-2024", formatter),
            9, 16, ShiftTime.MORNING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 2);
           hash.put(9, 0);
           hash.put(10, 1);
           hash.put(11, 1);
           hrSystem.branchService.addShift(123456789, 3, LocalDate.parse("10-06-2024", formatter),
            14, 21, ShiftTime.EVENING, hash);

// add new shifts to branch 4 ------------------------------------------------------------------------------------------------------------------
        hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 0);
           hash.put(10, 0);
           hash.put(11, 0);
           hrSystem.branchService.addShift(123456789, 4, LocalDate.parse("04-06-2024", formatter),
            9, 16, ShiftTime.MORNING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 0);
           hash.put(10, 0);
           hash.put(11, 0);
           hrSystem.branchService.addShift(123456789, 4, LocalDate.parse("04-06-2024", formatter),
            14, 21, ShiftTime.EVENING, hash);
        
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 0);
           hash.put(10, 0);
           hash.put(11, 0);
           hrSystem.branchService.addShift(123456789, 4, LocalDate.parse("05-06-2024", formatter),
            9, 16, ShiftTime.MORNING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 0);
           hash.put(10, 0);
           hash.put(11, 0);
           hrSystem.branchService.addShift(123456789, 4, LocalDate.parse("05-06-2024", formatter),
            14, 21, ShiftTime.EVENING, hash);
        
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 0);
           hash.put(10, 0);
           hash.put(11, 0);
           hrSystem.branchService.addShift(123456789, 4, LocalDate.parse("06-06-2024", formatter),
            9, 16, ShiftTime.MORNING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 0);
           hash.put(10, 0);
           hash.put(11, 0);
           hrSystem.branchService.addShift(123456789, 4, LocalDate.parse("06-06-2024", formatter),
            14, 21, ShiftTime.EVENING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 0);
           hash.put(10, 0);
           hash.put(11, 0);
           hrSystem.branchService.addShift(123456789, 4, LocalDate.parse("07-06-2024", formatter),
            9, 16, ShiftTime.MORNING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 0);
           hash.put(10, 0);
           hash.put(11, 0);
           hrSystem.branchService.addShift(123456789, 4, LocalDate.parse("07-06-2024", formatter),
            14, 21, ShiftTime.EVENING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 0);
           hash.put(10, 0);
           hash.put(11, 0);
           hrSystem.branchService.addShift(123456789, 4, LocalDate.parse("08-06-2024", formatter),
            9, 16, ShiftTime.MORNING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 0);
           hash.put(10, 0);
           hash.put(11, 0);
           hrSystem.branchService.addShift(123456789, 4, LocalDate.parse("08-06-2024", formatter),
            14, 21, ShiftTime.EVENING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 0);
           hash.put(10, 0);
           hash.put(11, 0);
           hrSystem.branchService.addShift(123456789, 4, LocalDate.parse("09-06-2024", formatter),
            9, 16, ShiftTime.MORNING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 0);
           hash.put(10, 0);
           hash.put(11, 0);
           hrSystem.branchService.addShift(123456789, 4, LocalDate.parse("09-06-2024", formatter),
            14, 21, ShiftTime.EVENING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 0);
           hash.put(10, 0);
           hash.put(11, 0);
           hrSystem.branchService.addShift(123456789, 4, LocalDate.parse("10-06-2024", formatter),
            9, 16, ShiftTime.MORNING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 0);
           hash.put(10, 0);
           hash.put(11, 0);
           hrSystem.branchService.addShift(123456789, 4, LocalDate.parse("10-06-2024", formatter),
            14, 21, ShiftTime.EVENING, hash);

// add new shifts to branch 5 ------------------------------------------------------------------------------------------------------------------
        hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 0);
           hash.put(10, 0);
           hash.put(11, 0);
           hrSystem.branchService.addShift(123456789, 5, LocalDate.parse("04-06-2024", formatter),
            10, 14, ShiftTime.MORNING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 0);
           hash.put(10, 0);
           hash.put(11, 0);
           hrSystem.branchService.addShift(123456789, 5, LocalDate.parse("04-06-2024", formatter),
            14, 20, ShiftTime.EVENING, hash);
        
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 0);
           hash.put(10, 0);
           hash.put(11, 0);
           hrSystem.branchService.addShift(123456789, 5, LocalDate.parse("05-06-2024", formatter),
            10, 14, ShiftTime.MORNING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 0);
           hash.put(10, 0);
           hash.put(11, 0);
           hrSystem.branchService.addShift(123456789, 5, LocalDate.parse("05-06-2024", formatter),
            14, 20, ShiftTime.EVENING, hash);
        
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 0);
           hash.put(10, 0);
           hash.put(11, 0);
           hrSystem.branchService.addShift(123456789, 5, LocalDate.parse("06-06-2024", formatter),
            10, 14, ShiftTime.MORNING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 0);
           hash.put(10, 0);
           hash.put(11, 0);
           hrSystem.branchService.addShift(123456789, 5, LocalDate.parse("06-06-2024", formatter),
            14, 20, ShiftTime.EVENING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 0);
           hash.put(10, 0);
           hash.put(11, 0);
           hrSystem.branchService.addShift(123456789, 5, LocalDate.parse("07-06-2024", formatter),
            10, 14, ShiftTime.MORNING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 0);
           hash.put(10, 0);
           hash.put(11, 0);
           hrSystem.branchService.addShift(123456789, 5, LocalDate.parse("07-06-2024", formatter),
            14, 20, ShiftTime.EVENING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 0);
           hash.put(10, 0);
           hash.put(11, 0);
           hrSystem.branchService.addShift(123456789, 5, LocalDate.parse("08-06-2024", formatter),
            10, 14, ShiftTime.MORNING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 0);
           hash.put(10, 0);
           hash.put(11, 0);
           hrSystem.branchService.addShift(123456789, 5, LocalDate.parse("08-06-2024", formatter),
            14, 20, ShiftTime.EVENING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 0);
           hash.put(10, 0);
           hash.put(11, 0);
           hrSystem.branchService.addShift(123456789, 5, LocalDate.parse("09-06-2024", formatter),
            10, 14, ShiftTime.MORNING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 0);
           hash.put(10, 0);
           hash.put(11, 0);
           hrSystem.branchService.addShift(123456789, 5, LocalDate.parse("09-06-2024", formatter),
            14, 20, ShiftTime.EVENING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 0);
           hash.put(10, 0);
           hash.put(11, 0);
           hrSystem.branchService.addShift(123456789, 5, LocalDate.parse("10-06-2024", formatter),
            10, 14, ShiftTime.MORNING, hash);
            
           hash = new HashMap<>();
           hash.put(4, 1);
           hash.put(5, 1);
           hash.put(6, 1);
           hash.put(7, 1);
           hash.put(9, 0);
           hash.put(10, 0);
           hash.put(11, 0);
           hrSystem.branchService.addShift(123456789, 5, LocalDate.parse("10-06-2024", formatter),
            14, 20, ShiftTime.EVENING, hash);
                                                

        // hrSystem.branchService.addNewEmployee(123456789,
        //////////////// "printAvailableShiftForEmployeeCheck", "Hatuli", 999, "123", 0,
        //////////////// 0, 0, 0, 0, localDate, "dsf", "cashier", 1);
        // System.out.println(hrSystem.employeeService.printAllEmployees(123456789));
        // System.out.println(hrSystem.branchService.printAllBranches(123456789));
        //System.out.println(hrSystem.employeeService.printAllDrivers(123456789));
        // System.out.println("check");
        // -----------------add new shift-----------------
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        // LocalDate localDate = LocalDate.parse("31-05-2024", formatter);
        // HashMap<Integer, Integer> numEmployeesForRole = new HashMap<>();
        // numEmployeesForRole.put(5, 1);
        // numEmployeesForRole.put(6, 1);
        // hrSystem.branchService.addShift(123456789, 1, localDate, 9, 17,
        //////////////// ShiftTime.MORNING, numEmployeesForRole);
        // -----------------submit final shift-----------------
        // HashMap<Integer, Integer> shiftAssign = new HashMap<>();
        // shiftAssign.put(999, 5);
        // hrSystem.branchService.approveFinalShift(123456789, 5, 1, shiftAssign);
        // -----------------add constraint-----------------
        // hrSystem.employeeService.logIn(207, "emp7");
        // memberSystem.branchService.addConstraint(2, 207, 3);
        // -----------------show all available shift for employee-----------------
        //  hrSystem.employeeService.logIn(207, "emp7");
        //  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        //  LocalDate localDate = LocalDate.parse("30-05-2024", formatter);
        //  System.out.println(memberSystem.branchService.printAvailableShiftForEmployee(207, localDate));
        // -----------------delete an employee-----------------
        // hrSystem.employeeService.logIn(123456789, "abc");
        // hrSystem.branchService.deleteEmployee(123456789, 999);
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        // LocalDate localDate = LocalDate.parse("30-05-2024", formatter);
        // hrSystem.employeeService.logIn(345, "345");
        // hrSystem.employeeService.AddConstraintDriver(345, localDate);
        //////////////// My Beautifual Test Area/////////////////////

        Scanner sc = new Scanner(System.in);

        while (true) {

            // System.out.println("1 - Exit the system\n2 - Login to the system\n");
            System.out.println(getMenu() + "\n");
            int option = Integer.parseInt(sc.nextLine());
            if (option == 1) {
                System.out.print("Hello there, in order to login to the system please enter your Id: ");
                int loginId = Integer.parseInt(sc.nextLine());
                System.out.println("");

                System.out.print("Great, now enter your password: ");
                String loginPassword = sc.nextLine();
                System.out.println("");

                while (true) {
                    try {
                        hrSystem.employeeService.logIn(loginId, loginPassword);
                        System.out.println("");
                        break;
                    } catch (Error e) {
                        System.out.println(e.toString());
                        System.out.println();
                        System.out.print("Enter your Id again: ");
                        loginId = Integer.parseInt(sc.nextLine());
                        System.out.println("");

                        System.out.print("Enter your password again: ");
                        loginPassword = sc.nextLine();
                        System.out.println("");
                    }
                }

                System.out.println("Wellcome to the system\n");

                String managerRole = hrSystem.employeeService.getManagerType(loginId);

                switch (managerRole) {
                    case ("HRMANAGER"): {
                        hrSystem.run(loginId);
                        break;
                    }

                    case ("TRANSPORTMANAGER"): {
                        transportSystem.run(loginId);
                        break;
                    }

                    case ("DRIVER"): {
                        driverSystem.run(loginId);
                        break;
                    }

                    default: {
                        memberSystem.run(loginId);
                        break;
                    }
                }
            }

            else {
                System.out.println("Goodbye, see you next time!");
                break;
            }
        }

        sc.close();
    }

    public static String getMenu() {
        String horizontalLine = "+---------------------------------------+";
        String option1 = "| 0 - Exit the system.                  |";
        String option2 = "| 1 - Login to the system.              |";
        String bottomLine = "+---------------------------------------+";

        StringBuilder sb = new StringBuilder();
        sb.append(horizontalLine).append("\n")
                .append(option1).append("\n")
                .append(option2).append("\n")
                .append(bottomLine);
        return sb.toString();
    }
}