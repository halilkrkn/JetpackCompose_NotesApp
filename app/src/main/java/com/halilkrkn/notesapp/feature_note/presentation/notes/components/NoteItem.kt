package com.halilkrkn.notesapp.feature_note.presentation.notes.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.halilkrkn.notesapp.core.util.TestTags
import com.halilkrkn.notesapp.feature_note.domain.model.Note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteItem(
    note: Note,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 10.dp,
    cutCornerSize: Dp = 30.dp,
    onDeleteClick: () -> Unit,
) {

    val dismissState = rememberDismissState(
        confirmValueChange = {
            if (it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd) {
                onDeleteClick()
                true
            } else false
        }, positionalThreshold = { 150.dp.toPx() }
    )

    SwipeToDismiss(
        state = dismissState,
        modifier = Modifier,
        background = {
            DismissBackground(
                dismissState = dismissState
            )
        },
        directions = setOf(DismissDirection.EndToStart),
        dismissContent = {
            Box(
                modifier = modifier
                    .testTag(TestTags.NOTE_ITEM)
            ) {

                // Not için oluşturulan şekil(Dikdörtgen) ve kesme köşesi ile not'un şeklini oluşturuyoruz.
                Canvas(modifier = Modifier.matchParentSize()) {

                    // kesme köşesi ile notun sağ köşesini kesiyoruz
                    val clipPath = Path().apply {
                        lineTo(size.width - cutCornerSize.toPx(), 0f)
                        lineTo(size.width, cutCornerSize.toPx())
                        lineTo(size.width, size.height)
                        lineTo(0f, size.height)
                        close()
                    }

                    // Dikdörtgen olan not'un şeklini oluşturuyoruz.
                    clipPath(clipPath) {
                        drawRoundRect(
                            color = Color(note.color),
                            size = size,
                            cornerRadius = CornerRadius(cornerRadius.toPx())
                        )
                        drawRoundRect(
                            color = Color(
                                ColorUtils.blendARGB(note.color, 0x000000, 0.2f)
                            ),
                            topLeft = Offset(size.width - cutCornerSize.toPx(), -100f),
                            size = Size(cutCornerSize.toPx() + 100f, cutCornerSize.toPx() + 100f),
                            cornerRadius = CornerRadius(cornerRadius.toPx())
                        )
                    }
                }

                // Oluşturulan Not'un içeriği
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .padding(end = 32.dp)
                        .fillMaxSize()
                ) {
                    Text(
                        text = note.title,
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        // Burada ne yapmaya çalışıyoruz?
                        // TextOverflow.Ellipsis: Text'in sonuna 3 nokta koyuyor.
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = note.content,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 10,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                IconButton(
                    onClick = {
                        onDeleteClick()
                    },
                    modifier = Modifier.align(
                        Alignment.BottomEnd
                    )   // Not'un sağ alt köşesine konumlandırıyoruz.
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete note"
                    )
                }
            }
        }
    )



}