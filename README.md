# ChatApplication

this application aims to create a chat between 2 people or a group of people

# Test the app
-import the .sql file which is in the BD folder in your mysql

-start the MainServer class

-run the client class

to test run the client 4 times and connect with:
1. login : karim / password : 123
2. login : hamza / password : 123
3. login : oussama / password : 123
4. login : taha / password : 123

# start interface

for the start interface you can either login or create an account.
after connecting to the app a menu is displayed.

# Menu

** you can consult your friends

**you can consult your groups

**you can add a friend:

    in this interface for adding a friend after choosing a user and clicking on add a request will be sent to the targeted user and he will either accept or refuse the request. in the case of acceptance of the request he must send a request himself to this user so that he becomes friends !!

**you can delete a friend

**you can create a group:

    after entering the group name the combobox will fill up and you can choose which group in which you want to add your friends. well just choose the group from the combobox and add your friends.

**you can edit a group:

    select a group from the combobox and remove a friend from the group.

**you can delete a group

**notification : 

    this is for friend requests (accept or decline)

**launch my chat:

    - all users must be in their chat.
    
    - a list of friends of logged in user will show
    
    -each friend has their own conversation 
    
    -you can choose a friend from the list then click on Chat a textpane will be displayed for the targeted conversation and you can chat with the selected friend
    
    -same thing for the group, the friends in the group must be in the conversation (by choosing the group from the list and clicking on chat) and it broadcasts the messages between them!
    
    -for the Get button it is to select an image from our desktop (just image) and it only works for conversations between 2 people it does not work for groups.