package com.automation.bussiness.admin;

import com.automation.utility.GenericMethod;
import com.automation.utility.WaitMethod;
import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;


public class Admin {

  private static Logger logger = LogManager.getLogger(Admin.class);
  @Autowired
  private GenericMethod genericmethod;
  @Autowired
  private WaitMethod waitmethod;


  /**
   * This method is used to open the Admin URL page.
   * @param dataParam contain the parameter value
   * @return the status of execution i.e. pass or fail
   *
   */

  public String adminurlopen(List<String> dataParam)  {
    //enter username and password 
    logger.debug("Enter the User name ");
    waitmethod.waitForElementPresent("admin_username");
    WebElement username = genericmethod.getElement("admin_username");
    username.sendKeys(dataParam.get(0));
    
    logger.debug("Enter the User Password ");
    WebElement password = genericmethod.getElement("admin_password");
    password.sendKeys(dataParam.get(1));
    
    logger.debug("click on login button");
    WebElement login = genericmethod.getElement("admin_login");
    login.click();
    
    return "pass";
  }

  /**
   * This method is used to create a new competition in admin page. 
   * @param dataParam contain the parameter value
   * @return  the status of execution i.e. pass or fail
   * 
   */
  public String competationForm(List<String> dataParam)  {
    
    gotoCompetation();
    logger.debug("create new Competitions");
    createNewCompetitions();
    
    logger.debug("Enter the Competition name");
    WebElement element = genericmethod.getElement("competition_name");
    waitmethod.waitForElementVisible(element);
    element.sendKeys(dataParam.get(0));
    
    logger.debug("Enter the competition description ");
    element = genericmethod.getElement("competition_description");
    element.sendKeys(dataParam.get(1));
    
    logger.debug("click on the Client dropdown");
    element = genericmethod.getElement("competition_client");
    element.click();
    
    logger.debug("selecting the option from client list");
    element = genericmethod.getElement("competition_client_options");
    //waitmethod.waitForElementVisible(element);
    genericmethod.matOption(element, dataParam.get(2));
    
    logger.debug("click on the locatons");
    element = genericmethod.getElement("competition_location");
    waitmethod.waitForElementVisible(element);
    element.click();
    
    logger.debug("select the location options");
    element = genericmethod.getElement("competition_location_options");
    genericmethod.matOption(element, dataParam.get(3));
    
    logger.debug("clicking on the categeory");
    element = genericmethod.getElement("competition_categories");
    element.click();
    
    logger.debug("Selecting the Multiple categories");
    element = genericmethod.getElement("competition_categories_options");
    List<String> categorieslist =  Arrays.asList(dataParam.get(4).split("~"));
    genericmethod.matOptions(element, categorieslist);
    
    logger.debug("clicking on the overlay");
    element = genericmethod.getElement("competition_overlay");
    element.click();
    
    logger.debug("clicking on the calendar button");
    element = genericmethod.getElement("competition_opencalendar");
    element.click();
    
    logger.debug("selecting the date from calendar");
    element = genericmethod.getElement("competition_calender");
    genericmethod.dateSelection(element, dataParam.get(5), dataParam.get(6), dataParam.get(7));
    
    logger.debug("clicking on the competition add button");
    element = genericmethod.getElement("competition_add");
    element.click();
    
    logger.debug("enable the isActive button");
    waitmethod.waitForElementPresent("competition_active");
    element = genericmethod.getElement("competition_active");
    element.click();
    
    logger.debug("Wait for notification disappear");
    waitmethod.waitForElementPresent("notification");
    element = genericmethod.getElement("notification");
    waitmethod.waitForNotificationAppear(element);
    closeNotification();
    waitmethod.waitForNotificationDisAppear(element);
    
    logger.debug("click on the save button");
    element = genericmethod.getElement("competition_save");
    waitmethod.waitForElementClickable(element);
    element.click();
    
    logger.debug("Wait for notification appear");
    waitmethod.waitForElementPresent("notification");
    element = genericmethod.getElement("notification");
    waitmethod.waitForNotificationAppear(element);
    closeNotification();
    logger.debug("Wait for notification disappear");
    waitmethod.waitForNotificationDisAppear(element);
    
    return "pass";
  }
  
