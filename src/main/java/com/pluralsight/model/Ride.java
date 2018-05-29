package com.pluralsight.model;

public class Ride {

  private int id;
  private String name;
  private int duration;

  private Ride(Builder builder) {
    name = builder.name;
    duration = builder.duration;
    id = builder.id;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getDuration() {
    return duration;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Ride{");
    sb.append("id=").append(id);
    sb.append(", name='").append(name).append('\'');
    sb.append(", duration=").append(duration);
    sb.append('}');
    return sb.toString();
  }

  public static Name builder() {
    return new Builder();
  }

  public interface Build {

    Build withId(int val);

    Ride build();
  }

  public interface Duration {

    Build withDuration(int val);
  }

  public interface Name {

    Duration withName(String val);
  }

  public static final class Builder implements Duration, Name, Build {

    private int id;
    private int duration;
    private String name;

    private Builder() {
    }

    @Override
    public Build withDuration(int val) {
      duration = val;
      return this;
    }

    @Override
    public Duration withName(String val) {
      name = val;
      return this;
    }

    @Override
    public Build withId(int val) {
      id = val;
      return this;
    }

    public Ride build() {
      return new Ride(this);
    }
  }
}
