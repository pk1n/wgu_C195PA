package patrickkinney.c195pa;

/**Class to hold appointment types.
 *
 */

public class Types {

    private String type;
    private int duration;
    private String location;
    private int qty;

    /**Type object constructor.
     *
     * @param type Type Name/Classification
     * @param duration Meeting Type duration
     * @param location Meeting Type Location
     * @param qty Count of Meetings of simliar type
     */
    public Types(String type, int duration, String location, int qty) {

        this.type = type;
        this.duration = duration;
        this.location=location;
        this.qty=qty;
    }

    /**Gets Type name.
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**Sets Type Name.
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**Gets Type duration
     *
     * @return
     */
    public int getDuration() {
        return duration;
    }

    /**Sets type duration
     *
     * @param duration
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**Gets type location
     *
     * @return
     */
    public String getLocation() {
        return location;
    }

    /**Sets type locaiton
     *
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**Gets type quantity
     *
     * @return
     */
    public int getQty() {
        return qty;
    }

    /**Sets type quantity
     *
     * @param qty
     */
    public void setQty(int qty) {
        this.qty = qty;
    }
}
