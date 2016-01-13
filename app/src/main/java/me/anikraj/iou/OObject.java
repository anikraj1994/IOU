package me.anikraj.iou;

public class OObject {
    private String who;
    private long number;
    private String email;
    private double amount;
    private long date;
    private int payed;

    public int getPayed() {
        return payed;
    }

    public void setPayed(int payed) {
        this.payed = payed;
    }

    public OObject() {
    }

    public OObject(String who, long number, String email, double amount, long date, int payed) {

        this.who = who;
        this.number = number;
        this.email = email;
        this.amount = amount;
        this.date = date;
        this.payed=payed;
    }

    public String getWho() {

        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
