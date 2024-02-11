package ru.petrov.model;

import java.time.LocalDateTime;
import java.util.Objects;

public final class Log extends AbstractEntity {
    private final LocalDateTime registered;
    private final LogLevel level;
    private final User user;
    private final String logValue;


    public Log(LocalDateTime registered, LogLevel level, User user, String logValue) {
        super(null);
        this.registered = registered;
        this.level = level;
        this.user = user;
        this.logValue = logValue;
    }

    public Log(LogLevel level, User user, String logValue) {
        this(LocalDateTime.now(), level, user, logValue);
    }

    public LocalDateTime getRegistered() {
        return registered;
    }

    public LogLevel getLevel() {
        return level;
    }

    public User getUser() {
        return user;
    }

    public String getLogValue() {
        return logValue;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Log) obj;
        return Objects.equals(this.registered, that.registered) &&
                Objects.equals(this.level, that.level) &&
                Objects.equals(this.user, that.user) &&
                Objects.equals(this.logValue, that.logValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registered, level, user, logValue);
    }

    @Override
    public String toString() {
        return "Log[" +
                "dateTime=" + registered + ", " +
                "level=" + level + ", " +
                "user=" + user + ", " +
                "log=" + logValue + ']';
    }

}
