package org.example.project.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.example.project.viewmodel.Note

// --- 1. LAYAR DAFTAR CATATAN ---
@Composable
fun NoteListScreen(
    notes: List<Note>,
    onNoteClick: (Int) -> Unit,
    onAddClick: () -> Unit,
    onToggleFav: (Int) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onAddClick,
                icon = { Icon(Icons.Default.Add, "Tambah") },
                text = { Text("Catatan Baru") },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    ) { padding ->
        if (notes.isEmpty()) {
            EmptyStateView(icon = Icons.Default.Create, message = "Belum ada catatan.\nYuk, tulis sesuatu!")
        } else {
            LazyColumn(
                modifier = Modifier.padding(padding).fillMaxSize(),
                contentPadding = PaddingValues(top = 8.dp, bottom = 80.dp) // Spasi bawah biar ga ketutup FAB
            ) {
                items(notes) { note -> NoteCard(note, onNoteClick, onToggleFav) }
            }
        }
    }
}

// --- 2. LAYAR FAVORIT ---
@Composable
fun FavoritesScreen(
    notes: List<Note>,
    onNoteClick: (Int) -> Unit,
    onToggleFav: (Int) -> Unit
) {
    val favNotes = notes.filter { it.isFavorite }
    if (favNotes.isEmpty()) {
        EmptyStateView(icon = Icons.Default.FavoriteBorder, message = "Belum ada catatan favorit.\nTekan ikon hati untuk menambahkan!")
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(favNotes) { note -> NoteCard(note, onNoteClick, onToggleFav) }
        }
    }
}

// --- KOMPONEN EMPTY STATE (BARU) ---
@Composable
fun EmptyStateView(icon: androidx.compose.ui.graphics.vector.ImageVector, message: String) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Kosong",
            modifier = Modifier.size(72.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}

// --- KOMPONEN KARTU CATATAN (DIPERBAGUS) ---
@Composable
fun NoteCard(note: Note, onClick: (Int) -> Unit, onToggleFav: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick(note.id) },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = note.title,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = note.content,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                )
            }
            IconButton(onClick = { onToggleFav(note.id) }) {
                Icon(
                    imageVector = if (note.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (note.isFavorite) Color(0xFFE53935) else MaterialTheme.colorScheme.onSurfaceVariant // Warna merah cerah
                )
            }
        }
    }
}

// --- 3. LAYAR TAMBAH CATATAN (DIPERBAGUS) ---
@Composable
fun AddNoteScreen(onSave: (String, String) -> Unit, onBack: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Tulis Catatan Baru", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Judul Catatan") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Isi Catatan...") },
            modifier = Modifier.fillMaxWidth().weight(1f),
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(onClick = onBack, modifier = Modifier.padding(end = 8.dp)) { Text("Batal") }
            Button(onClick = { if (title.isNotBlank()) { onSave(title, content); onBack() } }) { Text("Simpan") }
        }
    }
}

// --- 4. LAYAR DETAIL CATATAN (DIPERBAGUS) ---
@Composable
fun NoteDetailScreen(note: Note?, onBack: () -> Unit, onEdit: (Int) -> Unit) {
    if (note == null) return
    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text(note.title, style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.ExtraBold)
        Spacer(Modifier.height(24.dp))

        // Membungkus teks konten agar lebih enak dibaca
        Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
            Text(
                text = note.content,
                style = MaterialTheme.typography.bodyLarge,
                lineHeight = MaterialTheme.typography.bodyLarge.lineHeight * 1.5 // Spasi antar baris
            )
        }

        Divider(modifier = Modifier.padding(vertical = 16.dp))
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(onClick = onBack) { Text("Kembali") }
            Button(onClick = { onEdit(note.id) }) { Text("Edit Catatan") }
        }
    }
}

// --- 5. LAYAR EDIT CATATAN (DIPERBAGUS) ---
@Composable
fun EditNoteScreen(note: Note?, onSave: (Int, String, String) -> Unit, onBack: () -> Unit) {
    if (note == null) return
    var title by remember { mutableStateOf(note.title) }
    var content by remember { mutableStateOf(note.content) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Edit Catatan", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Judul Catatan") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Isi Catatan...") },
            modifier = Modifier.fillMaxWidth().weight(1f),
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(onClick = onBack, modifier = Modifier.padding(end = 8.dp)) { Text("Batal") }
            Button(onClick = { if (title.isNotBlank()) { onSave(note.id, title, content); onBack() } }) { Text("Simpan Perubahan") }
        }
    }
}