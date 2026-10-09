package com.example.homework3


import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                MainScreen(
                    onOpenSecondActivity = { text ->
                        val intent = Intent(
                            this@MainActivity,
                            SecondActivity::class.java
                        ).apply {
                            putExtra(SecondActivity.EXTRA_TEXT, text)
                        }

                        startActivity(intent)
                    },
                    onCallFriend = { phone ->
                        val normalizedPhone =
                            phone.replace(Regex("[ ()-]"), "")

                        val intent = Intent(
                            Intent.ACTION_DIAL,
                            Uri.parse("tel:$normalizedPhone")
                        )

                        try {
                            startActivity(intent)
                        } catch (e: ActivityNotFoundException) {
                            Toast.makeText(
                                this@MainActivity,
                                "Приложение для звонков не найдено",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    onShareText = { text ->
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, text)
                        }

                        startActivity(
                            Intent.createChooser(
                                intent,
                                "Поделиться через…"
                            )
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun MainScreen(
    onOpenSecondActivity: (String) -> Unit,
    onCallFriend: (String) -> Unit,
    onShareText: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
                error = null
            },
            label = {
                Text("Текст или номер телефона")
            },
            modifier = Modifier.fillMaxWidth(),
            isError = error != null,
            supportingText = {
                error?.let { Text(it) }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            )
        )

        Button(
            onClick = {
                if (text.isBlank()) {
                    error = "Введите текст"
                } else {
                    onOpenSecondActivity(text.trim())
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Открыть вторую Activity")
        }

        Button(
            onClick = {
                val phone = text.trim()
                val phonePattern = Regex("""\+?[0-9 ()-]{5,20}""")

                if (!phone.matches(phonePattern)) {
                    error = "Введите корректный номер телефона"
                } else {
                    onCallFriend(phone)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Позвонить другу")
        }

        Button(
            onClick = {
                if (text.isBlank()) {
                    error = "Введите текст для отправки"
                } else {
                    onShareText(text.trim())
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Поделиться текстом")
        }
    }
}