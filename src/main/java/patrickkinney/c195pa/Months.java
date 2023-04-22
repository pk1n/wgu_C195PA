package patrickkinney.c195pa;

/**Class to hold count of appointments by month.
 *
 */
public class Months {

    private int jan;
    private int feb;
    private int march;
    private int april;
    private int may;
    private int june;
    private int july;
    private int aug;
    private int sep;
    private int oct;
    private int nov;
    private int dec;
    private int customerId;
    private String appType;

    /**Constructor for months class.Each month contains the total count of appointments in that month by customer id.
     *
     * @param jan
     * @param feb
     * @param march
     * @param april
     * @param may
     * @param june
     * @param july
     * @param aug
     * @param sep
     * @param oct
     * @param nov
     * @param dec
     * @param customerId
     */
    public Months(int jan, int feb, int march, int april, int may, int june, int july, int aug, int sep, int oct, int nov, int dec, int customerId) {
        this.jan = jan;
        this.feb = feb;
        this.march = march;
        this.april = april;
        this.may = may;
        this.june = june;
        this.july = july;
        this.aug = aug;
        this.sep = sep;
        this.oct = oct;
        this.nov = nov;
        this.dec = dec;
        this.customerId = customerId;

    }

    /** Gets count of appointment in jan
     *
     * @return
     */
    public int getJan() {
        return jan;
    }

    /**Sets count of appointments for jan
     *
     * @param jan
     */
    public void setJan(int jan) {
        this.jan = jan;
    }

    /**Gets count of appointments in Feb
     *
     * @return
     */
    public int getFeb() {
        return feb;
    }

    /**Sets count of appointments in feb
     *
     * @param feb
     */
    public void setFeb(int feb) {
        this.feb = feb;
    }

    /**
     * Gets count of appointments in march
     * @return
     */
    public int getMarch() {
        return march;
    }

    /**
     * Sets counts of appointments in march
     * @param march
     */
    public void setMarch(int march) {
        this.march = march;
    }

    /** Gets count of appointments in april
     *
     * @return
     */
    public int getApril() {
        return april;
    }

    /**
     * Sets count of appointments in april
     * @param april
     */
    public void setApril(int april) {
        this.april = april;
    }

    /**Gets count of appointments in may
     *
     * @return
     */
    public int getMay() {
        return may;
    }

    /**Sets count of appointments in may
     *
     * @param may
     */
    public void setMay(int may) {
        this.may = may;
    }

    /** Gets count of appointments in June
     *
     * @return
     */
    public int getJune() {
        return june;
    }

    /**Sets count of appointments in June
     *
     * @param june
     */
    public void setJune(int june) {
        this.june = june;
    }

    /**Gets count of appointments in July
     *
     * @return
     */
    public int getJuly() {
        return july;
    }

    /**Sets count of appointments in July
     *
     * @param july
     */
    public void setJuly(int july) {
        this.july = july;
    }

    /**Gets count of appointments in August
     *
     * @return
     */
    public int getAug() {
        return aug;
    }

    /**Sets count of appointments in August
     *
     * @param aug
     */
    public void setAug(int aug) {
        this.aug = aug;
    }

    /**Gets count of appointments in September
     *
     * @return
     */
    public int getSep() {
        return sep;
    }

    /**Sets count of appointments in September
     *
     * @param sep
     */
    public void setSep(int sep) {
        this.sep = sep;
    }

    /**Gets count of appointments in October
     *
     * @return
     */
    public int getOct() {
        return oct;
    }

    /**Sets count of appointments in October
     *
     * @param oct
     */
    public void setOct(int oct) {
        this.oct = oct;
    }

    /**Gets count of appointments in November
     *
     * @return
     */
    public int getNov() {
        return nov;
    }

    /**Sets count of appointments in November 
     *
     * @param nov
     */
    public void setNov(int nov) {
        this.nov = nov;
    }

    /**Gets count of appointments in December
     *
     * @return
     */
    public int getDec() {
        return dec;
    }

    /**Sets count of appointments in December
     *
     * @param dec
     */
    public void setDec(int dec) {
        this.dec = dec;
    }

    /**Gets Customer ID
     *
     * @return
     */
    public int getCustomerId() {
        return customerId;
    }

    /**Sets Customer ID
     *
     * @param customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

}
