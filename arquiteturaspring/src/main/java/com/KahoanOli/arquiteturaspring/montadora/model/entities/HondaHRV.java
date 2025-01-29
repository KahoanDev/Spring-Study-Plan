package com.KahoanOli.arquiteturaspring.montadora.model.entities;

import com.KahoanOli.arquiteturaspring.montadora.model.entities.enums.Montadora;

import java.awt.*;

public class HondaHRV extends Carro{

    public HondaHRV(Motor motor) {
        super(motor);
        setModelo("HRV");
        setCor(Color.BLACK);
        setMontadora(Montadora.HONDA);
    }
}
