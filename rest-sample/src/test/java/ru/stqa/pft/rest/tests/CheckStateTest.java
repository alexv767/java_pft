package ru.stqa.pft.rest.tests;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.testng.SkipException;
import org.testng.annotations.Test;
import ru.stqa.pft.rest.model.Issue;

import java.io.IOException;
import java.util.Set;

public class CheckStateTest extends TestBase {

    @Test(alwaysRun = true)
    public void testCheckState() throws IOException {

            int id = 556;

            Set<Issue> issue = getIssue(id);

            try {
                skipIfNotFixed(id);
                System.out.println("Issue is NOT Open. Test will be run now!");
            } catch (SkipException e) {
                e.printStackTrace();
            }
    }

}
