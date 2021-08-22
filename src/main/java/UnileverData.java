import java.util.Calendar;
import java.util.Date;

public class UnileverData {
    private Calendar date;
    private Integer randomValue;
    public UnileverData(Calendar date){
        this.date = date;
        randomValue = (int) (Math.random() * 100 + 100);
    }

    public Calendar getCalendar() {
        return date;
    }

    public Integer getYear(){
        return date.get(Calendar.YEAR);
    }
    public Integer getMonth(){
        return date.get(Calendar.MONTH);
    }
    public Integer getDay(){
        return date.get(Calendar.DATE);
    }
    public Integer getHour(){
        return date.get(Calendar.HOUR);
    }
    public Integer getMinute(){
        return date.get(Calendar.MINUTE);
    }
    public Integer getSecond(){
        return date.get(Calendar.SECOND);
    }

    public Integer getRandomValue() {
        return randomValue;
    }

    public void setRandomValue(Integer randomValue) {
        this.randomValue = randomValue;
    }

    public Date getDate() {
        return getCalendar().getTime();
    }
}
