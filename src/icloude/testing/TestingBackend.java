package icloude.testing;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import icloude.testing.backend.BuildServerInteractionTest;
import icloude.testing.backend.DownloadCodeTest;
import icloude.testing.backend.Test;
import icloude.testing.backend.Test.TestResult;

@Path("/testing/backend")
public class TestingBackend {
	
	private List<Test> tests = new ArrayList<Test>();
	
	public TestingBackend() {
		tests.add(new DownloadCodeTest());  // All tests added here!!!
		tests.add(new BuildServerInteractionTest());
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getInfoJSON() {
		StringBuilder outputMessageBuilder = new StringBuilder();
		outputMessageBuilder.append("Test results: <br/>");
		for (Test test : tests) {
			outputMessageBuilder.append("<br/>" + test.getClass().getSimpleName());
			for (TestResult result : test.launch()) {
				outputMessageBuilder.append("<br/>" + result.getName());
				if (result.getResult()) {
					outputMessageBuilder.append(": <font color=\"green\">  passed");
				} else {
					outputMessageBuilder.append(": <font color=\"red\">  failed");
				}
				outputMessageBuilder.append(" ( ");
				outputMessageBuilder.append(result.getDescription());
				outputMessageBuilder.append(" ) </font><br/>");
			}
		}
		return outputMessageBuilder.toString();
	}
}
