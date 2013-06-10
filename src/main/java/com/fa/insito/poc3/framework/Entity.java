package com.fa.insito.poc3.framework;

import java.io.Serializable;
import java.util.Date;

public interface Entity extends Serializable {

    Long getId();

    Date getDateCreation();

    Date getDateDerniereMiseAJour();

}
