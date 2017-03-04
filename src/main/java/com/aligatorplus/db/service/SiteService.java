package com.aligatorplus.db.service;

import com.aligatorplus.model.Site;
import com.aligatorplus.model.User;

import java.util.List;

public interface SiteService {
    void create(Site entity);
    List<Site> findAll();
    Site findById(Long id);
    Site findByHost(String host);
    Site findByRssLink(String link);
    void update(Site entity);
    void delete(Site entity);
}
