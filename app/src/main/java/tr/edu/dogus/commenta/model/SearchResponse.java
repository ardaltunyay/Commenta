package tr.edu.dogus.commenta.model;

/**
 * Created by ardaltunyay on 20.05.2017.
 */

public class SearchResponse {

    private Meta meta;
    private Response response;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}