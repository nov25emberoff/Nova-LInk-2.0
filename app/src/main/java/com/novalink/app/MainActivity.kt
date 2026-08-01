package com.novalink.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.novalink.app.data.AppDatabase
import com.novalink.app.data.Repository
import com.novalink.app.navigation.NovaLinkNavGraph
import com.novalink.app.ui.theme.NovaLinkTheme
import com.novalink.app.viewmodel.ChatViewModel
import com.novalink.app.viewmodel.ChatViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = AppDatabase.getInstance(applicationContext)
        val repository = Repository(db)

        setContent {
            NovaLinkTheme {
                val viewModel: ChatViewModel = viewModel(factory = ChatViewModelFactory(repository))
                NovaLinkNavGraph(viewModel = viewModel)
            }
        }
    }
}
