package ru.stqa.pft.rest.tests;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.testng.SkipException;
import ru.stqa.pft.rest.model.Issue;

import java.io.IOException;
import java.util.Set;


public class TestBase {

    public void skipIfNotFixed(int issueId) throws IOException {
        if (isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }

    private boolean isIssueOpen(int id) throws IOException {
        int state = getIssue(id).iterator().next().getState();
        System.out.println(String.format("state = %d", state));
        if ( (state == 0 || state == 4 || state == 1) ) // open, reopen, in progress
            return true;
        else
            return false;
    }

    public Set<Issue> getIssue(int id) throws IOException {  // Set with the only 1 Issue !
        String json = getExecutor().execute(Request.Get(String.format("http://bugify.stqa.ru/api/issues/%d.json", id)))
                .returnContent().asString();
        JsonElement parsed = new JsonParser().parse(json);
        JsonElement issues = parsed.getAsJsonObject().get("issues");
        return new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType());
    }


    public Executor getExecutor(){
        return Executor.newInstance().auth("288f44776e7bec4bf44fdfeb1e646490", "");
    }

}
