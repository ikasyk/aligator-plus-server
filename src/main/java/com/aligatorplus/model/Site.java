package com.aligatorplus.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sites")
public class Site extends AbstractEntity {
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NotNull
    private String host;

    @NotNull
    @Column(unique = true)
    private String rssLink;

    @NotNull
    private String title;

    @ManyToMany
    @JoinTable(name = "subscribes",
            joinColumns = @JoinColumn(name = "site_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;

    public Site() {
        this.id = 0L;
    }

    public Site(String host, String rssLink, String title) {
        this.host = host;
        this.rssLink = rssLink;
        this.title = title;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getRssLink() {
        return rssLink;
    }

    public void setRssLink(String rssLink) {
        this.rssLink = rssLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Site site = (Site) o;

        if (!id.equals(site.id)) return false;
        if (!host.equals(site.host)) return false;
        if (!rssLink.equals(site.rssLink)) return false;
        return title.equals(site.title);

    }

    @Override
    public int hashCode() {
//        int result = id.hashCode();
        System.out.println("PREV HASH");
        System.out.println("HASHCODE = " + host.hashCode());
        try {
            int result = host.hashCode();
            result = 31 * result + rssLink.hashCode();
            result = 31 * result + title.hashCode();
            result = 31 * result + (users != null ? users.hashCode() : 0);
            return result;
        } catch (NullPointerException e) {
            return 1;
        }
    }

    @Override
    public String toString() {
        return "Site{" +
                "id=" + id +
                ", host='" + host + '\'' +
                ", rssLink='" + rssLink + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
