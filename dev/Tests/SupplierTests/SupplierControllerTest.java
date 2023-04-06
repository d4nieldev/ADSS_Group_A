package Tests.SupplierTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.TreeMap;

import org.junit.*;
import org.junit.Assert.*;

import BusinessLayer.Suppliers.Supplier;
import BusinessLayer.Suppliers.SupplierController;
import BusinessLayer.Suppliers.exceptions.SuppliersException;

public class SupplierControllerTest {

    SupplierController sc = SupplierController.getInstance();

    @BeforeClass
    public static void setup() throws Exception {
        try {
            
            createSupplier0();
        } catch (Exception e) {
            System.out.println("Could not setup environment");
            e.printStackTrace();
        }
    }

    /**
     * Creation of an 'OnOrder' type supplier for testing purposes
     * @throws Exception
     */
    public static void createSupplier0() throws SuppliersException {
        String name = "FastAndBest ";
        String phone = "0507164509";
        String bankAccount = "12-128-148258";
        List<String> fields = List.of("Tech", "Cleaning");
        String paymentCondition = "net 30 EOM";
        TreeMap<Integer, Double> amountToDiscount = new TreeMap<>();
        amountToDiscount.put(100, 2.5);
        amountToDiscount.put(500, 3.0);
        List<String> contactNames = List.of("Dana Grinberg", "Roni Katz");
        List<String> contactPhones = List.of("0525948325", "0535669897");
        int maxSupplyDays = 4 ;

        SupplierController.getInstance().addOnOrderSupplierBaseAgreement(name, phone, bankAccount, fields,
            paymentCondition, amountToDiscount, contactNames, contactPhones, maxSupplyDays);
    }


