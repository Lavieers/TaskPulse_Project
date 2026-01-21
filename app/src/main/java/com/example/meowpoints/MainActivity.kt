package com.example.meowpoints

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.meowpoints.ui.theme.MeowPointsTheme
import kotlinx.coroutines.launch
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeowPointsTheme {
                val navController = rememberNavController()
                val context = LocalContext.current
                val db = remember { CatDatabase.getDatabase(context) }
                val dao = db.catDao()

                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") {
                            HomeScreen(
                                dao,
                                onNavigateToHistory = { navController.navigate("history") },
                                onLanguageChange = { lang -> updateResource(lang) }
                            )
                        }
                        composable("history") {
                            HistoryScreen(dao, onBack = { navController.popBackStack() })
                        }
                    }
                }
            }
        }
    }

    private fun updateResource(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources = resources
        val configuration = resources.configuration
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
        recreate()
    }
}

@Composable
fun HomeScreen(dao: CatDao, onNavigateToHistory: () -> Unit, onLanguageChange: (String) -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val totalPoints by dao.getTotalPoints().collectAsState(initial = 0)
    var behaviorText by rememberSaveable { mutableStateOf("") }

    val points = totalPoints ?: 0

    val statusText = when {
        points < 0 -> stringResource(R.string.status_naughty)
        points > 50 -> stringResource(R.string.status_angel)
        else -> stringResource(R.string.status_normal)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            TextButton(onClick = { onLanguageChange("pl") }) { Text("PL") }
            TextButton(onClick = { onLanguageChange("en") }) { Text("EN") }
            TextButton(onClick = { onLanguageChange("it") }) { Text("IT") }
        }

        Text(text = stringResource(R.string.app_name), style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(20.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = stringResource(R.string.points_label), fontSize = 20.sp)
            Text(text = " $points", fontSize = 40.sp, color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.displayMedium)
        }

        Text(text = "${stringResource(R.string.status_label)} $statusText", modifier = Modifier.padding(16.dp))

        OutlinedTextField(
            value = behaviorText,
            onValueChange = { behaviorText = it },
            label = { Text(stringResource(R.string.event_name_hint)) },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = {
                if (behaviorText.isNotBlank()) {
                    scope.launch {
                        dao.insertEvent(CatEvent(title = behaviorText, points = 10, timestamp = System.currentTimeMillis()))
                        behaviorText = ""
                        if (points + 10 >= 100) {
                            Toast.makeText(context, context.getString(R.string.toast_angel), Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(context, context.getString(R.string.error_empty), Toast.LENGTH_SHORT).show()
                }
            }) { Text(stringResource(R.string.good_behavior)) }

            Button(
                onClick = {
                    if (behaviorText.isNotBlank()) {
                        scope.launch {
                            dao.insertEvent(CatEvent(title = behaviorText, points = -10, timestamp = System.currentTimeMillis()))
                            behaviorText = ""
                        }
                    } else {
                        Toast.makeText(context, context.getString(R.string.error_empty), Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) { Text(stringResource(R.string.bad_behavior)) }
        }

        Spacer(modifier = Modifier.height(32.dp))

        TextButton(onClick = onNavigateToHistory) {
            Text(stringResource(R.string.history_button))
        }
    }
}

@Composable
fun HistoryScreen(dao: CatDao, onBack: () -> Unit) {
    val events by dao.getAllEvents().collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            TextButton(
                onClick = { onBack() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Text(
                    text = "< ${stringResource(R.string.back_button)}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Text(
                text = stringResource(R.string.history_title),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.align(Alignment.Center)
            )
            IconButton(
                onClick = {
                    scope.launch {
                        dao.deleteAllEvents()
                    }
                },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Text(text = "❌", fontSize = 18.sp)
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(events) { event ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = event.title, modifier = Modifier.weight(1f))
                        Text(
                            text = if (event.points > 0) "+${event.points}" else "${event.points}",
                            color = if (event.points > 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }
}