  /**
   * This method is used to fill the offers forms.
   * @param dataParam data required for form
   * @return "pass" if executed success
   */
  public String offersForm(List<String> dataParam) {
    gotoOffers();
    createNewCompetitions();


    logger.debug("Enter the Competition name");
    WebElement element = genericmethod.getElement("competition_name");
    waitmethod.waitForElementVisible(element);
    element.sendKeys(dataParam.get(0));
    
    logger.debug("Enter the competition description ");
    element = genericmethod.getElement("competition_description");
    element.sendKeys(dataParam.get(1));
    
    logger.debug("click on the Client dropdown");
    element = genericmethod.getElement("competition_client");
    element.click();
    
    logger.debug("selecting the option from client list");
    element = genericmethod.getElement("competition_client_options");
    //waitmethod.waitForElementVisible(element);
    genericmethod.matOption(element, dataParam.get(2));
    
    logger.debug("click on the locatons");
    element = genericmethod.getElement("competition_location");
    waitmethod.waitForElementVisible(element);
    element.click();
    
    logger.debug("select the location options");
    element = genericmethod.getElement("competition_location_options");
    genericmethod.matOption(element, dataParam.get(3));
    
    logger.debug("clicking on the categeory");
    element = genericmethod.getElement("competition_categories");
    element.click();
    
    logger.debug("Selecting the Multiple categories");
    element = genericmethod.getElement("competition_categories_options");
    List<String> categorieslist =  Arrays.asList(dataParam.get(4).split("~"));
    genericmethod.matOptions(element, categorieslist);
    
    logger.debug("clicking on the overlay");
    element = genericmethod.getElement("competition_overlay");
    element.click();
    
    logger.debug("clicking on the start calendar button");
    element = genericmethod.getElement("competition_opencalendar");
    element.click();
    
    logger.debug("selecting the date from calendar");
    element = genericmethod.getElement("competition_calender");
    genericmethod.dateSelection(element, dataParam.get(5), dataParam.get(6), dataParam.get(7));
    
    logger.debug("clicking on the end calendar button");
    element = genericmethod.getElement("competition_opencalendar2");
    element.click();
    
    logger.debug("selecting the date from calendar");
    element = genericmethod.getElement("competition_calender2");
    genericmethod.dateSelection(element, "10", "FEB", "2026");
    
    
    return "pass";
    
  }

  /**
   * This the common method used when ever used to click on Competition.
   * @return the status of execution i.e. pass or fail
   */
  public String gotoCompetation() {

    logger.debug("Waiting for the Angular request completed");
    waitmethod.angularWait();
    
    WebElement element = null;  
    logger.debug("click on the Encash button");
    element = genericmethod.getElement("expand_encash");
    element.click();

    logger.debug("Waiting for the Angular request completed");
    waitmethod.angularWait();
    
    logger.debug("click on the Encash button");
    element = genericmethod.getElement("competitions");
    waitmethod.waitForElementClickable(element);
    element.click();

    logger.debug("Waiting for the Angular request completed");
    waitmethod.angularWait();
    
    return "Pass";
  }
  
  
  /**
   * This method is used to create the new competitions.
   * @return "pass" if executed success
   */
  public String createNewCompetitions() {
    
    WebElement element;
    logger.debug("click on the three dot  new competations");
    element = genericmethod.getElement("threedot");
    element.click();

    logger.debug("waiting for the Angular request completed");
    waitmethod.angularWait();

    logger.debug("clicking on the Add to queue button");
    element = genericmethod.getElement("competitions_queue");
    element.click();
    waitmethod.angularWait();
    
    return "Pass";
  }
  
  /**
   * This method is used to go to Offers.
   * @return "pass" if executed success
   */
  public String gotoOffers() {
    
    logger.debug("Waiting for the Angular request completed");
    waitmethod.angularWait();
    
    WebElement element = null;  
    logger.debug("click on the Encash button");
    element = genericmethod.getElement("expand_encash");
    element.click();

    logger.debug("Waiting for the Angular request completed");
    waitmethod.angularWait();
    
    logger.debug("click on the Encash button");
    element = genericmethod.getElement("offers");
    element.click();

    logger.debug("Waiting for the Angular request completed");
    waitmethod.angularWait();
    
    logger.debug("create new offers");
    //createNewCompetitions();
    
    return "pass";
  }
  
  /**
   * This method is used to logout from admin application.
   * @return "pass" if executed success
   */
  public String logoutAdmin() {
    
    logger.debug("clicking the on the user login name menu");
    WebElement element = genericmethod.getElement("logout_admin_menu");
    element.click();
    
    logger.debug("clickin on the logout button");
    waitmethod.waitForElementPresent("logout_admin");
    element = genericmethod.getElement("logout_admin");
    element.click();
    
    genericmethod.refreshPage();
    
    return "pass";
    
  }
 
  /**
  * This method is used to Search for particular competition.
  * @param dataParam contain competition name which need to search
  * @return "pass" if executed success
  */
  public String searchCompetition(List<String> dataParam) {
    logger.debug("Go to competatition ");
    gotoCompetation();

    logger.debug("typing on competitition search box");
    waitmethod.waitForElementPresent("competition_search_page");
    WebElement element = genericmethod.getElement("competition_search_page");
    element.sendKeys(dataParam.get(0));

    logger.debug("wait for result data to load");
    //waitmethod.waitForElementLessThan("competititon_search_result", 2);
    waitmethod.waitForMatTableUpadate(5);
    
    logger.debug("click on the competition search result");
    element = genericmethod.getElement("competititon_search_result");
    //waitmethod.waitThread();
    genericmethod.matTable(element, dataParam.get(0));
    
    return "pass";
  }
  
