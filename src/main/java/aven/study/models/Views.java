package aven.study.models;

public class Views {

    public interface Id {};

    public interface ForeignId extends Id {};

    public interface DataWithoutKeys extends Fields {};

    public interface Password {};

    public interface PublicData {};

    public interface DefaultFields {};

    public interface Fields {};
}