    @Test
    public void getExistingSupplierByIdTest() {
        //TODO: This test fails when all tests run, maybe because there is a test that delete a supplier.
        //test for existing supplier id in system.
        try {
            Supplier s = sc.getSupplierById(0);
            assertEquals(0, s.getId());
        }catch(SuppliersException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void getNotExistingSupplierByIdTest() {
        //test for Non existing supplier id in system.
        Exception e = assertThrows(SuppliersException.class, () -> sc.getSupplierById(5));

        //check that error message is correct.
        assertEquals("SUPPLIERS EXCEPTION: There is no supplier with id " + 5 + " in the system.", e.getMessage());
    }

    @Test
    public void getNegativeSupplierByIdTest() {
        //test for negative supplier id in system.
        Exception e = assertThrows(SuppliersException.class, () -> sc.getSupplierById(-1));

        //check that error message is correct.
        assertEquals("SUPPLIERS EXCEPTION: Supplier with negative id is illegal in the system.", e.getMessage()); 
    }

    @Test
    public void deleteExistingSupplierTest() {
        //test for existing supplier id in system.
        try {
            sc.deleteSupplier(0);
            Exception e = assertThrows(SuppliersException.class, () -> sc.getSupplierById(0));

            //check that error message is correct.
            assertEquals("SUPPLIERS EXCEPTION: There is no supplier with id " + 0 + " in the system.", e.getMessage());
        }catch(SuppliersException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void deleteNotExistingSupplierTest() {
        //test for not existing supplier id in system.
        Exception e = assertThrows(SuppliersException.class, () -> sc.getSupplierById(7));

        //check that error message is correct.
        assertEquals("SUPPLIERS EXCEPTION: There is no supplier with id " + 7 + " in the system.", e.getMessage());
    }

    @Test
    public void addFixedDaysSupplierBaseAgreementTest(){
        //to validate creation we try to get this supplier and check if his id, name and phone are correct.

        //TODO: maybe add ToString test to check that unique fields are correct too. (JSON?)
        String name = "AllYouNeed";
        String phone = "0507164588";
        String bankAccount = "09-319-158988";
        List<String> fields = List.of("Meat" , "Sweet drinks");
        String paymentCondition = "net 45 EOM";
        TreeMap<Integer, Double> amountToDiscount = new TreeMap<>();
        amountToDiscount.put(50, 1.5);
        amountToDiscount.put(90, 2.0);
        List<String> contactNames = List.of("Kevin Monk");
        List<String> contactPhones = List.of("0525869525");
        List<Integer> days = List.of(2,4);
       try{ 
            SupplierController.getInstance().addFixedDaysSupplierBaseAgreement(name, phone, bankAccount, fields, paymentCondition,
             amountToDiscount, contactNames, contactPhones, days);
             Supplier s = sc.getSupplierById(1);
            assertEquals(1, s.getId());
            assertEquals("AllYouNeed", s.getName());
            assertEquals("0507164588", s.getPhone());
        }catch (SuppliersException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void addOnOrderSupplierBaseAgreementTest(){
        //to validate creation we try to get this supplier and check if his id, name and phone are correct.
        String name = "AllYouNeed";
        String phone = "0507164588";
        String bankAccount = "09-319-158988";
        List<String> fields = List.of("Meat" , "Sweet drinks");
        String paymentCondition = "net 45 EOM";
        TreeMap<Integer, Double> amountToDiscount = new TreeMap<>();
        amountToDiscount.put(50, 1.5);
        amountToDiscount.put(90, 2.0);
        List<String> contactNames = List.of("Kevin Monk");
        List<String> contactPhones = List.of("0525869525");
        int maxSupplyDays = 7 ;
       try{ 
            SupplierController.getInstance().addOnOrderSupplierBaseAgreement(name, phone, bankAccount, fields, paymentCondition,
             amountToDiscount, contactNames, contactPhones, maxSupplyDays);
             Supplier s = sc.getSupplierById(1);
            assertEquals(1, s.getId());
            assertEquals("AllYouNeed", s.getName());
            assertEquals("0507164588", s.getPhone());
        }catch (SuppliersException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void addSelfPickupSupplierBaseAgreementTest(){
        //to validate creation we try to get this supplier and check if his id, name and phone are correct.
        String name = "AllYouNeed";
        String phone = "0507164588";
        String bankAccount = "09-319-158988";
        List<String> fields = List.of("Meat" , "Sweet drinks");
        String paymentCondition = "net 45 EOM";
        TreeMap<Integer, Double> amountToDiscount = new TreeMap<>();
        amountToDiscount.put(50, 1.5);
        amountToDiscount.put(90, 2.0);
        List<String> contactNames = List.of("Kevin Monk");
        List<String> contactPhones = List.of("0525869525");
        String address = "Shilo 4, Ashkelon" ;
       try{ 
            SupplierController.getInstance().addSelfPickupSupplierBaseAgreement(name, phone, bankAccount, fields, paymentCondition,
             amountToDiscount, contactNames, contactPhones, address);
             Supplier s = sc.getSupplierById(1);
            assertEquals(1, s.getId());
            assertEquals("AllYouNeed", s.getName());
            assertEquals("0507164588", s.getPhone());
        }catch (SuppliersException e){
            fail(e.getMessage());
        }
    }
    
    @Test
    public void setSupplierNameTest(){
        try{
            sc.setSupplierName(0, "Test");
            assertEquals("Test", sc.getSupplierById(0).getName());
        }catch(SuppliersException e){
            fail(e.getMessage());
        }

        //test for Non existing supplier id in system.
        Exception e = assertThrows(SuppliersException.class, () -> sc.setSupplierName(5, "Test"));

        //check that error message is correct.
        assertEquals("SUPPLIERS EXCEPTION: There is no supplier with id " + 5 + " in the system.", e.getMessage());

    }

    @Test
    public void setSupplierPhoneTest(){
        try{
            sc.setSupplierPhone(0, "0500000000");
            assertEquals("0500000000", sc.getSupplierById(0).getPhone());
        }catch(SuppliersException e){
            fail(e.getMessage());
        }

        //test for Non existing supplier id in system.
        Exception e = assertThrows(SuppliersException.class, () -> sc.setSupplierPhone(5, "0500000000"));

        //check that error message is correct.
        assertEquals("SUPPLIERS EXCEPTION: There is no supplier with id " + 5 + " in the system.", e.getMessage());
    }

    @Test
    public void setSupplierBankAccountTest(){
        try{
            sc.setSupplierBankAccount(0, "12-4558-6996");
            assertEquals("12-4558-6996", sc.getSupplierById(0).getBankAcc());
        }catch(SuppliersException e){
            fail(e.getMessage());
        }

        //test for Non existing supplier id in system.
        Exception e = assertThrows(SuppliersException.class, () -> sc.setSupplierBankAccount(5, "12-4558-6996"));

        //check that error message is correct.
        assertEquals("SUPPLIERS EXCEPTION: There is no supplier with id " + 5 + " in the system.", e.getMessage());
    }

    @Test
    public void addSupplierFieldTest(){
        try{
            sc.addSupplierField(0, "Food");
            assertTrue("Field not exist!", sc.getSupplierById(0).getFields().contains("Food"));
        }catch(SuppliersException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void setSupplierPaymentConditionTest(){
        
    }

    @Test
    public void setSupplierAmountToDiscountTest(){
        
    }

    @Test
    public void setSupplierContactNamesAndPhonesTest(){
        
    }

    @Test
    public void addSupplierContactTest(){
        
    }

    @Test
    public void deleteSupplierContactTest(){
        
    }

    @Test
    public void deleteAllSupplierContactsTest(){
        
    }

    @Test
    public void addSupplierProductAgreementTest(){
        
    }
    
    @Test
    public void updateSupplierProductAgreementTest(){
        
    }

    @Test
    public void calculateSupplierDiscountTest(){
        
    }

    @Test
    public void getSupplierCardTest(){
        
    }
    
    @Test
    public void getRandomContactOfTest(){
        
    }

}