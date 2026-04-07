package org.example.project

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

import org.example.project.components.BottomNav
import org.example.project.navigation.BottomNavItem
import org.example.project.navigation.Screen
import org.example.project.ui.*
import org.example.project.viewmodel.ProfileViewModel

@Composable
fun App() {
    // 1. Inisialisasi NavController
    val navController = rememberNavController()

    // 2. Inisialisasi ViewModel Tugas 4 kamu (ProfileViewModel)
    val viewModel = remember { ProfileViewModel() }
    val uiState by viewModel.uiState.collectAsState()

    // Tema dinamis dari Tugas 4
    val colorScheme = if (uiState.isDarkMode) darkColorScheme() else lightColorScheme()

    MaterialTheme(colorScheme = colorScheme) {
        // 3. Pasang Scaffold dengan BottomBar
        Scaffold(
            bottomBar = { BottomNav(navController = navController) }
        ) { paddingValues ->
            // 4. NavHost pengatur navigasi
            NavHost(
                navController = navController,
                startDestination = BottomNavItem.Notes.route,
                modifier = Modifier.padding(paddingValues)
            ) {
                // =============== BOTTOM NAV TAB TABS ===============
                composable(BottomNavItem.Notes.route) {
                    NoteListScreen(
                        onNoteClick = { noteId ->
                            navController.navigate(Screen.NoteDetail.createRoute(noteId))
                        },
                        onAddClick = {
                            navController.navigate(Screen.AddNote.route)
                        }
                    )
                }

                composable(BottomNavItem.Favorites.route) {
                    FavoritesScreen()
                }

                composable(BottomNavItem.Profile.route) {
                    // Masukkan kode ProfileScreen dari tugas minggu 4!
                    ProfileScreen(
                        uiState = uiState,
                        onEditProfile = { newName, newBio ->
                            viewModel.updateProfile(newName, newBio)
                        },
                        onToggleDarkMode = { isDark ->
                            viewModel.toggleDarkMode(isDark)
                        }
                    )
                }

                // =============== SCREEN LAINNYA ===============
                // Navigasi dengan arguments 'noteId'
                composable(
                    route = Screen.NoteDetail.route,
                    arguments = listOf(navArgument("noteId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0
                    NoteDetailScreen(
                        noteId = noteId,
                        onBack = { navController.popBackStack() }, // Back Button Proper
                        onEdit = { id ->
                            navController.navigate(Screen.EditNote.createRoute(id))
                        }
                    )
                }

                composable(Screen.AddNote.route) {
                    AddNoteScreen(onBack = { navController.popBackStack() })
                }

                composable(
                    route = Screen.EditNote.route,
                    arguments = listOf(navArgument("noteId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0
                    EditNoteScreen(
                        noteId = noteId,
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}