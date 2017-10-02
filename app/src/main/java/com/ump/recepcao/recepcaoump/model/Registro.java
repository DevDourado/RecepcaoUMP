package com.ump.recepcao.recepcaoump.model;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Felipe Dourado on 22/03/2017.
 */

public class Registro implements Serializable {

    private String id;
    private String nomeVisitante;
    private String telefone;
    private String email;

    /*private int idade;*/

    private String observacao;

    public Registro() {
        this.id = UUID.randomUUID().toString();
    }

    public Registro(String nomeVisitante, String telefone, String email, String observacao) {
        this.id = UUID.randomUUID().toString();
        this.nomeVisitante = nomeVisitante;
        this.telefone = telefone;
        this.email = email;
        /*this.idade = idade;*/
        this.observacao = observacao;
    }

    public String getId(){
        return this.id;
    }

    public String getNomeVisitante() {
        return nomeVisitante;
    }

    public void setNomeVisitante(String nomeVisitante) {
        this.nomeVisitante = nomeVisitante;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public String toString() {

        String texto = "Nome: "+nomeVisitante+ System.lineSeparator()+
                "Telefone: "+telefone+System.lineSeparator()+
                "Email: "+email+System.lineSeparator()+
                /*"Idadae: "+idade+System.lineSeparator()+*/
                "Observação: "+observacao+System.lineSeparator();
        return texto;

    }
}
