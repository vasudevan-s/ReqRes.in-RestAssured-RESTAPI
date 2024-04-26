package in.reqres.base;

import io.restassured.RestAssured;
import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.xml.XmlTest;

/*
Created By: Vasudevan Sampath

 APIBase.java has init methods for REST API operations
 */
public class APIBase {

    @BeforeSuite(alwaysRun = true)
    @Parameters("APIBaseURL")
    public void initSuite(String apiBaseURL) {
        RestAssured.baseURI = apiBaseURL;
    }
}
