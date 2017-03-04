package com.aligatorplus.db.repository;

import com.aligatorplus.model.Site;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteRepository extends AbstractRepository<Site> {
    @Query("select s from Site s where s.host = ?1")
    Site findByHost(String host);

    @Query("select s from Site s where s.rssLink = ?1")
    Site findByRssLink(String link);
}
