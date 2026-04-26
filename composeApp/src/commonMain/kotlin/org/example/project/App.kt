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
import org.example.project.viewmodel.NotesViewModel // <-- IMPORT BARU

@Composable
fun App() {
    val navController = rememberNavController()

    // ViewModel Profil (Dari Minggu 4)
    val profileViewModel = remember { ProfileViewModel() }
    val profileUiState by profileViewModel.uiState.collectAsState()

    // ViewModel Notes (BARU)
    val notesViewModel = remember { NotesViewModel() }
    val notesList by notesViewModel.notes.collectAsState()

    val colorScheme = if (profileUiState.isDarkMode) darkColorScheme() else lightColorScheme()

    MaterialTheme(colorScheme = colorScheme) {
        Scaffold(
            bottomBar = { BottomNav(navController = navController) }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = BottomNavItem.Notes.route,
                modifier = Modifier.padding(paddingValues)
            ) {
                // =============== BOTTOM TABS ===============
                composable(BottomNavItem.Notes.route) {
                    NoteListScreen(
                        notes = notesList,
                        onNoteClick = { id -> navController.navigate(Screen.NoteDetail.createRoute(id)) },
                        onAddClick = { navController.navigate(Screen.AddNote.route) },
                        onToggleFav = { id -> notesViewModel.toggleFavorite(id) }
                    )
                }
                composable(BottomNavItem.Favorites.route) {
                    FavoritesScreen(
                        notes = notesList,
                        onNoteClick = { id -> navController.navigate(Screen.NoteDetail.createRoute(id)) },
                        onToggleFav = { id -> notesViewModel.toggleFavorite(id) }
                    )
                }
                composable(BottomNavItem.Profile.route) {
                    ProfileScreen(
                        uiState = profileUiState,
                        onEditProfile = { name, bio -> profileViewModel.updateProfile(name, bio) },
                        onToggleDarkMode = { isDark -> profileViewModel.toggleDarkMode(isDark) }
                    )
                }

                // =============== SCREEN DETAIL & EDIT ===============
                composable(
                    route = Screen.NoteDetail.route,
                    arguments = listOf(navArgument("noteId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val noteId = backStackEntry.arguments?.getInt("noteId") ?: -1
                    NoteDetailScreen(
                        note = notesViewModel.getNoteById(noteId),
                        onBack = { navController.popBackStack() },
                        onEdit = { id -> navController.navigate(Screen.EditNote.createRoute(id)) }
                    )
                }

                composable(Screen.AddNote.route) {
                    AddNoteScreen(
                        onSave = { title, content -> notesViewModel.addNote(title, content) },
                        onBack = { navController.popBackStack() }
                    )
                }

                composable(
                    route = Screen.EditNote.route,
                    arguments = listOf(navArgument("noteId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val noteId = backStackEntry.arguments?.getInt("noteId") ?: -1
                    EditNoteScreen(
                        note = notesViewModel.getNoteById(noteId),
                        onSave = { id, title, content -> notesViewModel.updateNote(id, title, content) },
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}