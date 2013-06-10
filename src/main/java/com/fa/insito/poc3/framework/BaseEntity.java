package com.fa.insito.poc3.framework;

import org.hibernate.CallbackException;
import org.hibernate.Session;
import org.hibernate.classic.Lifecycle;

import java.io.Serializable;
import java.util.Date;

/**
 * Classe de base pour les entités.
 * Fournit la gestion de l'ID, de la version et des dates de création et de mise à jour et du gestionnaire d'évènements.
 */
public abstract class BaseEntity implements Entity, Lifecycle {

    protected Long id;

    private Date version;

    private Date dateCreation;

    protected BaseEntity() {
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public Date getDateCreation() {
        return dateCreation;
    }

    @Override
    public Date getDateDerniereMiseAJour() {
        return version;
    }

    public Date getVersion() {
        return version;
    }

    /**
     * Méthode equals finale pour imposer la sémantique de comparaison des entités :
     * - 2 entités sont égales si leur id sont égaux
     * - 2 nouvelles entités sont égales si leurs références sont égales
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof BaseEntity)) {
            return false;
        }

        BaseEntity that = (BaseEntity) o;

        if (getId() != null && that.getId() != null) {
            return getId().equals(that.getId());
        }

        if (getId() != null || that.getId() != null) {
            return false;
        }

        return this == o;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : super.hashCode();
    }


    public boolean isNew(){
        return id == null;
    }

    /**
     * *******************************
     * <p/>
     * Implémentation du Lifecycle Hibernate
     * <p/>
     * ********************************
     */

    @Override
    public boolean onSave(Session s) throws CallbackException {
        beforeSaveOrUpdate();
        this.dateCreation = new Date();
        return NO_VETO;
    }

    @Override
    public final boolean onUpdate(Session s) throws CallbackException {
        beforeSaveOrUpdate();
        return NO_VETO;
    }

    @Override
    public final boolean onDelete(Session s) throws CallbackException {
        return NO_VETO;
    }

    @Override
    public void onLoad(Session s, Serializable id) {
        postLoad();
    }

    protected void postLoad() {

    }

    protected void beforeSaveOrUpdate() {

    }
}
