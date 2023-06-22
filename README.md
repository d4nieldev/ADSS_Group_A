# ADSS_Group_A

Inbar Ben-Chaim 209289081
Tal Koren 213904972
Kfir Shalom 318818481
Rotem Akiki 208549782

## GUI
Please read to instructions book that include in the docs file.

## CLI
How to start the system?

Wen you start the system it shows a message:
0 - Exit the system.
1 - Login to the system.

If you want to login as an HR manager, please choose 1 and insert the Id 123456789 and password HRmanager.
If you want to login as an Transport manager, please choose 1 and insert the Id 987654321 and password TRANSPORTmanager.
If you want to log in as an employee or a driver - you can do that by using their Id and password.

Each of the users above have a different menu then log in to the system because each one of them have a different functions and access to the system and DataBase.

Hope you will have fun,
Inbar Tal Kfir and Rotem

## DataBase

 - Transport Modul
      there are 5 trucks all available for transportation, from several kind of models
      there are deliveris pending for transports
      there is one transport waiting to be activated
   
 - Employee Modul
   6 branches: 0 - SuperBranch of manager and drivers, 1 - Beer-Sheva , 2 - Tel-Aviv , 3 - Yafo , 4 - Eilat , 5-Haifa
      80 employees with the id's 201-280
      12 drivers with the id's 101-112
      Each branch have a morning and an evning shifts from 04-06-2023 till 10-06-2023 (include)
      All the shifts on dates from 04-06-2023 till 06-06-2023 were approved by the HR manager
      The shifts on the date 07-06-2023 were not approved yet by the HR manager, but have constraints for employees.


## Tools

- **junit-4.13.2** for the tests
- **sqlite-jdbc-3.41.2.1** for the database
