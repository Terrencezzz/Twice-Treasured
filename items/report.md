# [G09 - Twice Treasured] Report

## Table of Contents

1. [Team Members and Roles](#team-members-and-roles)
2. [Summary of Individual Contributions](#summary-of-individual-contributions)
3. [Application Description](#application-description)
4. [Application UML](#application-uml)
5. [Application Design and Decisions](#Code Design and Decisions)
6. [Summary of Known Errors and Bugs](#summary-of-known-errors-and-bugs)
7. [Testing Summary](#testing-summary)
8. [Implemented Features](#implemented-features)
9. [Team Meetings](#Meetings Records)
10. [Conflict Resolution Protocol](#conflict-resolution-protocol)

## Administrative
- Firebase Repository Link: [Second Hand Market-Twice Treasured](https://console.firebase.google.com/project/second-hand-market-affd5/overview)
  
   - Confirm: I have already added comp21006442@gmail.com as a Developer to the Firebase project prior to due date.
   
     ![Check_Developer_to_the_Firebase_project](media/report/Check_Developer_to_the_Firebase_project.png)
- Two user accounts for markers' access are usable on the app's APK (do not change the username and password unless there are exceptional circumstances. Note that they are not real e-mail addresses in use):
   - Username: comp2100@anu.edu.au	Password: comp2100
   
   - Username: comp6442@anu.edu.au	Password: comp6442
   
     
   
     ![Check_Special_Account_comp2100](media/report/Check_Special_Account_comp2100.png)
   
     ![Check_Special_Account_comp6442](media/report/Check_Special_Account_comp6442.png)
   
   

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

1.**u7756873, Lingjie Qin**  I have <u>20.6%</u> contribution, as follows: 

- **Code Contribution in the final App**
  - Feature
    - Basic Features 4 [DataStream] - [Post.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/Post.java)
    - Basic Features 5 [Search] - [SearchService.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/SearchService.java)
    - S2 Search-Filter - [SearchResultPage.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/SearchResultPage.java)
- **Code and App Design** 
  - Data Structure
    - AVLTree - [inOrderTraversalAscending, inOrderTraversalDescending](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/common/AVLTree.java#L216-234)
- **Others**
  - APP display video
  - Test: [TokenTest.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/test/java/com/example/myapplication/TokenTest.java), [AVLTreeTest.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/test/java/com/example/myapplication/AVLTreeTest.java)
  - Meeting-0416 Lead and scribe

2.**u7303997, Scott Ferrageau de St Amand** I have <u>16.8%</u> contribution, as follows: 

- **Code Contribution in the final App**
  - 1
- **Code and App Design** 
  - 1
- **Others**
  - 1

3.**u7642453, Wanzhong Wu** I have 21.4% contribution, as follows: 

- **Code Contribution in the final App**
  - Feature
    - B1 Login, F1 FB-Auth - [LoginPage.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/LoginPage.java),[RegisterActivity.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/RegisterActivity.java)
    - B5 Search, S1 Search-Invalid, S2 Search-Filter - [Token.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/Token.java),[Tokenizer.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/Tokenizer.java),[Parser.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/Parser.java)
    - B3 LoadShowData, B4 DataStream -[Post.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/Post.java)
    - FB-Persist - [build.gradle.kts](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/build.gradle.kts?ref_type=heads)
- **Others**
  - APK packaging
  - Create report slide
  - Setup environment, connect firebase
  - Design database

4.**u7706423, Wen Li** I have <u>21.2%</u> contribution, as follows: 

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
  - Meeting-0423 Lead and scribe

5.**u7769944, Xiaojie Zhou** I have <u>20%</u> contribution, as follows: <br>

- **Code Contribution in the final App**
  - Feature
    - DataFiles 2,500 valid data instances in the firebase ,you can also see the src/main/assets to find them
    - LoadShowData [Database.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/426017bae89d609e9e99b822e2c032ee09310a88/src/app/src/main/java/com/example/myapplication/basicClass/Database.java#L48-75)
    - AVLTree  finish basic methods. [AVLNode.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/426017bae89d609e9e99b822e2c032ee09310a88/src/app/src/main/java/com/example/myapplication/common/AVLNode.java#L1-70)[AVLTree.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/426017bae89d609e9e99b822e2c032ee09310a88/src/app/src/main/java/com/example/myapplication/common/AVLTree.java#L1-164)
    - U1 Interact-Micro [ProductPage.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/ProductPage.java?ref_type=heads#L161-229)
    - U2 Interact-Follow [Favorite.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/Favorite.java?ref_type=heads#L1-61) [FavoriteAdapter.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Adapters/FavoriteAdapter.java?ref_type=heads#L1-274)[FavoritePage.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/FavoritePage.java?ref_type=heads#L1-347)[IntroPage.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/426017bae89d609e9e99b822e2c032ee09310a88/src/app/src/main/java/com/example/myapplication/Activities/IntroPage.java#L27-29)
  - other
    - [ProductPage.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/426017bae89d609e9e99b822e2c032ee09310a88/src/app/src/main/java/com/example/myapplication/Activities/ProductPage.java#L1-158)
    - [ProductsManagePage.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/426017bae89d609e9e99b822e2c032ee09310a88/src/app/src/main/java/com/example/myapplication/Activities/ProductsManagePage.java#L1-97) [ProductsManageAdapter.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/426017bae89d609e9e99b822e2c032ee09310a88/src/app/src/main/java/com/example/myapplication/Adapters/ProductsManageAdapter.java#L1-115)[ProductEditorPage.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/426017bae89d609e9e99b822e2c032ee09310a88/src/app/src/main/java/com/example/myapplication/Activities/ProductEditorPage.java#L1-191)
    - [SearchItemAdapter.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/426017bae89d609e9e99b822e2c032ee09310a88/src/app/src/main/java/com/example/myapplication/Adapters/SearchItemAdapter.java#L1-103)
    - Test:[AVLNodeTest.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/426017bae89d609e9e99b822e2c032ee09310a88/src/app/src/test/java/com/example/myapplication/AVLNodeTest.java#L1-106) [AVLTreeTest.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/426017bae89d609e9e99b822e2c032ee09310a88/src/app/src/test/java/com/example/myapplication/AVLTreeTest.java#L113-170)[FavoriteTest.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/426017bae89d609e9e99b822e2c032ee09310a88/src/app/src/test/java/com/example/myapplication/FavoriteTest.java#L1-76)
- **Code and App Design** 
  - UI design: I completed the design and function implementation of the product details page, seller product management page, user collection list page, and UI design of the search results page.
- **Others**
  - Report Writing：Wen Li and I completed the writing of the entire report
  - Write two meeting records

## Application Description

**Twice Treasured** is a platform designed for buying and selling new and second-hand items. With Twice Treasured, users can:

- Browse and search for products of interest, and communicate with sellers anytime, anywhere.

- "Like" products to follow updates such as price drops or item delistings, ensuring they don't miss out on great deals.

- Upload and sell their own items, promoting the circulation of unused goods and supporting a sustainable second-hand market.

  ![intropage](media/report/intropage.png)

  

  ![homepage](media/report/homepage.png)
  
  ![search](media/report/search.png)
  
  ![chat](media/report/chat.png)
  
  ![favorite list](media/report/favorite list.png)
  
  ![post](media/report/post.png)
  
  **image:login,scan goods,search,chat,favorite,post(from top to bottom)**

### Application Use Cases and or Examples

**Browsing and Searching for Items**

![search](media/report/search.png)

- Lily wants to buy a new dress, so she opens Twice Treasured and searches for second-hand branded dresses.

**Communicating with Sellers and Favoriting Items**

![chat01](media/report/chat01.png)

![product_detail_page](media/report/product_detail_page.png)

- Lily finds a dress she likes and clicks the product link to view the details. She thinks it’s great and uses the “Chat with Seller” feature in the bottom right corner to negotiate the price. 
- If Lily likes the item but wants to compare it with others, she can click the heart icon in the bottom left corner to favorite the item and easily find it later in her favorites list.

**Post Items for Sale**

![post](media/report/post.png)

- While preparing to move  home, Lily finds the TV she seldom use. She decides to list the TV for sale on Twice Treasured. 

**Notifications**

![notification](media/report/notification.png)

- Within a day, Lily receives a notification from the system that two users are interested in her item and have favorited it.

 **Managing and Modifying Items**

![userpage](media/report/user%20page.png)

![manageproduct](media/report/manage%20product.png)

- A week passes and Lily’s dress hasn’t sold. She decides to lower the price to see if she can sell it more quickly.

![](media/report/userdetail.png)

- After moving to a new place, Lily edits her profile and changes her location, avatar, nickname and other information.

<hr> 

### Application UML

***go to media/report/UML.png to see the clearer picture***

![UML](media/report/UML.png)

<hr>


## Code Design and Decisions

- Details about the parser (describe the formal grammar and language used)

- Decisions made (e.g., explain why you chose one or another data structure, why you used a specific data model, etc.)

- Details about the design patterns used (where in the code, justification of the choice, etc)



<hr>

### Data Structures

*I used the following data structures in my project:*

1. **AVLTree**

* The Parser class uses AVLTree to handle the filtering of product information, based on location, category and name tags.
* Code Locations: defined in [Class AVLTree](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/common/AVLTree.java?ref_type=heads#L6-348) ; processed using [Class Parser](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/Parser.java?ref_type=heads#L15-113)
* Reasons:
    * *Searching product information by using an AVLTree is much more efficient than using a LinkedList. If there are N elements in the LinkedList, the worst case requires traversing the entire LinkedList, and the time complexity is O(n). But in AVLTree, the height of the tree is kept at O(log n) and the search operation can be very fast.*
    * *Complex query logic can be implemented efficiently by dynamically building and updating AVLTree in different parsing stages.*
    * *AVLTree naturally maintains the order of elements during their operations, which makes range queries and minimum/maximum query operations very efficient.*


<hr>

### Design Patterns
**1.Singleton**

* *Objective*: used for storing **the only one database connection instance** for Firebase Integration feature.
* *Code Locations*
   * defined in [Database.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/Database.java)
   * processed using in [RecommendAdapter()](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Adapters/RecommendAdapter.java#L39), [FavoritePage.onCreate()](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/FavoritePage.java#L77-78), [HomePage.onCreate()](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/HomePage.java?#L75), [ProductPage.onCreate()](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/ProductPage.java#L53-54),[UserDetailPage.onCreate()](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/UserDetailPage.java#L98),[UserPage.onCreate()](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/UserPage.java?#L67),...
* *Reasons*
   * Singleton ensures only one database connection is created, <u>saving resources</u>.
   * <u>Reusing</u> the same connection reduces system <u>overhead</u>, making things faster.
   * Singleton ensures <u>safe access</u> to the connection, even in a multi-threaded environment.
   * All parts of the program use the same connection, <u>ensuring data consistency</u>.

**2.State**

* *Objective*:  used for **retrieve the current user's login status** from [GlobalVariables](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/GlobalVariables.java#L42-65) to dynamically control the visibility of UI elements and enforce corresponding functional restrictions. 

* *Code Locations*
  * defined in  [UserState.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/UserState.java), [UserLoggedInState.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/UserLoggedInState.java), [UserLoggedOutState.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/UserLoggedOutState.java)
  * processed using in [GlobalVariables.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/GlobalVariables.java#L42-65), [CommonHelper.refreshLoginUser()](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/common/CommonHelper.java?#L94-95), [HomePage.Logout](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/HomePage.java?#L141-142), [HomePage.initLoginUser()](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/HomePage.java?#L211-212), [UserDetailPage.updateUserInfo()](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/UserDetailPage.java#L193-194)
* *Reasons*
  * <u>UI elements and functional restrictions can change dynamically</u> based on the user's login status, improving user experience and responsiveness.
  * Easily <u>adapt to changes and new requirements</u> without affecting the entire codebase.

**3.Facotry Method**

* *Objective*: used for **generating different notices** and store them into different database reference in factory 
* *Code Locations*
  
  * defined in [Notice.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/Notice.java), [NoticeFactory.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/NoticeFactory.java) , [UserNotice.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/UserNotice.java), [FavoriteNotice.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/FavoriteNotice.java)
  * processed using in [ProductPage.toggleFavoriteStatus()](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/ProductPage.java#L220-222), [UserDetailPage.updateUserInfo()](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/UserDetailPage.java#L187-189)
* *Reasons*
  
  *  <u>Easy addition of new notice types</u> without modifying existing code. 
  *  <u>Relying on factory interfaces instead of concrete classes</u>, it fosters a modular and loosely coupled design, facilitating system maintenance and evolution.
  
  

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

`Tokenizer`

- code: [Tokenizer.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/Tokenizer.java?),[Token.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/Token.java?ref_type=heads)

- feature: The user input will be converted to different kinds of Tokens, this will help Parser.

- implementation: After we get the input from user, it will be converted into following Tokens; LOCATION, CATEGORY, NAME and IGNORE. The basic idea is to track the keyword, Every single type of token will have a checklist, after correct the typo of user input, if the key word is contained by one of the checklist, it will become that type of Token. If the key word doesn't contain by any of the checklist, it will become NAME.

`Parser`

- code: [Parser.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/Parser.java)


- feature: The search result will be provided, and it will be store in an `AVLtree`.


- implementation: Once will have the user input, the Tokenizer will convert it into Tokens, then the Parser will use Tokens to clip the `AVLtree`. When we create a parser, it will 
  generate three query base on the user input, the first time it will go through location query, then clip `AVLtree`, after that, it will go through the `Category query` and `Name query`. If the Token type is IGNORE, we will do nothing, just ignore it.

<hr>

## Implemented Features
### Basic Features
1. [LogIn] Users must be able to log in (not necessarily sign up). (easy)
   * Code: [LoginPage.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/LoginPage.java)
   * Feature: Users need to <u>input their email and password for authentication</u>. If the credentials are correct, they will be directed to the `HomePage`.
   * Implementation: On the login screen, <u>enter your email address and password</u>, then call `FirebaseAuth.signInWithEmailAndPassword` to <u>authenticate the user</u>. If authentication is successful, you will be <u>redirected</u> to the `HomePage`; if it fails, a login failure message will be displayed.
2. [DataFiles] Create a dataset with at least 2,500 valid data instances, each representing a meaningful piece of information in your app. The data should be represented and stored in a structured format taught in the course. (easy)
   * Code to the Data File [user_data_0513.json](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/assets/user_data_0513.json), [product_data.json](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/assets/product_data.json)
   * Link to the Firebase repo: [Second Hand Market-Twice Treasured](https://console.firebase.google.com/project/second-hand-market-affd5/overview)
3. [LoadShowData] Load and display data instances from your dataset. Choose an appropriate format to present the different types of data. (easy) 
   - Code:[initLoginUser()](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/HomePage.java?#L189-229)
   - Feature: Load and display current User Information
   - Implementation:  Base on user's email, query and retrieve data from firebase, update `GlobalVariables.LoginUser` and textView value accordingly.
4. [DataStream] Create data instances to simulate users’ actions and interactions, which are then used to feed the app so that when a user is logged in, these data are loaded at regular time intervals and visualized on the app. (medium)
   - Code: [updateUserInfo()](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/UserDetailPage.java#L176-205)
   - Feature: Update current User information
   - Implementation: Retrieve user data reference from firebase, call `setValue` to change value. If password changes, call `FirebaseAuth.updatePassword` change user authentication identification.
5. [Search] Users must be able to search for information on your app. (medium) 
   - Code: [findProduct()](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/SearchService.java?ref_type=heads), [Parser.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/Parser.java), [Tokenizer.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/Tokenizer.java?ref_type=heads), [Token.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/Token.java?ref_type=heads)
   - Feature: Track user's input, then display the result from the database.
   - Implementation: Use `findProduct()` to track user's input, then deal with the input with parser, the parser, the Tokenizer and Tokens will help parser to provide an `AVLtree `which contains the result.

### Custom Features

| Feature ID | Feature                                             | Criteria |
| :--------: | --------------------------------------------------- | -------- |
|                                      | **Search-related features** ||
| 1          | Search-Invalid                                      | medium   |
| 2          | Search-Filter                                       | easy     |
|            | **UI Design and Testing**                           |          |
| 1          | UI-Layout                                           | easy     |
|            | **Greater Data Usage, Handling and Sophistication** |          |
| 2          | Data-Profile                                        | easy     |
| 3          | Data-GPS                                            | easy     |
| 5          | Data-Deletion                                       | medium   |
|            | **Firebase Integration**                            |          |
| 1          | FB-Auth                                             | easy     |
| 2          | FB-Persist-extension                               | hard |
|            | **Peer to Peer Messaging**                          |          |
| 1          | P2P-DM                                              | hard     |
|            | **User Interactivity**                              |          |
| 1 | Interact-Micro | easy |
| 2          | Interact-Follow                                     | medium   |
| 4          | Interact-Noti                                       | medium   |
|            | **Privacy**                                         |          |
| 2          | Privacy-Visibility                                  | easy     |

**Search-related features** 

1. [Search-Invalid] On top of giving search results from valid inputs, search functionality can process and 	correctly handle partially invalid search queries and give meaningful results. <u>(medium)</u> 
   - Code: [checkNameTypo()](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/Parser.java?ref_type=heads), [checkTypo()](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/Tokenizer.java?ref_type=heads), [checkMiss()](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/Tokenizer.java?ref_type=heads), [checkTypoWithMiss()](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/Tokenizer.java?ref_type=heads), [checkDuplicate()](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/Tokenizer.java?ref_type=heads),
   - Implementation: Once we get the user input, we will track key words and transfer them to each different type of token. During the processing, these functions will check if it's a typo or not, then it will correct it.
2. [Search-Filter] Sort and filter a list of items returned from searches, with the use of suitable UI 
   components. <u>(easy)</u> 
   - Code: 
     - [FindNewProduct()](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/SearchService.java#L51-57)
     - [FindUsedProduct()]((https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/SearchService.java#L59-65))
     - [FindProductsAscendingOrder()](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/SearchService.java#L108-115)
     - [FindProductsDescendingOrde()](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/SearchService.java#L117-124)
   - Implementation: Use `AVLTree's inOrderTraversalAscending` and `inOrderTraversalDescending ` to sort the prices of items in ascending/descending order. For condition filtering, the product is added to the AVL tree only when the product instance is not null and the product's condition is New/Used.

**UI Design and Testing** 

​	1.[UI-Layout]. Incorporate suitable layout adjustments in the UI components for portrait and landscape 	layout variants, as well as different screen sizes. <u>(easy)</u> 
- Code: [HomePageLayout](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/res/layout/activity_home_page.xml)
- Implementation: Arrange the layout with <u>a Top bar, Content, and Bottom bar</u> within a `RelativeLayout` . The Top bar's contents are set within a `ConstraintLayout `for precise alignment, while the Content resides in a `ScrollView`, enabling seamless scrolling. Lastly, the Bottom bar is nested within a `LinearLayout`, ensuring its placement at the absolute bottom using *layout_alignParentBottom*.

![CustomerFeature_UILayout](media/report/CustomerFeature_UILayout.png)



*Note: majority of pages adhere to this framework; here, we're simply using the homepage layout as an illustrative example.*

**Greater Data Usage, Handling and Sophistication** 

2.[Data-Profile]. Create a Profile Page for Users or any Entities, which contains a media file.<u>(easy)</u>

- Code: [UserDetailPage.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/UserDetailPage.java)
- Implementation: Transition from `UserPage `to Profile, then to `UserPageDetailPage`. On the `UserPageDetailPage`, seamlessly load all details of the logged-in user. Users can effortlessly modify their <u>avatar, name, location, and password</u>, then *double-check alert shows*, after user confirms, system will store new data into database. After successfully changing the password, <u>prompt the user to log in again for security.</u>

3.[Data-GPS] Use GPS information based on location data in your App. <u>(easy)</u> 

- Code: [getLocation()](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/UserDetailPage.java?#L285-331)
- Implementation: Add location permission to `AndroidManifest.xml`. Upon user approval, create a location service client to <u>fetch last known location</u>. Convert the location to a city name and display it in a TextView.

5.[Data-Deletion] Implement Deletion for your chosen tree data structure, and the deletion must serve 
a purpose within your application. <u>(medium)</u> 

- Code:[AVLTree.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/common/AVLTree.java?ref_type=heads),[AVLNode.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/common/AVLNode.java?ref_type=heads)
- Implementation: We decide to use `AVLtree `and compare the production by price, the reason is that it will be efficient to do the filter. Every times the user  search 	something, the application will put the product into the `avltree`, then use parser to "clip" the `avltree ` to get the result. Then after that, user can user the filter to show the 	production with a certain sequence without reloading the database again, this will increate the efficiency significantly.

**Firebase Integration**

1.[FB-Auth] Use Firebase to implement User Authentication/Authorisation. <u>(easy)</u> 

- Code: [loginUser()](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/LoginPage.java?#L86-104), [registerUser()](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/RegisterActivity.java?#L85-118), [updateUserInfo()](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/UserDetailPage.java?#L183)
- Implementation: Use `FirebaseAuth.createUserWithEmailAndPassword` to <u>create a user</u>, `FirebaseAuth.signInWithEmailAndPassword` to <u>authenticate the user</u>, and `FirebaseAuth.updatePassword` to <u>change the user's password</u>.

2.[FB-Persist-extension]] Use Firebase to persist all data used in your app. <u>(medium)</u> 

- Code: [refreshLoginUser()](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/common/CommonHelper.java?#L94-95),[updateUserInfo](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/UserDetailPage.java#L187-189)
- Implementation: Implement a listener that triggers subsequent actions once data updates are completed.

**Peer to Peer Messaging**

1.[P2P-DM] Provide users with the ability to message each other directly in private. <u>(hard)</u> 

- Code
- Implementation

**User Interactivity**

1.[Interact-Micro] The ability to micro-interact with items/users (e.g. like, block, connect to another 
user, etc.) [stored in-memory]. <u>(easy)</u> 

- Code: [ProductPage.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/ProductPage.java?ref_type=heads#L161-229)
- Implementation:In `ProductPage.java`, the favorite feature is implemented using `checkFavoriteStatus` and `toggleFavoriteStatus`. `checkFavoriteStatus` checks if the user has favorited the product and updates the button state. When the button is clicked, `toggleFavoriteStatus` adds or removes the favorite and updates the button state accordingly.

2.[Interact-Follow] The ability to ‘follow’ items. There must be a section that presents all the items 
followed by a user, grouped, and ordered. [stored in-memory] <u>(medium)</u> 

- Code:[Favorite.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/Favorite.java?ref_type=heads#L1-61) [FavoriteAdapter.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Adapters/FavoriteAdapter.java?ref_type=heads#L1-274)[FavoritePage.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/Activities/FavoritePage.java?ref_type=heads#L1-347)[IntroPage.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/426017bae89d609e9e99b822e2c032ee09310a88/src/app/src/main/java/com/example/myapplication/Activities/IntroPage.java#L27-29)
- Implementation:The products collected by users are all displayed on the favorite page, and the products can be viewed in categories and sorted in ascending or descending order according to price.

4.[Interact-Noti] The ability to send notifications for interactions (e.g., follow request, product viewed, 
etc.). A notification must be sent only after a predetermined number of interactions are set [e.g., 
when ≥2 requests have been received or 2 follow requests have been received). <u>(medium)</u> 

- Code: [Notice.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/Notice.java), [NoticeFactory.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/NoticeFactory.java) , [UserNotice.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/UserNotice.java), [FavoriteNotice.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/FavoriteNotice.java)
- Implementation:  <u>Generate different notices</u> and store them into different database reference by using <u>Factory Method</u>. The `UserNotice `used for password changing, `FavoriteNotice `used for user's product favorites number >=2.

**Privacy**

2.[Privacy-Visibility] Given there are at least two types of visibility (public, private, ...), users can only 
see some profiles or contents that are set to Public or after request being accepted <u>(easy)</u>. 

- Code:  [UserState.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/UserState.java), [UserLoggedInState.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/UserLoggedInState.java), [UserLoggedOutState.java](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/src/app/src/main/java/com/example/myapplication/basicClass/UserLoggedOutState.java)
- Implementation: <u>Using State</u>, create different user states to control access to the application. Based on the current state, users who log in successfully will <u>have access to all features and the complete UI</u>. Visitors who choose to skip login will <u>have limited access</u>, allowing them only to use the search features and the login function.



<hr>

### Surprise Features

- If implemented, explain how your solution addresses the task (any detail requirements will be released with the surprise feature specifications).
- State that "Suprised feature is not implemented" otherwise.

<br> <hr>

## Summary of Known Errors and Bugs

**Bugs**

1. *Bug 1:* Due to <u>asynchronous database data retrieval</u>, if a user navigates to the `HomePage ` and clicks on the bottom navigation bar before `initLoginUser() `finishes, the system might incorrectly assume they are not logged in.<u>(fixed by add a loading overlay or dialog to wait for `initLoginUser ` to finish.)</u>

**Errors**

1. Error 1: In case of <u>slow internet connection</u>, <u>timeout errors</u> may occur while retrieving GPS coordinates, preventing the current location from being obtained.


## Testing Summary

 **Overall Coverage Summary**

![UnitTestofOverallCoverageSummary](media/report/UnitTestofOverallCoverageSummary.png)

Although the data in the graph shows that the test coverage has not reached the minimum requirement of 70%, the actual unit test coverage is 100% when excluding special cases such as UI interaction classes and database interaction classes. See the introduction below for details.

The unit tests mainly cover the basic class and common packages, with details as follows:

**Package: basic class**

![UnitTestofbasicclass](media/report/UnitTestofbasicclass.png)

- Unit tests cover 18 out of 21 classes in the basic class package.
- The remaining 3 classes interact with the Firebase database, making them unsuitable for unit tests, resulting in a class test coverage of 0% for these classes.
- Excluding these three exceptional cases, the actual class-level unit test coverage for the basic class package is 100%.



**Package: common**

![](media/report/Unittestofcommon.png)

- This package includes 3 classes.

- Due to the interaction with the Firebase database in the `CommonHelper` class, the unit tests did not cover all of its code.

- Excluding the special case of this class, the actual unit test coverage for the `common` package is 100%.

**Package: Activities, Adapters**

![UnitTestofActivitiesandAdapter](media/report/UnitTestofActivitiesandAdapter.png)

- Both packages involve UI interactions and are not suitable for unit testing.

- Therefore, only 4 out of 9 classes in the Adapters package were teste

  


## Team Management

### Meetings Records
* [Meeting-0415](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/items/meeting-0415.md?ref_type=heads)
  
* [Meeting-0416](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/items/meeting-0416.md?ref_type=heads)
  
* [Meeting-0423](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/items/meeting-0423.md?ref_type=heads)
  
* [Meeting-0429](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/items/meeting-0429.md?ref_type=heads)
  
* [Meeting-0506](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/items/meeting-0506.md?ref_type=heads)
  
* [Meeting-0514](https://gitlab.cecs.anu.edu.au/u7706423/gp-24s1/-/blob/main/items/meeting-0514.md?ref_type=heads)
  
  

<hr>

### Conflict Resolution Protocol
*[Write a well defined protocol your team can use to handle conflicts. That is, if your group has problems, what is the procedure for reaching consensus or solving a problem?
(If you choose to make this an external document, link to it here)]*

This shall include an agreed procedure for situations including (but not limited to):
- e.g., if a member fails to meet the initial plan and/or deadlines
- e.g., if your group has issues, how will your group reach consensus or solve the problem?
- e.g., if a member gets sick, what is the solution? Alternatively, what is your plan to mitigate the impact of unforeseen incidents for this 6-to-8-week project? 
