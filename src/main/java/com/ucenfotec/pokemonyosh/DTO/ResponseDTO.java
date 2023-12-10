package com.ucenfotec.pokemonyosh.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {
    @JsonProperty("success")
    boolean success;
    @JsonProperty("message")
    Object message;

    public boolean isSuccess() {
        return this.success;
    }

    public Object getMessage() {
        return this.message;
    }

    @JsonProperty("success")
    public void setSuccess(boolean success) {
        this.success = success;
    }

    @JsonProperty("message")
    public void setMessage(Object message) {
        this.message = message;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ResponseDTO)) return false;
        final ResponseDTO other = (ResponseDTO) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.isSuccess() != other.isSuccess()) return false;
        final Object this$message = this.getMessage();
        final Object other$message = other.getMessage();
        if (this$message == null ? other$message != null : !this$message.equals(other$message)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ResponseDTO;
    }
}
