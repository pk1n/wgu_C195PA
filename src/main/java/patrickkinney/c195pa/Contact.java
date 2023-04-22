package patrickkinney.c195pa;

/** Contact Class. Used to create Contact objects.
 *  This is used to operate on contacts associated with appointments.
 */
public class Contact {

    public int contactId;
    public String contactName;
    public String email;

    /** Contact constructor.
     *
     * @param contactId Contact ID
     * @param contactName Contact Name
     * @param email Contact Email
     */
    public Contact(int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }

    /**Gets contact id.
     *
     * @return
     */
    public int getContactId() {
        return contactId;
    }

    /**Sets contact id.
     *
     * @param contactId
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**Gets contact name.
     *
     * @return
     */
    public String getContactName() {
        return contactName;
    }

    /**Sets contact name.
     *
     * @param contactName
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**Gets contact email.
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**Sets contact email.
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
