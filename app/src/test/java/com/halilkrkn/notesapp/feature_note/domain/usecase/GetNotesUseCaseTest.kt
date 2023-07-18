package com.halilkrkn.notesapp.feature_note.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.halilkrkn.notesapp.feature_note.data.repository.FakeNoteRepository
import com.halilkrkn.notesapp.feature_note.domain.model.Note
import com.halilkrkn.notesapp.feature_note.domain.util.NoteOrder
import com.halilkrkn.notesapp.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNotesUseCaseTest {

    private lateinit var getNotes: GetNotesUseCase
    private lateinit var fakeNoteRepository: FakeNoteRepository
    private val noteToInsert = mutableListOf<Note>()

    // Bu fonksiyonda neden böyle bir şey yapıldı anlamadım.
    // Bu fonksiyonun içindeki kodlar, FakeNoteRepository() içindeki kodlarla aynı.
    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        getNotes = GetNotesUseCase(fakeNoteRepository)    // FakeNoteRepository() is a fake repository


        // Burada neden aşağıdaki gibi bir şey yapıldı anlamadım.
        // 26 tane Note objesi oluşturuldu ve bunlar noteToInsert listesine eklendi.
        // Sonra da bu liste karıştırıldı ve her bir elemanı için insertNote() fonksiyonu çağırıldı.
        // insertNote() fonksiyonu FakeNoteRepository() içindeki insertNote() fonksiyonu.
        // insertNote() fonksiyonu FakeNoteRepository() içindeki notes listesine note ekliyor.
        // Yani burada 26 tane note eklenmiş oluyor.
        // Ama bu 26 tane note, FakeNoteRepository() içindeki notes listesine ekleniyor.
        // getNotes() fonksiyonu FakeNoteRepository() içindeki notes listesini döndürüyor.
        // Yani getNotes() fonksiyonu 26 tane note döndürüyor.
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
                fakeNoteRepository.insertNote(
                    it
                )
            }
        }
    }


    // Burada notları baştan sona doğru sıralıyoruz.
    // Yani ilk notun title'ı 'a' ve son notun title'ı 'z' oluyor.
    // Bu yüzden ilk notun title'ı son notun title'ından küçük olmalı.
    // Bu yüzden assertThat(notes[i].title).isLessThan(notes[i + 1].title) yazıyoruz.
    // Eğer ilk notun title'ı son notun title'ından küçük değilse, test başarısız olur.
    @Test
    fun `Order notes by title ascending, return list of notes`() = runBlocking {
        val notes = getNotes.invoke(noteOrder = NoteOrder.Title(OrderType.Ascending)).first()
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].title).isLessThan(notes[i + 1].title)
        }
    }

    @Test
    fun `Order notes by title descending, return list of notes`() = runBlocking {
        val notes = getNotes.invoke(noteOrder = NoteOrder.Title(OrderType.Descending)).first()
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].title).isGreaterThan(notes[i + 1].title)
        }
    }

    @Test
    fun `Order notes by date ascending, return list of notes`() = runBlocking {
        val notes = getNotes.invoke(noteOrder = NoteOrder.Date(OrderType.Ascending)).first()
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].timestamp).isLessThan(notes[i + 1].timestamp)
        }
    }

    @Test
    fun `Order notes by date descending, return list of notes`() = runBlocking {
        val notes = getNotes.invoke(noteOrder = NoteOrder.Date(OrderType.Descending)).first()
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].timestamp).isGreaterThan(notes[i + 1].timestamp)
        }
    }

    @Test
    fun `Order notes by color ascending, return list of notes`() = runBlocking {
        val notes = getNotes.invoke(noteOrder = NoteOrder.Color(OrderType.Ascending)).first()
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].color).isLessThan(notes[i + 1].color)
        }
    }

    @Test
    fun `Order notes by color descending, return list of notes`() = runBlocking {
        val notes = getNotes.invoke(noteOrder = NoteOrder.Color(OrderType.Descending)).first()
        for (i in 0..notes.size - 2) {
            assertThat(notes[i].color).isGreaterThan(notes[i + 1].color)
        }
    }


}