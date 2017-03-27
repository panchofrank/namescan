package com.fgaudin.namescan.scheduling.parsers.ofac.impl;

import java.time.LocalDate;

class PublicationInformation {
    private LocalDate publicationDate;
    private int count;

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setCount(int count) {
        this.count = count;
    }

    LocalDate getPublicationDate() {
        return publicationDate;
    }

    int getCount() {
        return count;
    }

}
