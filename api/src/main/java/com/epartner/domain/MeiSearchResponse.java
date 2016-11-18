package com.epartner.domain;

import java.util.List;

/**
 * Created by mapsi on 11/17/16.
 */
public class MeiSearchResponse {

    private List<MeliItem> results;

    private MeliPaging paging;

    public List<MeliItem> getResults() {
        return results;
    }

    public void setResults(List<MeliItem> results) {
        this.results = results;
    }

    public MeliPaging getPaging() {
        return paging;
    }

    public void setPaging(MeliPaging paging) {
        this.paging = paging;
    }
}
