package patrickkinney.c195pa;

import java.time.LocalDateTime;
/** Customer Class. Used to create Customer objects.
 *  This is used to operate on customers associated with appointments.
 */
public class Customer {

    public int id;
    public String name;
    public String address;
    public String postal;
    public String phone;
    public int divisionId;
    public String country;
    public String state;

    /**Customer constructor.
     *
     * @param id Customer ID.
     * @param name Customer Name.
     * @param address Customer Address.
     * @param postal Customer Postal Code.
     * @param phone Customer Phone Number.
     * @param divisionId Customer Division ID.
     * @param country Customer Country Name.
     * @param state Customer Division Name.
     */
    public Customer(int id, String name, String address, String postal, String phone, int divisionId, String country, String state) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postal = postal;
        this.phone = phone;
        this.divisionId = divisionId;
        this.country = country;
        this.state = state;
    }

    /**Gets Customer ID.
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**Sets Customer ID.
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }
    /**Gets Customer Name.
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**Sets Customer Name.
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**Gets Customer Address.
     *
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**Sets Customer Address.
     *
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }
    /**Gets Customer Postal Code.
     *
     * @return
     */
    public String getPostal() {
        return postal;
    }

    /**Sets Customer Postal Code.
     *
     * @param postal
     */
    public void setPostal(String postal) {
        this.postal = postal;
    }
    /**Gets Customer Phone Number.
     *
     * @return
     */
    public String getPhone() {
        return phone;
    }

    /**Sets Customer Phone Number.
     *
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    /**Gets Customer Division ID.
     *
     * @return
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**Sets Customer Division ID.
     *
     * @param divisionId
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }
    /**Gets Customer Country Name.
     *
     * @return
     */
    public String getCountry() {
        return country;
    }

    /**Sets Customer Country Name.
     *
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }
    /**Gets Customer Division Name.
     *
     * @return
     */
    public String getState() {
        return state;
    }

    /**Sets Customer Division Name.
     *
     * @param state
     */
    public void setState(String state) {
        this.state = state;
    }
}


