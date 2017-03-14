package webanalyzer.response;

import javax.ws.rs.core.Response;

/**
 * Created by jacekniedzwiecki on 08.03.2017.
 */
public class AddResponse {

    private final Response.Status status;
    private final int pathId;

    public static AddResponse response(Response.Status status, int id) {
        return new AddResponse(status, id);
    }

    private AddResponse(Response.Status status, int pathId) {
        this.status = status;
        this.pathId = pathId;
    }

    public Response.Status getStatus() {
        return status;
    }

    public int getPathId() {
        return pathId;
    }

    @Override
    public String toString() {
        return "{ \"type\" : \"AddResponse\"," +
                " \"status\" : \"" + status.toString() + "\", " +
                " \"pathId\" : \"" + pathId + "\"" +
                "}";
    }
}
