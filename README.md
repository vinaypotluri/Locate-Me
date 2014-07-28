Locate-Me
============

Language: Java
Environment: Eclipse, Android Studio

The project involved developing a Location Based app for google glass users.  Location based updates within the University Campus such as building name, address and the available resources are pushed to the glass interface in real time. The Google glass applications commonly known as Glassware are built using Glass Development Kit (GDK) and Android SDK with the help of mirror APIs for extracting data from the web. A first of its kind implementation by a University for the emerging technology.

The app is focussed initially to benefit the incoming college freshmen who are totally new to the University and need assistance. The app here acts as a virtual assistant to tour the University.

Application consists of Main Activity and the Service Class

Main Activity:
===============
1. Pulls the location values from the service class
2. Retrieves current location information.
3. Displays all the necessary resources information about the user.
4. Gives options to start and start
5. Minimizes to let the service class run in the background.
6. General Information about the App in the options



Service Class:
==============
1. Gets the location of the user from best available providers in the Background.
2. Geocoder API to decode address from latitude longitude values in real time.
3. Gets the building name.
4. Pulls up information about the building resources from the University website.
5. Broadcasts the content to the Main Activity to display on the Interface.
6. Voice and Notifications are triggered when a new location is entered.
7. Runs in the background even when screen is locked without interrupting the user.






Google Glass HomeScreen
![](http://i.imgur.com/iJYu32Y.png)




