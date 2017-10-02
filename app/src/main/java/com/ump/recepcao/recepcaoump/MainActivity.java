package com.ump.recepcao.recepcaoump;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Felipe Dourado on 22/03/2017.
 */

public class MainActivity extends Activity {
    
    /*Primeiro método que é chamado de uma Activity quando ela é inicializada*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());

        /*Atribui a (layout) view para a (java) activity*/
        setContentView(R.layout.activity_main);

        /*Botão - Lembrando que precisa criar ele na activity para pegar o id*/
        final Button novo = (Button) findViewById(R.id.button);

        /* Acao de clicar no botão */
        novo.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            /*De onde eu estou e para onde eu vou redirecionar quando clicar no botão */
            Intent intent = new Intent(MainActivity.this, RegistroActivity.class);
            startActivity(intent);

        }
    });

    }

}
