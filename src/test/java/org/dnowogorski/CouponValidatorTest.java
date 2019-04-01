package org.dnowogorski;

import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CouponValidatorTest {

    private CouponValidator couponValidator;

    @Before
    public void setUp() {
        couponValidator = new CouponValidator();
    }

    @Test
    public void validate_couponIdNotAlphaDigit_returnsError() {
        Validation<Seq<String>, Coupon> result = couponValidator.validate("1234&&aaa", 2);

        assertThat(result.isInvalid()).isTrue();
        assertThat(result.getError()).contains("Id contains invalid characters");
    }

    @Test
    public void validate_negativeStake_returnsError() {
        Validation<Seq<String>, Coupon> result = couponValidator.validate("1234aaa", -1);

        assertThat(result.isInvalid()).isTrue();
        assertThat(result.getError()).contains("Stake must be non zero");
    }

    @Test
    public void validate_zeroStakeAndCouponIdNotAlphaDigit_returnsError() {
        Validation<Seq<String>, Coupon> result = couponValidator.validate("1234$$aaa", 0);

        assertThat(result.isInvalid()).isTrue();
        assertThat(result.getError().size()).isEqualTo(2);
        assertThat(result.getError()).contains("Id contains invalid characters", "Stake must be non zero");
    }

    @Test
    public void validate_returnsCouponInstance() {
        String couponId = "1a2b3c4d";
        int stake = 200;
        Validation<Seq<String>, Coupon> result = couponValidator.validate(couponId, stake);

        assertThat(result.isValid()).isTrue();
        assertThat(result.get()).isEqualToComparingFieldByField(new Coupon(couponId, stake));
    }
}
