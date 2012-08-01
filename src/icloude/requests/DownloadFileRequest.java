package icloude.requests;

/**
 * @author DimaTWL This class describes 'Download file' request from protocol.
 * 
 */
public class DownloadFileRequest extends BaseRequest {
	private String projectID;
	private String fileID;

	private DownloadFileRequest() {
	}

	/**
	 * @return the projectID
	 */
	public String getProjectID() {
		return projectID;
	}

	/**
	 * @return the fileID
	 */
	public String getFileID() {
		return fileID;
	}

}