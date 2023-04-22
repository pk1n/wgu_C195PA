package patrickkinney.c195pa;

/** Country Class. Used to create Country objects.
 *  This is used to operate on countires associated with appointments.
 */
public class Country {

    public int countryId;
    public String country;

    /**Country constructor.
     *
     * @param countryId Country ID
     * @param country Country Name
     */
    public Country(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;
    }

    /**Gets Country ID.
     *
     * @return
     */
    public int getCountryId() {
        return countryId;
    }

    /**Sets Country ID.
     *
     * @param countryId
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**Gets Country Name.
     *
     * @return
     */
    public String getCountry() {
        return country;
    }

    /**Sets Country Name.
     *
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

}
