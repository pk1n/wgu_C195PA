Title: Scheduling Application: 
Purpose: The purpose of this applicaiton is a software program for scheduling customer appointements, interacting with an existing MySQL datebase.
Purpose: Customer and appointment information can be created, modifed, and deleted. Information is stored in the database.
Version: v2.0

Author: Patrick Kinney, pkinne2@my.wgu.edu, 03/29/2023
IDE: IntelliJ Community 2022.3.1
JDK: Oracle Open JDK version 17.0.6
JavaFX: OpenJFX 17.0.2

Directions for use:
Start the program. A login screen will apear. Enter username and password. Login attempts are recorded in a local text file. Users will be notified of incorrect username/passwords. 
Once a login is sucessful, the user is taken to a main landing page. 
From this page, the user can selected to add/modify/delete customers or appointments.
Upon adding/modifying/deleting customers/appointments, users will be taken to specific pages or promted to complete their desired action.  
On the main landing page is the button for accessing customer and appointment reports. 
To exit the program, either close out of the application with the window close button or select exit on the main landing page.

Additional report:
An additional summary report was provided. "Summary by Location" displaces a summary total of all appointments by their entered location. 

MySQL Connector: mysql-connector-j-8.0.32


Change Log:
Updates since v1.0:
- Customer deletion checks & deleted associated appointments: Tested functionality. Worked as intended during testings. Customers with appointments trigger confirmation promt and customer & appointments are deleted. No changes made.
- Original appointment times not saved: v1.0 assumed DB times were UTC and convereted to local time. v2.0 now assumes DB stores in local time and converts to UTC internally. Removed UTC converstion from program.
- Overlapping appointment checking: Changed logic to assume new/mod appointments have an overlap and must prove they don't to add/mod. Fixed error for adding appointments for customers with no existing appointment.
- No upcoming appointment alert: Fixed logic to check if a user has an appointment within 15mins of log-in. Added confirmation dialog if no upcoming appointments.
- No contact schedule: Updated report from customer scheulde to contact schedule as required. 
- Readme version: Added version under main block. Updated with change log. 


