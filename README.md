# Name of Project: GroupProject
# Group 1
# Group Members: Mohamed Uzair Anees, Ryan Harrison, Reez Khan, Joshua Mayfield, Krutin Patel
# GroupProject is an app meant to allow users to set alarms for important times, and to time events
# Known Bugs: None
# No login information needed
# Java Version: 1.8 (8), Scenebuilder Version: 3
# How to clone: 
  1. Download repo as zip
  2. Extract files
  3. Make new JavaFX Project in Eclipse
  4. Import Alarm_Clock project from repo into new JavaFX project
  5. If default-package with module-info.java exists after creating project, delete the class
  6. If JavaFX-SDK library exists in JavaFX project, remove from buildpath
  7. If getting error "Error: JavaFX runtime components are missing, and are required to run this application" follow these steps
    - Open run-configurations page (can search for it or find it next to down-arrow when trying to run program)
    - Under Main tab, make sure project name is the name of the project you are trying to run
    - Then, go to Arguments tab
    - Under arguments tab, copy and paste "--module-path "C:\JavaFX\javafx-sdk-18\lib" --add-modules javafx.controls,javafx.fxml,javafx.base,javafx.media" under VM arguments
    - Apply and Run
 8. If any other errors come up, email rpharrison02@gmail.com for help
