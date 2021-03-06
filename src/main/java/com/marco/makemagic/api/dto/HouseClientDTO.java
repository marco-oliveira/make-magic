package com.marco.makemagic.api.dto;

import java.io.Serializable;
import com.marco.makemagic.api.client.MakeMagicClient;

/**
 * Classe de transferência referente a serialização da consulta no {@link MakeMagicClient}.
 *
 * @author Marco Antônio
 */
public class HouseClientDTO implements Serializable {

    private static final long serialVersionUID = 3732648579131570922L;

    private String _id;

    private String name;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
