student id:s3973503
Name:Prathiksha

IDE used:Eclipse-Version: 2023-06 (4.28.0)
Java version used-17.0.7+7
Java SDK version used-sdk 21
Datbase -SQLlite
sqlite-jdbc-3.43.0.0.jar
Vm arguments used: --module-path "javafx-sdk-21/lib" --add-modules=javafx.controls,javafx.fxml,javafx.graphics,javafx.base

Steps to run the program:
1. Download the submssion
1. unzip the file 
2. Open Eclipse click on file menu and click on import
3. click on general
4. From the general drop down list select Existing project into workspace
5. click on Next
6. click on browse and select the folder from the downloads and click on finish
7. right click on the main.java and run as configurations
8. under the arguments tab untick all the boxes and add 
--module-path "javafx-sdk-21/lib" --add-modules=javafx.controls,javafx.fxml,javafx.graphics,javafx.base
9. click on Apply & run


Assumptions:
1. Application model has database connection which is used across all the class achieving singleton pattern.
2.Separting views in fxmls and all the button ,searchfiled or tectfiled actions and validations in controller and business logic in model
3.User cannot login without registation.
4.Only registered values are allowed to login.
5.Once they login in they login to UserDashboard and can see all the posts even created by other users 
6. When the registered user logins to the dashboard he can see all the posts including his posts with other users posts.
7. user can only delete or edit his posts
8. User cannot delete the posts which are created by others.
9. User can search , export all the posts.
10.Date format used everwhere as in specification of Assignment 1 :dd/MM/yyyy HH:mm


Class diagram: refer to the pdf attached with the zip file


Description of class :

created three packages:
model,view and controller in which all the respective controller, view and model classes are placed

view package:(which contains all the fxml files )
CreateScene:which displays the post creation CreateScene
DisplayPost:Which displays the postdetails
EditScene:this scene allows user to edit the user details
Login:login dashboard
register:Register dashboard
Userdashboard: This is the scene where user can see his dashboard
VipUserDashboard:This is the user dashboard for vip users which has extra features.

model pacakage:(which contains the business logic for all the functionality)
Application model: To get the instance of main model class and use it in other classes.Private constructor created to use singleton pattern 
User class: which is a POJO class which just contains getters and setters
Post:which is a POJO class which just contains getters and setters
UserModel:it contains all the logics for registration,login ,update and other functions related to user
PostModel:it contains all the logics for add post ,delete ,search, export, import and other vip functionalities include pie chart.
Vip User and Non Vip User:these are the child class of user which extends User (implementing inheritence)

Controller class:
Create controller: which contains all the action events for buttons or textfileds related  to add post and validations of the input 
DisplayPostController:which contains all the action events for buttons or textfileds related to displaying thre contents of the post and validations of the input 
Editscene Controller:which contains all the action events for buttons or textfileds related  to edit user details and validations of the input 
LoginController:which contains all the action events for buttons or textfileds related to login and validations of the input 
RegisterController:which contains all the action events for buttons or textfileds related to register and validations of the input 
UserDashboardcontroller:which contains all the action events for buttons or textfileds related to export, import, search ,delete ,upgrade to vip & logout andvalidations of the input 
VipController:which contains all the action events for buttons or textfileds related to vip actions and validations of the input 

Git hub link:https://github.com/s3973503/ApAssignment2
