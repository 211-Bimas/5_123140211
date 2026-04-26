package org.example.project

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.launch

import org.example.project.components.BottomNav
import org.example.project.navigation.BottomNavItem
import org.example.project.navigation.Screen
import org.example.project.ui.*
import org.example.project.viewmodel.ProfileViewModel
import org.example.project.viewmodel.NotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val navController = rememberNavController()

    // ViewModels
    val profileViewModel = remember { ProfileViewModel() }
    val profileUiState by profileViewModel.uiState.collectAsState()
    val notesViewModel = remember { NotesViewModel() }
    val notesList by notesViewModel.notes.collectAsState()

    val colorScheme = if (profileUiState.isDarkMode) darkColorScheme() else lightColorScheme()

    // SETUP DRAWER & COROUTINE
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // SETUP CURRENT ROUTE (Untuk mengubah judul TopBar secara dinamis)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    MaterialTheme(colorScheme = colorScheme) {
        // 1. BUNGKUS DENGAN NAVIGATION DRAWER
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    Spacer(Modifier.height(24.dp))
                    Text(
                        text = "Menu Navigasi",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    HorizontalDivider()
                    Spacer(Modifier.height(8.dp))

                    // Item di dalam Drawer
                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Info, contentDescription = "Tentang") },
                        label = { Text("Tentang Aplikasi") },
                        selected = currentRoute == Screen.About.route,
                        onClick = {
                            navController.navigate(Screen.About.route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                            scope.launch { drawerState.close() } // Tutup drawer setelah diklik
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        ) {
            // 2. SCAFFOLD UTAMA
            Scaffold(
                topBar = {
                    // Tampilkan TopBar hanya di layar utama (Notes, Fav, Profile, About)
                    val isTopLevel = currentRoute in listOf(
                        BottomNavItem.Notes.route,
                        BottomNavItem.Favorites.route,
                        BottomNavItem.Profile.route,
                        Screen.About.route
                    )

                    if (isTopLevel) {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(
                                    text = when(currentRoute) {
                                        BottomNavItem.Notes.route -> "Catatan Ku"
                                        BottomNavItem.Favorites.route -> "Favorit"
                                        BottomNavItem.Profile.route -> "Profil Mahasiswa"
                                        Screen.About.route -> "Tentang"
                                        else -> "Aplikasi"
                                    },
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            navigationIcon = {
                                IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                    Icon(Icons.Default.Menu, contentDescription = "Buka Menu")
                                }
                            },
                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                containerColor = MaterialTheme.colorScheme.background
                            )
                        )
                    }
                },
                bottomBar = { BottomNav(navController = navController) }
            ) { paddingValues ->

                // 3. NAVHOST
                NavHost(
                    navController = navController,
                    startDestination = BottomNavItem.Notes.route,
                    modifier = Modifier.padding(paddingValues)
                ) {
                    // --- BOTTOM TABS ---
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

                    // --- DRAWER SCREEN ---
                    composable(Screen.About.route) {
                        AboutScreen()
                    }

                    // --- DETAIL & EDIT SCREENS ---
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
}