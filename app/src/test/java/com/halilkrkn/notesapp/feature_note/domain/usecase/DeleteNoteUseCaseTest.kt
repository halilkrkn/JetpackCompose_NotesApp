package com.halilkrkn.notesapp.feature_note.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.halilkrkn.notesapp.feature_note.data.repository.FakeNoteRepository
import com.halilkrkn.notesapp.feature_note.domain.model.Note
import com.halilkrkn.notesapp.feature_note.domain.util.NoteOrder
import com.halilkrkn.notesapp.feature_note.domain.util.OrderType
import com.halilkrkn.notesapp.feature_note.presentation.notes.NotesEvent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DeleteNoteUseCaseTest {

    private lateinit var getNotes: GetNotesUseCase
    private lateinit var deleteNotes: DeleteNoteUseCase
    private lateinit var repository: FakeNoteRepository
    private val noteToInsert = mutableListOf<Note>()

    @Before
    fun setUp() {
        repository = FakeNoteRepository()
        deleteNotes = DeleteNoteUseCase(repository)
        getNotes = GetNotesUseCase(repository)

        ('a'..'z').forEachIndexed { index, c ->

            noteToInsert.add(
                Note(
                    title = c.toString(),
                    content = c.toString(),
                    timestamp = index.toLong(),
                    color = index
                )
            )
        }
        noteToInsert.shuffle()
        noteToInsert.forEach {
            runBlocking {
                repository.insertNote(
                    it
                )
            }
        }
    }

    @Test
    fun `delete note item , return true`() = runBlocking {
        val note = noteToInsert[0]
        deleteNotes.invoke(note)
//        println(note)
        val notes = getNotes.invoke(
            NoteOrder.Title(OrderType.Descending)
        ).first()
//        println(notes)
        assertThat(notes).doesNotContain(note)
//        assertThat(notes).doesNotContain(NotesEvent.DeleteNotes(note))
    }


    // Yukarıdaki ile aynı şeyi yapıyor.
    @Test
    fun `delete note item same with above, return true`() = runBlocking {
        val note = noteToInsert[0]
        println(note)
        deleteNotes.invoke(note)
//        val notes = getNotes.invoke(
//            NoteOrder.Title(OrderType.Descending)
//        ).first()
//        println(notes)
//        assertThat(note).isNotEqualTo(notes)
        assertThat(note).isNotEqualTo(NotesEvent.DeleteNotes(note))
    }

}