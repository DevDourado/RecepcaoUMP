package com.ump.recepcao.recepcaoump;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ump.recepcao.recepcaoump.model.Registro;

/**
 * Created by Felipe Dourado on 22/03/2017.
 */

public class SucessoActivity extends Activity {


    /* Request...*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*Atribui a (layout)view para a (java)activity */
        setContentView(R.layout.activity_sucesso);

        final Registro registro = ((Registro) getIntent().getSerializableExtra("Registro"));

        final TextView usuario = (android.widget.TextView) findViewById(R.id.usuario);

        /*Mensagem do usuário cadastrado*/
        usuario.setText("Visitante "+ registro.getNomeVisitante()+" Recebido!");

        final Button novoCadastro = (Button) findViewById(R.id.button2);

        final Button enviarEmail = (Button) findViewById(R.id.button3);

        enviarEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendEmail(new String[]{registro.getEmail()}, registro);
            }
        });

        /* Acao de clicar no botão */
        novoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            /*De onde eu estou e para onde eu vou redirecionar quando clicar no botão */
                Intent intent = new Intent(SucessoActivity.this, RegistroActivity.class);
                startActivity(intent);

            }
        });

    }

    /** Método que envia os dados salvos para o e-mail */
    public void sendEmail(String[] emails, Registro registro){

        String conteudo = "Um novo visitante foi recepcionado pela equipe de recepção da UMP."

                +System.lineSeparator()+
                System.lineSeparator()+

                registro.toString()+System.lineSeparator()+

                System.lineSeparator()+
                "Essa mensagem foi enviada automaticamente pelo aplicativo de recepção de visitantes da UMP Pinheiros. "
                +System.lineSeparator()+
                "Por favor, não responda a este e-mail.";

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , emails);
        i.putExtra(Intent.EXTRA_SUBJECT, "Novo visitante UMP Pinheiros – "+registro.getNomeVisitante());
        i.putExtra(Intent.EXTRA_TEXT, conteudo);
        try {
            startActivity(Intent.createChooser(i, "Enviar email..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(SucessoActivity.this, "Não existe um cliente de email instaldo.", Toast.LENGTH_SHORT).show();
        }
    }
}
