package com.example.meowpoints

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.meowpoints.ui.theme.MeowPointsTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeowPointsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MeowPointsApp()
                }
            }
        }
    }
}
    @Composable
    fun MeowPointsApp() {
        val context = LocalContext.current
        var points by rememberSaveable { mutableIntStateOf(0) }
        val status = when {
            points < 0 -> "Rozrabiaka"
            points > 50 -> "Aniołek"
            else -> "Zwykły kotek"
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 32.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = stringResource(id = R.string.points_label), fontSize = 20.sp)
                Text(
                    text = " $points",
                    fontSize = 40.sp,
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Text(text = "Status: $status", modifier = Modifier.padding(16.dp))

            Spacer(modifier = Modifier.height(24.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(onClick = {
                    points += 10
                    if (points >= 100) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.toast_angel),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }) {
                    Text(text = stringResource(id = R.string.good_behavior))
                }
                Button(
                    onClick = { points -= 10 },
                    colors = ButtonDefaults.buttonColors(contentColor = MaterialTheme.colorScheme.error)
                ) {
                    Text(text = stringResource(id = R.string.bad_behavior))
                }
            }
        }
    }





