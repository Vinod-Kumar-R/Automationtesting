package com.encash.offers.configuration;

import com.encash.offers.beanclass.RepositoryBean;
import com.encash.offers.utility.ExcelReader;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

/**
 * This class the constant variable data which is used during execution of script. 
 * @author Vinod Kumar R
 *
 */
public class ConstantVariable {
  static Logger logger = LogManager.getLogger(ConstantVariable.class.getName());
  /**
   * This variable contain the Browser name in which test script need to execute.
   */
  public static String Browser_Binary_Name;
  /**
   * This variable the contain application URL in which test script need to executed. 
   */
  public static String EncashURL;
  public static String AdminURL;
  public static String LogFile;
  public static String Test_Execution;
  /**
   * This variable contain the Test Script file which need to executed.
   */
  public static String TestDatas;
  /**
   * This variable contain test case file (test case ID) which which need to executed. 
   */
  public static String TestCases;
  public static String TestObjectsWeb;
  public static String TestObjectsMobile;
  public static String ExtentReportsLocation;
  public static String ExtentReportsPropeties;
  public static String ScreenShotlocation;
  public static int ExplictWait;
  public static int polling;
  public static HashMap<String, Integer> TestDataRowNumber;
  public static HashMap<String, List<String>> GetObject;
  public static boolean HeadlessBrowser;
  public static String DesiredAndroidCapability;
  public static String AppiumURL;
  private String dateformat = "dd_MMM_yyyy";
  private String timeformat = "HH_mm_ss";
  public static String ResultBaseLocation;
  public static String ResultLocation;
  public static String ResultDatelocaton;
  public static String Configlocation;



  /**
   * This is the Constructor which is used to initialized the static variable.
   */
  public ConstantVariable()  {
    Configlocation = readEnvironmnetVariable("encashoffers");
    //Read the properties file
    ConfigurationReader cr = new ConfigurationReader();
    cr.readConfig(Configlocation + File.separator + "config.properties");

    //Setting the logger context
    LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) 
                    LogManager.getContext(false);
    File file = new File(Configlocation + File.separator + "log4j2.xml");
    context.setConfigLocation(file.toURI());

    //setting the properties value 
    ResultBaseLocation = Configlocation + File.separator + "Result";
    Test_Execution = cr.getConfigurationStringValue("test_execution");
    ResultDatelocaton = dateTime(dateformat, ResultBaseLocation);
    ResultLocation = dateTime(timeformat, ResultDatelocaton);
    ExtentReportsLocation = ResultLocation + File.separator + "encashoffer.html";
    ScreenShotlocation = folderCreation(ResultLocation, "ScreenShot");
    Browser_Binary_Name = cr.getConfigurationStringValue("browser_Binary_file");
    EncashURL = cr.getConfigurationStringValue("encashurl");
    AdminURL = cr.getConfigurationStringValue("adminurl");
    TestDatas = cr.getConfigurationStringValue("testData");
    TestCases = cr.getConfigurationStringValue("testcase");
    TestObjectsWeb = cr.getConfigurationStringValue("testobjectweb");
    TestObjectsMobile = cr.getConfigurationStringValue("testobjectmobile");
    ExtentReportsPropeties = Configlocation + File.separator + "extentreportpropertes.xml";
    ExplictWait = cr.getConfigurationIntValue("explictwait");
    polling = cr.getConfigurationIntValue("polling");
    HeadlessBrowser = cr.getConfigurationBooleanValue("headlessbrowser");
    AppiumURL = cr.getConfigurationStringValue("appiumServerurl");
  }

  /**
   * This method read the Test case Excel sheet and store the data of test case ID. 
   * starting number in an HashMap so that search can be faster
   * 
   * @throws Exception throw an generic exception
   */

  public void searchTestData() throws Exception  {

    ExcelReader std = new ExcelReader(ConstantVariable.TestDatas, 0);
    this.TestDataRowNumber = new HashMap<String, Integer>();
    // int index = 0;

    for (int i = 0; i < std.rowCout(0); i++) {
      if (std.getCellData(i, 0) != null 
                      && !std.getCellData(i, 0).equalsIgnoreCase("End")
                      && !std.getCellData(i, 0).equalsIgnoreCase("EndTestCase")) {
        this.TestDataRowNumber.put(std.getCellData(i, 0), i);
        logger.debug(std.getCellData(i, 0));
      }
    }

    std.closeWorkbook();
    logger.debug("found the testcase in the TestDatas" + ConstantVariable.TestDatas 
                    + "in the row number ");
  }

  /**
   * This Method is used to read the Object Repository file which is csv file with header.
   * ObjectName and ObjectValue
   * this file contain key and value pair of data where 
   * ObjectName is key and it is unique for human readable format 
   * ObjectValue is the value which contain the xpath values used to identified location 
   *
   *<p>csv file are read the stored in the HashMap&lt;String,String&gt; so that during execution
   * we will get the xpath for an element 
   *
   * @throws Exception throw an generic exception
   */
  public void objectRepository() throws Exception {

    String key;

    this.GetObject = new HashMap<String, List<String>>();
    FileReader file = null;

    if (Test_Execution.equalsIgnoreCase("ANDROID_CHROME") 
                    | Test_Execution.equalsIgnoreCase("IOS_SAFARI")) {
      file = new FileReader(TestObjectsMobile);
    } else {
      file = new FileReader(TestObjectsWeb);
    }

    List<RepositoryBean> repositoryobject = new CsvToBeanBuilder<RepositoryBean>(file)
                    .withType(RepositoryBean.class).build().parse();

    for (RepositoryBean ro : repositoryobject) {
      List<String> object = new ArrayList<String>();
      key = ro.getObjectName();
      object.add(ro.getObjectType());
      object.add(ro.getObjectValue());
      //Value = ro.getObjectValue();
      logger.debug("Key ---> " + key + "  Type ----> " + ro.getObjectType() 
            + "  Value-----> " + ro.getObjectValue());
      GetObject.put(key, object);
    }

  }

  /**
   * This method is used to created the folder structure based on the date and time format. 
   * @param timeformat it can be any time format
   * @param baseLocation this contain the location in which it has to create
   * @return  AbsolutePath of folder is return back  
   */

  public String dateTime(String timeformat, String baseLocation) {
    SimpleDateFormat formatter = new SimpleDateFormat(timeformat); 

    Date date = new Date();

    File file = new File(baseLocation + File.separator + formatter.format(date));
    try {
      FileUtils.forceMkdir(file);
      logger.debug("Folder created at location " + file.getAbsolutePath());
    } catch (IOException e) {
      // TODO Auto-generated catch block

      logger.error("Folder cann't be created", e);
    }

    return file.getAbsolutePath();
  }


  /**
   * This method is used folder based on the given base folder location. 
   * @param baseLocation contain the location in which folder has to create
   * @param foldername contain the folder name in which has to create
   * @return AbsolutePath of folder is return back
   */
  public String folderCreation(String baseLocation, String foldername) {

    File file = new File(baseLocation + File.separator + foldername);
    try {
      FileUtils.forceMkdir(file);
      logger.debug("Folder created at location " + file.getAbsolutePath());
    } catch (IOException e) {
      // TODO Auto-generated catch block
      logger.error("Folder cann't be created", e);
    }

    return file.getAbsolutePath();
  }

  /**
   * This method is used to read the environment variable.
   * @return
   */
  public String  readEnvironmnetVariable(String key) {
    String configuration;
    configuration = System.getenv(key);
    return configuration;
  }

}