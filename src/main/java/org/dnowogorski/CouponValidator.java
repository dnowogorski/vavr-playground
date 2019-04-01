package org.dnowogorski;

import io.vavr.collection.CharSeq;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;

class CouponValidator {

    private static final String VALID_ID_CHARS = "[0-9a-z]";

    Validation<Seq<String>, Coupon> validate(String id, long stake) {
        return Validation.combine(validateId(id), validateStake(stake)).ap(Coupon::new);
    }

    private Validation<String, String> validateId(String id) {
        return CharSeq.of(id)
                .replaceAll(VALID_ID_CHARS, "")
                .transform(CharSeq::isEmpty) ? Validation.valid(id) : Validation.invalid("Id contains invalid characters");
    }

    private Validation<String, Long> validateStake(long stake) {
        return stake <= 0 ? Validation.invalid("Stake must be non zero") : Validation.valid(stake);
    }
}