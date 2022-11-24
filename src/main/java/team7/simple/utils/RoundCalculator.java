package team7.simple.utils;

import org.springframework.stereotype.Component;

@Component
public class RoundCalculator {

    /**
     * 수를 반올림하여 특정 자리수까지 나타낸다.
     * @param number 반올림할 수
     * @param digit 몇 자리까지 나타낼 것인지 설정
     * @return 반올림 결과
     */
    public double round(double number, int digit){
        double exp10 = Math.pow(10, digit);

        return Math.round(number * exp10) / exp10;
    }
}
