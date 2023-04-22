package patrickkinney.c195pa;

/**Users class to hold the various types of users.
 *
 */
public class Users {

    public int userId;
    public String userName;
    public String userPassword;

    /**User object constructor.
     *
     * @param userId Integer user ID
     * @param userName User name
     * @param userPassword User password
     */
    public Users(int userId, String userName, String userPassword) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;

    }

    /**Gets user ID
     *
     * @return
     */
    public int getUserId() {
        return userId;
    }

    /**Sets User ID
     *
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**Gets User name
     *
     * @return
     */
    public String getUserName() {
        return userName;
    }

    /**Sets User name
     *
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**Gets User password
     *
     * @return
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**Sets User password
     *
     * @param userPassword
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

}
