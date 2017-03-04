package com.aligatorplus.db.service.impl;

import com.aligatorplus.db.repository.SiteRepository;
import com.aligatorplus.db.service.SiteService;
import com.aligatorplus.model.Site;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SiteServiceImpl implements SiteService {
    private static Logger logger = LoggerFactory.getLogger(SiteServiceImpl.class);

    @Autowired
    private SiteRepository siteRepository;

    public void create(Site entity) {
        siteRepository.saveAndFlush(entity);
    }

    public List<Site> findAll() {
        return siteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Site findById(Long id) {
        return siteRepository.getOne(id);
    }

    @Transactional(readOnly = true)
    public Site findByHost(String host) {
        return siteRepository.findByHost(host);
    }

    public Site findByRssLink(String link) {
        return siteRepository.findByRssLink(link);
    }

    public void update(Site entity) {
        siteRepository.save(entity);
    }

    public void delete(Site entity) {
        siteRepository.delete(entity);
    }
}
