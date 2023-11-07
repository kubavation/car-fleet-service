package com.durys.jakub.carfleet.requests.vo;

import com.durys.jakub.carfleet.common.errors.ValidationError;
import com.durys.jakub.carfleet.common.errors.ValidationErrorHandler;
import com.durys.jakub.carfleet.common.errors.ValidationErrorHandlers;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class RequestPurpose implements Serializable {

    private final String content;

    public RequestPurpose(String content) {
        this.content = content;
        test(content, ValidationErrorHandlers.throwingValidationErrorHandler());
    }

    public RequestPurpose(String content, ValidationErrorHandler handler) {
        this.content = content;
        test(content, handler);
    }

    public String content() {
        return content;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (RequestPurpose) obj;
        return Objects.equals(this.content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }

    public static void test(String purpose, ValidationErrorHandler handler) {
        if (Objects.isNull(purpose) || purpose.trim().isEmpty()) {
            handler.handle(new ValidationError("Purpose cannot be empty"));
        }
    }

}
