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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
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
        var behaviorText by rememberSaveable { mutableStateOf("") }
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
            OutlinedTextField(
                value = behaviorText,
                onValueChange = {behaviorText = it},
                label = {Text(stringResource(R.string.event_name_hint))},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(24.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(onClick = {
                    if (behaviorText.isNotBlank()) {
                        points += 10
                        behaviorText = ""
                        if (points >= 100) {
                            Toast.makeText(context, context.getString(R.string.toast_angel), Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "Wpisz co zrobił kot!", Toast.LENGTH_SHORT).show()
                    }
                }) {
                    Text(text = stringResource(id = R.string.good_behavior))
                }
                Button(
                    onClick = {
                        if (behaviorText.isNotBlank()) {
                            points -= 10
                            behaviorText = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text(text = stringResource(id = R.string.bad_behavior))
                }
            }
        }
    }