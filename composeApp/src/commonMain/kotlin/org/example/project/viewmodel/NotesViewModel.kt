package org.example.project.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// 1. Struktur Data Catatan
data class Note(
    val id: Int,
    val title: String,
    val content: String,
    val isFavorite: Boolean = false
)

// 2. ViewModel untuk mengatur State Catatan
class NotesViewModel {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    private var nextId = 1 // Auto-increment ID

    fun addNote(title: String, content: String) {
        val newNote = Note(id = nextId++, title = title, content = content)
        _notes.update { currentNotes -> currentNotes + newNote }
    }

    fun updateNote(id: Int, title: String, content: String) {
        _notes.update { currentNotes ->
            currentNotes.map { if (it.id == id) it.copy(title = title, content = content) else it }
        }
    }

    fun toggleFavorite(id: Int) {
        _notes.update { currentNotes ->
            currentNotes.map { if (it.id == id) it.copy(isFavorite = !it.isFavorite) else it }
        }
    }

    fun getNoteById(id: Int): Note? {
        return _notes.value.find { it.id == id }
    }
}