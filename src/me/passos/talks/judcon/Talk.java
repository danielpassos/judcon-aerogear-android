package me.passos.talks.judcon;

import org.jboss.aerogear.android.RecordId;

import java.io.Serializable;

public class Talk implements Serializable {

    @RecordId
    private Long id;
    private String title;

    public Talk(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }

}
