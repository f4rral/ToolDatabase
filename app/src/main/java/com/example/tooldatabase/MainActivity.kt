package com.example.tooldatabase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.tooldatabase.navigation.NavigationGraph
import com.example.tooldatabase.ui.theme.ToolDatabaseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ToolDatabaseApplication.context.navController = rememberNavController()

            ToolDatabaseTheme {
                NavigationGraph(
                    navController = ToolDatabaseApplication.context.navController
                )
            }
        }
    }
}
