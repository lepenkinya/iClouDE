package icloude;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import storage.DatabaseException;
import test.FolderTest;
import test.PackageTest;
import test.ProjectTest;
import test.SourceFileTest;

@Path("/testing")
public class TestingResource {

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getInfoJSON() throws IOException, DatabaseException {	
		StringBuilder outputMessageBuilder = new StringBuilder();
		outputMessageBuilder.append("Testing results: <br/>");
		
		
		outputMessageBuilder.append("<br/>SourceFileTest<br/>");
		SourceFileTest SourceFileTest = new SourceFileTest();
		for (String s: SourceFileTest.test()) {
			outputMessageBuilder.append(s + "<br/>");
		}
		
		outputMessageBuilder.append("<br/>ProjectTest<br/>");
		ProjectTest projectTest = new ProjectTest();
		for (String s: projectTest.test()) {
			outputMessageBuilder.append(s + "<br/>");
		}
		
		outputMessageBuilder.append("<br/>FolderTest<br/>");
		FolderTest folderTest = new FolderTest();
		for (String s: folderTest.test()) {
			outputMessageBuilder.append(s + "<br/>");
		}
		
		outputMessageBuilder.append("<br/>PackageTest<br/>");
		PackageTest packageTest = new PackageTest();
		for (String s: packageTest.test()) {
			outputMessageBuilder.append(s + "<br/>");
		}
		
		return outputMessageBuilder.toString();
	}

}