  /**
   * This method is used to click on the question tab and click on add button.  
   * @param dataParam , contain tab name in which it has to click
   * @return "pass" if click on the question tab else false
   */
  public String questionnairesTab(List<String> dataParam) {
    
    logger.debug("click on the question tab");
    waitmethod.waitForElementPresent("competition_tab");
    WebElement element = genericmethod.getElement("competition_tab");
    genericmethod.tabselect(element, dataParam.get(0));
    
    //click on the questionnaries button for first time
    if (dataParam.get(1).equalsIgnoreCase("yes")) {
      logger.debug("click on the Add questionnaires click");
      element = genericmethod.getElement("competition_question_button");
      waitmethod.waitForElementClickable(element);
      element.click();
    }
    
    return "pass";
  }
  
  
  /**
   * This method is used to add the questionnaires in competition.
   * @param dataParam contain the data
   * @return pass if execution success
   */
  public String questionnariesCreate(List<String> dataParam) {

    WebElement element = genericmethod.getElement("competition_questionaries_list");

    if (dataParam.get(0).equalsIgnoreCase("New")) {
      logger.debug("Create a new row for questionnnaries");
      genericmethod.createNewQuesetionnariesRow(element);
      logger.debug("select the questinnaries from dropdown list");
      genericmethod.newQuestionnaire(element, dataParam.get(1));
    } else {
      genericmethod.newQuestionnaire(element, dataParam.get(1));
    }

    logger.debug("Save the questionnaires");
    genericmethod.saveQuestionnaries(element);
    
    logger.debug("waiting for the notification");
    waitmethod.waitForElementPresent("notification");
    element = genericmethod.getElement("notification");
    waitmethod.waitForNotificationAppear(element);
    closeNotification();
    waitmethod.waitForNotificationDisAppear(element);
    

    return "pass";

  }
  
  /**
   * This method is used to Delete the Questionnaires in Admin.
   * @param dataParam contain which questionnaires need to deleted
   * @return "pass" if execution success
   */
  public String questionnariesDelete(List<String> dataParam) {
    
    WebElement element = genericmethod.getElement("competition_questionaries_list");
    
    logger.debug("delete the questionnaries row");
    genericmethod.deleteQuestionnariesRow(element, dataParam.get(0));
    
    
    return "pass";
  }
  
  /**
   * This method is used to select the Level tab.
   * @param dataParam a list of data which is required for clicking on level tab
   * @return "pass" if execution success
   */
  public String leveltab(List<String> dataParam) {
    
    logger.debug("click on the level tab");
    waitmethod.waitForElementPresent("competition_tab");
    WebElement element = genericmethod.getElement("competition_tab");
    genericmethod.tabselect(element, dataParam.get(0));
    
    //click on the levels button for first time
    if (dataParam.get(1).equalsIgnoreCase("yes")) {
      logger.debug("clicking on the add level button");
      element = genericmethod.getElement("add_levels");
      waitmethod.waitForElementVisible(element);
      element.click();
    }
    
    return "pass";
  }
  
  /**
   * This method is used to select the levels while creating the questionnaires.
   * @param dataParam contain the list of data required for creating levels
   * @return "pass" if execution success.
   */
  public String levels(List<String> dataParam) {
    
    logger.debug("getting the base element for levels");
    WebElement element = genericmethod.getElement("competition_levels_base");

    if (!dataParam.get(0).equalsIgnoreCase("New")) {
      logger.debug("selecting the questionnaires for levels");
      genericmethod.selectLevelsQuestionnaries(element, dataParam.get(1));
    } else {
      logger.debug("click creating the new level to add");
      genericmethod.createNewLevelsRow(element);
      logger.debug("selecting the questionnaires for levels");
      genericmethod.selectLevelsQuestionnaries(element, dataParam.get(1));
    }

    genericmethod.levelSave(element);
    
    logger.debug("waiting for the notification");
    waitmethod.waitForElementPresent("notification");
    element = genericmethod.getElement("notification");
    waitmethod.waitForNotificationAppear(element);
    closeNotification();
    waitmethod.waitForNotificationDisAppear(element);

    return "pass";
  }
  
  
  /**
   * This method is used to close the notification shown to user.
   */
  public void closeNotification() {
    logger.debug("wait for close notification button present");
    waitmethod.waitForElementPresent("closenotification");
    
    logger.debug("click on the close notification button");
    WebElement element = genericmethod.getElement("closenotification");
    element.click();
  }
  
  

}
