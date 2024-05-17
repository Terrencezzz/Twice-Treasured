# [G09 - Twice Treasured] Report

## Table of Contents

1. [Team Members and Roles](#team-members-and-roles)
2. [Summary of Individual Contributions](#summary-of-individual-contributions)
3. [Application Description](#application-description)
4. [Application UML](#application-uml)
5. [Application Design and Decisions](#application-design-and-decisions)
6. [Summary of Known Errors and Bugs](#summary-of-known-errors-and-bugs)
7. [Testing Summary](#testing-summary)
8. [Implemented Features](#implemented-features)
9. [Team Meetings](#team-meetings)
10. [Conflict Resolution Protocol](#conflict-resolution-protocol)

## Administrative
- Firebase Repository Link: [Second Hand Market-Twice Treasured](https://console.firebase.google.com/project/second-hand-market-affd5/overview)
  
   - Confirm: I have already added comp21006442@gmail.com as a Developer to the Firebase project prior to due date.
   
     ![Check_Developer_to_the_Firebase_project](D:\A_Study\2024S1-COMP6442\Assignment\GroupProject\gp-24s1\items\media\report\Check_Developer_to_the_Firebase_project.png)
- Two user accounts for markers' access are usable on the app's APK (do not change the username and password unless there are exceptional circumstances. Note that they are not real e-mail addresses in use):
   - Username: comp2100@anu.edu.au	Password: comp2100
   
   - Username: comp6442@anu.edu.au	Password: comp6442
   
     
   
     ![Check_Special_Account_comp2100](D:\A_Study\2024S1-COMP6442\Assignment\GroupProject\gp-24s1\items\media\report\Check_Special_Account_comp2100.png)
   
     ![Check_Special_Account_comp6442](D:\A_Study\2024S1-COMP6442\Assignment\GroupProject\gp-24s1\items\media\report\Check_Special_Account_comp6442.png)
   
   

## Team Members and Roles
The key area(s) of responsibilities for each member

| UID      |            Name             |                                    Role |
| :------- | :-------------------------: | --------------------------------------: |
| u7756873 |         Lingjie Qin         | Full stack developer, Tester, Supporter |
| u7303997 | Scott Ferrageau de St Amand | Full stack developer, Tester, Supporter |
| u7642453 |         Wanzhong Wu         | Full stack developer, Tester, Supporter |
| u7706423 |           Wen Li            | Full stack developer, Tester, Supporter |
| u7769944 |        Xiaojie Zhou         | Full stack developer, Tester, Supporter |


## Summary of Individual Contributions

1. **UID1, Name1**  I have 30% contribution, as follows: <br>
  - **Code Contribution in the final App**
    - Feature A1, A2, A3 - class Dummy: [Dummy.java](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java)
    - XYZ Design Pattern -  class AnotherClass: [functionOne()](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43), [function2()](the-URL)
    - ... (any other contribution in the code, including UI and data files) ... [Student class](../src/path/to/class/Student.java), ..., etc.*, [LanguageTranslator class](../src/path/to/class/LanguageTranslator.java): function1(), function2(), ... <br><br>

  - **Code and App Design** 
    - [What design patterns, data structures, did the involved member propose?]*
    - [UI Design. Specify what design did the involved member propose? What tools were used for the design?]* <br><br>

  - **Others**: (only if significant and significantly different from an "average contribution") 
    - [Report Writing?] [Slides preparation?]*
    - [You are welcome to provide anything that you consider as a contribution to the project or team.] e.g., APK, setups, firebase* <br><br>

<hr>

1.**u7756873, Lingjie Qin**  I have <u>20.6%</u> contribution, as follows: <br>

- **Code Contribution in the final App**
  - 
- **Code and App Design** 
  - 
- **Others**
  - 

2.**u7303997, Scott Ferrageau de St Amand** I have <u>16.8%</u> contribution, as follows: <br>

- **Code Contribution in the final App**
  - 1
- **Code and App Design** 
  - 1
- **Others**
  - 1

3.**u7642453, Wanzhong Wu** I have 21.4% contribution, as follows: 

- **Code Contribution in the final App**
  - 1
- **Code and App Design** 
  - 1
- **Others**
  - 1

4.**u7706423, Wen Li** I have <u>21.2%</u> contribution, as follows: <br>

- **Code Contribution in the final App**
  - Feature
    - G2 Data-Profile - [UserDetailPage.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/UserDetailPage.java)
    - G3 Data-GPS  - [getLocation()](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/UserDetailPage.java?#L285-331)
    - U4 Interact-Noti - [UserNotice.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/UserNotice.java), [FavoriteNotice.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/FavoriteNotice.java)
  - Design Pattern
    - Singleton - [Database.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/Database.java)
    - State - [UserState.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/UserState.java), [UserLoggedInState.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/UserLoggedInState.java), [UserLoggedOutState.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/UserLoggedOutState.java)
    - Factory Method - [Notice.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/Notice.java), [NoticeFactory.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/NoticeFactory.java) , [UserNotice.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/UserNotice.java), [FavoriteNotice.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/FavoriteNotice.java)
  - Other
    - [CommonHelper.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/common/CommonHelper.java): showAlertDialog(), refreshLoginUser(), getLastLocation()
    - [GlobalVariables.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/GlobalVariables.java): Except isVisitorMode()
    - Test: [CategoryAdapterTest.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/test/java/com/example/myapplication/CategoryAdapterTest.java), [CategoryTest.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/test/java/com/example/myapplication/CategoryTest.java), [CommonHelperTest.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/test/java/com/example/myapplication/CommonHelperTest.java), [GlobalVariablesTest.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/test/java/com/example/myapplication/GlobalVariablesTest.java),[NoticeFactoryTest.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/test/java/com/example/myapplication/NoticeFactoryTest.java), [NotificationAdapterTest.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/test/java/com/example/myapplication/NotificationAdapterTest.java), [NotificationTest.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/test/java/com/example/myapplication/NotificationTest.java), [UserLoggedInStateTest.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/test/java/com/example/myapplication/UserLoggedInStateTest.java),[UserLoggedOutStateTest.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/test/java/com/example/myapplication/UserLoggedOutStateTest.java),[UserTest.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/test/java/com/example/myapplication/UserTest.java)
- **Code and App Design** 
  - Design Pattern: Singleton, State, Factory Method
  - UI Design
    - Use `RelativeLayout` and `ScrollView `to keep the top and bottom bars fixed while allowing the content to be scrollable for a smooth user experience.
    - Establish a custom `ViewHolder `and employ `CardView `to seamlessly display various layouts with different icons, encompassing `LinearLayout `and `GridLayout`.
- **Others**
  - Report Writing
  - Bug-fixing process for APK packaging

5.**u7769944, Xiaojie Zhou** I have <u>20%</u> contribution, as follows: <br>

- **Code Contribution in the final App**
  - 1
- **Code and App Design** 
  - 1
- **Others**
  - 1



## Application Description

*[What is your application, what does it do? Include photos or diagrams if necessary]*

*Here is a pet specific application example*

*PetBook is a social media application specifically targetting pet owners... it provides... certified practitioners, such as veterians are indicated by a label next to their profile...*

### Application Use Cases and or Examples

*[Provide use cases and examples of people using your application. Who are the target users of your application? How do the users use your application?]*

*Here is a pet training application example*

*Molly wants to inquiry about her cat, McPurr's recent troublesome behaviour*
1. *Molly notices that McPurr has been hostile since...*
2. *She makes a post about... with the tag...*
3. *Lachlan, a vet, writes a reply to Molly's post...*
4. ...
5. *Molly gives Lachlan's reply a 'tick' response*

*Here is a map navigation application example*

*Targets Users: Drivers*

* *Users can use it to navigate in order to reach the destinations.*
* *Users can learn the traffic conditions*
* ...

*Target Users: Those who want to find some good restaurants*

* *Users can find nearby restaurants and the application can give recommendations*
* ...

*List all the use cases in text descriptions or create use case diagrams. Please refer to https://www.visual-paradigm.com/guide/uml-unified-modeling-language/what-is-use-case-diagram/ for use case diagram.*

<hr> 

### Application UML

![ClassDiagramExample](media/_examples/ClassDiagramExample.png) <br>
*[Replace the above with a class diagram. You can look at how we have linked an image here as an example of how you can do it too.]*

<hr>

## Code Design and Decisions

This is an important section of your report and should include all technical decisions made. Well-written justifications will increase your marks for both the report as well as for the relevant parts (e.g., data structure). This includes, for example,

- Details about the parser (describe the formal grammar and language used)

- Decisions made (e.g., explain why you chose one or another data structure, why you used a specific data model, etc.)

- Details about the design patterns used (where in the code, justification of the choice, etc)

*Please give clear and concise descriptions for each subsections of this part. It would be better to list all the concrete items for each subsection and give no more than `5` concise, crucial reasons of your design.

<hr>

### Data Structures

*[What data structures did your team utilise? Where and why?]*

Here is a partial (short) example for the subsection `Data Structures`:*

*I used the following data structures in my project:*

1. *LinkedList*
   * *Objective: used for storing xxxx for xxx feature.*
   * *Code Locations: defined in [Class X, methods Z, Y](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43) and [class AnotherClass, lines l1-l2](url); processed using [dataStructureHandlerMethod](url) and ...
   * *Reasons:*
      * *It is more efficient than Arraylist for insertion with a time complexity O(1)*
      * *We don't need to access the item by index for xxx feature because...*
      * For the (part), the data ... (characteristics) ...

2. *AVLTree*
    * The Parser class uses AVLTree to handle the filtering of product information, based on location, category and name tags.
    * Code Locations: defined in [Class AVLTree](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/common/AVLTree.java?ref_type=heads#L6-348) ; processed using [Class Parser](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/Parser.java?ref_type=heads#L15-113)
    * Reasons:
        * *Searching product information by using an AVLTree is much more efficient than using a LinkedList. If there are N elements in the LinkedList, the worst case requires traversing the entire LinkedList, and the time complexity is O(n). But in AVLTree, the height of the tree is kept at O(log n) and the search operation can be very fast.*
        * *Complex query logic can be implemented efficiently by dynamically building and updating AVLTree in different parsing stages.*
        * *AVLTree naturally maintains the order of elements during their operations, which makes range queries and minimum/maximum query operations very efficient.*

3. ...

<hr>

### Design Patterns
1.*Singleton*

* Objective: used for storing **the only one database connection instance** for Firebase Integration feature.
* Code Locations
   * defined in [Database.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/Database.java)
   * processed using in [RecommendAdapter()](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Adapters/RecommendAdapter.java#L39), [FavoritePage.onCreate()](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/FavoritePage.java#L77-78), [HomePage.onCreate()](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/HomePage.java?#L75), [ProductPage.onCreate()](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/ProductPage.java#L53-54),[UserDetailPage.onCreate()](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/UserDetailPage.java#L98),[UserPage.onCreate()](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/UserPage.java?#L67),...
* Reasons
   * Singleton ensures only one database connection is created, <u>saving resources</u>.
   * <u>Reusing</u> the same connection reduces system <u>overhead</u>, making things faster.
   * Singleton ensures <u>safe access</u> to the connection, even in a multi-threaded environment.
   * All parts of the program use the same connection, <u>ensuring data consistency</u>.

2.State

* Objective:  used for retrieve the current user's login status from [GlobalVariables](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/GlobalVariables.java#L42-65) to dynamically control the visibility of UI elements and enforce corresponding functional restrictions. 

* Code Locations

  - defined in  [UserState.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/UserState.java), [UserLoggedInState.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/UserLoggedInState.java), [UserLoggedOutState.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/UserLoggedOutState.java)
  - processed using in [GlobalVariables.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/GlobalVariables.java#L42-65), [CommonHelper.refreshLoginUser()](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/common/CommonHelper.java?#L94-95), [HomePage.Logout](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/HomePage.java?#L141-142), [HomePage.initLoginUser()](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/HomePage.java?#L211-212), [UserDetailPage.updateUserInfo()](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/UserDetailPage.java#L193-194)

* Reasons

  * <u>UI elements and functional restrictions can change dynamically</u> based on the user's login status, improving user experience and responsiveness.
  * Easily <u>adapt to changes and new requirements</u> without affecting the entire codebase.

3.Facotry Method

* Objective: used for generating different notices and store them into different database reference in factory 
* Code Locations
  * defined in [Notice.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/Notice.java), [NoticeFactory.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/NoticeFactory.java) , [UserNotice.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/UserNotice.java), [FavoriteNotice.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/FavoriteNotice.java)
  * processed using in [ProductPage.toggleFavoriteStatus()](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/ProductPage.java#L220-222), [UserDetailPage.updateUserInfo()](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/UserDetailPage.java#L187-189)
* Reasons
  *  <u>Easy addition of new notice types</u> without modifying existing code. 
  * <u>Relying on factory interfaces instead of concrete classes</u>, it fosters a modular and loosely coupled design, facilitating system maintenance and evolution.

  

<hr>

### Parser

### <u>Grammar(s)</u>
*Basically my grammar is for tracking key words of the user input, it's made of different filters combine with each other.
The advantage of doing this is this way is easier to modify in the future if we want to improve the grammar, and also it's
very powerful and easy to implement.*

Production Rules:

    <query> ::= <location-filter> <category-filter> <name-filter>
    <location-filter> ::= 'LOCATION' <string> | ε
    <category-filter> ::= 'CATEGORY' <string> | ε
    <name-filter> ::= 'NAME' <string> | ε
    <string> ::= [a-zA-Z0-9]+

### <u>Tokenizers and Parsers</u>

*[I use the parser when they user want to search something on whatever homepage or the search result page, first I will
grab the data from the real time database and put it into an empty AVL tree, then I will use the parser to get the result
from the AVL tree base on the use input. Tokenizers will be used inside the parser to classify user's input and track key
words.]*

<hr>

### Others

*[What other design decisions have you made which you feel are relevant? Feel free to separate these into their own subheadings.]*

<br>
<hr>

## Implemented Features
*[What features have you implemented? where, how, and why?]* <br>
*List all features you have completed in their separate categories with their featureId. THe features must be one of the basic/custom features, or an approved feature from Voice Four Feature.*

### Basic Features
1. [LogIn]. Description of the feature ... (easy)
   * Code: [Class X, methods Z, Y](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43) and Class Y, ...
   * Description of feature: ... <br>
   * Description of your implementation: ... <br>

2. [DataFiles]. Description  ... ... (...)
   * Code to the Data File [users_interaction.json](link-to-file), [search-queries.xml](link-to-file), ...
   * Link to the Firebase repo: ...

3. ...
   <br>

### Custom Features
Feature Category: Privacy <br>
1. [Privacy-Request]. Description of the feature  (easy)
   * Code: [Class X, methods Z, Y](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43) and Class Y, ...
   * Description of your implementation: ... <br>
     <br>

2. [Privacy-Block]. Description ... ... (medium)
   ... ...
   <br><br>

Feature Category: Firebase Integration <br>
3. [FB-Auth] Description of the feature (easy)
   * Code: [Class X, entire file](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43) and Class Y, ...
   * [Class B](../src/path/to/class/file.java#L30-85): methods A, B, C, lines of code: 30 to 85
   * Description of your implementation: ... <br>

<hr>

### Surprise Features

- If implemented, explain how your solution addresses the task (any detail requirements will be released with the surprise feature specifications).
- State that "Suprised feature is not implemented" otherwise.

<br> <hr>

## Summary of Known Errors and Bugs

*[Where are the known errors and bugs? What consequences might they lead to?]*
*List all the known errors and bugs here. If we find bugs/errors that your team does not know of, it shows that your testing is not thorough.*

*Here is an example:*

1. *Bug 1:*
   - *A space bar (' ') in the sign in email will crash the application.*
   - ...

2. *Bug 2:*
3. ...

<br> <hr>


## Testing Summary

*[What features have you tested? What is your testing coverage?]*
*Please provide some screenshots of your testing summary, showing the achieved testing coverage. Feel free to provide further details on your tests.*

*Here is an example:*

1. Tests for Search
   - Code: [TokenizerTest Class, entire file](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java) for the [Tokenizer Class, entire file](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43)
   - *Number of test cases: ...*
   - *Code coverage: ...*
   - *Types of tests created and descriptions: ...*

2. xxx

...

<br> <hr>


## Team Management

### Meetings Records
* Link to the minutes of your meetings like above. There must be at least 4 team meetings.
  (each commited within 2 days aftre the meeting)
* Your meetings should also have a reasonable date spanning across Week 6 to 11.*


- *[Team Meeting 1](meeting-template.md)*
- ...
- ...
- [Team Meeting 4](link_to_md_file.md)
- ... (Add any descriptions if needed) ...

<hr>

### Conflict Resolution Protocol
*[Write a well defined protocol your team can use to handle conflicts. That is, if your group has problems, what is the procedure for reaching consensus or solving a problem?
(If you choose to make this an external document, link to it here)]*

This shall include an agreed procedure for situations including (but not limited to):
- e.g., if a member fails to meet the initial plan and/or deadlines
- e.g., if your group has issues, how will your group reach consensus or solve the problem?
- e.g., if a member gets sick, what is the solution? Alternatively, what is your plan to mitigate the impact of unforeseen incidents for this 6-to-8-week project? 
