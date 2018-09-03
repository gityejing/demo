package date;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2015/4/16.
 */
public class CfgOfWorkdayService {
    
	public int getCount(CfgOfWorkday cwd){
        int count = 0;
        Calendar now = Calendar.getInstance();
        now.setTime(cwd.getSt());
        while(now.getTime().compareTo(cwd.getEn())<=0){//����ʱ��Σ���������
            //1��7Ϊ��ĩ  2��6Ϊ��һ������
            Integer weekday = now.get(Calendar.DAY_OF_WEEK);
            if(cwd.getType().intValue()==1){// 1.��һ������ż�,���������յ���
                if(weekday>=2 && weekday<=6){//�����������շżٵĲ���ӣ�
                    count +=1;
                }
            }else if(cwd.getType().intValue()==2){//2.��Ϣ�յ��ϰ�,���������ϰ�
                if(weekday==1 || weekday==7){//��������һ�������ϰ�Ĳ���ӣ�
                    count +=1;
                }
            }
            now.add(Calendar.DAY_OF_MONTH,1);
        }
        return count;
    }

    public Date getBeforeOrAfterWorkDay(Date date ,int num){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int mod = num % 5;
        int other = num / 5 * 7;
        for (int i = 0; i < mod;) {
            cal.add(Calendar.DATE, 1);
            switch (cal.get(Calendar.DAY_OF_WEEK)) {
                case Calendar.SUNDAY:
                case Calendar.SATURDAY:
                    break;
                default:
                    i++;
                    break;
            }
        }
        if (other > 0)
            cal.add(Calendar.DATE, other);
        return new Date(cal.getTimeInMillis());
    }

}
