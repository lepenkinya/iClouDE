package icloude.backend_buildserver.requests;

public class DownloadBuildLogsRequest extends BaseRequest {

	/**
	 * @param protocolVersion
	 * @param zipID
	 */
	public DownloadBuildLogsRequest(Integer protocolVersion, String zipID) {
		super(protocolVersion, zipID);
		// TODO Auto-generated constructor stub
	}

}
