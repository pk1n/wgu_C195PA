package patrickkinney.c195pa;
/** Division Class. Used to create Division objects.
 *  This is used to operate on Divisions associated with appointments.
 */
public class Division {

    public int divisionId;
    public String division;
    public int countryId;

    /**Division object constructor.
     *
     * @param divisionId Integer Division ID
     * @param division String Division Name
     * @param countryId Integer Country ID
     */
    public Division(int divisionId, String division, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId;
    }

    /**Gets Division ID.
     *
     * @return
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**Sets Division Id.
     *
     * @param divisionId
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**Gets Division Name.
     *
     * @return
     */
    public String getDivision() {
        return division;
    }

    /**Sets Division Name.
     *
     * @param division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**Gets Country ID.
     *
     * @return
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Sets Country ID.
     * @param countryId
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
}
