package patrickkinney.c195pa;

import java.time.LocalDateTime;

/** Appointment Class. Used to create Appointment objects.
 * This is used to operate on appointments.
 */
public class Appointment {

    public int id;
    public String title;
    public String description;
    public String location;
    public String type;
    public LocalDateTime start;
    public LocalDateTime end;
    public LocalDateTime createdDate;
    public String createdBy;
    public LocalDateTime lastUpdated;
    public String lastUpdatedBy;
    public int customerId;
    public int userId;
    public int contactId;
    public String contactName;

    /** Declares the constructor
     *
     * @param id Appointment ID
     * @param title Appointment Title
     * @param description Appointment Description
     * @param location Appointment Location
     * @param type Appointment Type
     * @param start Appointment Start
     * @param end Appointment End
     * @param createdDate Appointment Created Date
     * @param createdBy Appointment Created By
     * @param lastUpdated Appointment Last Updated
     * @param lastUpdatedBy Appointment Last Updated By
     * @param customerId Appointment Associated Customer ID
     * @param userId Appointment Associated User ID
     * @param contactId Appointment Associated Contact ID
     * @param contactName Appointment Contact Name
     */
    public Appointment(int id, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, LocalDateTime createdDate, String createdBy,LocalDateTime lastUpdated, String lastUpdatedBy, int customerId, int userId, int contactId, String contactName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
        this.contactName = contactName;
    }

    /** This methods returns the Appointment ID when called.
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /** This method sets the Appointment ID.
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /** This method returns the Appointment Title when called.
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /** This method sets the Appointment Title.
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /** This method returns the Appointment description when called.
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /** This method sets the Appointment description.
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /** This method returns the Appointment location.
     *
     * @return
     */
    public String getLocation() {
        return location;
    }

    /** This method sets the Appointment location.
     *
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /** This method gets the Appointment type.
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /** This method sets the Appointment Type.
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /** This method gets the Appointment start datetime.
     *
     * @return
     */
    public LocalDateTime getStart() {
        return start;
    }

    /** Sets the Appointment start time.
     *
     * @param start
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /** Gets the Appointment end time.
     *
     * @return
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /** Sets the appointment end time.
     *
     * @param end
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /** Gets the Appointment created datetime.
     *
     * @return
     */
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    /** Sets the appointment created datetime.
     *
     * @param createdDate
     */
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    /** Gets appointment created by.
     *
     * @return
     */
    public String getCreatedBy() { return createdBy;}

    /**Sets the appointment created by.
     *
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy;}

    /** Gets the appointment lasted udpated timestamp.
     *
     * @return
     */
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    /** Sets the appointment last updated timestamp.
     *
     * @param lastUpdated
     */
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /** Gets the appointment last updated by.
     *
     * @return
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /** Sets the appointment last updated by.
     *
     * @param lastUpdatedBy
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**Gets appointment associated customer ID.
     *
     * @return
     */
    public int getCustomerId() {
        return customerId;
    }

    /** Sets the appointment associated customer ID.
     *
     * @param customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**Gets the associated user ID.
     *
     * @return
     */
    public int getUserId() {
        return userId;
    }

    /**Sets the associated user ID.
     *
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**Gets the appointment contact ID.
     *
     * @return
     */
    public int getContactId() {
        return contactId;
    }

    /** Sets appointment contact ID.
     *
     * @param contactId
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**Gets appointment contact name.
     *
     * @return
     */
    public String getContactName(){return contactName;}

    /** Sets appointment contact name.
     *
     * @param contactName
     */
    public void setContactName(String contactName) {this.contactName = contactName;}
}
