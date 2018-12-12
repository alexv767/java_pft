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

//import static org.testng.Assert.assertEquals;

//public class RestTests extends TestBase{
    public class RestTests {

    @Test(alwaysRun = true)
    public void testCreateIssue() throws IOException {
        Set<Issue> oldIssues = getIssues();
        Issue newIssue = new Issue()
                .withSubject("Test issue")
                .withDescription("New test issue");
        int issueId = createIssue(newIssue);
        Set<Issue> newIssues = getIssues();
        oldIssues.add(newIssue.withId(issueId));
//        assertEquals(newIssues, oldIssues);
    }

//    @Test(alwaysRun = true)
//    public void testCheckIssueState() throws IOException {
//
//        int id = 556;
//
//        Set<Issue> issue = getIssue(id);
//
//    }

//    @Test(alwaysRun = true)
//    public void testCheckIssueOpen() throws IOException {
//        Set<Issue> issues = getIssues();  // get all issues
//
//        for (Issue issue : issues){
//
//            int issueId = issue.getId();
//
////            issueId = 123456;
////            if(getIssue(issueId).size() != 1){
////                System.out.println("TOO MANY / OR NOT AT ALL Issues with Id: " + String.format("%d", issueId));
////                return;
////            }
//
//            try {
//                skipIfNotFixed(issueId);
//                System.out.println("Issue is NOT Open. Test will be run now!");
//            } catch (SkipException e) {
//                e.printStackTrace();
//            }
//        }
//    }


    private Set<Issue> getIssues() throws IOException {
        String json = getExecutor().execute(Request.Get("http://bugify.stqa.ru/api/issues.json"))
                .returnContent().asString();
        JsonElement parsed = new JsonParser().parse(json);
        JsonElement issues = parsed.getAsJsonObject().get("issues");
        return new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType());
    }

    private Set<Issue> getIssue(int id) throws IOException {
        String json = getExecutor().execute(Request.Get(String.format("http://bugify.stqa.ru/api/issues/%d.json", id)))
                .returnContent().asString();
        JsonElement parsed = new JsonParser().parse(json);
        JsonElement issues = parsed.getAsJsonObject().get("issues");
        return new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType());
    }

    private int createIssue(Issue newIssue) throws IOException {
        String json = getExecutor().execute(Request.Post("http://bugify.stqa.ru/api/issues.json")
                .bodyForm(new BasicNameValuePair("subject", newIssue.getSubject()),
                          new BasicNameValuePair("description", newIssue.getSubject())))
                .returnContent().asString();
        JsonElement parsed = new JsonParser().parse(json);
        return parsed.getAsJsonObject().get("issue_id").getAsInt();
    }


    private Executor getExecutor(){
        return Executor.newInstance().auth("288f44776e7bec4bf44fdfeb1e646490", "");
    }


}
