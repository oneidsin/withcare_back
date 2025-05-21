package com.withcare.admin.dto;

import java.sql.Timestamp;

public class CrawlManagementDTO {
    private int source_idx;
    private String create_admin_id;
    private String source_name;
    private String base_url;
    private int crawl_cycle;
    private String crawl_cate;
    private Timestamp last_crawl_at;
    private Timestamp crawl_cycle_updated_at;

    public Timestamp getCrawl_cycle_updated_at() {
        return crawl_cycle_updated_at;
    }

    public void setCrawl_cycle_updated_at(Timestamp crawl_cycle_updated_at) {
        this.crawl_cycle_updated_at = crawl_cycle_updated_at;
    }

    private boolean crawl_yn;

    public int getSource_idx() {
        return source_idx;
    }

    public void setSource_idx(int source_idx) {
        this.source_idx = source_idx;
    }

    public String getCreate_admin_id() {
        return create_admin_id;
    }

    public void setCreate_admin_id(String create_admin_id) {
        this.create_admin_id = create_admin_id;
    }

    public String getSource_name() {
        return source_name;
    }

    public void setSource_name(String source_name) {
        this.source_name = source_name;
    }

    public String getBase_url() {
        return base_url;
    }

    public void setBase_url(String base_url) {
        this.base_url = base_url;
    }

    public int getCrawl_cycle() {
        return crawl_cycle;
    }

    public void setCrawl_cycle(int crawl_cycle) {
        this.crawl_cycle = crawl_cycle;
    }

    public String getCrawl_cate() {
        return crawl_cate;
    }

    public void setCrawl_cate(String crawl_cate) {
        this.crawl_cate = crawl_cate;
    }

    public Timestamp getLast_crawl_at() {
        return last_crawl_at;
    }

    public void setLast_crawl_at(Timestamp last_crawl_at) {
        this.last_crawl_at = last_crawl_at;
    }

    public boolean isCrawl_yn() {
        return crawl_yn;
    }

    public void setCrawl_yn(boolean crawl_yn) {
        this.crawl_yn = crawl_yn;
    }
}
