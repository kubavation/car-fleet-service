package com.durys.jakub.carfleet.requests.vo;

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


}
