package com.aligatorplus.controller.sites;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SiteAddRequestBody {
    @NotNull
    @Size(min = 3)
    private String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
