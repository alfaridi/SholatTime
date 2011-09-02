package info.alfaridi.sholattime;

import java.util.Calendar;


public class MainClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
            Calendar time = Calendar.getInstance();
            time.set(Calendar.YEAR, 2011);
            time.set(Calendar.MONTH, 10);
            time.set(Calendar.DATE, 3);
            
            LibSholatTime libSholatTime = new LibSholatTime();
            libSholatTime.setNowTime(time);
            libSholatTime.setSubuhDegree(20);
            libSholatTime.setIsyaDegree(18);
            libSholatTime.setCalculation(CalculationMethod.SHAFII);
            System.out.println("Tanggal sekarang : " + libSholatTime.getNowTime().getTime());
            System.out.println("Fajr    = " + libSholatTime.timeConverter(libSholatTime.getFajrTime()));
            System.out.println("Dawn    = " + libSholatTime.timeConverter(libSholatTime.getDawnTime()));
            System.out.println("Dhuhur  = " + libSholatTime.timeConverter(libSholatTime.getZuhurTime()));
            System.out.println("Ashr    = " + libSholatTime.timeConverter(libSholatTime.getAshrTime()));
            System.out.println("Maghrib = " + libSholatTime.timeConverter(libSholatTime.getMaghribTime()));
            System.out.println("Isya    = " + libSholatTime.timeConverter(libSholatTime.getIshaTime()));
	}

}
