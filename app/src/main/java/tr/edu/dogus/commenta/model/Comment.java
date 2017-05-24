package tr.edu.dogus.commenta.model;

/**
 * Created by ardaltunyay on 21.05.2017.
 */

public class Comment {
    private String userId;
    private String userMail;
    private String venueId;
    private String comment;
    private String date;

    public Comment() {

    }

    public Comment(String userId,String venueId, String comment, String date, String userMail) {
        setUserId(userId);
        setVenueId(venueId);
        setComment(comment);
        setDate(date);
        setUserMail(userMail);
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getVenueId() {
        return venueId;
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
