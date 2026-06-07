package com.example.myapplication.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.R

@Composable
fun EsenlinkLauncherScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Arka plan resmi
        // Not: Bu resmin res/drawable/esenlink_bg.png olarak kaydedilmiş olması gerekir.
        // Eğer resim henüz eklenmediyse hata almamak için placeholder bir yapı kurulabilir.
        Image(
            painter = painterResource(id = R.drawable.esenlink_bg),
            contentDescription = "Esenlink Background",
            modifier = Modifier.fillMaxSize(),            contentScale = ContentScale.FillWidth
        )

        // MentorNet ikonu ve yazısının olduğu tıklanabilir alan
        // Resimdeki konuma göre yaklaşık koordinatlar (Ekranın üst-orta sağ bölgesi)
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 180.dp, start = 100.dp) // MentorNet ikonunun yaklaşık yeri
                .size(width = 90.dp, height = 110.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null // Tıklama efektini gizleyerek daha doğal durmasını sağlarız
                ) {
                    navController.navigate("splash") {
                        popUpTo("launcher") { inclusive = true }
                    }
                }
        )
    }
}
