package team7.simple.unitTest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import team7.simple.utils.RoundCalculator;

import static org.assertj.core.api.Assertions.assertThat;

public class RoundCalculatorTest {
    @Test
    @DisplayName("반올림하여 정수로 표현한다.")
    void 반올림하여_정수로_표현한다() {
        // given
        double number = 10.258;
        RoundCalculator roundCalculator = new RoundCalculator();

        // when
        double result = roundCalculator.round(number, 0);

        // then
        assertThat(result).isEqualTo(10);
    }

    @Test
    @DisplayName("반올림하여 소수점 첫째자리까지 표현한다.")
    void 반올림하여_소수점_첫째자리까지_표현한다() {
        // given
        double number = 10.258;
        RoundCalculator roundCalculator = new RoundCalculator();

        // when
        double result = roundCalculator.round(number, 1);

        // then
        assertThat(result).isEqualTo(10.3);
    }

    @Test
    @DisplayName("반올림하여 소수점 둘째자리까지 표현한다.")
    void 반올림하여_소수점_둘째자리까지_표현한다() {
        // given
        double number = 10.258;
        RoundCalculator roundCalculator = new RoundCalculator();

        // when
        double result = roundCalculator.round(number, 2);

        // then
        assertThat(result).isEqualTo(10.26);
    }
}
