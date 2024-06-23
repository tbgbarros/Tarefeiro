package com.example.tarefeiro

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 2000 // Tempo de exibição da splash em milissegundos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

        // Handler para iniciar a MainActivity após um período de tempo
        Handler().postDelayed({
            // Intent para iniciar a MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Finaliza a SplashActivity para impedir que o usuário volte para ela pressionando o botão de voltar
        }, SPLASH_TIME_OUT)
    }
}
