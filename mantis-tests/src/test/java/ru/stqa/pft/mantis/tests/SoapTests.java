package ru.stqa.pft.mantis.tests;

import org.testng.SkipException;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.Issue;
import ru.stqa.pft.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class SoapTests extends TestBase{

    @Test(alwaysRun = false)
    public void testGetProjects() throws MalformedURLException, ServiceException, RemoteException {
        Set<Project> projects = app.soap().getProjects();

        System.out.println(projects.size());
        for(Project project : projects){
            System.out.println(project.getName());
        }
    }

    @Test(alwaysRun = false)
    public void testCreateIssue() throws MalformedURLException, ServiceException, RemoteException {
        Set<Project> projects = app.soap().getProjects();
        Issue issue = new Issue()
                .withSummary("Test issue").withDescription("Test issue description")
                .withProject(projects.iterator().next());
        Issue created = app.soap().addIssue(issue);
        assertEquals(issue.getSummary(), created.getSummary());
    }

    @Test(alwaysRun = true)
    public void testRunTestForOpenedIssueOnly() throws RemoteException, ServiceException, MalformedURLException {

        int issueId = 1;

        try {
            skipIfNotFixed(issueId);
            System.out.println("Test will be run now!");

        } catch (SkipException e) {
            e.printStackTrace();
        }
    }


}
