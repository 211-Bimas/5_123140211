package org.example.project.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// --- 1. LAYAR DAFTAR CATATAN (DENGAN FAB) ---
@Composable
fun NoteListScreen(
    onNoteClick: (Int) -> Unit,
    onAddClick: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Catatan")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).fillMaxSize()) {
            items(5) { index ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { onNoteClick(index) } // Pindah ke Detail passing ID
                ) {
                    Text("Catatan Dummy ID: $index", modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}

// --- 2. LAYAR FAVORIT ---
@Composable
fun FavoritesScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Daftar Catatan Favorit")
    }
}

// --- 3. LAYAR DETAIL CATATAN ---
@Composable
fun NoteDetailScreen(
    noteId: Int,
    onBack: () -> Unit,
    onEdit: (Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Detail Catatan ID: $noteId", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onEdit(noteId) }) { Text("Edit Catatan Ini") }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onBack) { Text("Kembali (Back)") }
    }
}

// --- 4. LAYAR TAMBAH CATATAN ---
@Composable
fun AddNoteScreen(onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Layar Tambah Catatan Baru", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBack) { Text("Simpan & Kembali") }
    }
}

// --- 5. LAYAR EDIT CATATAN ---
@Composable
fun EditNoteScreen(noteId: Int, onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Layar Edit Catatan ID: $noteId", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBack) { Text("Simpan Perubahan & Kembali") }
    }
}