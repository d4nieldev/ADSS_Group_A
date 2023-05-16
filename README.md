# ADSS_Group_A

Daniel Ohayon 322404559 Vladislav Shembel 322126350

Metar Bachar 206892317 Assaf Mishael 318359734

## Instructions

**help** = show the manual

### Suppliers Menu

**makereservation** [branch] = open the reservation menu.

- enter lines in the format of "[supplier_id] [product_id] [amount]"
- for changing amount of product type the line again with the updated amount
- for closing the reservation menu enter "done"
- for aborting the reservation enter "abort"
- after completing the reservation. You will get a response with the reservation id if was successful

**cancelreservation** [reservation_id] = cancel the reservation

**readyreservation** [reservation_id] = make the reservation ready

**closereservation** [reservation_id] = close the reservation

**addPeriodicReservation** [supplier_id] [branch_id] [week_day] = add a new periodic reservation for the supplier and branch

**updatePeriodicReservation** [supplier_id] [branch_id] [week_day] = update an existing periodic reservation for the supplier and branch

**receipt** [reservation_id] = show all items, amounts, and prices for this reservation

**reservations** [supplier_id] = show all reservations history with the supplier

**addSupplier** = Adds a new supplier to the system. Enter the information that the system will ask you to about the supplier you want to add.

**deleteSupplier** [supplier_id] = Deletes an existing supplier from the system.

**editSupplier** [supplier_id] = After typing this command, you can edit the supplier information with the following commands:

- **updateName** [new_name]
- **updateBankAccount** [new_bankAccount]
- **addField** [new_field]
- **removeField** [field_to_remove]
- **updatePaymentCondition** [new_paymentCondition]
- **addContact** [contact_phone] [contact_name]
- **deleteContact** [contact_phone] [contact_name]
- **addAgreement** [product_id] [supplier_id] = Adds a new product agreement with a supplier. The system will ask you about information needed to this action. If an agreement already exist, the system will update it to the new one.

**getCard** [supplier_id] = Information about the supplier will be presented.

### Inventory menu

**addBranch** = adds a new branch to the system

**addProductBranch** = adds a new product to an existing branch

**1** Add new product

**2** Add Category

**3** Sell product

**4** Set discount - categories

**5** Set discount - product

**6** Report flaw product

**7** Import inventory Report

**8** Import expired and flaws report

**9** Import deficiency report

**10** Import inventory report by categories

**11** Import report by report id

**12** Import product report

**13** Import shortage report

**exit** = exit the program

## Tools

- **junit-4.13.2** for the tests
- **sqlite-jdbc-3.41.2.1** for the database
