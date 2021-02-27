package pl.bookstore.restapi.util;

public class Deleted {

    public static String msg(String obj, long id) {
        return obj.toUpperCase().charAt(0) + obj.substring(1) + " " + id + " deleted.";
    }
}
