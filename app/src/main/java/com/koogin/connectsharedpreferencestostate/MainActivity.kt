package com.koogin.connectsharedpreferencestostate

import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.koogin.connectsharedpreferencestostate.ui.theme.ConnectSharedPreferencesToStateTheme

class MainActivity : ComponentActivity() {
    private var showTitle : MutableState<Boolean> = mutableStateOf(false)

    private val sharedPreferences by lazy {
        getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE)
    }

    private val sharedPreferencesListener = OnSharedPreferenceChangeListener { sPrefs, key ->
        if (key == SHOW_TITLE_PREF_KEY) {
            showTitle.value = sPrefs.getBoolean(SHOW_TITLE_PREF_KEY, false)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences.registerOnSharedPreferenceChangeListener(sharedPreferencesListener)

        enableEdgeToEdge()
        setContent {
            ConnectSharedPreferencesToStateTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding).fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        Title(showTitle.value)
                        Box(modifier = Modifier.padding(16.dp))
                        Switch(checked = showTitle.value, onCheckedChange = {
                            sharedPreferences.edit().putBoolean(SHOW_TITLE_PREF_KEY, it).commit()
                        })
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        showTitle.value = sharedPreferences.getBoolean(SHOW_TITLE_PREF_KEY, false)
    }

    companion object {
        const val SHARED_PREFS_NAME = "LittleLemon"
        const val SHOW_TITLE_PREF_KEY = "showTitle"
    }
}

@Composable
fun Title(showTitle: Boolean) {
    if (showTitle) {
        Text(
            "LittleLemon",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
        )

    }
}