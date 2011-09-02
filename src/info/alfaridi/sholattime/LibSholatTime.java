/**
 * Saya membuat library perhitungan sholat berdasarkan perhitungan yang 
 * diberikan oleh DR. Rinto Anugraha (Dosen Fisika UGM).
 * Jangan tanyakan saya tentang nama-nama variable yang digunakan atau darimana
 * rumus ini berasal, harap bertanya langsung pada perumusnya di 
 * rinto74@yahoo.com
 */
package info.alfaridi.sholattime;

import java.util.Calendar;
import java.util.Date;

public class LibSholatTime {

    private Double latitude;
    private Double langitude;
    private Integer altitude;
    private Integer timeZone;
    private Integer subuhDegree;
    private Integer isyaDegree;
    private Calendar nowTime;
    private Integer ashrShadow;

    /**
     * Public methods
     */
    public LibSholatTime() {
        this.nowTime = Calendar.getInstance();
        nowTime.setTime(new Date());
        this.latitude = new Double(0);
        this.langitude = new Double(0);
        this.subuhDegree = 20;
        this.isyaDegree = 18;
        this.timeZone = 1;
        this.altitude = 50;
        this.ashrShadow = 1;
    }

    public LibSholatTime(Double langitude, Double latitude, Integer timeZone,
            Integer altitude, Integer subuhDegree, Integer isyaDegree, CalculationMethod method) {
        this.latitude = latitude;
        this.langitude = langitude;
        this.timeZone = timeZone;
        this.altitude = altitude;
        this.nowTime = Calendar.getInstance();
        this.isyaDegree = isyaDegree;
        this.subuhDegree = subuhDegree;
        nowTime.setTime(new Date());
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLangitude() {
        return langitude;
    }

    public void setLangitude(Double langitude) {
        this.langitude = langitude;
    }

    public Integer getAltitude() {
        return altitude;
    }

    public void setAltitude(Integer altitude) {
        this.altitude = altitude;
    }

    public Integer getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(Integer timeZone) {
        this.timeZone = timeZone;
    }

    public Integer getSubuhDegree() {
        return subuhDegree;
    }

    public void setSubuhDegree(Integer subuhDegree) {
        this.subuhDegree = subuhDegree;
    }

    public Integer getIsyaDegree() {
        return isyaDegree;
    }

    public void setIsyaDegree(Integer isyaDegree) {
        this.isyaDegree = isyaDegree;
    }

    public Calendar getNowTime() {
        return nowTime;
    }

    public void setNowTime(Calendar nowTime) {
        this.nowTime = nowTime;
    }

    public Double getFajrTime() {
        return this.getZuhurTime() - (12 / Math.PI)
                * Math.acos((Math.sin(-1 * this.getSubuhDegree() * Math.PI / 180)
                - Math.sin(this.getSunDeclinationRadians())
                * Math.sin(this.getLatitudeRadians())) / (Math.cos(this.getSunDeclinationRadians())
                * Math.cos(this.getLatitudeRadians())));
    }

    public Double getDawnTime() {
        return this.getZuhurTime() - (12 / Math.PI)
                * Math.acos((Math.sin((-0.8333 - 0.0347 * Math.sqrt(this.getAltitude()))
                * Math.PI / 180) - Math.sin(this.getSunDeclinationRadians())
                * Math.sin(this.getLatitudeRadians())) / (Math.cos(this.getSunDeclinationRadians())
                * Math.cos(this.getLatitudeRadians())));
    }

    public String timeConverter(Double timeToConvert) {
        Double secondLeft = timeToConvert - timeToConvert.intValue();
        secondLeft = secondLeft * 3600;
        Integer minutes = secondLeft.intValue() / 60;
        Integer seconds = secondLeft.intValue() - (minutes * 60);
        return String.valueOf(timeToConvert.intValue()) + ":" + minutes + ":" + seconds;
    }

    public Double getZuhurTime() {
        return 12 + this.timeZone - this.getLangitude() / 15 - getEquationOfTime() / 60;
    }

    public Double getAshrTime() {
        // Need to add support for Hanafi.
        return this.getZuhurTime()
                + (12 / Math.PI)
                * Math.acos((Math.sin(Math.atan(1 / (this.ashrShadow + Math.tan(Math.abs(this.getLatitudeRadians()
                - this.getSunDeclinationRadians())))))
                - Math.sin(this.getSunDeclinationRadians())
                * Math.sin(this.getLatitudeRadians())) / (Math.cos(this.getSunDeclinationRadians())
                * Math.cos(this.getLatitudeRadians())));
    }

    public Double getMaghribTime() {
        return this.getZuhurTime()
                + (12 / Math.PI)
                * Math.acos((Math.sin((-0.8333 - 0.0347
                * Math.sqrt(this.getAltitude())) * Math.PI / 180)
                - Math.sin(this.getSunDeclinationRadians())
                * Math.sin(this.getLatitudeRadians())) / (Math.cos(this.getSunDeclinationRadians())
                * Math.cos(this.getLatitudeRadians())));
    }

    public Double getIshaTime() {
        return this.getZuhurTime()
                + (12 / Math.PI)
                * Math.acos((Math.sin(-1 * this.getIsyaDegree() * Math.PI / 180)
                - Math.sin(this.getSunDeclinationRadians())
                * Math.sin(this.getLatitudeRadians())) / (Math.cos(this.getSunDeclinationRadians())
                * Math.cos(this.getLatitudeRadians())));
    }

    public void setCalculation(CalculationMethod method) {

        switch (method) {
            case HANAFI:
                this.ashrShadow = 2;
                break;
            case SHAFII:
                this.ashrShadow = 1;
                break;
            default:
                this.ashrShadow = 1;
                break;
        }

    }

    /**
     * Private methods
     */
    private Integer getYear() {
        return this.nowTime.get(Calendar.YEAR);
    }

    private Integer getMonth() {
        return this.nowTime.get(Calendar.MONTH) + 1;
    }

    private Integer getDate() {
        return this.nowTime.get(Calendar.DATE);
    }

    private Integer getVarC() {
        return this.getYear() / 100;
    }

    private Integer getVarB() {
        Integer val = null;

        if (this.getYear() < 1583) {
            if (this.getMonth() < 11) {
                if (this.getDate() < 4) {
                    val = 0;
                } else {
                    if (this.getDate() > 14) {
                        val = 2 + (this.getVarC() / 4) - this.getVarC();
                    } else {
                    }
                }
            } else {
                val = 2 + (this.getVarC() / 4) - this.getVarC();
            }
        } else {
            val = 2 + (this.getVarC() / 4) - this.getVarC();
        }

        return val;
    }

    private Double getUniversalTimeOnJulianDate() {
        Double val = new Double(0);
        Double year = 365.25 * this.getYear();
        Double month = 30.60001 * (this.getMonth() + 1);

        val = Double.valueOf("1720994.5") + year.intValue() + month.intValue()
                + this.getVarB().doubleValue() + this.getDate().doubleValue()
                + 12 / 24;
        return val;
    }

    private Double getLocalTimeOnJulianDate(Integer TimeZone) {
        return this.getUniversalTimeOnJulianDate() - TimeZone.doubleValue() / 24;
    }

    private Double getRadianOnDate() {
        return 2 * Math.PI * (this.getLocalTimeOnJulianDate(this.timeZone) - 2451545)
                / 365.25;
    }

    private Double getVarU() {
        return (this.getLocalTimeOnJulianDate(this.timeZone) - 2451545) / 36525;
    }

    private Double getL0() {
        return (280.46607 + 36000.7698 * this.getVarU()) * Math.PI / 180;
    }

    private Double getSunDeclination() {
        return 0.37877
                + 23.264 * Math.sin((57.297 * this.getRadianOnDate() - 79.547) * Math.PI / 180)
                + 0.3812 * Math.sin((2 * 57.297 * this.getRadianOnDate() - 82.682) * Math.PI / 180)
                + 0.17132 * Math.sin((3 * 57.297 * this.getRadianOnDate() - 59.722) * Math.PI / 180);
    }

    private Double getSunDeclinationRadians() {
        return Math.toRadians(this.getSunDeclination());
    }

    private Double getEquationOfTime() {
        return (-1 * (1789 + 237 * this.getVarU()) * Math.sin(this.getL0())
                - (7146 - 62 * this.getVarU()) * Math.cos(this.getL0())
                + (9934 - 14 * this.getVarU()) * Math.sin(2 * this.getL0())
                - (29 + 5 * this.getVarU()) * Math.cos(2 * this.getL0())
                + (74 + 10 * this.getVarU()) * Math.sin(3 * this.getL0())
                + (320 - 4 * this.getVarU()) * Math.cos(3 * this.getL0())
                - 212 * Math.sin(4 * this.getL0())) / 1000;
    }

    private Double getLatitudeRadians() {
        return this.getLatitude() * Math.PI / 180;
    }
}
