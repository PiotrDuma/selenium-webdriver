# selenium-webdriver

Test automation project built with Selenium WebDriver for end-to-end and regression testing of a web-based email application. This project focuses on smote testing basic user features such as logging in and sending eamils. Modular and extensible architecture provides easly configurable enviroment settings, cross-browser support and extensible structure for adding new test cases.

> [!CAUTION]
> The 'https://account.proton.me/mail' page includes CAPTCHA verification to secure anti bot activity, which may result test failures. 

## Requirements:
- Java 21;
- Maven;
- browsers: Firefox, Chrome;

## Installation:

1. Clone the repository:
```
git clone https://github.com/PiotrDuma/selenium-webdriver.git
```
2. Navigate to project folder
3. Execute maven command:
  - default settings:
```
mvn clean test 
```
  - configurable flag examples:
    - -Dconfig=dev.properties
    - -Dbrowser=chrome
    - -DsuiteXmlFile=src/test/resources/testng.xml
```
mvn clean test -Dconfig=dev.properties -DsuiteXmlFile=src/test/resources/testng.xml
```


## Changelog:

### init_project: 
- add webdriver singleton factory;
- add PageObject structure;
- init success login test case;
- add property reader;
- add test listener to capture screenshot on test failure;
- add SLF4J logger;

### init_review:
- refactor PageObject classes;
- remove base test class;
- rename test methods and paths;
- add failure test cases;
- move DataProvider methods to test classes;
- extend webdriver factory with chrome browser;
- change testng.xml options to run methods in parallel;

### branch 1_1:
- fix failure test cases;
- add thread sleep (after click and setting text) to slow automation process;
- add dev.properties file with chrome browser and slow mode option;
- add Lombok private field setting;
- add maven plugin to run testNG test suite;
