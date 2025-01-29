package com.KahoanOli.arquiteturaspring.montadora.configuration;

import com.KahoanOli.arquiteturaspring.montadora.model.entities.Motor;
import com.KahoanOli.arquiteturaspring.montadora.model.entities.enums.TipoMotor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MontadoraConfiguration {

    @Bean
    @Primary
    public Motor motorAspirado(){
        var motor = new Motor();
        motor.setModelo("XPTO-0");
        motor.setCavalos(120);
        motor.setCilindros(4);
        motor.setLitragem(2.0);
        motor.setTipo(TipoMotor.ASPIRADO);

        return motor;
    }

    @Bean
    public Motor motorEletrico(){
        var motor = new Motor();
        motor.setModelo("TH10");
        motor.setCavalos(100);
        motor.setCilindros(0);
        motor.setLitragem(1.4);
        motor.setTipo(TipoMotor.ELETRICO);

        return motor;
    }

    @Bean
    public Motor motorTurbo(){
        var motor = new Motor();
        motor.setModelo("Porsche 911 GTS3");
        motor.setCavalos(150);
        motor.setCilindros(6);
        motor.setLitragem(2.2);
        motor.setTipo(TipoMotor.TURBO);

        return motor;
    }


}
