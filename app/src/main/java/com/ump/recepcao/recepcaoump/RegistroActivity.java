package com.ump.recepcao.recepcaoump;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ump.recepcao.recepcaoump.model.Registro;
import com.ump.recepcao.recepcaoump.util.Mask;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Felipe Dourado on 22/03/2017.
 */

public class RegistroActivity extends Activity {

    private Registro registro;

    //* Função do campo Nome do Visitante - Nome deve ser maior do que dois */
    private boolean isEmptyNome(EditText validacao) {
        String text = validacao.getText().toString().trim();
        if (text.length() > 2){
            return true;
        }else

            return false;
    }

    //*Função do campo Telefone*//
    private boolean isEmptyTelefone(EditText validacao) {

        String text = validacao.getText().toString().trim();

        if ((text.length() == 13) || (text.length() == 14)) {
            return true;

        } else {

            return false;

        }

    }

    //*Função do campo E-mail*//
    public boolean isEmptyEmail(String email) {

        Pattern pattern;
        Matcher matcher;

        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);

        return matcher.matches();

    }

    //*Quando uma activity for chamada, o método "onCreate" é onde seu código irá começar a rodar*//
    DatabaseReference ref;
    /**ValueEventListener listener;*/
    String selected;
    Boolean readyToGo = false;

    /* Request...*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        /* Atribui a (layout)view para a (java)activity */
        setContentView(R.layout.activity_registro);

        /*Pega a referência do banco(semelhante a uma estrutura de pastas) - Informações no Banco de Dados*/
        ref = FirebaseDatabase.getInstance().getReference().child("Registro");

        /*Campos - Devem ser criados nas activity para pegar o id*/
        final EditText nomeVisitante = (android.widget.EditText) findViewById(R.id.nomeVisitante);
        final EditText telefone = (android.widget.EditText) findViewById(R.id.telefone);
        final EditText email = (EditText) findViewById(R.id.email);
        final EditText observacao = (android.widget.EditText) findViewById(R.id.observacao);
        final Button salvar = (android.widget.Button) findViewById(R.id.salvar);

        TextWatcher mask = Mask.insert("(##)#####-####", telefone);
        telefone.addTextChangedListener(mask);

        //Se você quiser pegar o click de um botão é só fazer assim:
        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                /*Validacao do campo: "Nome" colocando a mensagem de validação*/
                if (!isEmptyNome(nomeVisitante)) {

                    nomeVisitante.setError("Necessário preencher o Nome!");

                    return;

                /*Validacao do campo: "Telefone" colocando a mensagem de validação*/
                } else if (!isEmptyTelefone(telefone)) {

                    telefone.setError("Necessário preencher o Telefone!");

                    return;

                 /*Validacao do campo: "Email" colocando a mensagem de validação*/
                } else if (!isEmptyEmail(email.getText().toString())){

                    email.setError("Necessário preencher o E-mail!");

                    return;

                }


                /*Quando você vai pegar um Texto de um EditText, quando vc usa o método "getText()"
                você não pega a String direto, vc usa um toString() para transformar o objeto que volta no getText()
                para uma String*/
                String nome = nomeVisitante.getText().toString();
                String tel = telefone.getText().toString();
                String ema = email.getText().toString();
                String obs = observacao.getText().toString();

                /*Instanciando Objeto Registro.*/
                registro = new Registro(nome, tel, ema, obs);

                /*Confirmação da ação do botão "SALVAR"*/
                AlertDialog.Builder builder = new AlertDialog.Builder(RegistroActivity.this);
                builder.setMessage("Deseja realmente salvar?")
                        .setCancelable(false)
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                saveRegistro();
                            }
                        })
                        .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

    }

    /*Bloco de código que salva dados no Firebase, foi adicionado dois callbacks, um olhando o sucesso da transação e outro a falha*/
    public void saveRegistro(){
        ref.child(registro.getId()).setValue(registro).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Intent intent = new Intent(RegistroActivity.this, SucessoActivity.class);
                intent.putExtra("Registro",registro);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegistroActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        readyToGo=false;
    }
}

