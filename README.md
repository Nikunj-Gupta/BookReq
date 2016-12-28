### Introduction
This is a Book Requesting System. It is presently made keeping IIITB as an institution in mind, but it can be used by and for any other organisation with minor changes. It has three kinds of users, namely, User, Librarian and Admin.

### Project
* There is a user account where a user can simply request a book by writting and submitting asked details.
* The request of a book first goes to an Admin Account, where the pre-assigned admin (say, superadmin) can accept or reject it. A Superadmin can add more admins and librarians.
* A librarian is a user who can view the request only once it is accepted by an admin. His job is to order those requested set of books and change the status of the request to "Ordered" once he had done so. Finally, when the order has been recieved at the library, the librarian must change the status of the request to "Procured" so that the user who had requested the book, stays familiar with what is happenning to is book request.


### Usage

* Step 1: You need a hosting server to launch the server files. I used Apache Server but Tomcat can be used too. Just launch all the php files from `<proj>` folder on your server. (Note: You could use a local host server too - like, *XAMPP*)
* Developer Mode:
    - Use android studio to open the project.
    - Sync the gradle.
    - The app has used Google's github repository volley; and so you need to download it and learn to sync it in the app. Follow steps as mention [here][volleyinstall]. Refer [here][androidtuts] for an implementation example.
    - Note: Check out for *volley*.
* App mode
    - Go to `<Demo2/app/build/outputs/apk>` and copy `<app-debug.apk>` in your android mobile phone. This will install the android application in your phone and **BAM - Ready to use!!!**  

### Tech

* [Android][android]
* [Android Studio][androidstudio]
* [Volley][volley]
* [PHP][php]
* [Apache][apache] Server (using [XAMPP][xampp])

### Acknowledgements

* [Prof. Sujit Kumar Chakrabarti][profskc] for guiding us and giving us an opportunity to learn a lot from such a project.
* My seniors for giving useful tips and ideas for successfully completing our project.

### Development

Want to contribute? Great!

   [android]: <https://www.android.com/>
   [androidstudio]: <https://developer.android.com/studio/index.html> 
   [php]: <http://php.net/manual/en/intro-whatis.php>
   [xampp]: <https://www.apachefriends.org/index.html>
   [apache]: <https://www.apache.org/>
   [volley]: <https://developer.android.com/training/volley/index.html>
   [volleyinstall]: <https://developer.android.com/training/volley/index.html>
   [androidtuts]: <https://www.androidtutorialpoint.com/networking/android-volley-tutorial/>
   [profskc]: <http://www.iiitb.ac.in/faculty_page.php?name=SujitKumarChakrabarti>
