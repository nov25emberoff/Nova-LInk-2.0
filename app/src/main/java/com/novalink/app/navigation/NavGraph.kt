package com.novalink.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.novalink.app.ui.ChatListScreen
import com.novalink.app.ui.ChatScreen
import com.novalink.app.viewmodel.ChatViewModel
import java.net.URLDecoder
import java.net.URLEncoder

@Composable
fun NovaLinkNavGraph(viewModel: ChatViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "chatList") {
        composable("chatList") {
            ChatListScreen(
                viewModel = viewModel,
                onChatClick = { id, name ->
                    val encoded = URLEncoder.encode(name, "UTF-8")
                    navController.navigate("chat/$id/$encoded")
                }
            )
        }
        composable(
            route = "chat/{chatId}/{chatName}",
            arguments = listOf(
                navArgument("chatId") { type = NavType.LongType },
                navArgument("chatName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val chatId = backStackEntry.arguments?.getLong("chatId") ?: 0L
            val chatNameEncoded = backStackEntry.arguments?.getString("chatName") ?: ""
            val chatName = URLDecoder.decode(chatNameEncoded, "UTF-8")
            ChatScreen(
                viewModel = viewModel,
                chatId = chatId,
                chatName = chatName,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
