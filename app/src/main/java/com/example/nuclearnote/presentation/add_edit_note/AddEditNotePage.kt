package com.example.nuclearnote.presentation.add_edit_note

import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.nuclearnote.R
import com.example.nuclearnote.domain.model.Note
import com.example.nuclearnote.presentation.add_edit_note.components.TransparentHintTextField
import com.example.nuclearnote.presentation.util.StatusBarColor
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddEditNotePage(
    navController: NavController,
    noteColor: Int,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {
    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value

    val attachmentState = viewModel.attachments.value

    var job: Job? = null
    val scope = rememberCoroutineScope()

    val warningText = remember {
        mutableStateOf("")
    }

    val isSnackBarVisible = remember {
        mutableStateOf(false)
    }

    val isNoteBackgroundAnimation = remember {
        Animatable(
            Color(if (noteColor != -1) noteColor else viewModel.noteColor.value)
        )
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditNoteViewModel.UiEvent.ShowSnackBar -> {
                    warningText.value = event.message
                    isSnackBarVisible.value = true
                    job?.cancel()
                    job = scope.launch {
                        delay(2000)
                        isSnackBarVisible.value = false
                    }
                }

                is AddEditNoteViewModel.UiEvent.SaveNote -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(isNoteBackgroundAnimation.value),
        contentAlignment = Alignment.Center
    ) {

        StatusBarColor(color = isNoteBackgroundAnimation.value.toArgb())

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(isNoteBackgroundAnimation.value)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Note.noteColors.forEach { color ->
                    val colorInt = color.toArgb()
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 3.dp,
                                color = if (viewModel.noteColor.value == colorInt) {
                                    Color.Black
                                } else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    isNoteBackgroundAnimation.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                viewModel.onEvent(AddEditNoteEvent.ChangeColor(colorInt))
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = titleState.text,
                hint = titleState.hint,
                onValueChange = { changedValue ->
                    viewModel.onEvent(AddEditNoteEvent.EnteredTitle(changedValue))
                },
                onFocusChange = { focusState ->
                    viewModel.onEvent(AddEditNoteEvent.ChangeTitleFocus(focusState))
                },
                isHintVisible = titleState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = contentState.text,
                hint = contentState.hint,
                onValueChange = { changedValue ->
                    viewModel.onEvent(AddEditNoteEvent.EnteredContent(changedValue))
                },
                onFocusChange = { focusState ->
                    viewModel.onEvent(AddEditNoteEvent.ChangeContentFocus(focusState))
                },
                isHintVisible = contentState.isHintVisible,
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier.fillMaxHeight()
            )
        }

        FloatingActionButton(
            onClick = {
                viewModel.onEvent(AddEditNoteEvent.SaveNote)
            },
            backgroundColor = MaterialTheme.colors.primary,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Default.Done, contentDescription = "Save note")
        }

        FloatingActionButton(
            onClick = {
                viewModel.onEvent(AddEditNoteEvent.SaveNote)
            },
            backgroundColor = MaterialTheme.colors.primary,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.upload_image_property),
                contentDescription = "Upload Image"
            )
        }

        AnimatedVisibility(
            visible = isSnackBarVisible.value,
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 80.dp)
        ) {

            Snackbar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .background(Color.Black, shape = RoundedCornerShape(5.dp))
                    .zIndex(1000f)
                    .clickable {
                        isSnackBarVisible.value = false
                    }
            ) {

                Text(
                    text = warningText.value,
                    color = Color.White,
                    fontSize = TextUnit(value = 16f, TextUnitType.Sp),
                    fontStyle = FontStyle.Normal,
                    fontFamily = FontFamily.Default
                )

            }


        }

    }

}